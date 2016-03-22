package com.lite.blackdream.framework.util;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author LaineyC
 */
public abstract class WebUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String METHOD_POST = "POST";

    private static final String METHOD_GET = "GET";

    private static class DefaultTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    private WebUtil() {
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url 请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws java.io.IOException
     */
    public static String doPost(String url, Map<String, String> params,  byte[] content, int connectTimeout, int readTimeout)  throws IOException {
        return doPost(url, params, content, DEFAULT_CHARSET, connectTimeout, readTimeout);
    }

    /**
     * 执行HTTP POST请求。
     *
     * @param url 请求地址
     * @param params 请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     * @throws java.io.IOException
     */
    public static String doPost(String url, Map<String, String> params, byte[] content, String charset, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, content, charset, connectTimeout, readTimeout, null);
    }

    public static String doPost(String url, Map<String, String> params, byte[] content, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        return doPost(url, params, content, DEFAULT_CHARSET, connectTimeout, readTimeout, headerMap);
    }

    public static String doPost(String url, Map<String, String> params, byte[] content, String charset, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        String contentType = "application/json;charset=" + charset;
        String query = buildQuery(params, charset);
        if (query != null) {
            url += (url.lastIndexOf("?") == -1 ? "?" : "&") + query;
        }
        return _doPost(url, contentType, content, connectTimeout, readTimeout, headerMap);
    }

    private static String _doPost(String url, String contentType, byte[] content, int connectTimeout, int readTimeout, Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            conn = getConnection(new URL(url), METHOD_POST, contentType, headerMap);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);

            out = conn.getOutputStream();
            out.write(content);
            rsp = getResponseAsString(conn);
        }
        finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rsp;
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url 请求地址
     * @param params 请求参数
     * @return 响应字符串
     * @throws java.io.IOException
     */
    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, DEFAULT_CHARSET);
    }

    /**
     * 执行HTTP GET请求。
     *
     * @param url 请求地址
     * @param params 请求参数
     * @param charset 字符集，如UTF-8, GBK, GB2312
     * @return 响应字符串
     * @throws java.io.IOException
     */
    public static String doGet(String url, Map<String, String> params, String charset) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;
        try {
            String contentType = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params, charset);
            conn = getConnection(buildGetUrl(url, query), METHOD_GET, contentType, null);
            rsp = getResponseAsString(conn);
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    private static HttpURLConnection getConnection(URL url, String method, String contentType, Map<String, String> headerMap) throws IOException {
        HttpURLConnection conn;
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx;
            try {
                ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
            }
            catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier((hostname, session) -> true);
            conn = connHttps;
        }
        else {
            conn = (HttpURLConnection) url.openConnection();
        }

        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/json;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("User-Agent", "client-sdk-java");
        conn.setRequestProperty("Content-Type", contentType);
        if (headerMap != null) {
            for (Entry<String, String> entry : headerMap.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if (isEmpty(query)) {
            return url;
        }
        if (isEmpty(url.getQuery())) {
            if (strUrl.endsWith("?")) {
                strUrl = strUrl + query;
            }
            else {
                strUrl = strUrl + "?" + query;
            }
        }
        else {
            if (strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            }
            else {
                strUrl = strUrl + "&" + query;
            }
        }
        return new URL(strUrl);
    }

    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            if (areNotEmpty(name, value)) {
                if (hasParam) {
                    query.append("&");
                }
                else {
                    hasParam = true;
                }
                query.append(name).append("=").append(URLEncoder.encode(value, charset));
            }
        }

        return query.toString();
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        }
        else {
            String msg = getStreamAsString(es, charset);
            if (isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            }
            else {
                throw new IOException(msg);
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            Reader reader = new InputStreamReader(stream, charset);
            StringBuilder response = new StringBuilder();

            final char[] buff = new char[1024];
            int read;
            while ((read = reader.read(buff)) > 0) {
                response.append(buff, 0, read);
            }
            return response.toString();
        }
        finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = DEFAULT_CHARSET;

        if (!isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }
        return charset;
    }

    /**
     * 使用默认的UTF-8字符集反编码请求参数值。
     *
     * @param value 参数值
     * @return 反编码后的参数值
     */
    public static String decode(String value) throws IOException {
        return decode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用默认的UTF-8字符集编码请求参数值。
     *
     * @param value 参数值
     * @return 编码后的参数值
     */
    public static String encode(String value) throws IOException {
        return encode(value, DEFAULT_CHARSET);
    }

    /**
     * 使用指定的字符集反编码请求参数值。
     *
     * @param value 参数值
     * @param charset 字符集
     * @return 反编码后的参数值
     */
    public static String decode(String value, String charset)throws IOException {
        String result = null;
        if (!isEmpty(value)) {
            result = URLDecoder.decode(value, charset);
        }
        return result;
    }

    /**
     * 使用指定的字符集编码请求参数值。
     *
     * @param value 参数值
     * @param charset 字符集
     * @return 编码后的参数值
     */
    public static String encode(String value, String charset) throws IOException {
        String result = null;
        if (!isEmpty(value)) {
            result = URLEncoder.encode(value, charset);
        }
        return result;
    }

    private static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> map = null;
        if (url != null && url.indexOf('?') != -1) {
            map = splitUrlQuery(url.substring(url.indexOf('?') + 1));
        }
        if (map == null) {
            map = new HashMap<>();
        }
        return map;
    }

    /**
     * 从URL中提取所有的参数。
     *
     * @param query URL地址
     * @return 参数映射
     */
    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap<String, String>();

        String[] pairs = query.split("&");
        if (pairs != null && pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }

        return result;
    }

    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value 待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ( !Character.isWhitespace(value.charAt(i)) ) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

}

