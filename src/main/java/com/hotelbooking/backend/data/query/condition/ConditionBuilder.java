package com.hotelbooking.backend.data.query.condition;

public class ConditionBuilder {
    Condition<?> condition;

    public ConditionBuilder(Condition<?> cond) {
        this.condition = cond;
    }

    public ConditionBuilder equal(Condition<?> cond) {
        return new ConditionBuilder(new Equal(this.condition, cond));
    }

    public<T> Condition<T> get() {
        return (Condition<T>)condition;
    }
}
