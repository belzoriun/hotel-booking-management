package com.hotelbooking.backend.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.EntityField;
import com.hotelbooking.backend.data.KeyPart;

import java.util.Map;

public record Room(@EntityField(name = "id") int number, @EntityField(name = "beds") int beds) implements DataEntity {

    @Override
    @JsonIgnore
    public KeyPart<Room> getKeyDescriptor() {
        return e -> Map.of("id", e.number());
    }
}
