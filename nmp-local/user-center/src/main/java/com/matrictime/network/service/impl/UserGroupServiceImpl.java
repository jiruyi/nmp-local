package com.matrictime.network.service.impl;

import com.matrictime.network.api.modelVo.UserGroupVo;
import com.matrictime.network.api.request.UserGroupReq;
import com.matrictime.network.api.response.UserGroupResp;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.domain.UserGroupDomianService;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserGroupService;
import com.matrictime.network.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserGroupServiceImpl extends SystemBaseService implements UserGroupService {
    @Autowired
    UserGroupDomianService userGroupDomianService;

    @Override
    public Result<Integer> createUserGroup(UserGroupReq userGroupReq) {
        Result<Integer> result;
        try {
            result = buildResult(userGroupDomianService.createUserGroup(userGroupReq));
        }catch (Exception e){
            log.info("组用户新增异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<Integer> modifyUserGroup(UserGroupReq userGroupReq) {
        Result<Integer> result;
        try {
            result = buildResult(userGroupDomianService.modifyUserGroup(userGroupReq));
        }catch (Exception e){
            log.info("组用户修改异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<Integer> deleteUserGroup(UserGroupReq userGroupReq) {
        Result<Integer> result;
        try {
            result = buildResult(userGroupDomianService.deleteUserGroup(userGroupReq));
        }catch (Exception e){
            log.info("组用户删除异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<UserGroupResp> queryUserGroup(UserGroupReq userGroupReq) {
        Result<UserGroupResp> result;
        try {
            UserGroupResp userGroupResp = new UserGroupResp();
            List<UserGroupVo> userGroupVos = userGroupDomianService.queryUserGroup(userGroupReq);
            userGroupResp.setUserGroupVos(userGroupVos);
            result = buildResult(userGroupResp);
        }catch (Exception e){
            log.info("组用户查询异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }
}
