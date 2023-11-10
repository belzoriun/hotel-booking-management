package com.hotelbooking.backend.data.stream;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.filter.MockFilter;
import com.hotelbooking.backend.data.filter.QueryFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockDataStream<T extends DataEntity> implements DataStream<T> {

    private final List<T> data = new ArrayList<>();

    @Override
    public void connect() {
        //does nothing
    }

    @Override
    public void disconnect() {
        //does nothing
    }

    @Override
    public boolean exists(T value) {
        Map<String, Object> key = value.getKeyDescriptor().ExtractKey(value);
        MockFilter<T> filter = new MockFilter<>();
        for (Map.Entry<String, Object> entry : key.entrySet()) {
            filter.filterKey(entry.getKey(), entry.getValue());
        }
        return !this.pullData(filter).isEmpty();
    }

    @Override
    public void pushCommand(CommandType command, T data) {
        switch(command) {
            case ADD -> pushData(data);
            case CHANGE -> {
                Map<String, Object> key = data.getKeyDescriptor().ExtractKey(data);
                MockFilter<T> filter = new MockFilter<>();
                for (Map.Entry<String, Object> entry : key.entrySet()) {
                    filter.filterKey(entry.getKey(), entry.getValue());
                }
                List<T> toUpdate = pullData(filter);
                for (T value : toUpdate) {
                    this.data.remove(value);
                }
                this.pushData(data);
            }
            case REMOVE -> {
                Map<String, Object> key = data.getKeyDescriptor().ExtractKey(data);
                System.out.println(key);
                MockFilter<T> filter = new MockFilter<>();
                for (Map.Entry<String, Object> entry : key.entrySet()) {
                    filter.filterKey(entry.getKey(), entry.getValue());
                }
                List<T> toRemove = pullData(filter);
                System.out.println(toRemove);
                for (T value : toRemove) {
                    this.data.remove(value);
                }
            }
        }
    }


    @Override
    public void pushData(T data) {
        this.data.add(data);
    }

    @Override
    public void pushData(List<T> data) {
        this.data.addAll(data);
    }

    @Override
    public List<T> pullData(QueryFilter<T> filter) {
        return filter.setFilteringData(this.data).execute();
    }
}
