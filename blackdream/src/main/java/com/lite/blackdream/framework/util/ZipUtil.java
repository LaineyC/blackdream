package com.lite.blackdream.framework.util;

import java.io.*;
import java.util.zip.*;

/**
 * @author LaineyC
 */
public class ZipUtil {
  
    public static final String EXT = ".zip";

    private static final String BASE_DIR = "";  

    private static final String FILE_SEPARATOR = File.separator;

    private static final int BUFFER = 1024;  

    public static void compress(File srcFile) throws Exception {
        String name = srcFile.getName();  
        String basePath = srcFile.getParent();  
        String destPath = basePath + FILE_SEPARATOR + name + EXT;
        compress(srcFile, destPath);  
    }  

    public static void compress(File srcFile, File destFile) throws Exception {
        CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(destFile), new CRC32());
        ZipOutputStream zos = new ZipOutputStream(cos);
        compress(srcFile, zos, BASE_DIR);
        zos.flush();  
        zos.close();  
    }  

    public static void compress(File srcFile, String destPath) throws Exception {  
        compress(srcFile, new File(destPath));  
    }  

    private static void compress(File srcFile, ZipOutputStream zos, String basePath) throws Exception {
        if (srcFile.isDirectory()) {  
            compressDir(srcFile, zos, basePath);  
        }
        else {
            compressFile(srcFile, zos, basePath);  
        }  
    }  

    public static void compress(String srcPath) throws Exception {  
        File srcFile = new File(srcPath);
        compress(srcFile);  
    }  

    public static void compress(String srcPath, String destPath) throws Exception {
        File srcFile = new File(srcPath);
        compress(srcFile, destPath);  
    }

    private static void compressDir(File dir, ZipOutputStream zos, String basePath) throws Exception {
        File[] files = dir.listFiles();
        if (files.length < 1) {  
            ZipEntry entry = new ZipEntry(basePath + dir.getName() + FILE_SEPARATOR);
            zos.putNextEntry(entry);  
            zos.closeEntry();  
        }
        for (File file : files) {
            compress(file, zos, basePath + dir.getName() + FILE_SEPARATOR);
        }  
    }  

    private static void compressFile(File file, ZipOutputStream zos, String dir) throws Exception {
        ZipEntry entry = new ZipEntry(dir + file.getName());
        zos.putNextEntry(entry);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        int count;  
        byte data[] = new byte[BUFFER];  
        while ((count = bis.read(data, 0, BUFFER)) != -1) {  
            zos.write(data, 0, count);  
        }  
        bis.close();
        zos.closeEntry();  
    }

    public static void decompress(String srcPath) throws Exception {
        File srcFile = new File(srcPath);
        decompress(srcFile);
    }

    public static void decompress(File srcFile) throws Exception {
        String basePath = srcFile.getParent();
        decompress(srcFile, basePath);
    }

    public static void decompress(File srcFile, File destFile) throws Exception {
        CheckedInputStream cis = new CheckedInputStream(new FileInputStream(srcFile), new CRC32());
        ZipInputStream zis = new ZipInputStream(cis);
        decompress(destFile, zis);
        zis.close();
    }

    public static void decompress(File srcFile, String destPath) throws Exception {
        decompress(srcFile, new File(destPath));
    }

    public static void decompress(String srcPath, String destPath) throws Exception {
        File srcFile = new File(srcPath);
        decompress(srcFile, destPath);
    }

    private static void decompress(File destFile, ZipInputStream zis) throws Exception {
        ZipEntry entry = null;
        while ((entry = zis.getNextEntry()) != null) {
            String dir = destFile.getPath() + File.separator + entry.getName();
            File dirFile = new File(dir);
            fileProber(dirFile);
            if (entry.isDirectory()) {
                dirFile.mkdirs();
            }
            else {
                decompressFile(dirFile, zis);
            }
            zis.closeEntry();
        }
    }

    private static void fileProber(File dirFile) {
        File parentFile = dirFile.getParentFile();
        if (!parentFile.exists()) {
            // 递归寻找上级目录
            fileProber(parentFile);
            parentFile.mkdir();
        }
    }

    private static void decompressFile(File destFile, ZipInputStream zis) throws Exception {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
        int count;
        byte data[] = new byte[BUFFER];
        while ((count = zis.read(data, 0, BUFFER)) != -1) {
            bos.write(data, 0, count);
        }
        bos.close();
    }

}  