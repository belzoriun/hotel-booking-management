package com.hotelbooking.backend.data.filter.condition.evaluate;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.filter.condition.variable.ValueTypeHint;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ExecutionContext<T extends DataEntity>{
    private T reference;
    private Map<String, EvaluatorVariable> variables;

    public ExecutionContext(T reference) {
        this.reference = reference;
        this.variables = new HashMap<>();
    }

    public Optional<EvaluatorVariable> get(String name) {
        return Optional.of(variables.getOrDefault(name, null));
    }

    public ExecutionContext<T> setVariable(String name, Object value, ValueTypeHint type) {
        this.variables.put(name, new EvaluatorVariable(value, type));
        return this;
    }

    public T getReference() {
        return reference;
    }

    public void setReference(T evaluationReference) {
        this.reference = reference;
    }
}
