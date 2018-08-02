package com.scy.base.util.exception;

import org.apache.tomcat.websocket.AuthenticationException;

public class TokenExpiredException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenExpiredException(String message) {
		super(message);
	}
	
}
