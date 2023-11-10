package com.hotelbooking.backend;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.DataManager;
import com.hotelbooking.backend.data.MockDataManager;
import com.hotelbooking.backend.data.OperationResult;
import com.hotelbooking.backend.room.Room;
import com.hotelbooking.backend.utils.OperationExecutor;
import com.hotelbooking.backend.utils.Response;
import com.hotelbooking.backend.utils.ResponseWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public abstract class BaseController<T extends DataEntity> {
    protected final DataManager<T> dataManager;

    protected BaseController(DataManager<T> dataManager) {
        this.dataManager = dataManager;
    }

    protected Response<T> processOperation(OperationExecutor operation) {
        OperationResult res = operation.execute();
        if (res == OperationResult.DONE) {
            return ResponseWrapper.WrapSuccess();
        }
        return ResponseWrapper.WrapError(res.GetExplanation());
    }
}
