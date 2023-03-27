package com.matrictime.network.util;

import com.matrictime.network.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static com.matrictime.network.constant.DataConstants.*;

public class ShellUtil {

    private static Logger log = LoggerFactory.getLogger(ShellUtil.class);


    public static int runShell(List<String> commands,String workDir){
        log.info("runShell start,params{}",commands);
        ProcessBuilder pb = new ProcessBuilder(commands);
        if (!ParamCheckUtil.checkVoStrBlank(workDir)){
            pb.directory(new File(workDir));
        }
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
        log.info("runShell end,commands:{},result:{}",commands,runningStatus);
        return runningStatus;
    }

}
