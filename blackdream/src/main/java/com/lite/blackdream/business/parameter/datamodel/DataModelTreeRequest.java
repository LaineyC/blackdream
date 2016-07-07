package com.lite.blackdream.business.parameter.datamodel;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class DataModelTreeRequest extends Request {

    private Long rootId;

    public DataModelTreeRequest(){

    }

    public Long getRootId() {
        return rootId;
    }

    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }

}
