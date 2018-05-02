package edu.njit.cs631.fitness.tools;

import org.hibernate.dialect.PostgreSQL94Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

public class FitnessSQLDialect extends PostgreSQL94Dialect {

	public FitnessSQLDialect() {
		super();
		registerFunction("INSTRUCTOR_HOURS_BY_TIME_INTERVAL", new StandardSQLFunction("INSTRUCTOR_HOURS_BY_TIME_INTERVAL"));
	}

}
