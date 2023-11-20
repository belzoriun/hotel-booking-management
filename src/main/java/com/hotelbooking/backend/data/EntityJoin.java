package com.hotelbooking.backend.data;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EntityJoin {
    String ownerField();
    String joinedEntityField();
}
