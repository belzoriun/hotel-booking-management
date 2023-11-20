package com.hotelbooking.backend.data.query.condition;

import com.hotelbooking.backend.data.DataEntity;

public class Constant<T> implements Condition<T>{

    private final T data;

    public Constant(T data) {
        this.data = data;
    }
    @Override
    public T execute(DataEntity entity) {
        return data;
    }

    @Override
    public String toQueryString() {
        return data.toString();
    }
}
