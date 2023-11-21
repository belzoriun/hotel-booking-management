package com.hotelbooking.backend.vendor.co.streamx.fluent.extree;

import com.hotelbooking.backend.vendor.co.streamx.fluent.extree.expression.*;

/*public*/ interface QueryableFactory {
	<S> Queryable<S> createQueryable(Class<S> type, Expression e);
}
