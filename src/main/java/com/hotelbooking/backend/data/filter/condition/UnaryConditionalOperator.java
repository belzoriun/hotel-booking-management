package com.hotelbooking.backend.data.filter.condition;

import com.hotelbooking.backend.data.filter.condition.evaluate.EvaluatorVariable;
import com.hotelbooking.backend.data.filter.exception.InvalidOperationException;

import java.lang.reflect.Type;
import java.util.List;

public enum UnaryConditionalOperator implements ConditionalOperator{
    NOT(a->a.not());

    private final OperatorExecution executor;

    private interface OperatorExecution {
        public boolean execute(EvaluatorVariable a) throws InvalidOperationException;
    }

    private UnaryConditionalOperator(OperatorExecution op) {
        this.executor = op;
    }

    public boolean execute(EvaluatorVariable a) throws InvalidOperationException {
        return executor.execute(a);
    }
}
