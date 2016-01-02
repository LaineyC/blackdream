package com.lite.blackdream.framework.model;

/**
 * @author LaineyC
 */
public class DirtyData<E extends Domain> {

    public enum DirtyType{

        INSERT,

        UPDATE,

        DELETE

    }

    private DirtyType dirtyType;

    private E entity;

    public DirtyData(){

    }

    public DirtyType getDirtyType() {
        return dirtyType;
    }

    public void setDirtyType(DirtyType dirtyType) {
        this.dirtyType = dirtyType;
    }

    public E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }

}
