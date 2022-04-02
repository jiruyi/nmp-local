package com.matrictime.network.util;

import java.util.Properties;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;


public class PropertiesUtil {
    public  final static  Map<String,String> paramMap=PropertiesUtil.getMap("E:\\nmp_local\\nmp-local\\nmp-local-business\\business-service\\src\\main\\resources\\data.properties");

    public static Map<String, String> getMap(String path) {
        Properties properties = new Properties();
        properties = readFile(path);
        Map<String, String> map = new HashMap<String, String>((Map) properties);
        return map;
    }

    public static Properties readFile(String path) {
        File file = new File(path);
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

}
