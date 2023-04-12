package com.matrictime.network.base.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NetUtil {

    /**
     * 测试ip及端口连通性
     *
     * @param host
     * @param port
     * @param timeout
     * @return boolean
     */
    public static boolean testIpAndPort(String host, int port, int timeout) {
        Socket s = new Socket();
        boolean status = false;
        try {
            s.connect(new InetSocketAddress(host, port), timeout);
            System.out.println("ip及端口访问正常");
            status = true;
        } catch (IOException e) {
            System.out.println(host + ":" + port + "无法访问！");
        } finally {
            try {
                s.close();
            } catch (IOException ex) {
                System.out.println("关闭socket异常" + ex);
            }
        }
        return status;
    }


    public static void main(String[] args) {
        String host = "1.1.2.3";
        int port = 8006;
        int timeOut = 1000;
        System.out.println(testIpAndPort(host, port, timeOut));
    }

}
