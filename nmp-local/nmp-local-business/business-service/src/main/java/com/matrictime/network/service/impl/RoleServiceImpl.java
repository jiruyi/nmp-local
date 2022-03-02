package com.matrictime.network.service.impl;


import com.matrictime.network.base.exception.ErrorCode;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.domain.RoleDomainService;
import com.matrictime.network.dao.mapper.NmplRoleMenuRelationMapper;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplRole;
import com.matrictime.network.request.RoleRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.RoleResponse;
import com.matrictime.network.service.RoleService;
import com.matrictime.network.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.matrictime.network.base.SystemBaseService;

@Service
@Slf4j
public class RoleServiceImpl extends SystemBaseService implements RoleService {
    @Autowired
    RoleDomainService roleDomainService;
    @Autowired
    NmplRoleMenuRelationMapper nmplRoleMenuRelationMapper;


    @Override
    public Result<PageInfo> queryByConditon(RoleRequest roleRequest) {
        Result<PageInfo> result = null;
        NmplUser user = ShiroUtils.getUserEntity();
        try {
            //如果不是管理员用户则将管理员过滤
            if (user!=null&&Long.parseLong(user.getRoleId())== DataConstants.SUPER_ADMIN){
                roleRequest.setAdmin(true);
            }
            //多条件查询
            PageInfo<NmplRole> pageResult =  new PageInfo<>();
            pageResult = roleDomainService.queryByConditions(roleRequest);
            result = buildResult(pageResult);
        }catch (Exception e){
            log.error("查询角色异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }


    @Override
    public Result save(RoleRequest roleRequest) {
        Result<Integer> result;
        try {
            NmplUser nmplUser = ShiroUtils.getUserEntity();
            //roleRequest.setCreateUser(nmplUser.getNickName());
            result = buildResult(roleDomainService.save(roleRequest));
        }catch (Exception e){
            log.info("创建角色异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result modify(RoleRequest roleRequest) {
        Result<Integer> result = null;
        try {
            NmplUser nmplUser = ShiroUtils.getUserEntity();
            //除管理员用户，其他用户只能编辑自己创建的角色
            if (Long.parseLong(nmplUser.getRoleId())!=DataConstants.SUPER_ADMIN && roleRequest.getCreateUser()!=nmplUser.getNickName()){
                result = failResult(ErrorCode.SYSTEM_ERROR, "非该角色的创建者，无编辑该角色的权限");
                return result;
            }
            if(roleRequest.getRoleId()==null){
                result = failResult(ErrorCode.SYSTEM_ERROR, "参数缺失");
                return result;
            }
            result = buildResult(roleDomainService.modify(roleRequest));
        }catch (Exception e){
            log.info("编辑角色异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result delete(RoleRequest roleRequest) {
        //只能删除自己创建的且无关联用户的角色
        Result<Integer> result = null;
        try {
            NmplUser nmplUser = ShiroUtils.getUserEntity();
            //除管理员用户，其他用户只能删除自己创建的角色
            if (Long.parseLong(nmplUser.getRoleId())!=DataConstants.SUPER_ADMIN && roleRequest.getCreateUser()!=nmplUser.getNickName()){
                result = failResult(ErrorCode.SYSTEM_ERROR, "非该角色的创建者，无编辑该角色的权限");
                return result;
            }
            if(roleRequest.getRoleId()==null){
                result = failResult(ErrorCode.SYSTEM_ERROR, "参数缺失");
                return result;
            }
            result = buildResult(roleDomainService.delete(roleRequest));
        }catch (Exception e){
            log.info("删除角色异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<RoleResponse> queryOne(RoleRequest roleRequest) {

        Result<RoleResponse> result = null;
        try {
            NmplUser nmplUser = ShiroUtils.getUserEntity();
            //普通用户无法获取超级管理员信息
            if (Long.parseLong(nmplUser.getRoleId())!=DataConstants.SUPER_ADMIN && roleRequest.getRoleId()==DataConstants.SUPER_ADMIN){
                result = failResult(ErrorCode.SYSTEM_ERROR, "无权限查看");
                return result;
            }
            if(roleRequest.getRoleId()==null){
                result = failResult(ErrorCode.SYSTEM_ERROR, "参数缺失");
                return result;
            }
            result = buildResult(roleDomainService.queryOne(roleRequest));
        }catch (Exception e){
            log.info("查询角色异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }
}
