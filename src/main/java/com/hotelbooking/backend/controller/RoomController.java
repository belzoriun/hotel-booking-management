package com.hotelbooking.backend.controller;

import com.hotelbooking.backend.data.query.builder.QueryBuilder;
import com.hotelbooking.backend.data.query.condition.ConditionBuilder;
import com.hotelbooking.backend.data.query.condition.Constant;
import com.hotelbooking.backend.data.query.condition.Field;
import com.hotelbooking.backend.data.stream.DataStream;
import com.hotelbooking.backend.models.Room;
import com.hotelbooking.backend.models.RoomBooking;
import com.hotelbooking.backend.utils.Response;
import com.hotelbooking.backend.utils.ResponseWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController extends BaseController<Room> {

    protected RoomController(DataStream stream) {
        super(stream);
    }

    @GetMapping("/rooms")
    public Response<List<Room>> GetAllRooms() throws NoSuchFieldException {
        return ResponseWrapper.Wrap(dataStream.executeSelect(
            new QueryBuilder<>(Room.class)
                    .join(Room.class.getDeclaredField("booking"))
                    .join(RoomBooking.class.getDeclaredField("booking"))
        ));
    }

    @GetMapping("/room")
    public Response<Room> GetRoom(@RequestParam(value = "id", defaultValue = "1") int id) {
        try {
            return ResponseWrapper.Wrap(dataStream.executeSelect(
                    new QueryBuilder<>(Room.class)
                        .where(new ConditionBuilder(new Constant<>(id)).equal(new Field(Room.class, "id")).get())
                        .join(Room.class.getDeclaredField("booking"))
                        .join(RoomBooking.class.getDeclaredField("booking"))
            ).stream().findFirst());
        } catch (Exception e) {
            return ResponseWrapper.WrapError(e.getMessage());
        }
    }

    @PostMapping("/room")
    public Response<Room> AddRoom(@RequestBody Room newRoom) {
        return ResponseWrapper.Wrap(dataStream.add(newRoom));
    }

    @DeleteMapping("/room")
    public Response<Room> RemoveRoom(@RequestParam(value = "id", defaultValue = "1") int id) {
        return processOperation(() -> dataStream.remove(
                new QueryBuilder<>(Room.class)
                    .where(new ConditionBuilder(new Constant<>(id)).equal(new Field(Room.class, "id")).get())
        ));
    }
}
