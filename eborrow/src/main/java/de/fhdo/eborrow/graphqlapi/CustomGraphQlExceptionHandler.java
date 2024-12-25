package de.fhdo.eborrow.graphqlapi;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import javassist.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
public class CustomGraphQlExceptionHandler {
	@GraphQlExceptionHandler
	public GraphQLError mapToResponseStatusException(Exception ex) {
		HttpStatus status;
		String message;
		if (ex instanceof NotFoundException) {
			status = HttpStatus.NOT_FOUND;
			message = ex.getMessage();
		} else if (ex instanceof IllegalArgumentException) {
			status = HttpStatus.BAD_REQUEST;
			message = "Illegal argument passed: " + ex.getMessage();
		} else if (ex instanceof MethodArgumentNotValidException) {
			status = HttpStatus.BAD_REQUEST;
			message = "Validation failed: " + ex.getMessage();
		} else if (ex instanceof HandlerMethodValidationException) {
			status = HttpStatus.BAD_REQUEST;
			message = "Validation failed: " + ex.getMessage();
		} else if (ex instanceof DataIntegrityViolationException) {
			status = HttpStatus.CONFLICT;
			message = "Data integrity violation: " + ex.getMessage();
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = "Unexpected error: " + ex.getMessage();
		}

		return GraphqlErrorBuilder.newError().message(message).errorType(httpStatusToErrorType(status)).build();
	}

	private ErrorType httpStatusToErrorType(HttpStatus status) {
		if (status == HttpStatus.BAD_REQUEST) {
			return ErrorType.BAD_REQUEST;
		} else if (status == HttpStatus.UNAUTHORIZED) {
			return ErrorType.UNAUTHORIZED;
		} else if (status == HttpStatus.FORBIDDEN) {
			return ErrorType.FORBIDDEN;
		} else if (status == HttpStatus.NOT_FOUND) {
			return ErrorType.NOT_FOUND;
		} else if (status == HttpStatus.CONFLICT) {
			return ErrorType.FORBIDDEN;
		} else {
			return ErrorType.INTERNAL_ERROR;
		}
	}
}

