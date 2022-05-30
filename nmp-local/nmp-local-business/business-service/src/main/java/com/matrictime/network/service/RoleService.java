package com.matrictime.network.service;


import com.matrictime.network.model.Result;
import com.matrictime.network.request.RoleRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.RoleResponse;

public interface RoleService {
    Result queryByConditon(RoleRequest roleRequest);

    Result save(RoleRequest roleRequest);

    Result modify(RoleRequest roleRequest);

    Result delete(RoleRequest roleRequest);

    Result<RoleResponse> queryOne(RoleRequest roleRequest);


}
