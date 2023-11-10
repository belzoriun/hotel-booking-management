package com.hotelbooking.backend.data;

import com.hotelbooking.backend.data.filter.MockFilter;
import com.hotelbooking.backend.data.filter.QueryFilter;
import com.hotelbooking.backend.data.stream.CommandType;
import com.hotelbooking.backend.data.stream.DataStream;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataManager<T extends DataEntity> {

    private final DataStream<T> dataStream;

    public DataManager(DataStream<T> stream){
        this.dataStream = stream;
        this.dataStream.connect();
    }

    public void exit() {
        this.dataStream.disconnect();
    }

    public boolean exists(Map<String, Object> key) {
        MockFilter<T> filter = new MockFilter<>();
        for (Map.Entry<String, Object> entry : key.entrySet()){
            filter.filterKey(entry.getKey(), entry.getValue());
        }
        return !this.dataStream.pullData(filter).isEmpty();
    }

    public boolean exists(T data) {
        return this.dataStream.exists(data);
    }

    public Optional<T> getOne(Map<String, Object> key) {
        MockFilter<T> filter = new MockFilter<>();
        for (Map.Entry<String, Object> entry : key.entrySet()){
            filter.filterKey(entry.getKey(), entry.getValue());
        }
        return this.dataStream.pullData(filter).stream().findFirst();
    }

    public List<T> getAll() {
        return this.dataStream.pullData(QueryFilter.ALL());
    }

    public List<T> getFiltered(QueryFilter<T> filter) {
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
