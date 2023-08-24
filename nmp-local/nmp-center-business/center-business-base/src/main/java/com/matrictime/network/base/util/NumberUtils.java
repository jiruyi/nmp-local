package com.matrictime.network.base.util;

import static com.matrictime.network.base.constant.DataConstants.ZERO;

public class NumberUtils {

    public static long getLong(Long param){
        if (param != null){
            return param.longValue();
        }else {
            return ZERO;
        }
    }
}
