package com.matrictime.network.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.matrictime.network.constant.RegExpConstants.*;

public class CommonCheckUtil {

    /**
     * @param param 输入参数
     * @param minLength 最少长度(非必输，可传null)
     * @param maxLength 最大长度
     * @desc 校验param字符串长度是否小于等于maxLength并且大于等于minLength的长度
     * @return true/false param为null返回true
     */
    public static boolean checkStringLength(String param,Integer minLength,Integer maxLength){
        if (param == null){
            return true;
        }else {
            if (param.length() <= maxLength){
                if (minLength != null && param.length()<minLength){
                    return false;
                }
                return true;
            }
            return false;
        }
    }

    /**
     *
     * @param str
     * @param regExp
     * @return 正确返回true
     * @throws Exception
     */
    public static boolean isParamMatchPattern(String str,String regExp){
        if (regExp == null){
            return false;
        }
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     *
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 144,145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     * @param str
     * @return 正确返回true
     * @throws Exception
     */
    public static boolean isChinaPhoneLegal(String str){
        try {
            return isParamMatchPattern(str,REG_EXP_CHINA_PHONE);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     * @param str
     * @return 正确返回true
     * @throws Exception
     */
    public static boolean isHKPhoneLegal(String str){
        try {
            return isParamMatchPattern(str,REG_EXP_HK_PHONE);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * ipv4的ip地址都是（0~255）.（0~255）.（0~255）.（0~255）的格式
     * @param str
     * @return 正确返回true
     * @throws Exception
     */
    public static boolean isIpv4Legal(String str){
        try {
            return isParamMatchPattern(str,REG_EXP_IPV4);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     *
     * @param str
     * @return 正确返回true
     */
    public static boolean isIpv6Legal(String str){
        try {
            return isParamMatchPattern(str,REG_EXP_IPV6);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * port端口号校验（1-65535）
     * @param str
     * @return 正确返回true
     * @throws Exception
     */
    public static boolean isPortLegal(String str){
        try {
            return isParamMatchPattern(str,REG_EXP_PORT);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 邮箱格式校验
     * @param str
     * @return 正确返回true
     * @throws Exception
     */
    public static boolean isEmailLegal(String str){
        try {
            return isParamMatchPattern(str,REG_EXP_EMAIL);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * port端口号校验（1024-65535）
     * @param str
     * @return
     */
    public static boolean isPortWithinScope(String str){
        try {
            Integer port = Integer.valueOf(str);
            if (port>65535 || port<1024){
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        checkStringLength("dkajldas",0,10);
    }
}
