package com.hotelbooking.backend.data.filter.condition;

import com.hotelbooking.backend.data.filter.Evaluable;

public class BinaryConditionBuilder {
    private Evaluable left;
    private Evaluable right;
    private BinaryConditionalOperator operator;

    public BinaryConditionBuilder setLeft(Evaluable left) {
        this.left = left;
        return this;
    }
    public BinaryConditionBuilder setRight(Evaluable right) {
        this.right = right;
        return this;
    }
    public BinaryConditionBuilder setOperator(BinaryConditionalOperator operator) {
        this.operator = operator;
        return this;
    }

    public BinaryCondition build() {
        return new BinaryCondition(left, operator, right);
    }

    public BinaryConditionBuilder and(Evaluable right) {
        return new BinaryConditionBuilder().setLeft(build()).setRight(right).setOperator(BinaryConditionalOperator.AND);
    }

    public BinaryConditionBuilder or(Evaluable right) {
        return new BinaryConditionBuilder().setLeft(build()).setRight(right).setOperator(BinaryConditionalOperator.OR);
    }

    public UnaryConditionBuilder not() {
        return new UnaryConditionBuilder().setItem(build()).setOperator(UnaryConditionalOperator.NOT);
    }
}
