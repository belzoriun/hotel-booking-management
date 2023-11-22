package com.hotelbooking.backend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotelbooking.backend.data.query.builder.QueryBuilder;
import com.hotelbooking.backend.data.query.condition.Condition;
import com.hotelbooking.backend.data.query.condition.ConditionBuilder;
import com.hotelbooking.backend.data.query.condition.Constant;
import com.hotelbooking.backend.data.query.condition.Equal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DataEntity {

    @JsonIgnore
    public static Field getFieldFromName(Class<? extends DataEntity> entity, String name) throws Exception {
        for(Field f : entity.getDeclaredFields()) {
            if(f.isAnnotationPresent(EntityField.class) && f.getAnnotation(EntityField.class).name().equals(name)) {
                return f;
            }
        }
        throw new Exception("No such field "+name+ " in type "+entity.getSimpleName());
    }

    public static Object getDataFromFieldName(DataEntity data, String name) throws Exception {
        Field f = getFieldFromName(data.getClass(), name);
        f.setAccessible(true);
        return f.get(data);
    }

    public static QueryBuilder<? extends DataEntity> createKeyQuery(DataEntity data) {
        ConditionBuilder builder = new ConditionBuilder(new Constant<>(true));
        for(Field f : data.getClass().getDeclaredFields()) {
            if(f.isAnnotationPresent(EntityField.class) && f.getAnnotation(EntityField.class).isKey()) {
                try {
                    f.setAccessible(true);
                    builder.and(new Equal(
                            new com.hotelbooking.backend.data.query.condition.Field(data.getClass(), f.getAnnotation(EntityField.class).name()),
                            new Constant<>(f.get(data))
                    ));
                } catch (IllegalAccessException ignored) {
                }
            }
        }
        return new QueryBuilder<>(data.getClass()).where(builder.get());
    }
}
