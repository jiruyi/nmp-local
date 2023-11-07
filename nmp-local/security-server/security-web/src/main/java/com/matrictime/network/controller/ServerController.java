package com.matrictime.network.controller;

import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.modelVo.SecurityServerInfoVo;
import com.matrictime.network.req.EditServerReq;
import com.matrictime.network.req.HeartReportReq;
import com.matrictime.network.req.QueryServerReq;
import com.matrictime.network.req.StartServerReq;
import com.matrictime.network.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.matrictime.network.constant.DataConstants.*;

/**
 * 安全服务器配置
 * @author hx
 */
@RequestMapping(value = "/server")
@RestController
@Slf4j
public class ServerController {

    @Resource
    private ServerService serverService;

    /**
     * 查询安全服务器列表（分页）
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/queryServerByPage",method = RequestMethod.POST)
    public Result<PageInfo<SecurityServerInfoVo>> queryServerByPage(@RequestBody QueryServerReq req){
        try {
            Result result = serverService.queryServerByPage(req);
            return result;
        }catch (Exception e){
            log.error("ServerController.queryServerByPage exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 查询安全服务器列表（不分页）
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/queryServer",method = RequestMethod.POST)
    public Result<PageInfo<SecurityServerInfoVo>> queryServer(@RequestBody QueryServerReq req){
        try {
            Result result = serverService.queryServer(req);
            return result;
        }catch (Exception e){
            log.error("ServerController.queryServer exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 新增安全服务器
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/addServer",method = RequestMethod.POST)
    public Result addServer(@RequestBody EditServerReq req){
        try {
            req.setEditType(EDIT_TYPE_ADD);
            Result result = serverService.editServer(req);
            return result;
        }catch (Exception e){
            log.error("ServerController.addServer exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 修改安全服务器
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/updateServer",method = RequestMethod.POST)
    public Result updateServer(@RequestBody EditServerReq req){
        try {
            req.setEditType(EDIT_TYPE_UPD);
            Result result = serverService.editServer(req);
            return result;
        }catch (Exception e){
            log.error("ServerController.updateServer exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 删除安全服务器
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/delServer",method = RequestMethod.POST)
    public Result delServer(@RequestBody EditServerReq req){
        try {
            req.setEditType(EDIT_TYPE_DEL);
            Result result = serverService.editServer(req);
            return result;
        }catch (Exception e){
            log.error("ServerController.delServer exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 启动安全服务器
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/startServer",method = RequestMethod.POST)
    public Result startServer(@RequestBody StartServerReq req){
        try {
            Result result = serverService.startServer(req);
            return result;
        }catch (Exception e){
            log.error("ServerController.startServer exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }


    /**
     * 安全服务器状态上报
     * @param req
     * @return
     */
    @RequestMapping (value = "/heartReport",method = RequestMethod.POST)
    public Result heartReport(@RequestBody HeartReportReq req){
        try {
            return  serverService.heartReport(req);
        }catch (Exception e){
            log.error("ServerController.heartReport exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
}
