package com.matrictime.network.base.util;

import com.matrictime.network.constant.DataConstants;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;

import static com.matrictime.network.constant.DataConstants.KEY_SPLIT_MIDLINE;
import static com.matrictime.network.constant.DataConstants.KEY_SPLIT_UNDERLINE;

public class DataChangeUtil {

    public static final String ZERO = "0";

    public static String BidChange(String id){
        String[] split = id.split(KEY_SPLIT_MIDLINE);
        StringBuffer sb = new StringBuffer();

        String nation = split[0];
        nation = fixZero(nation,4);
        sb.append(nation);
        sb.append(KEY_SPLIT_MIDLINE);

        String operator = split[1];
        operator = fixZero(changeIntToHex(Integer.parseInt(operator)),4);
        sb.append(operator);
        sb.append(KEY_SPLIT_MIDLINE);

        String bigArea = split[2];
        bigArea = fixZero(changeIntToHex(Integer.parseInt(bigArea)),4);
        sb.append(bigArea);
        sb.append(KEY_SPLIT_MIDLINE);

        String smallArea = split[3];
        smallArea = fixZero(changeIntToHex(Integer.parseInt(smallArea)),4);
        sb.append(smallArea);
        sb.append(KEY_SPLIT_MIDLINE);

        String station = split[4];
        station = fixZero(changeIntToHex(Integer.parseInt(station)),8);
        sb.append(station);

        return sb.toString();
    }

    public static String fixZero(String nation,Integer fixLength){
        int length = nation.length();
        if (length<fixLength){
            StringBuffer sb = new StringBuffer(fixLength);
            for (int i=0;i<fixLength-length;i++){
                sb.append(ZERO);
            }
            sb.append(nation);
            return sb.toString();
        }else {
            return nation;
        }
    }

    public static String changeIntToHex(Integer inter){
        String hexString = Integer.toHexString(inter);
        return hexString;
    }

    public static void main(String[] args) {
        System.out.println(BidChange("1-1-1-1-1"));
    }
}
