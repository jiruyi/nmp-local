package com.matrictime.network.base.util;

import com.matrictime.network.api.request.BaseReq;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.util.ParamCheckUtil;

public class CheckUtil {

    public static void checkParam(BaseReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getDestination())){
            throw new SystemException("Destination"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }
}
