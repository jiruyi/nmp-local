package com.matrictime.network.base.util;

import com.matrictime.network.constant.DataConstants;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.validation.constraints.NotNull;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class DecimalConversionUtil {


    public static void main(String[] args) {
        String a ="9999-25-1-1-1";
//        byte[]res = bidToByteArray(a);
//        for (byte re : res) {
//            System.out.println(re);
//        }
        byte[] strings = idToByteArray(a);
        System.out.println(strings);
    }


    public static byte[] idToByteArray(String id){
        byte[]result = new byte[0];
        String[] idArray = idToIdStringArray(id);
        int length = idArray.length;
        for (int i = 0; i < length; i++) {
            if (i<2){
                result = splicingArrays(result,hexToByteLittle(idArray[i]));
            }else if (i<5){
                result = splicingArrays(result,intToByteLittle(Integer.parseInt(idArray[i])));
            }else {
                result = splicingArrays(result, toLH(Integer.parseInt(idArray[i])));
            }
        }
        return result;
    }

    public static String[] idToIdStringArray(String id){
        String[] split = id.split(DataConstants.KEY_SPLIT_MIDLINE);
        int slength = split.length;
        String first = split[0];
        char[] chars = first.toCharArray();
        int clength = chars.length;
        int tempLength = 4;
        String[] temp = new String[tempLength];
        for (int i=0;i<tempLength;i++){
            if (clength>(tempLength-1-i)){
                temp[i] = String.valueOf(chars[i-(tempLength-clength)]);
            }else {
                temp[i] = String.valueOf(0);
            }
        }
        int nationLength = 2;

        String[] nation = new String[nationLength];
        nation[0] = temp[0] + temp[1];
        nation[1] = temp[2] + temp[3];

        nation = Arrays.copyOf(nation,nationLength+slength-1);
        System.arraycopy(split,1,nation,nationLength,slength-1);
        return nation;
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

    public static byte[] hexToByteLittle(String args) {
        char[] chars = args.toCharArray();
        int high = Integer.parseInt(String.valueOf(chars[0]));
        high =  high << 4;
        int low = Integer.parseInt(String.valueOf(chars[1]));
        byte[] b = new byte[1];
        b[0] = (byte) ((high | low) & 0xff );
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
