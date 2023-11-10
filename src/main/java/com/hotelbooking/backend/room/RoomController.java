package com.hotelbooking.backend.room;

import com.hotelbooking.backend.BaseController;
import com.hotelbooking.backend.data.DataManager;
import com.hotelbooking.backend.data.MockDataManager;
import com.hotelbooking.backend.data.OperationResult;
import com.hotelbooking.backend.utils.OperationExecutor;
import com.hotelbooking.backend.utils.Response;
import com.hotelbooking.backend.utils.ResponseWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RoomController extends BaseController<Room> {

    protected RoomController() {
        super(new MockDataManager<>(List.of(new Room(1, 1))));
    }

    @GetMapping("/rooms")
    public Response<List<Room>> GetAllRooms() {
        return ResponseWrapper.Wrap(dataManager.GetAll());
    }

    @GetMapping("/room")
    public Response<Room> GetRoom(@RequestParam(value = "id", defaultValue = "1") int id) {
        return ResponseWrapper.Wrap(dataManager.GetOne(Map.of("id", id)));
    }

    @PostMapping("/room")
    public Response<Room> AddRoom(@RequestBody Room newRoom) {
        return processOperation(() -> dataManager.Add(newRoom));
    }
}
