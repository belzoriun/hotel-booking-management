package com.hotelbooking.backend.data.filter.condition.variable;

import com.hotelbooking.backend.data.filter.condition.BinaryConditionalOperator;
import com.hotelbooking.backend.data.filter.condition.UnaryConditionalOperator;
import com.hotelbooking.backend.data.filter.exception.InvalidOperationException;

import javax.lang.model.type.NullType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum ValueTypeHint {
    NUMERIC((a, b)->{ try {
            return Double.compare((double) a, (double) b);
        } catch(Exception e) {
            throw new InvalidOperationException();
        }
    }),
    STRING((a, b)->{ try {
            return ((String) a).compareTo((String)b);
        } catch(Exception e) {
            throw new InvalidOperationException();
        }
    }),
    BOOLEAN((a, b)->{ try {
            return Boolean.compare((boolean) a, (boolean) b);
        } catch(Exception e) {
            throw new InvalidOperationException();
        }
    }), NULL((a, b)->0);

    private interface Comparator {
        public int compare(Object a, Object b) throws InvalidOperationException;
    }

    private final Comparator comp;

    private ValueTypeHint(Comparator cmp) {
        this.comp = cmp;
    }

    public int compare(Object a, Object b) throws InvalidOperationException {
        return comp.compare(a, b);
    }
}
