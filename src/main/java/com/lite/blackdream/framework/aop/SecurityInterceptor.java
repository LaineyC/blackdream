package com.lite.blackdream.framework.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.lite.blackdream.framework.exception.AppException;
import com.lite.blackdream.framework.exception.ErrorMessage;
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
        if(session.getAttribute("user") == null){
            ErrorMessage errorMessage = new ErrorMessage("权限不足");
            errorMessage.setCode("401");
            throw new AppException(errorMessage);
        }
        return true;
    }

}
