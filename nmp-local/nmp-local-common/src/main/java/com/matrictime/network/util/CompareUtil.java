package com.matrictime.network.util;

import static com.matrictime.network.constant.DataConstants.KEY_POINT;

public class CompareUtil {

    public static int compareIp(String ip1,String ip2){
        Long ip1Str = Long.valueOf(ip1.replace(KEY_POINT,""));
        Long ip2Str = Long.valueOf(ip2.replace(KEY_POINT,""));
        return ip1Str.compareTo(ip2Str);
    }

    public static void main(String[] args) {
        compareIp("192.168.72.14","192.168.72.205");
    }
}
