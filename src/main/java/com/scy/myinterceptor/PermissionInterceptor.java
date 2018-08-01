package com.scy.myinterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.scy.myinterceptor.service.AuthorizeService;
import com.scy.myinterceptor.util.Utility;

@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {
	
	private static List<Pattern> URL_REGEX_PATTERN_OPEN = new ArrayList<Pattern>();
	// 正则匹配：不需要验证的url
	private static String[] URL_WITHOUT_CLAIMS = { "\\/[vV]\\d_\\d\\/account\\/createAnonymous" };
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
				// throw new TokenErrorException();
			} else if (auth == AuthorizeService.EXPIRED) {
				// throw new TokenExpiredException();
			}
			
			return true;
		}
	}
}
