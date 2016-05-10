package com.lite.blackdream.business.parameter.datamodel;

import com.lite.blackdream.framework.model.Request;

/**
 * @author LaineyC
 */
public class DataModelSortRequest extends Request {

    private Long id;

    private Long rootId;

    private Integer fromIndex;

    private Integer toIndex;

    public DataModelSortRequest(){

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

    public Integer getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(Integer fromIndex) {
        this.fromIndex = fromIndex;
    }

    public Integer getToIndex() {
        return toIndex;
    }

    public void setToIndex(Integer toIndex) {
        this.toIndex = toIndex;
    }

}
