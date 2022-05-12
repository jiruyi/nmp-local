package com.matrictime.network.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.matrictime.network.api.modelVo.WsResultVo;
import com.matrictime.network.api.modelVo.WsSendVo;
import com.matrictime.network.api.request.ChangePasswdReq;
import com.matrictime.network.api.request.DeleteFriendReq;
import com.matrictime.network.api.request.UserRequest;
import com.matrictime.network.api.request.VerifyReq;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.domain.CommonService;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/29 0029 10:12
 * @desc
 */
@Api(value = "用户中心",tags = "用户中心相关接口")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    /**
     * 用户信息修改
     * @title modifyUserInfok
     * @param userRequest
     * @return com.matrictime.network.model.Result
     * @description
     * @author jiruyi
     * @create 2022/4/6 0006 9:39
     */
    @ApiOperation(value = "用户信息修改",notes = "用户信息")
    @RequestMapping (value = "/modifyUserInfo",method = RequestMethod.POST)
    public Result modifyUserInfo(@RequestBody UserRequest userRequest){
        try {
            Result result = userService.modifyUserInfo(userRequest);
            result = commonService.encrypt(userRequest.getCommonKey(), userRequest.getDestination(), result);
            return  result;
        }catch (Exception e){
            log.error("modifyUserInfo exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * @title deleteFriend
     * @param deleteFriendReq
     * @return com.matrictime.network.model.Result
     * @description  删除好友
     * @author jiruyi
     * @create 2022/4/8 0008 9:19
     */
    @ApiOperation(value = "删除好友",notes = "删除好友")
    @RequestMapping (value = "/deleteFriend",method = RequestMethod.POST)
    public Result deleteFriend(@RequestBody DeleteFriendReq deleteFriendReq){
        try {
            Result result = userService.deleteFriend(deleteFriendReq);
            deleteFriendSendMsg(deleteFriendReq,result);
            result = commonService.encryptForWs(deleteFriendReq.getCommonKey(), deleteFriendReq.getDestination(), result);

            return  result;
        }catch (Exception e){
            log.error("modifyUserInfo exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @ApiOperation(value = "修改密码",notes = "修改密码")
    @RequestMapping (value = "/changePasswd",method = RequestMethod.POST)
    public Result changePasswd(@RequestBody ChangePasswdReq changePasswdReq){
        try {
            Result result = userService.changePasswd(changePasswdReq);
            result = commonService.encrypt(changePasswdReq.getCommonKey(), changePasswdReq.getDestination(), result);
            return result;
        }catch (Exception e){
            log.error("changePasswd exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @ApiOperation(value = "查询单个用户",notes = "查询单个用户")
    @RequestMapping (value = "/queryUserInfo",method = RequestMethod.POST)
    public Result queryUserInfo(@RequestBody UserRequest userRequest){
        try {
            /**1.0 参数校验**/
            Result result = userService.queryUser(userRequest);
            result = commonService.encrypt(userRequest.getCommonKey(), userRequest.getDestination(), result);
            return result;
        }catch (Exception e){
            log.error("queryUserInfo exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    @RequestMapping (value = "/verify",method = RequestMethod.POST)
    public Result verify(@RequestBody VerifyReq req){
        try {
            Result result = userService.verify(req);
            result = commonService.encrypt(req.getCommonKey(), req.getDestination(), result);
            return result;
        }catch (Exception e){
            log.error("UserController.verify exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    public void deleteFriendSendMsg(DeleteFriendReq deleteFriendReq,Result result){
        WsResultVo wsResultVo = new WsResultVo();
        WsSendVo wsSendVo = null;
        String sendObject = "";
        if(UcConstants.DESTINATION_IN.equals(deleteFriendReq.getDestination())){
            if (result.isSuccess()){
                wsSendVo = JSONObject.parseObject(result.getErrorMsg(), new TypeReference<WsSendVo>() {});
                sendObject = wsSendVo.getSendObject();
                wsSendVo.setSendObject(null);
                wsResultVo.setDestination(UcConstants.DESTINATION_OUT);
                try {
                    String encrypt = commonService.encryptToString(deleteFriendReq.getCommonKey(), deleteFriendReq.getDestination(), wsSendVo);
                    wsResultVo.setResult(encrypt);
                }catch (Exception e){
                    sendObject = "";
                    log.error("deleteFriendSendMsg exception:{}",e.getMessage());
                }

                result.setErrorMsg(null);


            }
        }
        if (UcConstants.DESTINATION_OUT.equals(deleteFriendReq.getDestination())){
            if (result.isSuccess()){
                wsSendVo = JSONObject.parseObject(result.getErrorMsg(), new TypeReference<WsSendVo>() {});
                sendObject = wsSendVo.getSendObject();
                wsSendVo.setSendObject(null);
                wsResultVo.setDestination(UcConstants.DESTINATION_OUT);
                wsResultVo.setResult(JSONObject.toJSONString(wsSendVo));
                result.setErrorMsg(null);
            }
        }

        if (StringUtils.isNotBlank(sendObject)){
            WebSocketServer webSocketServer = WebSocketServer.getWebSocketMap().get(sendObject);
            if(webSocketServer != null){
                webSocketServer.sendMessage(JSONUtils.toJSONString(wsResultVo));
            }
        }
    }

}
