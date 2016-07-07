package com.lite.blackdream.business.parameter.datamodel;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class DataModelDeleteRequest extends Request {

    private Long id;

    private Long rootId;

    public DataModelDeleteRequest(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

}
