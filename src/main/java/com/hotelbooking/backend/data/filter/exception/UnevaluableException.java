package com.hotelbooking.backend.data.filter.exception;

import com.hotelbooking.backend.data.filter.condition.variable.ValueTypeHint;

import java.lang.reflect.Type;
import java.util.List;

public class UnevaluableException extends Exception{
    public UnevaluableException(List<Type> expected, Type got) {
        super("Evaluation or execution expected one of types "+expected.toString()+", got "+got.getTypeName());
    }
}
