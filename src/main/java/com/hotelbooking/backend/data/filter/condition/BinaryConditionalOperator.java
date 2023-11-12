package com.hotelbooking.backend.data.filter.condition;

import com.hotelbooking.backend.data.filter.condition.evaluate.EvaluatorVariable;
import com.hotelbooking.backend.data.filter.condition.variable.ValueTypeHint;
import com.hotelbooking.backend.data.filter.exception.InvalidOperationException;
import com.hotelbooking.backend.data.filter.exception.UnevaluableException;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum BinaryConditionalOperator implements ConditionalOperator{
    AND(EvaluatorVariable::and),
    OR(EvaluatorVariable::or),
    EQUAL(EvaluatorVariable::equals),
    GREATER((a, b)->a.compareTo(b) > 0),
    LOWER((a, b)->a.compareTo(b) < 0),
    GREATER_OR_EQUAL((a, b)->a.equals(b) || a.compareTo(b) > 0),
    LOWER_OR_EQUAL((a, b)->a.equals(b) || a.compareTo(b) < 0),
    DIFFERENT((a, b)->!a.equals(b));

    private final OperatorExecution executor;

    private interface OperatorExecution {
        public boolean execute(EvaluatorVariable a, EvaluatorVariable b) throws InvalidOperationException;
    }

    private BinaryConditionalOperator(OperatorExecution op) {
        this.executor = op;
    }

    public boolean execute(EvaluatorVariable a, EvaluatorVariable b) throws InvalidOperationException {
        return this.executor.execute(a, b);
    }
}
