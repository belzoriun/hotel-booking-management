package com.hotelbooking.backend.data.stream;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.OperationResult;
import com.hotelbooking.backend.data.filter.QueryCommand;

import java.util.List;
import java.util.Optional;

public interface DataStream<T extends DataEntity, QT> {
    public void connect();
    public void disconnect();
    public boolean exists(T value);
    public Optional<T> getOne(T value);
    public OperationResult add(T value);
    public List<T> getAll();
    public OperationResult remove(T value);
    public void executeCommand(QueryCommand<QT> command);
}
