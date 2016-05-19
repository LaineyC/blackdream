package com.lite.blackdream.framework.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.lite.blackdream.framework.exception.AppException;
import com.lite.blackdream.framework.exception.ErrorMessage;
import com.lite.blackdream.framework.model.Authentication;
import com.lite.blackdream.framework.util.ConfigProperties;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LaineyC
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter{

    private static final Set<String> methods = new HashSet<>();

    //放行的方法
    static{
        methods.add("user.login");
        methods.add("user.logout");
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getParameter("method");
        if(methods.contains(method)){
            return true;
        }

        HttpSession session = request.getSession();
        Authentication authentication = (Authentication)session.getAttribute(ConfigProperties.SESSION_KEY_AUTHENTICATION);
        if(authentication == null || authentication.getIsDisable()){
            throw new AppException(new ErrorMessage("401", "权限不足"));
        }

        return true;
    }

}
