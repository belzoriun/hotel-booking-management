package com.hotelbooking.backend.data;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DataManager<T extends DataEntity > {
    public Optional<T> GetOne(Map<String, Object> key);
    public List<T> GetAll();
    public List<T> GetFiltered(QueryFilter<T> filter);
    public OperationResult Add(T entry);
    public OperationResult Remove(T entity);
    public OperationResult Update(T entity, Map<String, Object> values);
}
