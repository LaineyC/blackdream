package com.lite.blackdream.framework.util;

import java.io.*;
import java.nio.channels.FileChannel;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import sun.misc.BASE64Decoder;

/**
 * @author LaineyC
 */
public class FileUtil {

    public static void writeBase64(String base64, String fileAbsolutePath) throws IOException {
        File parentPathFile = new File(fileAbsolutePath).getParentFile();
        if(!parentPathFile.exists()){
            parentPathFile.mkdirs();
        }
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64);
        FileOutputStream out = new FileOutputStream(fileAbsolutePath);
        out.write(buffer);
        out.close();
    }

    public static Document readXml(String readFile) throws IOException{
        Document document;
        try {
            document = new SAXReader().read(new BufferedInputStream(new FileInputStream(readFile)));
        }
        catch (Exception exception) {
            throw new IOException("读取XML失败：" + readFile, exception);
        }
        return document;
    }

    public static void writeXml(Document document, String writeFile) throws IOException{
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(format);
            writer.setOutputStream(new BufferedOutputStream(new FileOutputStream(writeFile)));
            writer.write(document);
            writer.close();
        }
        catch (IOException exception) {
            throw new IOException("写入XML失败：" + writeFile, exception);
        }
    }

    public static void mkdirs(String path){
        File pathFile = new File(path);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
    }

    public static boolean deleteFile(File deleteFile){
        File[] files = deleteFile.listFiles();
        if(files != null) {
            for(int i = 0 ; i < files.length ; i++) {
                File file = files[i];
                if(file.isDirectory()) {
                    FileUtil.deleteFile(file);
                }
                else {
                    if(file.delete()){

                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return deleteFile.delete();
    }

    public static boolean deleteFile(File deleteFile, boolean delete){
        File[] files = deleteFile.listFiles();
        if(files != null) {
            for(int i = 0 ; i < files.length ; i++) {
                File file = files[i];
                if(file.isDirectory()) {
                    FileUtil.deleteFile(file, true);
                }
                else {
                    if(file.delete()){

                    }
                    else {
                        return false;
                    }
                }
            }
        }
        if(delete){
            return deleteFile.delete();
        }
        else{
            return true;
        }
    }

    public static void copyFile(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();
            out = fo.getChannel();
            in.transferTo(0, in.size(), out);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if(fi != null){
                    fi.close();
                }
                if(in != null){
                    in.close();
                }
                if(fo != null){
                    fo.close();
                }
                if(out != null){
                    out.close();
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
