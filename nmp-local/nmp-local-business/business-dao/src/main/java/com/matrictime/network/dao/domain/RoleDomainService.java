package com.matrictime.network.dao.domain;


import com.matrictime.network.modelVo.NmplRoleVo;
import com.matrictime.network.request.RoleRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.RoleResponse;

public interface RoleDomainService {
    public Integer save(RoleRequest roleRequest);

    public Integer delete(RoleRequest roleRequest);

    public Integer modify(RoleRequest roleRequest);

    public PageInfo<NmplRoleVo> queryByConditions(RoleRequest roleRequest);

    public RoleResponse queryOne(RoleRequest roleRequest);
}
