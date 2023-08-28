package com.matrictime.network.base.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/28 0028 14:05
 * @desc
 */
public class IpUtil {
    /**
     * 获取本机IP 地址
     */
        public static void main(String[] args) {
            System.out.println(getLocalIp4Address());
        }

        public static Set<String> getLocalIp4Address() {
            Set<String> result = new HashSet<>();
            List<Inet4Address> list = null;
            try {
                list = getLocalIp4AddressFromNetworkInterface();
                list.forEach(d -> {
                    result.add(d.toString().replaceAll("/", ""));
                });
            } catch (Exception e) {
                //LoggerManager.systemLogger.error("getLocalIp4Address Exception", e);
            }
            return result;
        }

        /*
         * 获取本机所有网卡信息   得到所有IPv4信息
         * @return Inet4Address>
         */
        public static List<Inet4Address> getLocalIp4AddressFromNetworkInterface() throws SocketException {
            List<Inet4Address> addresses = new ArrayList<>();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                return addresses;
            }
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress instanceof Inet4Address) {
                        addresses.add((Inet4Address) inetAddress);
                    }
                }
            }
            return addresses;
        }
}
