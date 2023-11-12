package com.hotelbooking.backend.data.filter.condition.variable;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.filter.condition.evaluate.Evaluator;
import com.hotelbooking.backend.data.filter.condition.evaluate.EvaluatorVariable;
import com.hotelbooking.backend.data.filter.condition.evaluate.ExecutionContext;

public class Constant<T> implements Variable<T> {

    private T value;
    private final ValueTypeHint type;

    public Constant(ValueTypeHint type) {
        this.type = type;
    }

    public Constant<T> set(T value) {
        this.value = value;
        return this;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public <T extends DataEntity> void evaluate(Evaluator<T> evaluator) {
        evaluator.evaluateVariable(value, type);
    }

    @Override
    public <T extends DataEntity> EvaluatorVariable execute(ExecutionContext<T> context) {
        return new EvaluatorVariable(value, type);
    }
}
