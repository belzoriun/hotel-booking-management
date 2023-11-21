package com.hotelbooking.backend.data.query.builder;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.EntityField;
import com.hotelbooking.backend.data.EntityJoin;
import com.hotelbooking.backend.data.query.condition.*;
import com.hotelbooking.backend.vendor.co.streamx.fluent.extree.expression.*;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class QueryBuilder<T extends DataEntity> {
    private final Class<T> baseEntity;
    private final List<Field> joins;
    private Condition<Boolean> condition = new Constant<>(true);

    public QueryBuilder(Class<T> entity) {
        this.baseEntity = entity;
        this.joins = new ArrayList<>();
    }

    public QueryBuilder<T> join(Field join) {
        if(join.isAnnotationPresent(EntityJoin.class)) {
            this.joins.add(join);
        }
        return this;
    }

    public QueryBuilder<T> join(FieldGetter<? extends DataEntity> join) {
        LambdaExpression<FieldGetter<? extends DataEntity>> parsed = LambdaExpression.parse(join);
        Expression body = parsed.getBody();
        while (body instanceof UnaryExpression)
            body = ((UnaryExpression) body).getFirst();
        if(body instanceof MemberExpression memberExp) {
            Member member = memberExp.getMember();
            if (member instanceof Field field && field.isAnnotationPresent(EntityJoin.class)) {
                this.joins.add(field);
            }
        }
        return this;
    }

    public QueryBuilder<T> where(Condition<Boolean> condition) {
        this.condition = condition;
        return this;
    }

    public QueryBuilder<T> where(ConditionPredicate<? extends DataEntity> cond) {
        this.condition = new ConditionBuilder(cond).get();
        return this;
    }

    public Class<T> getSelectedEntity() {
        return this.baseEntity;
    }

    public List<Field> getJoins() {
        return this.joins;
    }

    public Condition<Boolean> getConditions() {
        return this.condition;
    }
}
