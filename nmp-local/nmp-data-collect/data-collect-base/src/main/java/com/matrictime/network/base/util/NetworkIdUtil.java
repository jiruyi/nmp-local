package com.matrictime.network.base.util;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
public class NetworkIdUtil {

    /**
     * 切割基站中的networkId，生成小区唯一标识
     * @param str
     * @return
     */
    public static String splitNetworkId(String str){
        //切割小区唯一标识符
        String[] split = str.split("-");
        String networkIdString = "";
        for(int i = 0;i < split.length-1;i++){
            if(i < split.length-2){
                networkIdString = networkIdString + split[i] + "-";
            }else {
                networkIdString = networkIdString + split[i];
            }

        }
        return networkIdString;
    }
}
