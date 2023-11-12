package com.hotelbooking.backend.data.filter;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.filter.condition.BinaryCondition;
import com.hotelbooking.backend.data.filter.condition.evaluate.Evaluator;
import com.hotelbooking.backend.data.filter.condition.evaluate.EvaluatorVariable;
import com.hotelbooking.backend.data.filter.condition.evaluate.ExecutionContext;
import com.hotelbooking.backend.data.filter.condition.variable.Constant;
import com.hotelbooking.backend.data.filter.condition.variable.ValueTypeHint;
import com.hotelbooking.backend.data.filter.exception.InvalidOperationException;

import java.lang.reflect.Field;
import java.util.List;

public class QueryFilter implements Evaluable{
    private List<String> selectors;
    private Evaluable condition = new Constant<Boolean>(ValueTypeHint.BOOLEAN).set(true);

    public QueryFilter select(Field field) {
        return this;
    }

    @Override
    public <T extends DataEntity> void evaluate(Evaluator<T> evaluator) throws Exception {
        for (String selected : this.selectors) {
            evaluator.evaluateSelector(selected);
        }
        evaluator.evaluate(condition);
    }

    @Override
    public <T extends DataEntity> EvaluatorVariable execute(ExecutionContext<T> context) throws InvalidOperationException {
        return condition.execute(context);
    }
}
