package com.hotelbooking.backend.data.filter.condition.evaluate;

import com.hotelbooking.backend.data.filter.condition.BinaryConditionalOperator;
import com.hotelbooking.backend.data.filter.condition.variable.Variable;

public record Node(BinaryConditionalOperator operator, Variable var, Node left, Node right) {

}
