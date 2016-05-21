package com.lite.blackdream.framework.model;

/**
 * RoleT
 *
 * @author LaineyC
 */
public enum Role{

    /**
     * 使用者
     */
    USER(0),

    /**
     * 开发者
     */
    DEVELOPER(1),

    /**
     * 超管员
     */
    ROOT(2);

    /**
     * 权重
     */
    private int weight;

    private Role(int weight){
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

}
