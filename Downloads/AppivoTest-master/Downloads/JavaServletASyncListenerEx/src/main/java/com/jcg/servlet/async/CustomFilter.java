package com.jcg.servlet.async;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CustomFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
        String servletPath = ((HttpServletRequest)request).getServletPath();
        String requestURI = ((HttpServletRequest)request).getRequestURI();
        
        Pattern pattern = Pattern.compile(servletPath.substring(servletPath.lastIndexOf("/")+1, servletPath.length()));
        Matcher matcher = pattern.matcher(requestURI);
        if (matcher.matches())
        {
        String param = matcher.group(1);
        // do stuff with param here..
        }

    chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
