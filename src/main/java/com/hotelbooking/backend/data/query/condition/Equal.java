package com.hotelbooking.backend.data.query.condition;

import com.hotelbooking.backend.data.DataEntity;

public class Equal implements Condition<Boolean>{

    private Condition<?> left;
    private Condition<?> right;

    public Equal(Condition<?> left, Condition<?> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Boolean execute(DataEntity entity) {
        return left.execute(entity).equals(right.execute(entity));
    }

    @Override
    public String toQueryString() {
        return left.toQueryString()+" = "+right.toQueryString();
    }
}
