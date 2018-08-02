package com.scy.base;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.scy.base.service.AuthorizeService;
import com.scy.base.util.Utility;
import com.scy.base.util.exception.TokenErrorException;
import com.scy.base.util.exception.TokenExpiredException;

@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {
	
	private static List<Pattern> URL_REGEX_PATTERN_OPEN = new ArrayList<Pattern>();
	// 正则匹配：不需要验证的url
	private static String[] URL_WITHOUT_CLAIMS = { "\\/[vV]\\d_\\d\\/account\\/createUser" };
	static {
		URL_REGEX_PATTERN_OPEN = new ArrayList<Pattern>();
		for (String openUrl : URL_WITHOUT_CLAIMS) {
			URL_REGEX_PATTERN_OPEN.add(Pattern.compile(openUrl));
		}
	}
	
	@Autowired
	AuthorizeService authorizeService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (Utility.matchPattern(request.getServletPath(), URL_REGEX_PATTERN_OPEN)) {
			return true;
		} else {
			int auth = authorizeService.auth(request);
			
			if (auth == AuthorizeService.FAILURE) {
				 throw new TokenErrorException("认证失败");
			} else if (auth == AuthorizeService.EXPIRED) {
				 throw new TokenExpiredException("token过期");
			}
			/*
			 * 当返回值为true 时就会继续调用下一个Intercepter 的preHandle 方法
			 * 如果已经是最后一个Intercepter 的时候就会是调用当前请求的Controller 方法
			 */
			return true;
		}
	}
}
