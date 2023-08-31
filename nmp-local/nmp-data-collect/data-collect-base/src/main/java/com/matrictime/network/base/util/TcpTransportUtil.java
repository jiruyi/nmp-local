package com.matrictime.network.base.util;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.BusinessDataEnum;
import com.matrictime.network.modelVo.DataPushBody;
import com.matrictime.network.modelVo.TcpDataPushVo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
        ByteBuf byteBuf = Unpooled.buffer(1);

        //ByteBuf.order(ByteOrder.LITTLE_ENDIAN);
        byteBuf.writeByte(a);
        return byteBuf.array();
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
        ByteBuf byteBuf = Unpooled.buffer(2);
        ///ByteBuf.order(ByteOrder.LITTLE_ENDIAN);
        byteBuf.writeShort(a);
        return byteBuf.array();
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
        ByteBuf byteBuf = Unpooled.buffer(4);
        //  ByteBuf.order(ByteOrder.LITTLE_ENDIAN);
        byteBuf.writeInt(a);
        return byteBuf.array();
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
        ByteBuf byteBuf =Unpooled.buffer(a.getBytes(CharsetUtil.UTF_8).length);
        // ByteBuf.order(ByteOrder.LITTLE_ENDIAN);
        byteBuf.writeBytes(a.getBytes(CharsetUtil.UTF_8));
        return byteBuf.array();
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
        ByteBuf totalBuffer = Unpooled.buffer(16);
        if(StringUtils.isEmpty(networkId)){
            return null;
        }
        List<String> netIds = Arrays.asList(networkId.split(DataConstants.SPLIT_LINE)) ;
        for (int i= 0; i<netIds.size(); i++){
            if( i< 4){
                totalBuffer.writeBytes(convertShort(Short.parseShort(netIds.get(i))));
            }else{
                totalBuffer.writeBytes(convertInt(Integer.parseInt(netIds.get(i))));
            }
        }
        //补位4个字节
        totalBuffer.writeBytes(convertInt(0));
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
        ByteBuf byteBuf = Unpooled.buffer(capacity);
        byteBuf.writeBytes(convertByte(tdp.getVersion()));
        byteBuf.writeBytes(convertByte(tdp.getResv()));
        byteBuf.writeBytes(convertShort(tdp.getUMsgType()));
        //数据字节长度
        byteBuf.writeBytes(convertInt(capacity));
        //入网码
        byteBuf.writeBytes(convertNetWorkId(dstRID));
        byteBuf.writeBytes(convertNetWorkId(srcRID));
        byteBuf.writeBytes(convertInt(tdp.getUIndex()));
        byteBuf.writeBytes(convertByte(tdp.getUEncType()));
        byteBuf.writeBytes(convertByte(tdp.getUEncRate()));
        byteBuf.writeBytes(convertShort(tdp.getCheckSum()));
        //业务数据
        byteBuf.writeBytes(convertStr(reqData));
        return byteBuf.array();
    }
}
