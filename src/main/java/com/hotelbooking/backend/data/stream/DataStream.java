package com.hotelbooking.backend.data.stream;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.filter.QueryFilter;

import java.util.List;

public interface DataStream<T extends DataEntity, FT> {
    public void connect();
    public void disconnect();
    public boolean exists(T value);
    public void pushCommand(CommandType command, T data);
    public void pushData(T data);
    public void pushData(List<T> data);
    public List<T> pullData(QueryFilter filter);
    public List<T> pullData();
}
