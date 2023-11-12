package com.hotelbooking.backend.data.filter;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.filter.condition.evaluate.Evaluator;
import com.hotelbooking.backend.data.filter.condition.evaluate.EvaluatorVariable;
import com.hotelbooking.backend.data.filter.condition.evaluate.ExecutionContext;
import com.hotelbooking.backend.data.filter.exception.InvalidOperationException;
import com.hotelbooking.backend.data.filter.exception.UnevaluableException;

public interface Evaluable {
    public<T extends DataEntity> void evaluate(Evaluator<T> evaluator) throws Exception;

    public<T extends DataEntity> EvaluatorVariable execute(ExecutionContext<T> context) throws InvalidOperationException;
}
