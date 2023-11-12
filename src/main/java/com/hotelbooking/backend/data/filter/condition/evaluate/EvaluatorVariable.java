package com.hotelbooking.backend.data.filter.condition.evaluate;

import com.hotelbooking.backend.data.filter.condition.variable.ValueTypeHint;
import com.hotelbooking.backend.data.filter.exception.InvalidOperationException;

public class EvaluatorVariable implements Comparable<EvaluatorVariable> {
    public Object value;
    public ValueTypeHint type;

    public EvaluatorVariable(Object value, ValueTypeHint type){
        this.value = value;
        this.type = type;
    };

    @Override
    public int compareTo(EvaluatorVariable o) {
        try {
            return type.compare(value, o.value);
        } catch (InvalidOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(EvaluatorVariable o) {
        return type.equals(o.type) && value.equals(o.value);
    }

    public boolean and(EvaluatorVariable o) throws InvalidOperationException {
        if(this.type.equals(ValueTypeHint.BOOLEAN) && o.type.equals(ValueTypeHint.BOOLEAN)) return (boolean)value && (boolean)o.value;
        throw new InvalidOperationException();
    }

    public boolean or(EvaluatorVariable o) throws InvalidOperationException {
        if(this.type.equals(ValueTypeHint.BOOLEAN) && o.type.equals(ValueTypeHint.BOOLEAN)) return (boolean)value || (boolean)o.value;
        throw new InvalidOperationException();
    }

    public boolean not() throws InvalidOperationException {
        if(!this.type.equals(ValueTypeHint.BOOLEAN)) throw new InvalidOperationException();
        return !(boolean)value;
    }
}
