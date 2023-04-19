package com.matrictime.network.util;

import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.*;
import oshi.util.FormatUtil;
import oshi.util.Util;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

public class SystemUtils {

    private final static long CPU_WAIT_TIME = 1000;

    private final static int PINT_WAIT_TIME = 3000;

    private final static String GET_PID_CMD = "netstat -nlp|grep :$esPort|awk '{print $7}'|awk -F\"/\" '{ print $1 }'";

    private final static String GET_PID_CMD_PORT = "$esPort";
    /**
     * 获取物理cpu核心数
     * @return
     */
    public static int getCPUcores(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        return hal.getProcessor().getPhysicalProcessorCount();
    }

    /**
     * 获取cpu使用率(四舍五入保留两位)
     */
    public static String getCPUusePercent(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(CPU_WAIT_TIME);
        String cpuUsePercent = String.format("%.0f",processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100);
        return cpuUsePercent;
    }

    /**
     * 获取内存总量
     * @return
     */
    public static long getTotalMemory(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        GlobalMemory memory = hal.getMemory();
        long total = memory.getTotal();
        return total;
    }

    /**
     * 获取可用内存量
     * @return
     */
    public static long getAvailableMemory(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        GlobalMemory memory = hal.getMemory();
        long available = memory.getAvailable();
        return available;
    }

    /**
     * 获取物理磁盘总量
     * @return
     */
    public static long getTotalDisk(){
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        List<HWDiskStore> diskStores = hal.getDiskStores();
        long total = 0;
        for (HWDiskStore store : diskStores){
            total = total + store.getSize();
        }
        return total;
    }

    /**
     * 获取逻辑磁盘总量
     * @return
     */
    public static long getTotalFileSys(){
        SystemInfo si = new SystemInfo();
        OperatingSystem op = si.getOperatingSystem();
        FileSystem fileSystem = op.getFileSystem();
        long total = 0;
        for (OSFileStore fs : fileSystem.getFileStores()) {
            total = total + fs.getTotalSpace();
        }
        return total;
    }


    /**
     * 获取逻辑磁盘可使用量
     * @return
     */
    public static long getUseFileSys(){
        SystemInfo si = new SystemInfo();
        OperatingSystem op = si.getOperatingSystem();
        FileSystem fileSystem = op.getFileSystem();
        long use = 0;
        for (OSFileStore fs : fileSystem.getFileStores()) {
            use = use + fs.getUsableSpace();
        }
        return use;
    }

    /**
     * 获取ping ip结果
     * @param ip
     * @return
     * @throws IOException
     */
    public static boolean getPingResult(String ip) throws IOException {
        InetAddress inetAddress = InetAddress.getByName(ip);
        return inetAddress.isReachable(PINT_WAIT_TIME);
    }

    /**
     * 根据端口获取应用的PID
     * @param port
     * @return
     */
    public static Integer getPID(String port){
        Integer pid = -1;
        List<String> cmd = new ArrayList<>();
        cmd.add(GET_PID_CMD.replace(GET_PID_CMD_PORT,port));
        String getPid = ShellUtil.runShellgetEcho(cmd);
        if (!ParamCheckUtil.checkVoStrBlank(getPid)){
            pid = Integer.valueOf(getPid);
        }
        return pid;
    }

    /**
     * 根据pid获取对应应用的信息
     * @param pid
     * @return
     */
    public static Map<String,String> getSystemInfo(Integer pid){
        Map<String,String> res = new HashMap<>(4);
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hd = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();
        OSProcess processBegin = os.getProcess(pid);
        Util.sleep(CPU_WAIT_TIME);
        OSProcess processEnd = os.getProcess(pid);
        String startTime = DateUtils.formatDateToString(new Date(processEnd.getStartTime()));
        String runTime = String.valueOf(processEnd.getUpTime() / 1000L);
        String cpuPercent = String.format("%.2f", processEnd.getProcessCpuLoadBetweenTicks(processBegin) * 100);
        String memoryPercent = String.format("%.2f", 100d * processEnd.getResidentSetSize()/ hd.getMemory().getTotal());

        res.put("startTime",startTime);
        res.put("runTime",runTime);
        res.put("cpuPercent",cpuPercent);
        res.put("memoryPercent",memoryPercent);
        return res;
    }

    static List<String> oshi = new ArrayList<>();

    public static void main(String[] args) {
        // 定义要查询的端口号
        int port = 8080;

        try {
            SystemInfo si = new SystemInfo();

            HardwareAbstractionLayer hal = si.getHardware();
            OperatingSystem os = si.getOperatingSystem();
//            List<OSProcess> processes = os.getProcesses();
//            for (OSProcess osProcess : processes){
//                System.out.println(osProcess.toString());
//            }

//            printProcesses(os,hal.getMemory());

            for (String s : oshi){
                System.out.println(s);
            }

//            for (int i=0;i<2;i++){
////                String[] s = com.matrictime.network.util.FormatUtil.formatBy1024(getUseFileSys());
////                System.out.println(s[0]+s[1]);
//                String ip = "192.168.7.231";
//                boolean pingResult = getPingResult(ip);
//                System.out.println(pingResult);
//            }
        } catch (Exception e) {
            System.out.println("Failed to bind to port " + port);
            e.printStackTrace();
        }
    }


}
