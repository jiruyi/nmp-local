package com.matrictime.network.dao.mapper.extend;

import com.matrictime.network.modelVo.CompanyHeartbeatVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/18.
 */
public interface NmplCompanyHeartbeatExtMapper {

    int insertCompanyHeartbeat(@Param("list") List<CompanyHeartbeatVo> companyHeartbeatVos);
}
