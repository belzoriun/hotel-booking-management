package com.hotelbooking.backend.models;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.EntityField;
import com.hotelbooking.backend.data.EntityJoin;

import java.util.List;

public class Room implements DataEntity {
    @EntityField(name = "id", isKey = true)
    public int number;
    @EntityField(name = "beds")
    public int beds;
    @EntityJoin(ownerField = "id", joinedEntityField = "id")
    public List<Booking> booking;

    public Room(int number, int beds) {
        this.number = number;
        this.beds = beds;
        this.booking = null;
    }
}
