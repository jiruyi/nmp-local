package com.matrictime.network.util;

import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.dao.model.NmplOutlineSorterInfo;
import com.matrictime.network.dao.model.NmplUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvUtils {

    public static List<NmplOutlinePcInfo> readCsvToPc(File file) {
        List<NmplOutlinePcInfo> list = new ArrayList<>(); // 保存读取到的CSV数据
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file)); // 读取CSV文件
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
                nmplOutlinePcInfo.setDeviceId(rows[0]);
                nmplOutlinePcInfo.setDeviceName(rows[1]);
                nmplOutlinePcInfo.setRemark(rows[2]);
                nmplOutlinePcInfo.setId(SnowFlake.nextId());
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
            BufferedReader reader = new BufferedReader(new FileReader(file)); // 读取CSV文件
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
                NmplOutlineSorterInfo nmplOutlineSorterInfo = new NmplOutlineSorterInfo();
                nmplOutlineSorterInfo.setDeviceId(rows[0]);
                nmplOutlineSorterInfo.setDeviceName(rows[1]);
                nmplOutlineSorterInfo.setRemark(rows[2]);
                nmplOutlineSorterInfo.setId(SnowFlake.nextId());
                nmplOutlineSorterInfo.setCreateUser(user.getNickName());
                list.add(nmplOutlineSorterInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

