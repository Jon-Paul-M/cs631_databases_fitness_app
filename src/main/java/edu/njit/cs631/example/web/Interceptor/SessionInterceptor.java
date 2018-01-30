package edu.njit.cs631.example.web.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import edu.njit.cs631.example.business.entities.User;


public class SessionInterceptor extends HandlerInterceptorAdapter {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public SessionInterceptor() {
		super();
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.info("Before handling the request");
		request.getSession(true).setAttribute("user", new User("Bob", "Apricot", "Antarctica", null));
		
		return super.preHandle(request, response, handler);
	}

}
