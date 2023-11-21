package com.hotelbooking.backend.data.query.condition.parsing;

import com.hotelbooking.backend.data.EntityField;
import com.hotelbooking.backend.data.query.condition.*;
import com.hotelbooking.backend.vendor.co.streamx.fluent.extree.expression.*;

import java.util.ArrayList;
import java.util.List;

import static com.hotelbooking.backend.vendor.co.streamx.fluent.extree.expression.ExpressionType.*;

public class ToConditionVisitor implements ExpressionVisitor<Condition<?>> {

    private List<ConstantExpression> parameters = new ArrayList<>();

    @Override
    public Condition<Boolean> visit(BinaryExpression binaryExpression) {
        switch(binaryExpression.getExpressionType()) {
            case Equal: return new Equal(binaryExpression.getFirst().accept(this), binaryExpression.getSecond().accept(this));
            case LogicalAnd: return new And(binaryExpression.getFirst().accept(this), binaryExpression.getSecond().accept(this));
            case LogicalOr: return new Or(binaryExpression.getFirst().accept(this), binaryExpression.getSecond().accept(this));
            case NotEqual: return new Not(new Equal(binaryExpression.getFirst().accept(this), binaryExpression.getSecond().accept(this)));
            case GreaterThan: return new Greater(binaryExpression.getFirst().accept(this), binaryExpression.getSecond().accept(this));
            case LessThan: return new Lower(binaryExpression.getFirst().accept(this), binaryExpression.getSecond().accept(this));
            case GreaterThanOrEqual: return new Or(
                    new Equal(binaryExpression.getFirst().accept(this), binaryExpression.getSecond().accept(this)),
                    new Greater(binaryExpression.getFirst().accept(this), binaryExpression.getSecond().accept(this))
            );
            case LessThanOrEqual: return new Or(
                    new Equal(binaryExpression.getFirst().accept(this), binaryExpression.getSecond().accept(this)),
                    new Lower(binaryExpression.getFirst().accept(this), binaryExpression.getSecond().accept(this))
            );
            default: return new Constant<>(true);
        }
    }

    @Override
    public Condition<?> visit(ConstantExpression constantExpression) {
        return new Constant<>(constantExpression.getValue());
    }

    @Override
    public Condition<?> visit(InvocationExpression invocationExpression) {
        invocationExpression.getArguments().stream().filter(x -> x instanceof ConstantExpression).forEach(x -> parameters.add((ConstantExpression) x));
        return invocationExpression.getTarget().accept(this);
    }

    @Override
    public Condition<?> visit(LambdaExpression<?> lambdaExpression) {
        return lambdaExpression.getBody().accept(this);
    }

    @Override
    public Condition<?> visit(DelegateExpression delegateExpression) {
        return delegateExpression.getDelegate().accept(this);
    }

    @Override
    public Condition<?> visit(MemberExpression memberExpression) {
        String name = memberExpression.getMember().getName();
        name = name.replaceAll("^(get)", "");
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        try {
            if(memberExpression.getMember().getDeclaringClass().getField(name).isAnnotationPresent(EntityField.class)) {
                return new Field(
                        memberExpression.getMember().getDeclaringClass(),
                        memberExpression.getMember().getDeclaringClass().getField(name).getAnnotation(EntityField.class).name()
                );
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Encountered field "+name+" that is not an entity field, maybe you used an incorrectly named getter or forgot the @EntityField annotation");
    }

    @Override
    public Condition<?> visit(ParameterExpression parameterExpression) {
        return parameters.get(parameterExpression.getIndex()).accept(this);
    }

    @Override
    public Condition<?> visit(UnaryExpression unaryExpression) {
        switch(unaryExpression.getExpressionType()) {
            case LogicalNot: return new Not(unaryExpression.getFirst().accept(this));
            default: return new Constant<>(true);
        }
    }

    @Override
    public Condition<?> visit(BlockExpression e) {
        return new Constant<>(true);
    }

    @Override
    public Condition<?> visit(NewArrayInitExpression e) {
        return new Constant<>(true);
    }
}
