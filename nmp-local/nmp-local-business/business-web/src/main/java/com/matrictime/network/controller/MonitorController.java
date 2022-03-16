package com.matrictime.network.controller;


import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.CheckHeartReq;
import com.matrictime.network.request.QueryMonitorReq;
import com.matrictime.network.request.SignalIoReq;
import com.matrictime.network.request.TotalLoadChangeReq;
import com.matrictime.network.response.CheckHeartResp;
import com.matrictime.network.response.QueryMonitorResp;
import com.matrictime.network.response.SignalIoResp;
import com.matrictime.network.response.TotalLoadChangeResp;
import com.matrictime.network.service.MonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/monitor")
@RestController
@Slf4j
/**
 * 监控相关请求接口
 */
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    /**
     * 心跳缓存
     * @param req
     * @return
     */
    @RequestMapping (value = "/checkHeart",method = RequestMethod.POST)
    @SystemLog(opermodul = "监控模块",operDesc = "心跳监控",operType = "查询")
    public Result<CheckHeartResp> checkHeart(@RequestBody CheckHeartReq req){
        try {
            return  monitorService.checkHeart(req);
        }catch (Exception e){
            log.error("MonitorController.checkHeart exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 监控轮询展示查询
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryMonitor",method = RequestMethod.POST)
    @SystemLog(opermodul = "监控模块",operDesc = "监控轮询展示查询",operType = "查询")
    public Result<QueryMonitorResp> queryMonitor(@RequestBody QueryMonitorReq req){
        try {
            return  monitorService.queryMonitor(req);
        }catch (Exception e){
            log.error("MonitorController.queryMonitor exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 总带宽负载变化查询
     * @param req
     * @return
     */
    @RequestMapping (value = "/totalLoadChange",method = RequestMethod.POST)
    @SystemLog(opermodul = "监控模块",operDesc = "总带宽负载变化查询",operType = "查询")
    public Result<TotalLoadChangeResp> totalLoadChange(@RequestBody TotalLoadChangeReq req){
        try {
            return  monitorService.totalLoadChange(req);
        }catch (Exception e){
            log.error("MonitorController.totalLoadChange exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
}
