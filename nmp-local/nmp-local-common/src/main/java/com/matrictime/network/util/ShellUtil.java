package com.matrictime.network.util;

import com.matrictime.network.annotation.AesEncryJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShellUtil {

    private static Logger log = LoggerFactory.getLogger(ShellUtil.class);

    public static void runShell(String fileName,String dir){
        ProcessBuilder pb =
                new ProcessBuilder("./" + fileName);
        pb.directory(new File(dir));
        int runningStatus = 0;
        String s = null;
        try {
            Process p = pb.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((s = stdInput.readLine()) != null) {
                throw new RuntimeException(s);
            }
            while ((s = stdError.readLine()) != null) {
                throw new RuntimeException(s);
            }
            try {
                runningStatus = p.waitFor();
            } catch (InterruptedException e) {
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Integer runShell(List<String> commands){
        ProcessBuilder pb = new ProcessBuilder(commands);
        int runningStatus = 1;
        String s;
        try {
            Process p = pb.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((s = stdInput.readLine()) != null) {
                log.info(s);
            }
            while ((s = stdError.readLine()) != null) {
                log.warn(s);
            }
            runningStatus = p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return runningStatus;
    }

}
