package com.lite.blackdream.framework.web;

import com.lite.blackdream.framework.model.Request;
import org.apache.commons.io.IOUtils;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @author LaineyC
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private byte[] requestData;

    private Request requestBody;

    private String requestLog;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            requestData = IOUtils.toByteArray(request.getReader(), "UTF-8");
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestData);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public byte[] getRequestData() {
        return requestData;
    }

    public Request getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Request requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestLog() {
        return requestLog;
    }

    public void setRequestLog(String requestLog) {
        this.requestLog = requestLog;
    }

}
