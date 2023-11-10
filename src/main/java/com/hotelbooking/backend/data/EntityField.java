package com.hotelbooking.backend.data;

import org.springframework.lang.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EntityField {
    public String name();
}
