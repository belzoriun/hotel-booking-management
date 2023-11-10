package com.hotelbooking.backend.data.filter;

import com.hotelbooking.backend.data.DataEntity;

import java.util.ArrayList;
import java.util.List;

public interface QueryFilter<T extends DataEntity> {
    public QueryFilter<T> setFilteringData(List<T> toFilter);
    public List<T> execute();

    public static<T extends DataEntity> QueryFilter<T> ALL() {
        return new QueryFilter<T>() {
            private List<T> data = new ArrayList<>();
            @Override
            public QueryFilter setFilteringData(List toFilter) {
                data = toFilter;
                return this;
            }

            @Override
            public List execute() {
                return data;
            }
        };
    }
}
