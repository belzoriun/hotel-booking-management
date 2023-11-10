package com.hotelbooking.backend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotelbooking.backend.data.filter.MockFilter;
import com.hotelbooking.backend.data.filter.QueryFilter;

import java.util.Map;

public interface DataEntity {

    @JsonIgnore
    public<T> KeyPart<T> getKeyDescriptor();
}
