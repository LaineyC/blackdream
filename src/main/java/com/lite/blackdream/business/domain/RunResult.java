package com.lite.blackdream.business.domain;

import com.lite.blackdream.framework.model.Domain;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
public class RunResult extends Domain{

    private List<String> messages = new ArrayList<>();

    private String url;

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
}
