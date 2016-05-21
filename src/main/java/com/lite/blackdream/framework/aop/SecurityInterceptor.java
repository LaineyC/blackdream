package com.lite.blackdream.framework.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.lite.blackdream.framework.exception.AppException;
import com.lite.blackdream.framework.exception.ErrorMessage;
import com.lite.blackdream.framework.model.Authentication;
import com.lite.blackdream.framework.model.Role;
import com.lite.blackdream.framework.util.ConfigProperties;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author LaineyC
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter{

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Security security = handlerMethod.getMethodAnnotation(Security.class);
        if(security.open()){
            return true;
        }

        HttpSession session = request.getSession();
        Authentication authentication = (Authentication)session.getAttribute(ConfigProperties.SESSION_KEY_AUTHENTICATION);
        if(authentication == null){
            throw new AppException(new ErrorMessage("401", "权限不足"));
        }

        Role role = authentication.getRole();
        if(role.getWeight() < security.role().getWeight()){
            throw new AppException("权限不足");
        }

        return true;
    }

}
