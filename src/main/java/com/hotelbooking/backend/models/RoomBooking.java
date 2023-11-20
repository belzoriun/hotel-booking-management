package com.hotelbooking.backend.models;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.Entity;
import com.hotelbooking.backend.data.EntityField;
import com.hotelbooking.backend.data.EntityJoin;

@Entity(name = "room_booking")
public class RoomBooking implements DataEntity {
    @EntityField(name = "room_id")
    public int roomId;
    @EntityField(name = "booking_id")
    public int bookingId;
    @EntityJoin(ownerField = "room_id", joinedEntityField = "id")
    public Room room;
    @EntityJoin(ownerField = "booking_id", joinedEntityField = "id")
    public Booking booking;

    public RoomBooking(int roomId, int bookingId) {
        this.roomId = roomId;
        this.bookingId = bookingId;
    }
}
