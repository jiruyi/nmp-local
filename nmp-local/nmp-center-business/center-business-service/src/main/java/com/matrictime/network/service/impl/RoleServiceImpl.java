package com.matrictime.network.service.impl;


import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.exception.ErrorCode;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.RoleDomainService;
import com.matrictime.network.dao.mapper.NmplRoleMenuRelationMapper;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplRoleVo;
import com.matrictime.network.request.RoleRequest;
import com.matrictime.network.response.RoleResp;
import com.matrictime.network.response.RoleResponse;
import com.matrictime.network.service.RoleService;
import com.matrictime.network.util.CommonCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl extends SystemBaseService implements RoleService {
    @Autowired
    RoleDomainService roleDomainService;
    @Autowired
    NmplRoleMenuRelationMapper nmplRoleMenuRelationMapper;


    @Override
    public Result queryByConditon(RoleRequest roleRequest) {
        Result result = null;
        NmplUser user = RequestContext.getUser();
        try {
            //如果不是管理员用户则将管理员过滤
            if (user!=null&&Long.parseLong(user.getRoleId())== DataConstants.SUPER_ADMIN){
                roleRequest.setAdmin(true);
            }
            //多条件查询
            //PageInfo<NmplRoleVo> pageResult =  new PageInfo<>();
            RoleResp roleResp = new RoleResp();
            List<NmplRoleVo> list = roleDomainService.queryByConditions(roleRequest);
            roleResp.setList(list);
            result = buildResult(roleResp);
        }catch (SystemException e){
            log.error("查询角色异常：",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("查询角色异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    @Override
    public Result save(RoleRequest roleRequest) {
        Result<Integer> result;
        try {
            if(!parmLenthCheck(roleRequest)){
                throw new SystemException(ErrorMessageContants.PARAM_LENTH_ERROR_MSG);
            }
            NmplUser nmplUser = RequestContext.getUser();
            roleRequest.setCreateUser(String.valueOf(nmplUser.getUserId()));
            result = buildResult(roleDomainService.save(roleRequest));
        }catch (SystemException e){
            log.info("创建角色异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("创建角色异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result modify(RoleRequest roleRequest) {
        Result<Integer> result = null;
        try {
            if(!parmLenthCheck(roleRequest)){
                throw new SystemException(ErrorMessageContants.PARAM_LENTH_ERROR_MSG);
            }
            NmplUser nmplUser = RequestContext.getUser();
            roleRequest.setUpdateUser(String.valueOf(nmplUser.getUserId()));
            //除管理员用户，其他用户只能编辑自己创建的角色
            if (Long.parseLong(nmplUser.getRoleId())!=DataConstants.SUPER_ADMIN && !Long.valueOf(roleRequest.getCreateUser()).equals(nmplUser.getUserId())
                    &&Long.parseLong(nmplUser.getRoleId())!=DataConstants.COMMON_ADMIN){
                result = failResult(ErrorCode.SYSTEM_ERROR, "非该角色的创建者，无编辑该角色的权限");
                return result;
            }
            if(roleRequest.getRoleId()==null){
                result = failResult(ErrorCode.SYSTEM_ERROR, "参数缺失");
                return result;
            }
            result = buildResult(roleDomainService.modify(roleRequest));
        }catch (SystemException e){
            log.info("编辑角色异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("编辑角色异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    @Override
    public Result delete(RoleRequest roleRequest) {
        //只能删除自己创建的且无关联用户的角色
        Result<Integer> result = null;
        try {
            NmplUser nmplUser = RequestContext.getUser();

            roleRequest.setUpdateUser(String.valueOf(nmplUser.getUserId()));
            //除管理员用户，其他用户只能删除自己创建的角色
            if (Long.parseLong(nmplUser.getRoleId()) != DataConstants.SUPER_ADMIN && !Long.valueOf(roleRequest.getCreateUser()).equals(nmplUser.getUserId())
                    &&Long.parseLong(nmplUser.getRoleId())!=DataConstants.COMMON_ADMIN) {
                result = failResult(ErrorCode.SYSTEM_ERROR, "非该角色的创建者，无编辑该角色的权限");
                return result;
            }

            if (roleRequest.getRoleId() == null) {
                result = failResult(ErrorCode.SYSTEM_ERROR, "参数缺失");
                return result;
            }
            result = buildResult(roleDomainService.delete(roleRequest));
        }catch (SystemException e){
            log.info("删除角色异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("删除角色异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<RoleResponse> queryOne(RoleRequest roleRequest) {

        Result<RoleResponse> result = null;
        try {
            NmplUser nmplUser = RequestContext.getUser();
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
        }catch (SystemException e){
            log.info("查询角色异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("查询角色异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    @Override
    public Result queryCreateRole(RoleRequest roleRequest) {
        Result result = null;
        NmplUser user = RequestContext.getUser();
        try {
            roleRequest.setRoleId(Long.valueOf(user.getRoleId()));
            roleRequest.setCreateUser(String.valueOf(user.getUserId()));
            //多条件查询
            //PageInfo<NmplRoleVo> pageResult =  new PageInfo<>();
            RoleResp roleResp = new RoleResp();
            List<NmplRoleVo> list = roleDomainService.queryCreateRole(roleRequest);
            roleResp.setList(list);
            result = buildResult(roleResp);
        }catch (SystemException e){
            log.error("查询角色异常：",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("查询角色异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    private boolean parmLenthCheck(RoleRequest roleRequest){
        boolean flag = true;
        if(roleRequest.getRoleName()!=null){
            flag = CommonCheckUtil.checkStringLength(roleRequest.getRoleName(), null, 30);
        }
//        if(roleRequest.getRoleCode()!=null){
//            flag = CommonCheckUtil.checkStringLength(roleRequest.getRoleCode(), null, 100);
//        }
        return flag;
    }
}
