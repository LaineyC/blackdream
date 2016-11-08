package com.lite.blackdream.sdk;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lite.blackdream.sdk.util.WebUtil;

/**
 * @author LaineyC
 */
public class Client {

    private static ObjectMapper MAPPER = new ObjectMapper();
    static{
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private String serverUrl;

    private String session;

    public Client(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public <T extends Response> T execute(Request request, Class<T> responseClass, String method) {
      return execute(request, responseClass, method, null);
    }

    public <T extends Response> T execute(Request request, Class<T> responseClass, String method, String version) {
        T response;
        byte[] content;

        try {
            content = MAPPER.writeValueAsString(request).getBytes("UTF-8");

            Map<String, String> params = new HashMap<>();
            params.put("method", method);
            if(version != null){
                params.put("version", version);
            }

            Map<String, String> headerMap = new HashMap<>();
            if(session != null){
                headerMap.put("Cookie", session);
            }

            String charset = "UTF-8";
            String contentType = "application/json;charset=" + charset;
            String query = WebUtil.buildQuery(params, charset);
            String url = serverUrl;
            if (query != null) {
                url += (url.lastIndexOf("?") == -1 ? "?" : "&") + query;
            }

            HttpURLConnection conn = WebUtil.getConnection(new URL(url), "POST", contentType, headerMap);
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(15000);
            OutputStream out = conn.getOutputStream();
            out.write(content);

            String responseText = WebUtil.getResponseAsString(conn);
            session = session == null ? conn.getHeaderField("Set-Cookie").split(";")[0] : session;
            response = MAPPER.readValue(responseText, responseClass);
            if(response.hasError()){
                throw new AppException(response.getError());
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return response;
    }

}