package com.matrictime.network.util;

import java.util.Properties;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


public class PropertiesUtil {
    public final static  Map<String,String> paramMap =new HashMap<String, String>() {
        {
            put("data.10000.name", "在线用户数");
            put("data.10001.name", "使用秘钥量");
            put("data.10002.name", "下载秘钥量");
            put("data.10003.name", "剩余秘钥量");
            put("data.10004.name", "内网宽带负载");
            put("data.10005.name", "外网宽带负载");
            put("data.10006.name", "带宽");
            put("data.10007.name", "秘钥存储量");

            put("data.10000.unit", "人");
            put("data.10001.unit", "byte");
            put("data.10002.unit", "byte");
            put("data.10003.unit", "byte");
            put("data.10004.unit", "bps");
            put("data.10005.unit", "bps");
            put("data.10006.unit", "bps");
            put("data.10007.unit", "byte");
        }
    };
//    public  final static  Map<String,String> paramMap=PropertiesUtil.getMap();
//    public static Map<String, String> getMap() {
//        String url = String.valueOf(PropertiesUtil.class.getResource("/"));
//        String path = url+ "data.properties";
//        Properties properties = new Properties();
//        properties = readFile(path);
//        Map<String, String> map = new HashMap<String, String>((Map) properties);
//        return map;
//    }
//
//    public static Properties readFile(String path) {
//        File file = new File(path);
//        Properties properties = new Properties();
//        InputStream in = null;
//        try {
//            in = new FileInputStream(file);
//            properties.load(in);
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return properties;
//    }

}
