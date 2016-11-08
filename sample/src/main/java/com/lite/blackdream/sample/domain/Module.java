package com.lite.blackdream.sample.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
public class Module extends DataModel{

    private Properties properties;

    private List<Entity> children = new ArrayList<>();

    public List<Entity> getChildren() {
        return children;
    }

    public void setChildren(List<Entity> children) {
        this.children = children;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public static class Properties{

        private String comment;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

}
