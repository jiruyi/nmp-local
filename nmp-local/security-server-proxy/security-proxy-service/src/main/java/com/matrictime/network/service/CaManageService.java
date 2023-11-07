package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CaManageVo;

/**
 * @author by wangqiang
 * @date 2023/11/6.
 */
public interface CaManageService {

    Result<Integer> insertCaManage(CaManageVo caManageVo);

    Result<Integer> deleteCaManage(CaManageVo caManageVo);
}
