package com.hotelbooking.backend.data.filter.condition;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.filter.Evaluable;
import com.hotelbooking.backend.data.filter.condition.evaluate.Evaluator;
import com.hotelbooking.backend.data.filter.condition.evaluate.EvaluatorVariable;
import com.hotelbooking.backend.data.filter.condition.evaluate.ExecutionContext;
import com.hotelbooking.backend.data.filter.condition.variable.ValueTypeHint;
import com.hotelbooking.backend.data.filter.condition.variable.Variable;
import com.hotelbooking.backend.data.filter.exception.InvalidOperationException;
import com.hotelbooking.backend.data.filter.exception.UnevaluableException;

import java.lang.reflect.Type;

public record BinaryCondition(Evaluable left, BinaryConditionalOperator operator, Evaluable right) implements Evaluable {

    @Override
    public <T extends DataEntity> void evaluate(Evaluator<T> evaluator) throws Exception {
        evaluator.evaluate(this.left);
        evaluator.evaluateOperator(this.operator);
        evaluator.evaluate(this.right);
    }

    @Override
    public <T extends DataEntity> EvaluatorVariable execute(ExecutionContext<T> context) throws InvalidOperationException {
        EvaluatorVariable l = left().execute(context);
        EvaluatorVariable r = right().execute(context);
        return new EvaluatorVariable(operator.execute(l, r), ValueTypeHint.BOOLEAN);
    }
}
