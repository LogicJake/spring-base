package com.scy.base.util.exception;

import org.apache.tomcat.websocket.AuthenticationException;

public class TokenErrorException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenErrorException(String message) {
		super(message);
	}

	public TokenErrorException() {
		super(null);
	}
	
}
