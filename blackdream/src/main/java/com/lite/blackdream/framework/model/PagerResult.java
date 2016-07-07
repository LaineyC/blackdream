package com.lite.blackdream.framework.model;

import java.util.List;

/**
 * @author LaineyC
 */
public class PagerResult<E> extends Domain {

    private List<E> records;

    private Long total;
    
    /**
     * 其他无关分页数据
     */
    private Object userData;

    public PagerResult(){

    }

    public PagerResult(List<E> records, Long total) {
        this.records = records;
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<E> getRecords() {
        return records;
    }

    public void setRecords(List<E> records) {
        this.records = records;
    }

	public Object getUserData() {
		return userData;
	}

	public void setUserData(Object userData) {
		this.userData = userData;
	}

}
