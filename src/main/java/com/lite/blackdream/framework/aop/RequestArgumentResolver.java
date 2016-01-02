package com.lite.blackdream.framework.aop;

import com.lite.blackdream.framework.model.Request;
import com.lite.blackdream.framework.web.RequestWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author LaineyC
 */
public class RequestArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Request.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestWrapper requestWrapper = webRequest.getNativeRequest(RequestWrapper.class);
        if(Request.class.isAssignableFrom(parameter.getParameterType())){
            return requestWrapper.getRequestBody();
        }
        else {
            return null;
        }
    }

}