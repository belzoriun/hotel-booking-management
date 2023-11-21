package com.hotelbooking.backend.data.query.condition;

import com.hotelbooking.backend.data.DataEntity;

public class Or extends BinaryCondition<Boolean>{
    public Or(Condition<?> left, Condition<?> right) {
        super(left, right);
    }

    @Override
    public Boolean execute(DataEntity entity) {
        return (boolean)left.execute(entity) || (boolean)right.execute(entity);
    }

    @Override
    public String toQueryString() {
        return null;
    }
}
