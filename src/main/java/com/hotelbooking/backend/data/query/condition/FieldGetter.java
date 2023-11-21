package com.hotelbooking.backend.data.query.condition;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Predicate;

public interface FieldGetter<T> extends Function<T, Object>, Serializable {
}
