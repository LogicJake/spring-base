package com.scy.base;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scy.base.util.exception.TokenErrorException;
import com.scy.base.util.exception.TokenExpiredException;
import com.scy.base.util.model.GenericJsonResult;
import com.scy.base.util.model.HResult;

@ControllerAdvice
public class MyExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public GenericJsonResult<String> handler(Exception e) {
		GenericJsonResult<String> result = new GenericJsonResult<String>(HResult.E_UNKNOWN);
		result.setData(e.getClass().getName());
		result.setExtraData(toStackTrace(e));
		if (e instanceof TokenErrorException) {
			result.setHr(HResult.E_AUTH_FAIL);
			return result;
		} else if (e instanceof TokenExpiredException) {
			result.setHr(HResult.E_AUTH_EXPIRED);
			return result;
		} else {
			return result;
		}
	}

	public static String toStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		try {
			e.printStackTrace(pw);
			return sw.toString();
		} catch (Exception e1) {
			return "";
		}
	}
	
}
