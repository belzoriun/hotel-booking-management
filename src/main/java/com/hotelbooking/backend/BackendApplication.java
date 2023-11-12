package com.hotelbooking.backend;

import com.hotelbooking.backend.data.filter.Evaluable;
import com.hotelbooking.backend.data.filter.condition.BinaryConditionBuilder;
import com.hotelbooking.backend.data.filter.condition.BinaryConditionalOperator;
import com.hotelbooking.backend.data.filter.condition.evaluate.BasicEvaluator;
import com.hotelbooking.backend.data.filter.condition.evaluate.Evaluator;
import com.hotelbooking.backend.data.filter.condition.evaluate.EvaluatorVariable;
import com.hotelbooking.backend.data.filter.condition.evaluate.ExecutionContext;
import com.hotelbooking.backend.data.filter.condition.variable.Constant;
import com.hotelbooking.backend.data.filter.condition.variable.Dynamic;
import com.hotelbooking.backend.data.filter.condition.variable.ValueTypeHint;
import com.hotelbooking.backend.data.filter.exception.InvalidOperationException;
import com.hotelbooking.backend.room.Room;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		//SpringApplication.run(BackendApplication.class, args);
		try {
			Evaluable ev = new BinaryConditionBuilder()
					.setRight(new Constant<Integer>(ValueTypeHint.NUMERIC).set(1))
					.setLeft(new Constant<Integer>(ValueTypeHint.NUMERIC).set(2))
					.setOperator(BinaryConditionalOperator.EQUAL)
					.or(
							new BinaryConditionBuilder()
									.setRight(new Dynamic("var"))
									.setLeft(new Constant<Integer>(ValueTypeHint.NUMERIC).set(10))
									.setOperator(BinaryConditionalOperator.DIFFERENT)
									.or(new Constant<Boolean>(ValueTypeHint.BOOLEAN).set(true))
									.build()
					).build();
			ExecutionContext<Room> context = new ExecutionContext<Room>(null)
					.setVariable("var", 10, ValueTypeHint.NUMERIC);

			Evaluator<Room> eval = new BasicEvaluator<>(context);
			ev.evaluate(eval);
			System.out.println(eval.extract());
			EvaluatorVariable result = ev.execute(context);
			System.out.println(result.value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
