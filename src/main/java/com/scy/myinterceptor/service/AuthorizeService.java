package com.scy.myinterceptor.service;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthorizeService {
	public final static int SUCCESS = 0;
	public final static int FAILURE = -1;
	public final static int EXPIRED = -2;
	
	// 存放token的请求头名称
	public static final String AUTHORIZATION_QS = "Authorization-DEMO";
	
	private static final String QINGSHU_ACCESS_TOKEN = "myToken";
	
	public int auth(HttpServletRequest request) throws IllegalArgumentException, UnsupportedEncodingException {
		
		String token = request.getHeader(AUTHORIZATION_QS);
		
		if (token == null || token.isEmpty()) {
			return FAILURE;
		}
		
		try {
			
			DecodedJWT decodedJWT = JWT.decode(token);
			Algorithm algorithm = Algorithm.HMAC256(QINGSHU_ACCESS_TOKEN);
			algorithm.verify(decodedJWT);
			
			// 几种get方式
			Integer userId = Integer.valueOf(decodedJWT.getId());
			request.setAttribute("userId", userId);
			Integer userType = Integer.valueOf(decodedJWT.getKeyId());
			request.setAttribute("userType", userType);
			Integer appType = decodedJWT.getClaim("appType").asInt();
			
			// 检查token有没有过期
			if (System.currentTimeMillis() >= decodedJWT.getExpiresAt().getTime()) {
				return EXPIRED;
			}
			
		} catch (Exception e) {
			return FAILURE;
		}
		
		return SUCCESS;
	}
	
}
