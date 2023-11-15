package com.hotelbooking.backend.data.filter;

public interface QueryCommand<T> {
    public T build();
}
