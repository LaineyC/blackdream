package com.lite.blackdream.business.parameter.system;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class FileDownloadRequest extends Request {

    private String url;

    public FileDownloadRequest(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
