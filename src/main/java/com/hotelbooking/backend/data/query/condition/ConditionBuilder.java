package com.hotelbooking.backend.data.query.condition;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.query.condition.parsing.Lambda2Condition;

public class ConditionBuilder {
    Condition<?> condition;

    public ConditionBuilder(Condition<?> cond) {
        this.condition = cond;
    }
    public<T extends DataEntity> ConditionBuilder(ConditionPredicate<T> cond) {
        this.condition = Lambda2Condition.toCondition(cond);
    }

    public ConditionBuilder equal(Condition<?> cond) {
        this.condition = new Equal(this.condition, cond);
        return this;
    }
    public ConditionBuilder and(Condition<?> cond) {
        this.condition = new And(cond, this.condition);
        return this;
    }

    public<T> Condition<T> get() {
        return (Condition<T>)condition;
    }
}
