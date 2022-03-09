package com.matrictime.network.util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;


import com.matrictime.network.exception.SystemException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.lang3.StringUtils;

/**
 *  写csv文件
 * @Author hexu
 * @Date 2020-07-26
 */
public class ExportCSVUtil {

    /**
     * 写CSV并转换为字节流
     * @param tableHeaderArr 表头
     * @param cellList 数据
     * @return
     */
    public static byte[] writeDataAfterToBytes(String[] tableHeaderArr, List<String> cellList) {
        byte[] bytes = new byte[0];
        ByteArrayOutputStream byteArrayOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream,StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            //excel文件需要通过文件头的bom来识别编码，而CSV文件格式不自带bom,所以写文件时，需要先写入bom头，否则excel打开乱码
            bufferedWriter.write(new String(ByteOrderMark.UTF_8.getBytes()));
            //写表头
            StringBuilder sb = new StringBuilder();
            String tableHeader = String.join(",", tableHeaderArr);
            sb.append(tableHeader + StringUtils.CR + StringUtils.LF);
            for (String rowCell : cellList) {
                sb.append(rowCell + StringUtils.CR + StringUtils.LF);
            }
            bufferedWriter.write(sb.toString());
            bufferedWriter.flush();
            //把输出流转换字节流
            bytes = byteArrayOutputStream.toString(StandardCharsets.UTF_8.name()).getBytes();
            return bytes;
        } catch (IOException e) {
            throw new SystemException("writeDataAfterToBytes IOException:{"+e.getMessage()+"}");
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                throw new SystemException("iostream close IOException:{"+e.getMessage()+"}");
            }
        }
    }

    /**
     * 写CSV并转换为字节流
     * @param headers 表头
     * @param cellList 表数据
     * @return
     */
    public static byte[] writeCsvAfterToBytes(String[] headers,List<Object[]> cellList) {
        byte[] bytes = new byte[0];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        CSVPrinter  csvPrinter = null;
        try {
            //创建csvPrinter并设置表格头
            csvPrinter = new CSVPrinter(bufferedWriter, CSVFormat.DEFAULT.withHeader(headers));
            //写数据
            csvPrinter.printRecords(cellList);
            csvPrinter.flush();
            bytes = byteArrayOutputStream.toString(StandardCharsets.UTF_8.name()).getBytes();
        } catch (IOException e) {
            throw new SystemException("writeCsv IOException:{"+e.getMessage()+"}");
        } finally {
            try {
                if (csvPrinter != null) {
                    csvPrinter.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                throw new SystemException("iostream close IOException:{"+e.getMessage()+"}");
            }
        }
        return bytes;
    }

}