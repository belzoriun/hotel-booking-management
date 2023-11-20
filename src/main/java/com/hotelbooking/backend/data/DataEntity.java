package com.hotelbooking.backend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Field;
import java.util.HashMap;
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
}
