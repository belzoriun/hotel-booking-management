package com.hotelbooking.backend.data.query.condition;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.Entity;
import com.hotelbooking.backend.data.EntityField;
import org.apache.tomcat.util.http.parser.EntityTag;

public class Field implements Condition<Object>{

    private String name;
    private Class<? extends DataEntity> entity;

    public Field(Class<? extends DataEntity> entity, String name) {
        this.name = name;
    }

    @Override
    public Object execute(DataEntity entity) {
        try {
            return DataEntity.getDataFromFieldName(entity, name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toQueryString() {
        return entity.getName()+"."+name;
    }
}
