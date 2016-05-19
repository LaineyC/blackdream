package com.lite.blackdream.framework.util;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigProperties
 *
 * @author LaineyC
 */
public class ConfigProperties {

    public final static String fileSeparator = File.separator;

    /**
     * 项目所有数据资源存储路径
     */
    public final static String DATA_PATH;

    /**
     * 数据库存储路径
     */
    public final static String DATABASE_PATH;

    /**
     * 文件库存储路径
     */
    public final static String FILEBASE_PATH;

    /**
     * 日志库存储路径
     */
    public final static String LOGBASE_PATH;

    /**
     * 临时文件存放路径
     */
    public final static String TEMPORARY_PATH;

    /**
     * 默认用户名
     */
    public final static String USERNAME;

    /**
     * 默认密码
     */
    public final static String PASSWORD;

    /**
     * web项目根路径
     */
    public static String ROOT_PATH;

    /**
     * 用户认证信息
     */
    public static final String SESSION_KEY_AUTHENTICATION = "$authentication";

    /**
     * 开始时间
     */
    public static final String SESSION_KEY_START_TIME = "$startTime";

    static{
        InputStream is = ConfigProperties.class.getResourceAsStream("/blackdream.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        String userName =  properties.getProperty("blackdream.username");
        USERNAME = userName != null ? userName : "root";
        String password = properties.getProperty("blackdream.password");
        PASSWORD = password != null ?  password : "000000";
        String dataPath = properties.getProperty("blackdream.datapath");
        DATA_PATH = dataPath != null ? dataPath : "/BlackDream";
        DATABASE_PATH = DATA_PATH + fileSeparator + "Database";
        FILEBASE_PATH = DATA_PATH + fileSeparator + "Filebase";
        LOGBASE_PATH = DATA_PATH + fileSeparator + "Logbase";
        TEMPORARY_PATH = DATA_PATH + fileSeparator + "Temporary";
    }

}
