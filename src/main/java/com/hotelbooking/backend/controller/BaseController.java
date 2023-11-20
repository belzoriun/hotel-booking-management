package com.hotelbooking.backend.controller;

import com.hotelbooking.backend.data.OperationResult;
import com.hotelbooking.backend.data.stream.DataStream;
import com.hotelbooking.backend.utils.OperationExecutor;
import com.hotelbooking.backend.utils.Response;
import com.hotelbooking.backend.utils.ResponseWrapper;
import jakarta.annotation.PreDestroy;

public abstract class BaseController<T> {
    protected final DataStream dataStream;

    protected BaseController(DataStream dataStream) {
        this.dataStream = dataStream;
        this.dataStream.connect();
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
        this.dataStream.disconnect();
    }
}
