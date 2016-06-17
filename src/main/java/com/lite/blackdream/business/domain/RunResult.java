package com.lite.blackdream.business.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
public class RunResult{

    private List<String> messages = new ArrayList<>();

    private String url;

    private String fileName;

    public RunResult(){

    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
