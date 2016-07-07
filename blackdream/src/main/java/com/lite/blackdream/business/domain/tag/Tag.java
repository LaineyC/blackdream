package com.lite.blackdream.business.domain.tag;

import com.lite.blackdream.framework.el.Context;
import com.lite.blackdream.framework.model.Domain;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
public abstract class Tag extends Domain implements Cloneable{

    private String tagName;

    private List<Tag> children = new ArrayList<>();

    private Tag parent;

    public Tag(){
        this.tagName = this.getClass().getSimpleName();
    }

    public abstract void execute(Context context);

    public String getTagName() {
        return tagName;
    }

    public Tag getParent() {
        return parent;
    }

    public void setParent(Tag parent) {
        this.parent = parent;
    }

    public List<Tag> getChildren() {
        return children;
    }

    public void setChildren(List<Tag> children) {
        this.children = children;
    }

    public Tag clone(){
        Tag tag;
        try {
            tag = this.getClass().newInstance();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        tag.setId(this.getId());
        tag.setIsDelete(this.getIsDelete());
        tag.setParent(this.getParent());
        this.getChildren().forEach(child -> tag.getChildren().add((Tag)child.clone()));
        return tag;
    }

}
