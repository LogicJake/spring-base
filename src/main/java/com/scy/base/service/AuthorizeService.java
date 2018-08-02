package com.scy.base.service;

import java.io.UnsupportedEncodingException;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class AuthorizeService {
	public final static int SUCCESS = 0;
	public final static int FAILURE = -1;
	public final static int EXPIRED = -2;
	//过期时间
	public static final long SIX_MONTH_MILLIS = 7 * 24 * 60 * 60 * 1000L;

	// 存放token的请求头名称
	public static final String AUTHORIZATION_DEMO = "Authorization-DEMO";
	
	private static final String DEMO_ACCESS_TOKEN = "myToken";
	
	public int auth(HttpServletRequest request) throws IllegalArgumentException, UnsupportedEncodingException {
		
		String token = request.getHeader(AUTHORIZATION_DEMO);
		if (token == null || token.isEmpty()) {
			return FAILURE;
		}
		
		try {
			
			DecodedJWT decodedJWT = JWT.decode(token);
			Algorithm algorithm = Algorithm.HMAC256(DEMO_ACCESS_TOKEN);
			algorithm.verify(decodedJWT);
			
			// 几种get方式
			Integer userId = Integer.valueOf(decodedJWT.getId());
			System.out.println(userId);
			request.setAttribute("userId", userId);
			Integer userType = Integer.valueOf(decodedJWT.getKeyId());
			request.setAttribute("userType", userType);
			// Integer appType = decodedJWT.getClaim("appType").asInt();
			
			// 检查token有没有过期
			if (System.currentTimeMillis() >= decodedJWT.getExpiresAt().getTime()) {
				return EXPIRED;
			}
			
			//可以多加一步验证，将uid存储在数据库中，进行对比uid是否和token对应上，以防伪造token。
			
		} catch (Exception e) {
			return FAILURE;
		}
		
		return SUCCESS;
	}
	
	public String createToken(Integer userType, Integer userId, String userName)
			throws IllegalArgumentException, UnsupportedEncodingException {
		Algorithm algorithm = Algorithm.HMAC256(DEMO_ACCESS_TOKEN);		//加密算法
		String token = JWT.create()
				.withKeyId(String.valueOf(userType))
				.withJWTId(String.valueOf(userId))
				.withClaim("name", userName)
				.withExpiresAt(new Date(System.currentTimeMillis() + SIX_MONTH_MILLIS))
				.sign(algorithm);
		return token;
	}
}
