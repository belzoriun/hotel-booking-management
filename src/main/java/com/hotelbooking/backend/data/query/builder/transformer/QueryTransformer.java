package com.hotelbooking.backend.data.query.builder.transformer;

import com.hotelbooking.backend.data.query.builder.SelectQueryBuilder;

public interface QueryTransformer<T> {
    public T transform(SelectQueryBuilder<?> query);
}
