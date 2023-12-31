package com.hotelbooking.backend.models;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.Entity;
import com.hotelbooking.backend.data.EntityField;
import com.hotelbooking.backend.data.EntityJoin;

import java.util.Date;

@Entity(name = "booking")
public class Booking implements DataEntity {

    @EntityField(name = "id", isKey = true)
    public int id;
    @EntityField(name = "start", isKey = true)
    public Date start;
    @EntityField(name = "end", isKey = true)
    public Date end;
    @EntityJoin(ownerField = "id", joinedEntityField = "id")
    public Room room;

    public Booking(int id, Date start, Date end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }
}
