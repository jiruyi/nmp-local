package com.matrictime.network.base.util;

import com.alibaba.fastjson.JSONObject;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.util.EdException;
import com.matrictime.network.api.request.BaseReq;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.util.ParamCheckUtil;
import org.apache.commons.lang3.StringUtils;

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
        String decryptMsg = JServiceImpl.decryptMsg(req.getEncryptParam());
        if (StringUtils.isBlank(decryptMsg)){
            throw new Exception("decrypt fail");
        }
        String commonParam = req.getCommonParam();
        JSONObject decJson = JSONObject.parseObject(decryptMsg);
        if (!ParamCheckUtil.checkVoStrBlank(commonParam)) {
            JSONObject commonJson = JSONObject.parseObject(commonParam);
            decJson.putAll(commonJson);
        }
        return decJson.toJavaObject((Class<T>) this.dto.getClass());
    }

    public String encryJsonToReq(T resp, String sid) throws Exception {
        String encryptMsg = JServiceImpl.encryptMsg(JSONObject.toJSONString(resp),sid);
        if (StringUtils.isBlank(encryptMsg)){
            throw new Exception("encrypt fail");
        }

        return encryptMsg;
    }
}
