//package com.matrictime.network.controller;
//
//import com.matrictime.network.aop.MonitorRequest;
//import com.matrictime.network.api.request.GroupReq;
//import com.matrictime.network.api.request.UserGroupReq;
//import com.matrictime.network.model.Result;
//import com.matrictime.network.service.GroupService;
//import com.matrictime.network.service.UserGroupService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequestMapping(value = "/group")
//@Api(value = "用户组",tags = "用户组相关接口")
//@RestController
//@Slf4j
//public class GroupController {
//
//    @Autowired
//    GroupService groupService;
//    @Autowired
//    UserGroupService userGroupService;
//
//
//    @ApiOperation(value = "创建组",notes = "创建")
//    @RequestMapping (value = "/createGroup",method = RequestMethod.POST)
//    @MonitorRequest
//    public Result createGroup(@RequestBody GroupReq groupReq){
//        Result result = groupService.createGroup(groupReq);
////        try {
////            result = commonService.encrypt(groupReq.getCommonKey(), groupReq.getDestination(), result);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        return result;
//    }
//
//    @ApiOperation(value = "删除组",notes = "删除")
//    @RequestMapping (value = "/deleteGroup",method = RequestMethod.POST)
//    @MonitorRequest
//    public Result deleteGroup(@RequestBody GroupReq groupReq){
//        Result result = groupService.deleteGroup(groupReq);
////        try {
////            result = commonService.encrypt(groupReq.getCommonKey(), groupReq.getDestination(), result);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        return result;
//    }
//
//    @ApiOperation(value = "修改组",notes = "修改")
//    @RequestMapping (value = "/modifyGroup",method = RequestMethod.POST)
//    @MonitorRequest
//    public Result modifyGroup(@RequestBody GroupReq groupReq){
//        Result result = groupService.modifyGroup(groupReq);
////        try {
////            result = commonService.encrypt(groupReq.getCommonKey(), groupReq.getDestination(), result);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        return result;
//    }
//
//    @ApiOperation(value = "查询组",notes = "查询")
//    @RequestMapping (value = "/queryGroup",method = RequestMethod.POST)
//    @MonitorRequest
//    public Result queryGroup(@RequestBody GroupReq groupReq){
//        Result result = groupService.queryGroup(groupReq);
////        try {
////            result = commonService.encrypt(groupReq.getCommonKey(), groupReq.getDestination(), result);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        return result;
//    }
//
//    //-----------------------------------------------------------------------
//
//    @ApiOperation(value = "创建组内用户",notes = "创建")
//    @RequestMapping (value = "/createUserGroup",method = RequestMethod.POST)
//    @MonitorRequest
//    public Result createUserGroup(@RequestBody UserGroupReq userGroupReq){
//        Result result = userGroupService.createUserGroup(userGroupReq);
////        try {
////            result = commonService.encrypt(userGroupReq.getCommonKey(), userGroupReq.getDestination(), result);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        return result;
//    }
//
//    @ApiOperation(value = "删除组内用户",notes = "删除")
//    @RequestMapping (value = "/deleteUserGroup",method = RequestMethod.POST)
//    @MonitorRequest
//    public Result deleteUserGroup(@RequestBody UserGroupReq userGroupReq){
//        Result result = userGroupService.deleteUserGroup(userGroupReq);
////        try {
////            result = commonService.encrypt(userGroupReq.getCommonKey(), userGroupReq.getDestination(), result);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        return result;
//    }
//
//    @ApiOperation(value = "修改组内用户",notes = "修改")
//    @RequestMapping (value = "/modifyUserGroup",method = RequestMethod.POST)
//    @MonitorRequest
//    public Result modifyUserGroup(@RequestBody UserGroupReq userGroupReq){
//        Result result = userGroupService.modifyUserGroup(userGroupReq);
////        try {
////            result = commonService.encrypt(userGroupReq.getCommonKey(), userGroupReq.getDestination(), result);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        return result;
//    }
//
////    @ApiOperation(value = "查询组内用户",notes = "查询")
////    @RequestMapping (value = "/queryUserGroup",method = RequestMethod.POST)
////    public Result queryUserGroup(@RequestBody UserGroupReq userGroupReq){
////        return userGroupService.queryUserGroup(userGroupReq);
////    }
//
//
//}
