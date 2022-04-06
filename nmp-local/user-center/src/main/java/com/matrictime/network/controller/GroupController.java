package com.matrictime.network.controller;

import com.matrictime.network.api.request.GroupReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/group")
@Api(value = "用户组",tags = "用户组相关接口")
@RestController
@Slf4j
public class GroupController {

    @Autowired
    GroupService groupService;

    @ApiOperation(value = "创建用户组",notes = "创建")
    @RequestMapping (value = "/createGroup",method = RequestMethod.POST)
    public Result createGroup(@RequestBody GroupReq groupReq){
        return groupService.createGroup(groupReq);
    }

    @ApiOperation(value = "删除用户组",notes = "删除")
    @RequestMapping (value = "/deleteGroup",method = RequestMethod.POST)
    public Result deleteGroup(@RequestBody GroupReq groupReq){
        return groupService.deleteGroup(groupReq);
    }

    @ApiOperation(value = "修改用户组",notes = "修改")
    @RequestMapping (value = "/modifyGroup",method = RequestMethod.POST)
    public Result modifyGroup(@RequestBody GroupReq groupReq){
        return groupService.modifyGroup(groupReq);
    }

    @ApiOperation(value = "查询用户组",notes = "查询")
    @RequestMapping (value = "/queryGroup",method = RequestMethod.POST)
    public Result queryGroup(@RequestBody GroupReq groupReq){
        return groupService.queryGroup(groupReq);
    }


}
