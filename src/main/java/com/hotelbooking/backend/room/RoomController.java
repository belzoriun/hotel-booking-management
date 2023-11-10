package com.hotelbooking.backend.room;

import com.hotelbooking.backend.BaseController;
import com.hotelbooking.backend.data.DataManager;
import com.hotelbooking.backend.data.stream.factory.MockDataStreamFactory;
import com.hotelbooking.backend.utils.Response;
import com.hotelbooking.backend.utils.ResponseWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RoomController extends BaseController<Room> {

    protected RoomController() {
        super(new DataManager<>(new MockDataStreamFactory<Room>()
                .AddValue(new Room(1, 1))
                .build()));
    }

    @GetMapping("/rooms")
    public Response<List<Room>> GetAllRooms() {
        return ResponseWrapper.Wrap(dataManager.getAll());
    }

    @GetMapping("/room")
    public Response<Room> GetRoom(@RequestParam(value = "id", defaultValue = "1") int id) {
        if(!dataManager.exists(Map.of("id", id))) return ResponseWrapper.WrapError("Room "+id+" not found");

        return ResponseWrapper.Wrap(dataManager.getOne(Map.of("id", id)));
    }

    @PostMapping("/room")
    public Response<Room> AddRoom(@RequestBody Room newRoom) {
        return processOperation(() -> dataManager.add(newRoom));
    }

    @PostMapping("/room/delete")
    public Response<Room> RemoveRoom(@RequestParam(value = "id", defaultValue = "1") int id) {
        Room room = dataManager.getOne(Map.of("id", id)).orElse(null);
        if(room == null) return ResponseWrapper.WrapError("Room "+id+" not found");
        return processOperation(() -> dataManager.remove(room));
    }
}
