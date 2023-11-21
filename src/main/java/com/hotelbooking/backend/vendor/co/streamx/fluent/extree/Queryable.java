package com.hotelbooking.backend.vendor.co.streamx.fluent.extree;

import com.hotelbooking.backend.vendor.co.streamx.fluent.extree.expression.*;

/*public*/ interface Queryable<S> extends Iterable<S> {
	Class<S> getElementType();

	Expression getExpression();

	QueryableFactory getFactory();

	Iterable<S> iterable();

	S single();
}
