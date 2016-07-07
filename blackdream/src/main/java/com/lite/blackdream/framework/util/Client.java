package com.lite.blackdream.framework.util;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lite.blackdream.framework.model.Request;
import com.lite.blackdream.framework.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author LaineyC
 */
public class Client {

    private String serverUrl;

    private String session;

    public Client(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public <T extends Response> T execute(Request request, Class<T> responseClass, String method) {
        T response;
        byte[] content;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            content = objectMapper.writeValueAsString(request).getBytes("UTF-8");

            Map<String, String> params = new HashMap<>();
            params.put("method", method);

            String responseText = WebUtil.doPost(serverUrl, params, content, 3000, 15000);
            response = objectMapper.readValue(responseText, responseClass);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return response;
    }

}