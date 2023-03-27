package com.matrictime.network.util;


import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.matrictime.network.constant.DataConstants.KEY_SLASH;

public class FileUtils {
    /**
     * 判断文件大小
     *
     * @param len
     *            文件长度
     * @param size
     *            限制大小
     * @param unit
     *            限制单位（B,K,M,G）
     * @return
     */
    public static boolean checkFileSize(Long len, int size, String unit) {
//        long len = file.length();
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        if (fileSize > size) {
            return false;
        }
        return true;
    }

    /**
     * 解压缩tar.gz文件
     * @param sourceTarGzFile 压缩包文件
     * @param targetDir 目标文件夹
     */
    public static void decompressTarGz(File sourceTarGzFile, String targetDir) throws IOException {
        TarArchiveInputStream tarArchiveInputStream = new TarArchiveInputStream(new GzipCompressorInputStream(Files.newInputStream(sourceTarGzFile.toPath())));
        TarArchiveEntry entry;
        while ((entry = tarArchiveInputStream.getNextTarEntry()) != null){
            if (entry.isDirectory()){
                continue;
            }
            File targetFile = new File(targetDir, entry.getName());
            File parent = targetFile.getParentFile();
            if (!parent.exists()){
                parent.mkdirs();
            }
            IOUtils.copy(tarArchiveInputStream,Files.newOutputStream(targetFile.toPath()));
            boolean b = targetFile.setExecutable(true);
            System.out.println("setExecutable:"+b);
        }
    }


    public static void main(String[] args) {
        try {
//            decompressTarGz(new File("C:\\Users\\Administrator\\Desktop\\test\\redis-6.2.5.tar.gz"),"C:\\Users\\Administrator\\Desktop\\test\\");
            String operDir = FilenameUtils.removeExtension(FilenameUtils.removeExtension("zk.tar.gz")) + KEY_SLASH;
            System.out.println(operDir);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
