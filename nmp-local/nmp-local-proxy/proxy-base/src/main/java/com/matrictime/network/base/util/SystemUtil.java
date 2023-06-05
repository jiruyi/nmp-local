package com.matrictime.network.base.util;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/6/2 0002 10:04
 * @desc
 */
public class SystemUtil {

    public static String getCpuId() throws IOException {

        // linux，windows命令
        String[] linux = {"dmidecode", "-t", "processor", "|", "grep", "'ID'"};
        String[] windows = {"wmic", "cpu", "get", "ProcessorId"};

        // 获取系统信息
        String property = System.getProperty("os.name");
        Process process = Runtime.getRuntime().exec(property.contains("Window") ? windows : linux);
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream(), "utf-8");
        sc.next();
        return sc.next();
    }
}
