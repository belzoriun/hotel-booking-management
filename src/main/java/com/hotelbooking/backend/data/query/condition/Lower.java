package com.hotelbooking.backend.data.query.condition;

import com.hotelbooking.backend.data.DataEntity;

public class Lower extends BinaryCondition<Boolean>{

    public Lower(Condition<?> left, Condition<?> right) {
        super(left, right);
    }

    @Override
    public Boolean execute(DataEntity entity) {
        return ((Comparable<Object>)left.execute(entity)).compareTo(right.execute(entity)) < 0;
    }

    @Override
    public String toQueryString() {
        return null;
    }
}
