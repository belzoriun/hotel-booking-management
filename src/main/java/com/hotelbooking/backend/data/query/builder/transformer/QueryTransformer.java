package com.hotelbooking.backend.data.query.builder.transformer;

import com.hotelbooking.backend.data.query.builder.QueryBuilder;

public interface QueryTransformer<T> {
    public T transform(QueryBuilder<?> query);
}
