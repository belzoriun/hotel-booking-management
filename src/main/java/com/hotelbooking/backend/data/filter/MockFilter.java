package com.hotelbooking.backend.data.filter;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.EntityField;

import java.lang.reflect.Field;
import java.util.*;

public class MockFilter<T extends DataEntity> implements QueryFilter<T> {
    private List<T> toFilter = new ArrayList<>();
    private Map<String, Object> filter = new HashMap<>();

    public MockFilter<T> setFilteringData(List<T> toFilter) {
        this.toFilter = toFilter;
        return this;
    }

    public MockFilter<T> filterKey(String key, Object value) {
        filter.put(key, value);
        return this;
    }

    @Override
    public List<T> execute() {
        return toFilter.stream().filter(e-> {
            final Field[] fields = e.getClass().getDeclaredFields();
            Optional<Field> field = Arrays.stream(fields).filter(f -> {
                try {
                    f.setAccessible(true);
                    return f.isAnnotationPresent(EntityField.class)
                            && filter.containsKey(f.getAnnotation(EntityField.class).name())
                            && filter.get(f.getAnnotation(EntityField.class).name()).equals(f.get(e));
                } catch (IllegalAccessException ex) {
                    return false;
                }
            }).findFirst();
            return field.isPresent();
        }).toList();
    }
}
