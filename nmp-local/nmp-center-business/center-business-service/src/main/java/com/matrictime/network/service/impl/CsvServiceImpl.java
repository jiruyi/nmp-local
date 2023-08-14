package com.matrictime.network.service.impl;

import com.matrictime.network.dao.model.NmplDictionary;
import com.matrictime.network.util.SnowFlake;
import org.apache.commons.io.input.BOMInputStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvServiceImpl {

    public static List<NmplDictionary> readCsvToPc(File file) {
        List<NmplDictionary> list = new ArrayList<>(); // 保存读取到的CSV数据
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(file))));
            String line = null;// 循环读取每行
            int num = 0;
            while ((line = reader.readLine()) != null) {
                num++;
                if(num==1){
                    continue;
                }
                String[] row = line.split("\\|", -1);
                String[] rows = row[0].split(",");
                NmplDictionary dictionary = new NmplDictionary();
                dictionary.setIdCode(rows[0]);
                if(rows.length>=2){
                    dictionary.setIdName(rows[1]);
                }
                list.add(dictionary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

