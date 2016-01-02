package com.lite.blackdream.framework.model;

/**
 * @author LaineyC
 */
public class PagerRequest extends Request {

	private Integer page;

    private Integer pageSize;

    public PagerRequest(){

    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
