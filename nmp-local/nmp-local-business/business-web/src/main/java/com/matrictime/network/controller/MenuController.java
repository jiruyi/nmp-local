package com.matrictime.network.controller;

import com.github.pagehelper.PageInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.MenuReq;
import com.matrictime.network.request.RoleRequest;
import com.matrictime.network.response.MenuResponse;
import com.matrictime.network.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单管理
 * @author zhangyunjie
 */
@RequestMapping(value = "/menu")
@Api(value = "菜单管理",tags = "菜单管理")
@RestController
@Slf4j
public class MenuController {

    @Autowired
    MenuService menuService;

    /**
     * 菜单查询
     * @return
     */
    @ApiOperation(value = "菜单查询接口",notes = "查询所有菜单")
    @RequestMapping(value = "/queryAllMenu",method = RequestMethod.POST)
    @RequiresPermissions("sys:power:query")
    public Result<MenuResponse> queryAllMenu(@RequestBody MenuReq menuReq){
        return menuService.queryAllMenu(menuReq);
    }


    /**
     * 菜单查询
     * @return
     */
    @ApiOperation(value = "菜单查询接口",notes = "查询当前用户拥有的所有菜单")
    @RequestMapping(value = "/queryOwnerMenu",method = RequestMethod.POST)
    @RequiresPermissions("sys:power:query")
    public Result<MenuResponse> queryOwnerMenu(@RequestBody MenuReq menuReq){
        return menuService.queryOwnerMenu(menuReq);
    }





}
