package com.matrictime.network.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class ShellUtil {

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
}
