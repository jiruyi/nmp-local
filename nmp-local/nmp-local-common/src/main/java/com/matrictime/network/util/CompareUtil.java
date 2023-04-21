package com.matrictime.network.util;

import static com.matrictime.network.constant.DataConstants.FILE_SPLIT;

public class CompareUtil {

    public static int compareIp(String ip1,String ip2){
        Long ip1Str = Long.valueOf(ip1.replace(FILE_SPLIT,""));
        Long ip2Str = Long.valueOf(ip2.replace(FILE_SPLIT,""));
        return ip1Str.compareTo(ip2Str);
    }
}
