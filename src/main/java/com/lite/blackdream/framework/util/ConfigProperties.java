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
     * web项目根路径
     */
    public static String ROOT_PATH;

    /**
     * 代码库 生成代码的临时路径
     */
    public static String CODEBASE_PATH;

    /**
     * 默认用户名
     */
    public final static String USERNAME;

    /**
     * 默认密码
     */
    public final static String PASSWORD;

    static{
        InputStream is = ConfigProperties.class.getResourceAsStream("/config.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        USERNAME = properties.getProperty("blackdream.username");
        PASSWORD = properties.getProperty("blackdream.password");
        DATA_PATH = properties.getProperty("blackdream.datapath");
        DATABASE_PATH = DATA_PATH + fileSeparator + "Database";
        FILEBASE_PATH = DATA_PATH + fileSeparator + "Filebase";
        LOGBASE_PATH = DATA_PATH + fileSeparator + "Logbase";
    }

}
