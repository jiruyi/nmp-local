package com.matrictime.network.base.util;

import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DecimalConversionUtil {


    public static void main(String[] args) {
        String a ="35-23-53-444-2002";
        byte[]res = bidToByteArray(a);
        for (byte re : res) {
            System.out.println(re);
        }
    }

    public static byte[] bidToByteArray(String bid){
        byte[]result = new byte[0];
        String[] split = bid.split("-");
        for (int i = 0; i < split.length-1; i++) {
            byte[] bytes = intToByteLittle(Integer.parseInt(split[i]));
            result = splicingArrays(result,bytes);
        }
        result = splicingArrays(result, toLH(Integer.parseInt(split[split.length-1])));
        return result;
    }

    public static byte[] toLH(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }

    public static byte[] intToByteLittle(int n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0xff);
        b[0] = (byte) (n >> 8 & 0xff);
        return b;
    }

    public static byte[] splicingArrays(byte[]... bytes) {
        int length = 0;
        for (byte[] b : bytes) {
            length += b.length;
        }
        int interimLength = 0;
        byte[] result = new byte[length];
        for (byte[] b : bytes) {
            System.arraycopy(b, 0, result, interimLength, b.length);
            interimLength += b.length;
        }
        return result;
    }


}
