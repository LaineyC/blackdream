package com.lite.blackdream.framework.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

/**
 * @author LaineyC
 */
public class VelocityUtil {

    public static void mergeWrite(String templateFile, String outFile, Map<String,Object> varMap){
        try {
            Properties properties = new Properties();
            properties.setProperty("resource.loader", "file");
            properties.setProperty("file.resource.loader.path", FileUtil.filebasePath.replace(FileUtil.fileSeparator, "/"));
            properties.setProperty("input.encoding", "UTF-8");
            properties.put("output.encoding", "UTF-8");
            properties.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init(properties);
            Template template = velocityEngine.getTemplate(templateFile, "UTF-8");
            VelocityContext context = new VelocityContext();
            varMap.forEach(context::put);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            FileOutputStream fos = new FileOutputStream(outFile);
            PrintStream ps = new PrintStream(fos, true, "UTF-8");
            ps.print(writer.toString());
            ps.flush();
            ps.close();
            fos.close();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
