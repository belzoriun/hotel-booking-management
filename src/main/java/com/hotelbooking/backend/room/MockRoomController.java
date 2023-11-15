package com.hotelbooking.backend.room;

import com.hotelbooking.backend.BaseController;
import com.hotelbooking.backend.data.stream.mock.MockDataStream;
import com.hotelbooking.backend.data.stream.mock.MockQuery;
import com.hotelbooking.backend.utils.Response;
import com.hotelbooking.backend.utils.ResponseWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MockRoomController extends BaseController<Room, MockQuery<Room>> {

    protected MockRoomController() {
        super(new MockDataStream<>());
        dataStream.add(new Room(1, 1));
    }

    @GetMapping("/rooms")
    public Response<List<Room>> GetAllRooms() {
        return ResponseWrapper.Wrap(dataStream.getAll());
    }

    @GetMapping("/room")
    public Response<Room> GetRoom(@RequestParam(value = "id", defaultValue = "1") int id) {
        if(!dataStream.exists(new Room(id, 0))) return ResponseWrapper.WrapError("Room "+id+" not found");

        return ResponseWrapper.Wrap(dataStream.getOne(new Room(id, 0)));
    }

    @PostMapping("/room")
    public Response<Room> AddRoom(@RequestBody Room newRoom) {
        return processOperation(() -> dataStream.add(newRoom));
    }

    @DeleteMapping("/room")
    public Response<Room> RemoveRoom(@RequestParam(value = "id", defaultValue = "1") int id) {
        return processOperation(() -> dataStream.remove(new Room(id, 0)));
    }
}
