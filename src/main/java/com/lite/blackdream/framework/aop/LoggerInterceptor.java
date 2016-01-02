package com.lite.blackdream.framework.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.framework.model.Request;
import com.lite.blackdream.framework.util.JsonObjectMapper;
import com.lite.blackdream.framework.web.RequestWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author LaineyC
 */
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private static final Log logger = LogFactory.getLog("operation");

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        RequestWrapper requestWrapper = (RequestWrapper)request;
        HttpSession session = request.getSession(false);
        String sessionId = session == null ? null : session.getId();
        String method = requestWrapper.getParameter("method");
        logger.info("session=" + sessionId +",method=" + method + ",parameter=" + requestWrapper.getRequestLog());
    }

}