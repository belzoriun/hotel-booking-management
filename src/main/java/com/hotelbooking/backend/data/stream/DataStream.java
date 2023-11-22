package com.hotelbooking.backend.data.stream;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.OperationResult;
import com.hotelbooking.backend.data.query.builder.QueryBuilder;

import java.util.List;
import java.util.Optional;

public abstract class DataStream {
    public abstract void connect();
    public abstract void disconnect();
    public abstract<T extends DataEntity>  List<T> select(QueryBuilder<T> query);
    public abstract boolean exists(QueryBuilder<? extends DataEntity> query);
    public abstract<T extends DataEntity> Optional<T> add(T data);
    public abstract<T extends DataEntity> Optional<T> update(T data);
    public abstract OperationResult remove(QueryBuilder<? extends DataEntity> query);
    public <T extends DataEntity> Optional<T> addOrUpdate(T entity) {
        if(exists(DataEntity.createKeyQuery(entity))) {
            return update(entity);
        } else {
            return add(entity);
        }
    }
}
