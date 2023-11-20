package com.hotelbooking.backend.data.stream;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.OperationResult;
import com.hotelbooking.backend.data.query.builder.QueryBuilder;

import java.util.List;
import java.util.Optional;

public interface DataStream {
    public abstract void connect();
    public abstract void disconnect();
    public<T extends DataEntity> List<T> executeSelect(QueryBuilder<T> query);
    public<T extends DataEntity> Optional<T> add(T data);
    public OperationResult remove(QueryBuilder<?> query);
}
