package com.lite.blackdream.framework.aop;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.lite.blackdream.framework.util.WebUtil;
import com.lite.blackdream.framework.web.RequestWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.lite.blackdream.framework.exception.AppException;
import com.lite.blackdream.framework.exception.ErrorMessage;
import com.lite.blackdream.framework.model.Response;

/**
 * @author LaineyC
 */
public class ErrorResolver implements HandlerExceptionResolver {

    private HttpMessageConverter<Object> jsonHttpMessageConverter;

    private static final Log logger = LogFactory.getLog("error");

    public void setJsonHttpMessageConverter(HttpMessageConverter<Object> jsonHttpMessageConverter) {
        this.jsonHttpMessageConverter = jsonHttpMessageConverter;
    }

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        RequestWrapper requestWrapper = (RequestWrapper)request;
        String method = requestWrapper.getParameter("method");
        ErrorMessage errorMessage;
        if(exception instanceof AppException){
            AppException appException = (AppException)exception;
            errorMessage = appException.getErrorMessage();
            Throwable cause = appException.getCause();
            if(cause == null){
                logger.error("IP=" + WebUtil.getIp(request) + ",方法=" + method  + ",参数=" + requestWrapper.getRequestLog() + ",消息=" + errorMessage.getMessage());
            }
            else{
                logger.error("IP=" + WebUtil.getIp(request) + ",方法=" + method  + ",参数=" + requestWrapper.getRequestLog() + ",消息=" + errorMessage.getMessage(), exception);
            }
        }
        else{
            errorMessage = new ErrorMessage("[" + method + "]服务不可用");
            logger.error("IP=" + WebUtil.getIp(request) + ",方法=" + method  + ",参数=" + requestWrapper.getRequestLog() + ",消息=" + errorMessage.getMessage(), exception);
        }
        HttpInputMessage inputMessage = new ServletServerHttpRequest(requestWrapper);
        List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
        for (MediaType acceptedMediaType : acceptedMediaTypes) {
            if (jsonHttpMessageConverter.canWrite(Object.class, acceptedMediaType)) {
                try{
                    Response<Object> r = new Response<>();
                    r.setError(errorMessage);
                    jsonHttpMessageConverter.write(r, acceptedMediaType, new ServletServerHttpResponse(response));
                }
                catch (IOException ex){
                    throw new RuntimeException(ex);
                }
                break;
            }
        }
        return new ModelAndView();
    }

}
