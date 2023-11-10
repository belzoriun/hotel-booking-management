package com.hotelbooking.backend.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MockDataManager<T extends DataEntity> implements DataManager<T> {

    private List<T> mock;

    public MockDataManager(List<T> mock){
        this.mock = mock;
    }

    private Boolean objectHasProperty(Object obj, String propertyName){
        List<Field> properties = getAllFields(obj);
        for(Field field : properties){
            if(field.getName().equalsIgnoreCase(propertyName)){
                return true;
            }
        }
        return false;
    }

    private Object objectGetProperty(Object obj, String propertyName) throws IllegalAccessException {
        List<Field> properties = getAllFields(obj);
        for(Field field : properties){
            if(field.getName().equalsIgnoreCase(propertyName)){
                return field.get(obj);
            }
        }
        return null;
    }

    private static List<Field> getAllFields(Object obj){
        List<Field> fields = new ArrayList<Field>();
        getAllFieldsRecursive(fields, obj.getClass());
        return fields;
    }

    private static List<Field> getAllFieldsRecursive(List<Field> fields, Class<?> type) {
        for (Field field: type.getDeclaredFields()) {
            fields.add(field);
        }

        if (type.getSuperclass() != null) {
            fields = getAllFieldsRecursive(fields, type.getSuperclass());
        }

        return fields;
    }

    @Override
    public Optional<T> GetOne(Map<String, Object> key) {
        return mock.stream().filter(e->{
            Map<String, Object> definedKeys = e.getKeyDescriptor().ExtractKey(e);
            for (String k : key.keySet()) {
                if(!definedKeys.containsKey(k)) return false;
                if(!definedKeys.get(k).equals(key.get(k))) return false;
            }
            return true;
        }).findFirst();
    }

    @Override
    public List<T> GetAll() {
        return this.mock;
    }

    @Override
    public List<T> GetFiltered(QueryFilter<T> filter) {
        return null;
    }

    @Override
    public OperationResult Add(T entry) {
        mock.add(entry);
        return OperationResult.DONE;
    }

    @Override
    public OperationResult Remove(T entry) {
        return OperationResult.DONE;
    }

    @Override
    public OperationResult Update(T entry, Map<String, Object> values) {
        return OperationResult.DONE;
    }
}
