package com.hotelbooking.backend.data.stream.factory;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.stream.DataStream;
import com.hotelbooking.backend.data.stream.MockDataStream;
import com.hotelbooking.backend.room.Room;

import java.util.ArrayList;
import java.util.List;

public class MockDataStreamFactory<T extends DataEntity> implements DataStreamFactory<T> {

    List<T> data = new ArrayList<>();

    public MockDataStreamFactory<T> AddValue(T value) {
        data.add(value);
        return this;
    }

    @Override
    public DataStream<T> build() {
        DataStream<T> stream = new MockDataStream<>();
        stream.pushData(data);
        return stream;
    }
}
