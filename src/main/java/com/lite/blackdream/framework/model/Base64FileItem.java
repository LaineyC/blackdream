package com.lite.blackdream.framework.model;

/**
 * 只包含了上传文件的内容和文件类型等数据 不包含上传地址
 * @author LaineyC
 */
public class Base64FileItem {

    /**
     * 文件名
     * xxxx.jpg
     */
    private String name;

    /**
     * 后缀名
     */
    private String extension;

    /**
     * 文件base64内容
     */
    private String content;

    public Base64FileItem() {

    }

    public String getExtension() {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
