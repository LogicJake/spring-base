package com.scy.base.account;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scy.base.Application;
import com.scy.base.service.AuthorizeService;
import com.scy.base.util.exception.TokenErrorException;
import com.scy.base.util.model.GenericJsonResult;
import com.scy.base.util.model.HResult;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
	@Autowired
	AuthorizeService authorizeService;
	
	@RequestMapping(value = "/createUser", method = RequestMethod.GET)
	public GenericJsonResult<String> createUser(@RequestParam(name = "name", required = true) String name)
			throws TokenErrorException, IllegalArgumentException, UnsupportedEncodingException {
		GenericJsonResult<String> result = new GenericJsonResult<>(HResult.S_OK);
		
		String token = authorizeService.createToken(1, 2, name);
		result.setData(token);
		return result;
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public GenericJsonResult<Integer> login(HttpServletRequest httpRequest,@RequestParam(name = "name", required = true) String name) throws TokenErrorException{
		GenericJsonResult<Integer> result = new GenericJsonResult<>(HResult.S_OK);
		
		Integer userId = Application.getUserId(httpRequest);
		if (userId == null || userId <= 0) {
			result.setHr(HResult.E_INVALID_PARAMETERS);
			return result;
		}
		result.setData(userId);
		return result;
		
	}
}
