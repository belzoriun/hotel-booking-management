package com.hotelbooking.backend.data.filter.condition.evaluate;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.filter.Evaluable;
import com.hotelbooking.backend.data.filter.condition.BinaryCondition;
import com.hotelbooking.backend.data.filter.condition.BinaryConditionalOperator;
import com.hotelbooking.backend.data.filter.condition.ConditionalOperator;
import com.hotelbooking.backend.data.filter.condition.UnaryCondition;
import com.hotelbooking.backend.data.filter.condition.variable.ValueTypeHint;

public class BasicEvaluator<T extends DataEntity> extends Evaluator<T>{

    private String value = "";
    private int missingParenteses = 0;
    private boolean passedOperator = false;

    public BasicEvaluator(ExecutionContext<T> context) {
        super(context);
    }

    public void evaluate(Evaluable cond) throws Exception {
        if(cond instanceof BinaryCondition || cond instanceof UnaryCondition) {
            value += "(";
            missingParenteses++;
            passedOperator = false;
        }
        super.evaluate(cond);
    }

    @Override
    public void evaluateOperator(ConditionalOperator operator) {
        if(operator instanceof BinaryConditionalOperator op) {
            value += switch(op) {
                case OR -> "||";
                case AND -> "&&";
                case EQUAL -> "==";
                case GREATER -> ">";
                case LOWER -> "<";
                case GREATER_OR_EQUAL -> ">=";
                case LOWER_OR_EQUAL -> "<=";
                case DIFFERENT -> "!=";
            } + " ";
        }
        passedOperator = true;
    }

    @Override
    public void evaluateVariable(Object value, ValueTypeHint type) {
        this.value += value.toString()+ (missingParenteses > 0 && passedOperator ? ")" : "") +" ";
        if(missingParenteses > 0 && passedOperator) missingParenteses --;
        passedOperator = false;
    }

    @Override
    public void evaluateSelector(String selectedField) {

    }

    @Override
    public String extract() {
        return value;
    }
}
