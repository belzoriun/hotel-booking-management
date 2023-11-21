package com.hotelbooking.backend.data.query.condition.parsing;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.query.condition.Condition;
import com.hotelbooking.backend.data.query.condition.ConditionPredicate;
import com.hotelbooking.backend.vendor.co.streamx.fluent.extree.expression.LambdaExpression;

import java.util.function.Predicate;

public class Lambda2Condition {
    public static <T extends DataEntity> Condition<Boolean> toCondition(ConditionPredicate<T> predicate) {
        LambdaExpression<Predicate<T>> expression = LambdaExpression.parse(predicate);
        return (Condition<Boolean>) expression.accept(new ToConditionVisitor());
    }
}
