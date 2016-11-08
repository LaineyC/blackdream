package com.lite.blackdream.sample.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
public class Project extends DataModel{

    private List<Module> children = new ArrayList<>();

    private Properties properties;

    public Properties getProperties() {
        return properties;
    }

    public List<Module> getChildren() {
        return children;
    }

    public void setChildren(List<Module> children) {
        this.children = children;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public static class Properties{

        private String namespace;

        private String comment;

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

}