package com.hotelbooking.backend.data.query.condition;

import com.hotelbooking.backend.data.DataEntity;

public class Equal extends BinaryCondition<Boolean>{

    public Equal(Condition<?> left, Condition<?> right) {
        super(left, right);
    }

    @Override
    public Boolean execute(DataEntity entity) {
        Object leftValue = left.execute(entity);
        Object rightValue = right.execute(entity);
        if(leftValue == null && rightValue == null) return true;
        if(leftValue == null) return false;
        return leftValue.equals(rightValue);
    }

    @Override
    public String toQueryString() {
        return left.toQueryString()+" = "+right.toQueryString();
    }
}
