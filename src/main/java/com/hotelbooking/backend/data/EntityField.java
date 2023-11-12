package com.hotelbooking.backend.data;

import com.hotelbooking.backend.data.filter.condition.variable.ValueTypeHint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EntityField {
    public String name();
    public ValueTypeHint type();
}
