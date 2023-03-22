package com.matrictime.network.util;

import com.matrictime.network.annotation.AesEncryJoinPoint;
import com.matrictime.network.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.exception.ErrorMessageContants.RUN_SHELL_EXCEPTION;

public class ShellUtil {

    private static Logger log = LoggerFactory.getLogger(ShellUtil.class);

    public static Result baseRunShell(List<String> commands){
        log.info("runShell start,params{}",commands);
        Result result = new Result<>(false,null);
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
            if (runningStatus == 0){
                result.setSuccess(true);
            }
        } catch (IOException e) {
            result.setErrorMsg(RUN_SHELL_EXCEPTION);
            e.printStackTrace();
        }catch (InterruptedException e) {
            result.setErrorMsg(RUN_SHELL_EXCEPTION);
            e.printStackTrace();
        }
        return result;
    }

    public static Result runShell(List<String> commands){
        log.info("runShell start,params{}",commands);
        Result result = new Result<>(false,null);
        ProcessBuilder pb = new ProcessBuilder(commands);
        int runningStatus = 1;
        String s;
        try {
            Process p = pb.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((s = stdInput.readLine()) != null) {
                if (s.contains(VERSION_SHELL_RES_KEY)){
                    String res = StringUtils.substringAfter(s, VERSION_SHELL_RES_KEY);
                    if (VERSION_SHELL_RES_SUCCESS.equals(res)){
                        result.setSuccess(true);
                    }else {
                        result.setSuccess(false);
                    }
                }
                if (s.contains(VERSION_SHELL_ERR_KEY)){
                    result.setErrorMsg(StringUtils.substringAfter(s, VERSION_SHELL_ERR_KEY));
                }
                log.info(s);
            }
            while ((s = stdError.readLine()) != null) {
                log.warn(s);
            }
            runningStatus = p.waitFor();
        } catch (IOException e) {
            result.setErrorMsg(RUN_SHELL_EXCEPTION);
            e.printStackTrace();
        }catch (InterruptedException e) {
            result.setErrorMsg(RUN_SHELL_EXCEPTION);
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String s = "action_result:success";
        System.out.println(StringUtils.substringAfter(s,VERSION_SHELL_RES_KEY));
    }

}
