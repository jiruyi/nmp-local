package com.matrictime.network.util;

import com.matrictime.network.modelVo.NetCardInfoVo;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetCardUtils {

    public static List<NetCardInfoVo> getNetCardInfo() throws SocketException {
        List<NetCardInfoVo> netCardInfoVos = new ArrayList<>();
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface ni = en.nextElement();
            Enumeration<InetAddress> addresses = ni.getInetAddresses();
            InetAddress ip;
            while (addresses.hasMoreElements()) {
                ip = addresses.nextElement();
                if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(':') == -1) {
                    NetCardInfoVo netCardInfoVo = new NetCardInfoVo();
                    netCardInfoVo.setName(ni.getName());
                    netCardInfoVo.setDispalyName(ni.getDisplayName());
                    netCardInfoVo.setIpv4(ip.getHostAddress());
                    List<InterfaceAddress> list = ni.getInterfaceAddresses();
                    for (InterfaceAddress ia : list) {
                        System.out.println(" Address = " + ia.getAddress().getHostAddress());
                        if(!ia.getAddress().getHostAddress().equals(netCardInfoVo.getIpv4())){
                            netCardInfoVo.setIpv6(ia.getAddress().getHostAddress());
                        }
                    }
                    netCardInfoVos.add(netCardInfoVo);
                }
            }
        }
        return netCardInfoVos;
    }

}
