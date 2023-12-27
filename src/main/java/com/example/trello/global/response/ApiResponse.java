package com.example.trello.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;

@Getter
public class ApiResponse< T > {
	private boolean success = false;
	private HttpStatus httpStatus;
	private ResponseBody responseBody;

	public ApiResponse( HttpStatus httpStatus, ResponseBody responseBody ) {
		this.httpStatus = httpStatus;
		if( httpStatus.is2xxSuccessful() ) {
			this.success = true;
		}
		this.responseBody = responseBody;
	}

	public static < T > ApiResponse< T > ok( T data ) {
		return new ApiResponse< T >(
				HttpStatus.OK,
				new ResponseBody( data, null )
		);
	}

	public static < T > ApiResponse< T > fail( ErrorResponse errorResponse ) {
		return new ApiResponse< T >(
				HttpStatus.valueOf( errorResponse.getStatusCode().value() ),
				new ResponseBody( null, errorResponse )
		);
	}
}
