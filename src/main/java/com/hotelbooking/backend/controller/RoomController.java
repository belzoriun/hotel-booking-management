package com.hotelbooking.backend.controller;

import com.hotelbooking.backend.data.query.builder.QueryBuilder;
import com.hotelbooking.backend.data.stream.DataStream;
import com.hotelbooking.backend.models.Room;
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
    public Response<List<Room>> GetAllRooms() {
        return ResponseWrapper.Wrap(dataStream.select(
            new QueryBuilder<>(Room.class)
                    .join((Room r) -> r.booking)
        ));
    }

    @GetMapping("/room")
    public Response<Room> GetRoom(@RequestParam(value = "id", defaultValue = "1") int id) {
        if(!dataStream.exists(new QueryBuilder<>(Room.class)
                .where((Room room) -> room.number == id))) {
            return ResponseWrapper.WrapError("Room with id "+id+" not found");
        }
        try {
            return ResponseWrapper.Wrap(dataStream.select(
                    new QueryBuilder<>(Room.class)
                        .where((Room room) -> room.number == id)
                        .join((Room r) -> r.booking)
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
                    .where((Room room) -> room.number == id)
        ));
    }

    @PatchMapping("/room")
    public Response<Room> UpdateRoom(@RequestBody Room newRoom) {
        return ResponseWrapper.Wrap(dataStream.update(newRoom));
    }
    @PutMapping("/room")
    public Response<Room> AddOrUpdateRoom(@RequestBody Room newRoom) {
        return ResponseWrapper.Wrap(dataStream.addOrUpdate(newRoom));
    }
}
