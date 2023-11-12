package com.hotelbooking.backend.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface DataEntity {

    @JsonIgnore
    public<T> KeyPart<T> getKeyDescriptor();
}
