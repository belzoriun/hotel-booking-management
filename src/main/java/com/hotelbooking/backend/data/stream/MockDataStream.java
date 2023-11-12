package com.hotelbooking.backend.data.stream;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.filter.QueryFilter;
import com.hotelbooking.backend.data.filter.condition.evaluate.EvaluatorVariable;
import com.hotelbooking.backend.data.filter.condition.evaluate.ExecutionContext;
import com.hotelbooking.backend.data.filter.condition.variable.ValueTypeHint;
import com.hotelbooking.backend.data.filter.exception.InvalidOperationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MockDataStream<T extends DataEntity> implements DataStream<T, List<T>> {

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
        QueryFilter filter = new QueryFilter();
        return !this.pullData(filter).isEmpty();
    }

    @Override
    public void pushCommand(CommandType command, T data) {
        switch(command) {
            case ADD -> pushData(data);
            case CHANGE -> {
                Map<String, Object> key = data.getKeyDescriptor().ExtractKey(data);
                QueryFilter filter = new QueryFilter();
                List<T> toUpdate = pullData(filter);
                for (T value : toUpdate) {
                    this.data.remove(value);
                }
                this.pushData(data);
            }
            case REMOVE -> {
                Map<String, Object> key = data.getKeyDescriptor().ExtractKey(data);
                System.out.println(key);
                QueryFilter filter = new QueryFilter();
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
    public List<T> pullData(QueryFilter filter) {
        return data.stream().filter(v-> {
            try {
                EvaluatorVariable var = filter.execute(new ExecutionContext<>(v));
                if(var.type.equals(ValueTypeHint.BOOLEAN)) return (boolean)var.value;
            } catch (InvalidOperationException e) {
                return false;
            }
            return false;
        }).toList();
    }

    @Override
    public List<T> pullData() {
        return this.data;
    }
}
