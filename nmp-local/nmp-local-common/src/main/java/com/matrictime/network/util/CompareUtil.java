package com.matrictime.network.util;

import static com.matrictime.network.constant.DataConstants.KEY_POINT;

public class CompareUtil {

    public static int compareIp(String ip1,String ip2){
        Long ip1Str = Long.valueOf(ip1.replace(KEY_POINT,""));
        Long ip2Str = Long.valueOf(ip2.replace(KEY_POINT,""));
        return ip1Str.compareTo(ip2Str);
    }

    public static int compareShortStr(String st1,String st2){
        Short s1 = Short.valueOf(st1);
        Short s2 = Short.valueOf(st2);
        return s1.compareTo(s2);
    }

    public static void main(String[] args) {
        compareIp("192.168.72.14","192.168.72.205");
        System.out.println(compareShortStr("19","20"));
    }
}
