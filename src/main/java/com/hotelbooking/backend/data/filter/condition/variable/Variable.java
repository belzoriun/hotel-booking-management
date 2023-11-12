package com.hotelbooking.backend.data.filter.condition.variable;

import com.hotelbooking.backend.data.filter.Evaluable;

public interface Variable<T> extends Evaluable {
    public Variable<T> set(T value);
    public T get();
}
