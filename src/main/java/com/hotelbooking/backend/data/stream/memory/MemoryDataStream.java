package com.hotelbooking.backend.data.stream.memory;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.EntityJoin;
import com.hotelbooking.backend.data.OperationResult;
import com.hotelbooking.backend.data.query.builder.QueryBuilder;
import com.hotelbooking.backend.data.query.condition.Condition;
import com.hotelbooking.backend.data.stream.DataStream;
import com.hotelbooking.backend.models.Booking;
import com.hotelbooking.backend.models.Room;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class MemoryDataStream implements DataStream {

    private final Map<Class<? extends DataEntity>, List<? extends DataEntity>> data = new HashMap<>();

    public MemoryDataStream() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1, 1));
        rooms.add(new Room(2, 1));
        this.data.put(Room.class, rooms);

        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(1, new Date(), new Date()));
        data.put(Booking.class, bookings);
    }


    @Override
    public void connect() {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public <T extends DataEntity> List<T> select(QueryBuilder<T> query) {
        return (List<T>) data.get(query.getSelectedEntity()).stream().map(d -> createJoin(d, query.getJoins()))
                .filter(d -> performCondition(query.getConditions(), d)).toList();
    }
    private boolean performCondition(Condition<Boolean> cond, DataEntity entity) {
        List<DataEntity> entities = new ArrayList<>();
        List<DataEntity> process = new ArrayList<>();
        process.add(entity);
        entities.add(entity);
        while(!process.isEmpty()) {
            DataEntity e = process.remove(0);
            for (Field f : Arrays.stream(e.getClass().getDeclaredFields()).filter(f -> f.isAnnotationPresent(EntityJoin.class)).toList()) {
                try {
                    if(f.get(e) != null) {
                        if (Collection.class.isAssignableFrom(f.getType())) {
                            for (DataEntity en : ((Collection<DataEntity>) f.get(e))) {
                                if (!entities.contains(en)) {
                                    process.add(en);
                                    entities.add(en);
                                }
                            }
                        } else {
                            if (!entities.contains((DataEntity) f.get(e))) {
                                process.add((DataEntity) f.get(e));
                                entities.add((DataEntity) f.get(e));
                            }
                        }
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
        }
        boolean result = false;
        for(DataEntity e : entities) {
            result = result || cond.execute(e);
        }
        return result;
    }

    @Override
    public boolean exists(QueryBuilder<? extends DataEntity> query) {
        return !select(query).isEmpty();
    }

    @Override
    public <T extends DataEntity> Optional<T> add(T data) {
        if(this.data.containsKey(data.getClass())) {
            ((List<T>)this.data.get(data.getClass())).add(data);
        } else {
            List<T> newData = new ArrayList<>();
            newData.add(data);
            this.data.put(data.getClass(), newData);
        }
        return Optional.of(data);
    }

    @Override
    public OperationResult remove(QueryBuilder<? extends DataEntity> query) {
        if(!this.data.containsKey(query.getSelectedEntity())) return OperationResult.NOT_FOUND;

        List<? extends DataEntity> list = this.data.get(query.getSelectedEntity());
        list.removeIf(d -> query.getConditions().execute(d));
        return OperationResult.DONE;
    }

    private <T extends DataEntity> T createJoin(T data, List<Field> joins) {
        //Get every join data concerning the current entity class
        List<Field> join = joins.stream().filter(j -> j.getDeclaringClass().equals(data.getClass())).toList();
        for(Field f : join) {
            //set the field accessible for later use
            f.setAccessible(true);
            //check if the field is actually a join field
            if(f.isAnnotationPresent(EntityJoin.class)) {
                EntityJoin entityJoin = f.getAnnotation(EntityJoin.class);
                String fromFieldName = entityJoin.ownerField();
                String otherFieldName = entityJoin.joinedEntityField();
                //if the field represents a one to N join
                if (DataEntity.class.isAssignableFrom(f.getType()) && this.data.containsKey(f.getType())) {
                    //extract the first data found that checks the join requirements (fields equality)
                    Optional<? extends DataEntity> next = this.data.get(f.getType()).stream()
                            .filter(d -> {
                                try {
                                    Object comparisonData = DataEntity.getDataFromFieldName(data, fromFieldName);
                                    return DataEntity.getDataFromFieldName(d, otherFieldName).equals(comparisonData);
                                } catch (Exception e) {
                                    return false;
                                }
                            }).findFirst();
                    DataEntity value = null;
                    if(next.isPresent()) {
                        //create the joins for the newly added entity
                        value = createJoin(next.get(), joins);
                    }
                    try {
                        //set the value of the entity join field
                        f.set(data, value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                //if the field represents a many to N field
                } else if(List.class.isAssignableFrom(f.getType())
                        && this.data.containsKey((Class<?>)((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0])) {
                    //get all entities that checks the join requirements (join fields equality)
                    List<? extends DataEntity> nextValues =
                            this.data.get((Class<?>)((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0])
                            .stream().filter(d -> {
                                try {
                                    Object comparisonData = DataEntity.getDataFromFieldName(data, fromFieldName);
                                    return DataEntity.getDataFromFieldName(d, otherFieldName).equals(comparisonData);
                                } catch (Exception e) {
                                    return false;
                                }
                            }).toList();
                    //for each of these fields' join values if they exist
                    for(DataEntity next : nextValues) {
                        createJoin(next, joins);
                    }
                    try {
                        //add these newly added entities to the entity join field value
                        f.set(data, nextValues);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return data;
    }
}
