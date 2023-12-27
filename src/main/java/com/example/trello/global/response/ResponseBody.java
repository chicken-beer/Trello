package com.example.trello.global.response;

import lombok.Getter;
import org.springframework.web.ErrorResponse;

@Getter
public class ResponseBody< T > {
	private final T data;
	private final ErrorResponse errorResponse;

	public ResponseBody( T data, ErrorResponse errorResponse ) {
		this.data = data;
		this.errorResponse = errorResponse;
	}
}
