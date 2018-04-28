package com.github.gmcoringa.coordinator.core.rest;

import com.github.gmcoringa.coordinator.core.zookeeper.exception.ZNodeModificationNotAllowed;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings("unused")
@ControllerAdvice
class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ZNodeModificationNotAllowed.class)
	ResponseEntity<Object> notAllowed(ZNodeModificationNotAllowed e, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return handleExceptionInternal(e, e.getMessage(), headers, HttpStatus.FORBIDDEN, request);
	}

}
