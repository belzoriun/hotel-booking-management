package com.hotelbooking.backend.data.stream.factory;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.stream.DataStream;

public interface DataStreamFactory<T extends DataEntity> {
    public DataStream<T> build();
}
