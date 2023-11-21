package com.hotelbooking.backend.data.query.condition;

import com.hotelbooking.backend.data.DataEntity;

public class Not implements Condition<Boolean>{
    private Condition<?> cond;

    public Not(Condition<?> cond) {
        this.cond = cond;
    }

    @Override
    public Boolean execute(DataEntity entity) {
        return !((boolean)cond.execute(entity));
    }

    @Override
    public String toQueryString() {
        return null;
    }
}
