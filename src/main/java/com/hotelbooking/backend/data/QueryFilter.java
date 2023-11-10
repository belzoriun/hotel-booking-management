package com.hotelbooking.backend.data;

public interface QueryFilter<T> {
    public boolean accept(T entry);
}
