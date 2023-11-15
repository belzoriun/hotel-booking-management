package com.hotelbooking.backend.data.stream.mock;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.EntityField;
import com.hotelbooking.backend.data.OperationResult;
import com.hotelbooking.backend.data.filter.QueryCommand;
import com.hotelbooking.backend.data.stream.DataStream;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class MockDataStream<T extends DataEntity> implements DataStream<T, MockQuery<T>> {

    private final List<T> data = new ArrayList<>();

    private Stream<T> executeFilterOnKey(T value) {
        Map<String, Object> key = value.getKeyDescriptor().ExtractKey(value);
        return data.stream().filter(f->{
            boolean found = false;
            for(Field field : f.getClass().getDeclaredFields()) {
                if(field.isAnnotationPresent(EntityField.class)
                        && key.containsKey(field.getAnnotation(EntityField.class).name())) {
                    try {
                        field.setAccessible(true);
                        found = key.get(field.getAnnotation(EntityField.class).name()).equals(field.get(f));
                    } catch (IllegalAccessException e) {
                        return false;
                    }
                }
            }
            return found;
        });
    }

    @Override
    public void connect() {
        //does nothing
    }

    @Override
    public void disconnect() {
        //does nothing
    }

    @Override
    public boolean exists(T value) {
        return getOne(value).isPresent();
    }

    @Override
    public Optional<T> getOne(T value) {
        return executeFilterOnKey(value).findFirst();
    }

    @Override
    public OperationResult add(T value) {
        if(getOne(value).isPresent()) return OperationResult.DUPLICATE;

        this.data.add(value);
        return OperationResult.DONE;
    }

    @Override
    public List<T> getAll() {
        return data;
    }

    @Override
    public OperationResult remove(T value) {
        List<T> removable = executeFilterOnKey(value).toList();
        if(removable.isEmpty()) return OperationResult.NOT_FOUND;
        data.removeAll(removable);
        return OperationResult.DONE;
    }

    @Override
    public void executeCommand(QueryCommand<MockQuery<T>> command) {

    }
}
