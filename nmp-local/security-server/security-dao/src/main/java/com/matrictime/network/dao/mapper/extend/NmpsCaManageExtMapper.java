package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.CaManageVo;
import com.matrictime.network.req.CaManageRequest;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
public interface NmpsCaManageExtMapper {

    List<CaManageVo> selectCaManage(CaManageRequest caManageRequest);
}
