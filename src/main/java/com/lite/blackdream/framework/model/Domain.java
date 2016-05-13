package com.lite.blackdream.framework.model;

import java.util.Date;

/**
 * @author LaineyC
 */
public abstract class Domain {

    private Long id;

    private Boolean isDelete;

    private Date modifyDate;

    public Domain(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Domain domain = (Domain) o;
        return !(id != null ? !id.equals(domain.id) : domain.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
