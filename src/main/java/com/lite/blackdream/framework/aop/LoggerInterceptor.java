package com.lite.blackdream.framework.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lite.blackdream.framework.util.WebUtil;
import com.lite.blackdream.framework.web.RequestWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author LaineyC
 */
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private static final Log logger = LogFactory.getLog("operation");

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        RequestWrapper requestWrapper = (RequestWrapper)request;
        HttpSession session = requestWrapper.getSession();
        long ms = System.currentTimeMillis() - (Long)session.getAttribute("$startTime");
        String method = requestWrapper.getParameter("method");
        logger.info("用时=" + ms + "ms,IP=" + WebUtil.getIp(request) + ",接口=" + method +  ",参数=" + requestWrapper.getRequestLog());
    }

}