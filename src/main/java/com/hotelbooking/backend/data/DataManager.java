package com.hotelbooking.backend.data;

import com.hotelbooking.backend.data.filter.QueryFilter;
import com.hotelbooking.backend.data.stream.CommandType;
import com.hotelbooking.backend.data.stream.DataStream;

import java.util.List;
import java.util.Optional;

public class DataManager<T extends DataEntity, FT> {

    private final DataStream<T, FT> dataStream;

    public DataManager(DataStream<T, FT> stream){
        this.dataStream = stream;
        this.dataStream.connect();
    }

    public void exit() {
        this.dataStream.disconnect();
    }

    public boolean exists(T data) {
        return this.dataStream.exists(data);
    }

    public Optional<T> getOne(QueryFilter filter) {
        return this.dataStream.pullData(filter).stream().findFirst();
    }

    public List<T> getAll() {
        return this.dataStream.pullData();
    }

    public List<T> getFiltered(QueryFilter filter) {
        return this.dataStream.pullData(filter);
    }

    public OperationResult add(T entry) {
        if(exists(entry)) return OperationResult.DUPLICATE;

        this.dataStream.pushCommand(CommandType.ADD, entry);
        return OperationResult.DONE;
    }

    public OperationResult remove(T entry) {
        if(!exists(entry)) return OperationResult.NOT_FOUND;

        this.dataStream.pushCommand(CommandType.REMOVE, entry);
        return OperationResult.DONE;
    }

    public OperationResult update(T entry) {
        if(!exists(entry)) return OperationResult.NOT_FOUND;

        this.dataStream.pushCommand(CommandType.CHANGE, entry);
        return OperationResult.DONE;
    }
}
