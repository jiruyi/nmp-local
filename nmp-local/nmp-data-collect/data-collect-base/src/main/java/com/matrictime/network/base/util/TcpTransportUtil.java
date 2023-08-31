package com.matrictime.network.base.util;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.BusinessDataEnum;
import com.matrictime.network.modelVo.DataPushBody;
import com.matrictime.network.modelVo.TcpDataPushVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/25 0025 9:31
 * @desc
 */
@Slf4j
public class TcpTransportUtil {

    /**
     * @title convertByte
     * @param [a]
     * @return byte[]
     * @description
     * @author jiruyi
     * @create 2023/8/25 0025 10:08
     */
    public static byte[] convertByte(Byte a) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(a);
        return byteBuffer.array();
    }
    /**
     * @title convertShort
     * @param [a]
     * @return byte[]
     * @description
     * @author jiruyi
     * @create 2023/8/25 0025 10:08
     */
    public static byte[] convertShort(Short a) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putShort(a);
        return byteBuffer.array();
    }

    /**
     * @title convertInt
     * @param [a]
     * @return byte[]
     * @description
     * @author jiruyi
     * @create 2023/8/25 0025 10:08
     */
    public static byte[] convertInt(int a) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(a);
        return byteBuffer.array();
    }

    /**
     * @title convertStr
     * @param [a]
     * @return byte[]
     * @description
     * @author jiruyi
     * @create 2023/8/25 0025 10:09
     */
    public static byte[] convertStr(String a) {
        if(StringUtils.isEmpty(a)){
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(a.getBytes(StandardCharsets.UTF_8).length);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(a.getBytes(StandardCharsets.UTF_8));
        return byteBuffer.array();
    }

    /**
     * @title convertNetWorkId
     * @param [networkId]
     * @return byte[]
     * @description
     * @author jiruyi
     * @create 2023/8/25 0025 16:09
     */
    public static byte[] convertNetWorkId(String networkId) {
        ByteBuffer totalBuffer = ByteBuffer.allocate(16);
        if(StringUtils.isEmpty(networkId)){
            return null;
        }
        List<String> netIds = Arrays.asList(networkId.split(DataConstants.SPLIT_LINE)) ;
        for (int i= 0; i<netIds.size(); i++){
            if( i< 4){
                totalBuffer.put(convertShort(Short.parseShort(netIds.get(i))));
            }else{
                totalBuffer.put(convertInt(Integer.parseInt(netIds.get(i))));
            }
        }
        //补位4个字节
        totalBuffer.put(convertInt(0));
        return totalBuffer.array();
    }
    /**
     * @title getTcpDataPushVo
     * @param []
     * @return com.matrictime.network.modelVo.TcpDataPushVo
     * @description
     * @author jiruyi
     * @create 2023/8/25 0025 10:09
     */
    public static byte[] getTcpDataPushVo(BusinessDataEnum dataEnum, String tableData, String dstRID, String srcRID){
        if(ObjectUtils.isEmpty(tableData)){
            return null;
        }
        TcpDataPushVo tdp = new TcpDataPushVo();
        //计算业务数据大小
        DataPushBody dataPushBody =
                DataPushBody.builder().businessCode(dataEnum.getBusinessCode()).tableName(dataEnum.getTableName())
                        .busiDataJsonStr(tableData).build();
        String reqData = JSONObject.toJSONString(dataPushBody);
        int capacity = tdp.getUTotalLen()+ reqData.getBytes(StandardCharsets.UTF_8).length;
        log.info("TcpDataPushVo uTotalLen is :{}",capacity);
        //组装数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        byteBuffer.put(convertByte(tdp.getVersion()));
        byteBuffer.put(convertByte(tdp.getResv()));
        byteBuffer.put(convertShort(tdp.getUMsgType()));
        //数据字节长度
        byteBuffer.put(convertInt(capacity));
        //入网码
        byteBuffer.put(convertNetWorkId(dstRID));
        byteBuffer.put(convertNetWorkId(srcRID));
        byteBuffer.put(convertInt(tdp.getUIndex()));
        byteBuffer.put(convertByte(tdp.getUEncType()));
        byteBuffer.put(convertByte(tdp.getUEncRate()));
        byteBuffer.put(convertShort(tdp.getCheckSum()));
        //业务数据
        byteBuffer.put(convertStr(reqData));
        return byteBuffer.array();
    }
}
