package com.hotelbooking.backend.data.filter.condition;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.filter.Evaluable;
import com.hotelbooking.backend.data.filter.condition.evaluate.Evaluator;
import com.hotelbooking.backend.data.filter.condition.evaluate.EvaluatorVariable;
import com.hotelbooking.backend.data.filter.condition.evaluate.ExecutionContext;
import com.hotelbooking.backend.data.filter.condition.variable.ValueTypeHint;
import com.hotelbooking.backend.data.filter.exception.InvalidOperationException;
import com.hotelbooking.backend.data.filter.exception.UnevaluableException;

public record UnaryCondition(Evaluable inner, UnaryConditionalOperator operator) implements Evaluable {

    @Override
    public <T extends DataEntity> void evaluate(Evaluator<T> evaluator) throws Exception {
        evaluator.evaluateOperator(this.operator);
        evaluator.evaluate(this.inner);
    }

    @Override
    public <T extends DataEntity> EvaluatorVariable execute(ExecutionContext<T> context) throws InvalidOperationException {
        EvaluatorVariable value = inner().execute(context);
        return new EvaluatorVariable(operator.execute(value), ValueTypeHint.BOOLEAN);
    }
}
