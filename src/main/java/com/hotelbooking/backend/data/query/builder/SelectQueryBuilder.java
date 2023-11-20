package com.hotelbooking.backend.data.query.builder;

import com.hotelbooking.backend.data.EntityJoin;
import com.hotelbooking.backend.data.query.condition.Condition;
import com.hotelbooking.backend.data.query.condition.Constant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SelectQueryBuilder<T> {
    private final Class<T> baseEntity;
    private final List<Field> joins;
    private Condition<Boolean> condition = new Constant<>(true);

    public SelectQueryBuilder(Class<T> entity) {
        this.baseEntity = entity;
        this.joins = new ArrayList<>();
    }

    public SelectQueryBuilder<T> join(Field join) {
        if(join.isAnnotationPresent(EntityJoin.class)) {
            this.joins.add(join);
        }
        return this;
    }

    public SelectQueryBuilder<T> where(Condition<Boolean> condition) {
        this.condition = condition;
        return this;
    }

    public Class<T> getSelectedEntity() {
        return this.baseEntity;
    }

    public List<Field> getJoins() {
        return this.joins;
    }

    public Condition<Boolean> getConditions() {
        return this.condition;
    }
}
