package com.hotelbooking.backend.data.filter.condition.variable;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.filter.condition.evaluate.Evaluator;
import com.hotelbooking.backend.data.filter.condition.evaluate.EvaluatorVariable;
import com.hotelbooking.backend.data.filter.condition.evaluate.ExecutionContext;
import com.hotelbooking.backend.data.filter.condition.variable.Variable;

import java.util.Optional;

public class Dynamic implements Variable<Object> {

    private String name;

    public Dynamic(String name) {
        this.name = name;
    }

    @Override
    public <T extends DataEntity> void evaluate(Evaluator<T> evaluator) throws Exception {
        evaluator.evaluateVariable(name, ValueTypeHint.STRING);
    }

    @Override
    public Variable<Object> set(Object value) {
        return this;
    }

    @Override
    public Object get() {
        return null;
    }
    @Override
    public <T extends DataEntity> EvaluatorVariable execute(ExecutionContext<T> context) {
        Optional<EvaluatorVariable> value = context.get(name);
        return value.orElse(new EvaluatorVariable(null, ValueTypeHint.NULL));
    }

    public String toString() {
        return name;
    }
}
