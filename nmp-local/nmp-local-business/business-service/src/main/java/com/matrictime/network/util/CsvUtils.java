package com.matrictime.network.util;

import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.dao.model.NmplOutlineSorterInfo;
import com.matrictime.network.dao.model.NmplUser;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvUtils {

    public static List<NmplOutlinePcInfo> readCsvToPc(File file) {
        List<NmplOutlinePcInfo> list = new ArrayList<>(); // 保存读取到的CSV数据
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(file))));
            String line = null;// 循环读取每行
            NmplUser user = RequestContext.getUser();
            int num = 0;
            while ((line = reader.readLine()) != null) {
                num++;
                if(num==1){
                    continue;
                }
                String[] row = line.split("\\|", -1);
                String[] rows = row[0].split(",");
                NmplOutlinePcInfo nmplOutlinePcInfo = new NmplOutlinePcInfo();
                nmplOutlinePcInfo.setStationNetworkId(rows[0]);
                if(rows.length>=2){
                    nmplOutlinePcInfo.setDeviceName(rows[1]);
                }
                if(rows.length>=3){
                    nmplOutlinePcInfo.setRemark(rows[2]);
                }
                if(rows.length>=4){
                    nmplOutlinePcInfo.setSwingIn(Boolean.valueOf(rows[3]));
                }
                if(rows.length>=5){
                    nmplOutlinePcInfo.setSwingOut(Boolean.valueOf(rows[4]));
                }
                nmplOutlinePcInfo.setDeviceId(SnowFlake.nextId_String());
                nmplOutlinePcInfo.setCreateUser(user.getNickName());
                list.add(nmplOutlinePcInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<NmplOutlineSorterInfo> readCsvToSorter(File file) {
        List<NmplOutlineSorterInfo> list = new ArrayList<>(); // 保存读取到的CSV数据
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(file))));// 读取CSV文件
            String line = null;// 循环读取每行
            NmplUser user = RequestContext.getUser();
            int num = 0;
            while ((line = reader.readLine()) != null) {
                num++;
                if(num==1){
                    continue;
                }
                String[] row = line.split("\\|", -1);
                String[] rows = row[0].split(",");
                System.out.println(line);
                for (String s : rows) {
                    System.out.println(s);
                }
                NmplOutlineSorterInfo nmplOutlineSorterInfo = new NmplOutlineSorterInfo();
                nmplOutlineSorterInfo.setStationNetworkId(rows[0]);
                if(rows.length>=2){
                    nmplOutlineSorterInfo.setDeviceName(rows[1]);
                }
                if(rows.length>=3){
                    nmplOutlineSorterInfo.setRemark(rows[2]);
                }
                nmplOutlineSorterInfo.setDeviceId(SnowFlake.nextId_String());
                nmplOutlineSorterInfo.setCreateUser(user.getNickName());
                list.add(nmplOutlineSorterInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }




}

