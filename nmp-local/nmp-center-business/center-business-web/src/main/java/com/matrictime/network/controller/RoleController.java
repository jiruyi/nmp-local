package com.matrictime.network.controller;


import com.matrictime.network.model.Result;
import com.matrictime.network.request.RoleRequest;
import com.matrictime.network.response.RoleResponse;
import com.matrictime.network.service.RoleService;
import com.matrictime.network.shiro.UserRealm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色管理接口
 * @author zhangyunjie
 */
@RequestMapping(value = "/role")
@Api(value = "角色管理",tags = "用户角色管理")
@RestController
@Slf4j
public class RoleController {

    @Autowired
    RoleService roleService;


    /**
     * 角色条件查询
     * @param roleRequest
     * @return
     */
    @ApiOperation(value = "角色条件查询接口",notes = "条件查询，默认全量查询")
    @RequestMapping(value = "/queryByCondition",method = RequestMethod.POST)
    @RequiresPermissions("sys:role:query")
//    @SystemLog(opermodul = "角色管理模块",operDesc = "查询角色",operType = "查询")
    public Result queryRoleByConditon(@RequestBody RoleRequest roleRequest){
        return roleService.queryByConditon(roleRequest);
    }

    /**
     * 角色创建
     * @param roleRequest
     * @return
     */
    @ApiOperation(value = "角色创建接口",notes = "角色创建")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @RequiresPermissions("sys:role:save")
//    @SystemLog(opermodul = "角色管理模块",operDesc = "创建角色",operType = "新增")
    public Result saveRole(@RequestBody RoleRequest roleRequest){
        return roleService.save(roleRequest);
    }

    /**
     * 角色编辑
     * @param roleRequest
     * @return
     */
    @ApiOperation(value = "角色编辑接口",notes = "角色编辑")
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    @RequiresPermissions("sys:role:update")
//    @SystemLog(opermodul = "角色管理模块",operDesc = "修改角色",operType = "编辑",operLevl = "2")
    public Result modifyRole(@RequestBody RoleRequest roleRequest){
        return roleService.modify(roleRequest);
    }

    /**
     * 设置权限
     * @param roleRequest
     * @return
     */
    @ApiOperation(value = "角色编辑接口",notes = "角色编辑")
    @RequestMapping(value = "/modify111",method = RequestMethod.POST)
    @RequiresPermissions("sys:role:permersion")
//    @SystemLog(opermodul = "角色管理模块",operDesc = "修改角色",operType = "编辑",operLevl = "2")
    public Result permersion(@RequestBody RoleRequest roleRequest){
        return roleService.permission(roleRequest);
    }



    /**
     * 角色删除
     * @param roleRequest
     * @return
     */
    @ApiOperation(value = "角色删除接口",notes = "角色删除")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @RequiresPermissions("sys:role:delete")
//    @SystemLog(opermodul = "角色管理模块",operDesc = "删除角色",operType = "删除",operLevl = "2")
    public Result deleteRole(@RequestBody RoleRequest roleRequest){
        return roleService.delete(roleRequest);
    }

    /**
     * 单一角色查询
     * @param roleRequest
     * @return
     */
    @ApiOperation(value = "单一角色查询接口",notes = "查询一个角色具体信息")
    @RequestMapping(value = "/queryOne",method = RequestMethod.POST)
    @RequiresPermissions("sys:role:query")
    public Result<RoleResponse> queryOne(@RequestBody RoleRequest roleRequest) {
        return roleService.queryOne(roleRequest);
    }




    @ApiOperation(value = "用户登录测试",notes = "用户登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void login(){
        UserRealm realm =new UserRealm();
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(realm);
        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zyj","123456");
        subject.login(token);
        System.out.println("isAu"+subject.isAuthenticated());
        //测试删除接口
        subject.checkPermission("sys:role:delete");
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setRoleId(3L);
        roleService.delete(roleRequest);

        //测试更新接口
        subject.checkPermission("sys:role:update");
        roleRequest.setRoleCode("test1");
        roleService.modify(roleRequest);

        //查询单个用户信息
        subject.checkPermission("sys:role:query");
        roleRequest.setRoleId(1L);
        roleService.queryOne(roleRequest);

    }

    /**
     * 角色条件查询
     * @param roleRequest
     * @return
     */
    @ApiOperation(value = "角色条件查询接口",notes = "条件查询，默认全量查询")
    @RequestMapping(value = "/queryCreateRole",method = RequestMethod.POST)
//    @SystemLog(opermodul = "角色管理模块",operDesc = "查询角色",operType = "查询")
    public Result queryCreateRole(@RequestBody RoleRequest roleRequest){
        return roleService.queryCreateRole(roleRequest);
    }

}
