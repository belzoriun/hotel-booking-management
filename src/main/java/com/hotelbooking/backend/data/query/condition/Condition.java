package com.hotelbooking.backend.data.query.condition;

import com.hotelbooking.backend.data.DataEntity;

public interface Condition<T> {
    public T execute(DataEntity entity);
    public String toQueryString();
}
