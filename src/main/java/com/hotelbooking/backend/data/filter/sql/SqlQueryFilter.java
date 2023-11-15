package com.hotelbooking.backend.data.filter.sql;

import com.hotelbooking.backend.data.DataEntity;
import com.hotelbooking.backend.data.EntityField;
import com.hotelbooking.backend.data.filter.QueryCommand;
import com.hotelbooking.backend.data.stream.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlQueryFilter implements QueryCommand<String> {

    private SqlCommandType command;
    private List<Field> selectors;
    private DataEntity from;
    private Map<Class<DataEntity>, JoinCheck> joins;
    private String where;
    private String having;
    private List<Field> groupBy;
    private List<Field> update;

    public SqlQueryFilter(SqlCommandType command) {
        selectors = new ArrayList<>();
        joins = new HashMap<>();
        where = null;
        having = null;
        from = null;
        groupBy = new ArrayList<>();
        update = new ArrayList<>();
        this.command = command;
    }

    private StringBuilder createSelect() {
        if(from == null) throw new RuntimeException("'From' reference entity should be set");

        StringBuilder query = new StringBuilder("SELECT ");
        if(selectors.isEmpty()) query.append("* \n");
        else {
            for (Field f : selectors) {
                query.append(f.getType().getAnnotation(Entity.class).name())
                        .append(".")
                        .append(f.getAnnotation(EntityField.class).name())
                        .append(", ");
            }
        }
        query.append(" \n")
                .append("FROM ")
                .append(from.getClass().getAnnotation(Entity.class).name())
                .append(" \n");
        if(!joins.isEmpty()) {
            for (Map.Entry<Class<DataEntity>, JoinCheck> join : joins.entrySet()) {
                query.append("JOIN ")
                        .append(join.getKey().getAnnotation(Entity.class).name())
                        .append(" ON ")
                        .append(join.getValue().left.getType().getAnnotation(Entity.class).name())
                        .append(".")
                        .append(join.getValue().left.getAnnotation(EntityField.class).name())
                        .append(" = ")
                        .append(join.getValue().right.getType().getAnnotation(Entity.class).name())
                        .append(".")
                        .append(join.getValue().right.getAnnotation(EntityField.class).name())
                        .append(" \n");
            }
        }
        if(having != null) {
            query.append("HAVING ")
                    .append(having);
        }
        if(where != null) {
            query.append("WHERE ")
                    .append(where);
        }
        if(!groupBy.isEmpty()) {
            query.append("GROUP BY ");
            for (Field f : groupBy) {
                query.append(f.getType().getAnnotation(Entity.class).name())
                        .append(".")
                        .append(f.getAnnotation(EntityField.class).name());
                if(groupBy.indexOf(f) != groupBy.size()-1) {
                    query.append(", ");
                }
            }
            query.append(" \n");
        }
        return query;
    }

    private StringBuilder createUpdate() {
        if(from == null) throw new RuntimeException("'From' reference entity should be set");

        StringBuilder query = new StringBuilder("UPDATE ")
                .append(from.getClass().getAnnotation(Entity.class).name())
                .append(" \n")
                .append("SET ");
        for (Field f : update) {
            try {
                query.append(f.getAnnotation(EntityField.class).name())
                        .append(" = '")
                        .append(f.get(from))
                        .append("'");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if(update.indexOf(f) != update.size()-1) {
                query.append(", ");
            }
        }
        if(where != null) {
            query.append("WHERE ")
                    .append(where);
        }
        return query;
    }

    @Override
    public String build() {
        return switch(command) {
            case SELECT -> createSelect().toString();
            case UPDATE -> createUpdate().toString();
            case INSERT -> null;
            case DELETE -> null;
        };
    }
}
