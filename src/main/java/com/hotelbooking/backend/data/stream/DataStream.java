package com.hotelbooking.backend.data.stream;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.query.builder.SelectQueryBuilder;

import java.util.List;

public interface DataStream {
    public abstract void connect();
    public abstract void disconnect();
    public<T extends DataEntity> List<T> executeSelect(SelectQueryBuilder<T> query);
}
