package com.hotelbooking.backend.data.query.condition;

import com.hotelbooking.backend.data.DataEntity;

import java.io.Serializable;
import java.util.function.Predicate;

public interface ConditionPredicate<T extends DataEntity> extends Predicate<T>, Serializable {
}
