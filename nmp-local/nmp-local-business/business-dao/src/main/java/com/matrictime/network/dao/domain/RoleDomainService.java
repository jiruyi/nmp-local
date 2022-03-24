package com.matrictime.network.dao.domain;


import com.matrictime.network.modelVo.NmplRoleVo;
import com.matrictime.network.request.RoleRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.RoleResponse;

public interface RoleDomainService {
    public Integer save(RoleRequest roleRequest)throws Exception;

    public Integer delete(RoleRequest roleRequest)throws Exception;

    public Integer modify(RoleRequest roleRequest)throws Exception;

    public PageInfo<NmplRoleVo> queryByConditions(RoleRequest roleRequest) throws Exception;

    public RoleResponse queryOne(RoleRequest roleRequest)throws Exception;
}
