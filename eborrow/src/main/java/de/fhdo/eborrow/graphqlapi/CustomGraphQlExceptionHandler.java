package de.fhdo.eborrow.graphqlapi;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class CustomGraphQlExceptionHandler{
	@GraphQlExceptionHandler
	public GraphQLError handleResponseStatusException(ResponseStatusException ex) {
		GraphqlErrorBuilder<? extends GraphqlErrorBuilder<?>> error = GraphqlErrorBuilder.newError();
		String reason = ex.getReason();
		error.message(reason != null ? reason : "");

		int value = ex.getStatusCode().value();

		if (value == HttpStatus.BAD_REQUEST.value()) {
			error.errorType(ErrorType.BAD_REQUEST);
		} else if (value == HttpStatus.UNAUTHORIZED.value()) {
			error.errorType(ErrorType.UNAUTHORIZED);
		} else if (value == HttpStatus.FORBIDDEN.value()) {
			error.errorType(ErrorType.FORBIDDEN);
		} else if (value == HttpStatus.NOT_FOUND.value()) {
			error.errorType(ErrorType.NOT_FOUND);
		} else if (value == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			error.errorType(ErrorType.INTERNAL_ERROR);
		}

		return error.build();
	}
}

