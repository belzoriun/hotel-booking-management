package com.hotelbooking.backend.data.filter.condition;

import com.hotelbooking.backend.data.filter.Evaluable;

public class UnaryConditionBuilder {
    private Evaluable item;
    private UnaryConditionalOperator operator;

    public UnaryConditionBuilder setItem(Evaluable item) {
        this.item = item;
        return this;
    }
    public UnaryConditionBuilder setOperator(UnaryConditionalOperator operator) {
        this.operator = operator;
        return this;
    }

    public UnaryCondition build() {
        return new UnaryCondition(item, operator);
    }
}
