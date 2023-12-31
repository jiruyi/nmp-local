package com.matrictime.network.base.util;

import com.alibaba.fastjson.JSONObject;
import com.jzsg.bussiness.JServiceImpl;
import com.matrictime.network.api.request.BaseReq;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class ReqUtil<T> {

    private T dto;

    public ReqUtil(){

    }

    public ReqUtil(T dto) {
        this.dto = dto;
    }

    public T jsonReqToDto(BaseReq req){
        if (!ParamCheckUtil.checkVoStrBlank(req.getDestination())){
            return dto;
        }

        JSONObject dtoObj = new JSONObject();
        String commonParam = req.getCommonParam();
        String encryptParam = req.getEncryptParam();
        if (!ParamCheckUtil.checkVoStrBlank(commonParam)) {
            dtoObj = JSONObject.parseObject(commonParam);
            dtoObj.put(DataConstants.KEY_COMMONPARAM,commonParam);

        }

        if (!ParamCheckUtil.checkVoStrBlank(encryptParam)) {
            dtoObj.put(DataConstants.KEY_ENCRYPTPARAM,encryptParam);
        }

        if (!ParamCheckUtil.checkVoStrBlank(encryptParam)){
            dtoObj.put(DataConstants.KEY_DESTINATION, UcConstants.DESTINATION_IN);
        }else {
            dtoObj.put(DataConstants.KEY_DESTINATION, UcConstants.DESTINATION_OUT);
        }

        return dtoObj.toJavaObject((Class<T>) this.dto.getClass());
    }


    public T decryJsonToReq(BaseReq req) throws Exception {
        log.info("开始解密");
        String decryptMsg = JServiceImpl.decryptMsg(req.getEncryptParam());

        // TODO: 2022/5/30 跳过平台解密，上线需删除
//        String decryptMsg = AesEncryptUtil.aesDecrypt(req.getEncryptParam());
        log.info("解密结果：{}",decryptMsg);
        if (StringUtils.isBlank(decryptMsg)){
            throw new Exception("decrypt fail");
        }
        String commonParam = req.getCommonParam();
        JSONObject decJson = JSONObject.parseObject(decryptMsg);
        if (!ParamCheckUtil.checkVoStrBlank(commonParam)) {
            JSONObject commonJson = JSONObject.parseObject(commonParam);
            decJson.putAll(commonJson);
            decJson.put("destination",req.getDestination());
        }
        return decJson.toJavaObject((Class<T>) this.dto.getClass());
    }

    public String encryJsonToReq(T resp, String sid) throws Exception {
        if (ParamCheckUtil.checkVoStrBlank(sid)){
            throw new Exception("encrypt fail,sid null");
        }
        String encryptMsg = JServiceImpl.encryptMsg(JSONObject.toJSONString(resp),sid);

        // TODO: 2022/5/30 跳过平台加密，上线需删除
//        String encryptMsg = AesEncryptUtil.aesEncrypt(JSONObject.toJSONString(resp));

        if (StringUtils.isBlank(encryptMsg)){
            throw new Exception("encrypt fail,result null");
        }

        if (StringUtils.isBlank(encryptMsg)){
            throw new Exception("encrypt fail,result null");
        }

        return encryptMsg;
    }

    public String encryJsonStringToReq(String jsonString, String sid) throws Exception {
        if (ParamCheckUtil.checkVoStrBlank(sid)){
            throw new Exception("encrypt fail,sid null");
        }
        String encryptMsg = JServiceImpl.encryptMsg(jsonString,sid);

        // TODO: 2022/5/30 跳过平台加密，上线需删除
//        String encryptMsg = AesEncryptUtil.aesEncrypt(jsonString);

        if (StringUtils.isBlank(encryptMsg)){
            throw new Exception("encrypt fail,result null");
        }

        return encryptMsg;
    }
}
