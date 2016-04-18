package com.lite.blackdream.framework.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        String method = requestWrapper.getParameter("method");
        logger.info("IP=" + WebUtil.getIp(request) + ",方法=" + method +  ",参数=" + requestWrapper.getRequestLog());
    }

}