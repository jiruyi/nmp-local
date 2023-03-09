package com.matrictime.network.base.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 解压文件工具
 * @author by wangqiang
 * @date 2023/3/9.
 */
public class ZipFileUtil {
    /**
     * 源文件
     * 要压缩到的目录
     *
     * @param sourcesZipFile
     * @param targetDir
     */
    public static void unZipFile(String sourcesZipFile, String targetDir) {
        ZipInputStream zipInputStream = null;
        ZipEntry zipEntry = null;
        File file = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(sourcesZipFile));
            while ((zipEntry = zipInputStream.getNextEntry()) != null){
                file = new File(targetDir,zipEntry.getName());
                //创建要被解压到的目录
                if(!file.exists()){
                    new File(file.getParent()).mkdirs();
                }
                OutputStream outputStream = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(outputStream);
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = zipInputStream.read(bytes)) != -1){
                    bos.write(bytes,0,len);
                }
                bos.close();
                outputStream.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                zipInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
