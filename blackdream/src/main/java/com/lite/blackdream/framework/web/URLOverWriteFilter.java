package com.lite.blackdream.framework.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author LaineyC
 */
public class URLOverWriteFilter implements Filter {

    private FilterConfig config;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        String uri = httpServletRequest.getRequestURI();
        String realUri = config.getInitParameter(uri);
        if (realUri != null) {
            httpServletRequest.getRequestDispatcher(realUri).forward(httpServletRequest, servletResponse);
        }
        else{
            filterChain.doFilter(httpServletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

}
