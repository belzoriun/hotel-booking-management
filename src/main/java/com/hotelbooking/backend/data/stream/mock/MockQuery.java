package com.hotelbooking.backend.data.stream.mock;

public interface MockQuery<T> {
    public boolean accept(T value);
}
