package com.hotelbooking.backend.controller;

import com.hotelbooking.backend.BaseController;
import com.hotelbooking.backend.data.query.builder.SelectQueryBuilder;
import com.hotelbooking.backend.data.query.condition.Constant;
import com.hotelbooking.backend.data.query.condition.Equal;
import com.hotelbooking.backend.data.query.condition.Field;
import com.hotelbooking.backend.data.stream.memory.MemoryDataStream;
import com.hotelbooking.backend.models.Room;
import com.hotelbooking.backend.models.RoomBooking;
import com.hotelbooking.backend.utils.Response;
import com.hotelbooking.backend.utils.ResponseWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController extends BaseController<Room> {

    protected RoomController() {
        super(new MemoryDataStream());
    }

    @GetMapping("/rooms")
    public Response<List<Room>> GetAllRooms() throws NoSuchFieldException {
        return ResponseWrapper.Wrap(dataStream.executeSelect(
            new SelectQueryBuilder<>(Room.class)
                    .join(Room.class.getDeclaredField("booking"))
                    .join(RoomBooking.class.getDeclaredField("booking"))
        ));
    }

    @GetMapping("/room")
    public Response<Room> GetRoom(@RequestParam(value = "id", defaultValue = "1") int id) {
        try {
            return ResponseWrapper.Wrap(dataStream.executeSelect(
                    new SelectQueryBuilder<>(Room.class)
                        .where(new Equal(new Constant<>(id), new Field(Room.class, "id")))
                        .join(Room.class.getDeclaredField("booking"))
                        .join(RoomBooking.class.getDeclaredField("booking"))
            ).stream().findFirst());
        } catch (Exception e) {
            return ResponseWrapper.WrapError(e.getMessage());
        }
    }

    /*@PostMapping("/room")
    public Response<Room> AddRoom(@RequestBody Room newRoom) {
        return processOperation(() -> dataStream.add(newRoom));
    }

    @DeleteMapping("/room")
    public Response<Room> RemoveRoom(@RequestParam(value = "id", defaultValue = "1") int id) {
        return processOperation(() -> dataStream.remove(new Room(id, 0)));
    }*/
}
