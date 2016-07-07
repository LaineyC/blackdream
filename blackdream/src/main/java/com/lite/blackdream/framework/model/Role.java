package com.lite.blackdream.framework.model;

/**
 * Role
 *
 * @author LaineyC
 */
public enum Role{

    /**
     * 使用者
     */
    USER(1),

    /**
     * 开发者
     */
    DEVELOPER(10),

    /**
     * 超管员
     */
    ROOT(100);

    /**
     * 权重
     */
    private int weight;

    Role(int weight){
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public boolean hasRight(Role role){
        return this.weight >= role.weight;
    }

}
