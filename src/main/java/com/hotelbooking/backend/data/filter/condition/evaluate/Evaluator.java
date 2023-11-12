package com.hotelbooking.backend.data.filter.condition.evaluate;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.filter.condition.BinaryConditionalOperator;
import com.hotelbooking.backend.data.filter.Evaluable;
import com.hotelbooking.backend.data.filter.condition.ConditionalOperator;
import com.hotelbooking.backend.data.filter.condition.variable.ValueTypeHint;

import java.util.Optional;

public abstract class Evaluator<T extends DataEntity> {

    protected ExecutionContext<T> context;

    protected Evaluator(ExecutionContext<T> context) {
        this.context = context;
    }

    public void evaluate(T evaluationReference, Evaluable cond) throws Exception {
        this.context.setReference(evaluationReference);
        cond.evaluate(this);
    }

    public ExecutionContext<T> getEvaluationContext() {
        return context;
    }

    public Optional<EvaluatorVariable> getVariableIfExist(String name) {
        return context.get(name);
    }

    public void evaluate(Evaluable cond) throws Exception {
        cond.evaluate(this);
    }

    public abstract void evaluateOperator(ConditionalOperator operator);

    public abstract void evaluateVariable(Object value, ValueTypeHint type);

    public abstract void evaluateSelector(String selectedField);

    public abstract String extract();
}
