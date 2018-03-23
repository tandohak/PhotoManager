package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("[AuthInterceptor] preHandle=======================");
		
		HttpSession session = request.getSession();
		if(session.getAttribute("login") == null){
			logger.info("[AuthInterceptor] go login");
			response.sendRedirect(request.getContextPath()+"/login"); 
			saveDest(request); // 실제로 가야하는 경로를 받아서 저장한다
			return false;//controller로 이동 x
		}
		
		return true;//controller로 이동 o
	}
	
	private void saveDest(HttpServletRequest req){
		String uri = req.getRequestURI();
		String query = req.getQueryString();
		
		if(query == null || query.equals("null")){
			query = "";
		}else{
			query = "?" + query;
		}
		
		if(req.getMethod().equals("GET")){
			logger.info("dest : " + (uri+query));
			req.getSession().setAttribute("dest", uri+query); 
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("[AuthInterceptor] postHandle=======================");
	}

}
