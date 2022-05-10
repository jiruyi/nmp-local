package com.matrictime.network.base.util;

import com.alibaba.fastjson.JSONObject;
import com.jzsg.bussiness.JServiceImpl;
import com.jzsg.bussiness.util.EdException;
import com.matrictime.network.api.request.BaseReq;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.util.ParamCheckUtil;

public class ReqUtil<T> {

    private T dto;

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
        if (!ParamCheckUtil.checkVoStrBlank(req.getCommonParam())) {
            JSONObject jsonObject = JSONObject.parseObject(commonParam);
            dtoObj = JSONObject.parseObject(commonParam);
            if (jsonObject.containsKey(DataConstants.KEY_COMMONPARAM)){
                dtoObj.put(DataConstants.KEY_COMMONPARAM,jsonObject.getString(DataConstants.KEY_COMMONPARAM));
            }
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

    public T decryJsonToReq(BaseReq req) throws EdException {
        String decryptMsg = JServiceImpl.decryptMsg(req.getEncryptParam());
        String commonParam = req.getCommonParam();
        JSONObject decJson = JSONObject.parseObject(decryptMsg);
        JSONObject commonJson = JSONObject.parseObject(commonParam);
        decJson.putAll(commonJson);

        return decJson.toJavaObject((Class<T>) this.dto.getClass());
    }
}
