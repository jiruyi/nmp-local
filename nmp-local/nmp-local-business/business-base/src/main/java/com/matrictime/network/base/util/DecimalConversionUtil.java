package com.matrictime.network.base.util;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Slf4j
public class DecimalConversionUtil {

    /**
     * 16进制转2进制
     * @param hex 16进制
     * @return 2进制
     */
    @NotNull
    public static String fromHexString(String hex){
        if(StringUtil.isEmpty(hex)){
            return "";
        }
        String bString = "", tmp;
        for (int i = 0; i < hex.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hex.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    public static void main(String[] args) {
        String a ="9911-1241-1121-1111";
        byte[]res = bidToByteArray(a);
        for (byte re : res) {
            System.out.println(re);
        }
    }

    public static byte[] bidToByteArray(String bid){
        String hexString = bid.replaceAll("-","").trim();
        return hexStr2Byte(hexString);
    }

    public static byte[] hexStr2Byte(String hex) {
        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return bf.array();
    }



}
