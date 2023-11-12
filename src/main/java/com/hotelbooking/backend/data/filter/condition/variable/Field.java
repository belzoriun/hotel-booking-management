package com.hotelbooking.backend.data.filter.condition.variable;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.EntityField;
import com.hotelbooking.backend.data.filter.condition.evaluate.Evaluator;
import com.hotelbooking.backend.data.filter.condition.evaluate.EvaluatorVariable;
import com.hotelbooking.backend.data.filter.condition.evaluate.ExecutionContext;

public class Field implements Variable<java.lang.reflect.Field> {
    private java.lang.reflect.Field field;

    public Field(java.lang.reflect.Field field) {
        this.field = field;
    }

    @Override
    public <T extends DataEntity> void evaluate(Evaluator<T> evaluator) throws Exception {
        evaluator.evaluateVariable(field.getType().getName()+"."+field.getAnnotation(EntityField.class).name(), field.getAnnotation(EntityField.class).type());
    }

    @Override
    public Field set(java.lang.reflect.Field value) {
        this.field = value;
        return this;
    }

    @Override
    public java.lang.reflect.Field get() {
        return field;
    }

    @Override
    public <T extends DataEntity> EvaluatorVariable execute(ExecutionContext<T> context) {
        try {
            return new EvaluatorVariable(field.get(context.getReference()), field.getAnnotation(EntityField.class).type());
        } catch (Exception e) {
            return new EvaluatorVariable(null, ValueTypeHint.NULL);
        }
    }

    public String toString() {
        return field.getAnnotation(EntityField.class).name();
    }
}
