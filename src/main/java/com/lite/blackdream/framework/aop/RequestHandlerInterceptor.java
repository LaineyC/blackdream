package com.lite.blackdream.framework.aop;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.lite.blackdream.framework.model.Authentication;
import com.lite.blackdream.framework.model.Base64FileItem;
import com.lite.blackdream.framework.model.Request;
import com.lite.blackdream.framework.util.ConfigProperties;
import com.lite.blackdream.framework.util.JsonObjectMapper;
import com.lite.blackdream.framework.web.RequestWrapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author LaineyC
 */
public class RequestHandlerInterceptor extends HandlerInterceptorAdapter {

    private static ObjectMapper requestInstanceObjectMapper = new JsonObjectMapper();

    private static ObjectMapper requestLogObjectMapper = new JsonObjectMapper();
    static{
        SimpleModule module = new SimpleModule();
        module.addSerializer(Base64FileItem.class, new JsonSerializer<Base64FileItem>() {
            @Override
            public void serialize(Base64FileItem base64FileItem, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("name", base64FileItem.getName());
                jsonGenerator.writeEndObject();
            }
        });
        requestLogObjectMapper.registerModule(module);
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestWrapper requestWrapper = (RequestWrapper)request;
        HttpSession session = requestWrapper.getSession();
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        MethodParameter methodParameter = handlerMethod.getMethodParameters()[0];
        Class<?> clazz = methodParameter.getParameterType();

        Request requestBody = (Request)requestInstanceObjectMapper.readValue(requestWrapper.getRequestData(), clazz);
        requestWrapper.setRequestBody(requestBody);

        Authentication authentication = (Authentication)session.getAttribute(ConfigProperties.SESSION_KEY_AUTHENTICATION);
        if(authentication != null){
            requestBody.setAuthentication(authentication);
        }

        String requestLog = requestLogObjectMapper.writeValueAsString(requestBody);
        requestWrapper.setRequestLog(requestLog);

        session.setAttribute(ConfigProperties.SESSION_KEY_START_TIME, System.currentTimeMillis());
        return true;
    }

}