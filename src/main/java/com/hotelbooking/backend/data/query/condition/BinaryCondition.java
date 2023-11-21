package com.hotelbooking.backend.data.query.condition;

public abstract class BinaryCondition<T> implements Condition<T> {
    protected Condition<?> left;
    protected Condition<?> right;

    protected BinaryCondition(Condition<?> left, Condition<?> right) {
        this.left = left;
        this.right = right;
    }
}
