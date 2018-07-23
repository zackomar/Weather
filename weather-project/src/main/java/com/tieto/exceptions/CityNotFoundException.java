package com.tieto.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4424647233215431047L;

	public CityNotFoundException(String exception) {
		super(exception);
	}
}
