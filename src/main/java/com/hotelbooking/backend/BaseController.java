package com.hotelbooking.backend;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.DataManager;
import com.hotelbooking.backend.data.OperationResult;
import com.hotelbooking.backend.utils.OperationExecutor;
import com.hotelbooking.backend.utils.Response;
import com.hotelbooking.backend.utils.ResponseWrapper;
import jakarta.annotation.PreDestroy;

public abstract class BaseController<T extends DataEntity, FT> {
    protected final DataManager<T, FT> dataManager;

    protected BaseController(DataManager<T, FT> dataManager) {
        this.dataManager = dataManager;
    }

    protected Response<T> processOperation(OperationExecutor operation) {
        OperationResult res = operation.execute();
        if (res == OperationResult.DONE) {
            return ResponseWrapper.WrapSuccess();
        }
        return ResponseWrapper.WrapError(res.GetExplanation());
    }

    @PreDestroy
    public void destroy() {
        this.dataManager.exit();
    }
}
