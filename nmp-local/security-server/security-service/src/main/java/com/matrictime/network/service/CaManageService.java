package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CaManageVo;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.modelVo.StationManageVo;
import com.matrictime.network.req.CaManageRequest;
import com.matrictime.network.resp.CaManageResp;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
public interface CaManageService {

    Result<Integer> insertCaManage(CaManageRequest caManageRequest);

    Result<Integer> deleteCaManage(CaManageRequest caManageRequest);

    Result<PageInfo<CaManageVo>> selectCaManage(CaManageRequest caManageRequest);

    Result<Integer> updateCaManage(CaManageRequest caManageRequest);

}
