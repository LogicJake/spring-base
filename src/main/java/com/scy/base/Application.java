package com.scy.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

import com.scy.base.util.exception.TokenErrorException;

@SpringBootApplication
@ImportResource("classpath*:config/spring/**.xml")
public class Application extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	//可以用该方法获得拦截器中设置的Attribute值
	public static Integer getUserId(HttpServletRequest request) throws TokenErrorException {
		Object userId = request.getAttribute("userId");
		if (userId != null) {
			return (Integer) userId;
		}
		throw new TokenErrorException();
	}
}
