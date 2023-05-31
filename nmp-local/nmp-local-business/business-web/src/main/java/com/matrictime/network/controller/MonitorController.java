package com.matrictime.network.controller;


import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;
import com.matrictime.network.service.MonitorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
     * 站点状态上报
     * @author hexu
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
     * 物理设备心跳上报
     * @param req
     * @return
     */
    @RequestMapping (value = "/physicalDeviceHeartbeat",method = RequestMethod.POST)
    @SystemLog(opermodul = "监控模块",operDesc = "物理设备心跳",operType = "上报")
    public Result physicalDeviceHeartbeat(@RequestBody PhysicalDeviceHeartbeatReq req){
        try {
            return  monitorService.physicalDeviceHeartbeat(req);
        }catch (Exception e){
            log.error("MonitorController.physicalDeviceHeartbeat exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 物理设备资源情况信息上报
     * @param req
     * @return
     */
    @RequestMapping (value = "/physicalDeviceResource",method = RequestMethod.POST)
    @SystemLog(opermodul = "监控模块",operDesc = "物理设备资源情况信息",operType = "上报")
    public Result physicalDeviceResource(@RequestBody PhysicalDeviceResourceReq req){
        try {
            return  monitorService.physicalDeviceResource(req);
        }catch (Exception e){
            log.error("MonitorController.physicalDeviceResource exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 运行系统资源信息上报
     * @param req
     * @return
     */
    @RequestMapping (value = "/systemResource",method = RequestMethod.POST)
    @SystemLog(opermodul = "监控模块",operDesc = "运行系统资源信息",operType = "上报")
    public Result systemResource(@RequestBody SystemResourceReq req){
        try {
            return  monitorService.systemResource(req);
        }catch (Exception e){
            log.error("MonitorController.systemResource exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 物理设备网络拓扑图查询
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryPhysicalDevices",method = RequestMethod.POST)
    @SystemLog(opermodul = "监控模块",operDesc = "物理设备网络拓扑图",operType = "查询")
    public Result<QueryPhysicalDevicesResp> queryPhysicalDevices(@RequestBody QueryPhysicalDevicesReq req){
        try {
            return  monitorService.queryPhysicalDevices(req);
        }catch (Exception e){
            log.error("MonitorController.queryPhysicalDevices exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 物理设备资源查询
     * @param req
     * @return
     */
    @RequestMapping (value = "/queryPhysicalDeviceResource",method = RequestMethod.POST)
    @SystemLog(opermodul = "监控模块",operDesc = "物理设备资源",operType = "查询")
    public Result<QueryPhysicalDeviceResourceResp> queryPhysicalDeviceResource(@RequestBody QueryPhysicalDevicesResourceReq req){
        try {
            return  monitorService.queryPhysicalDeviceResource(req);
        }catch (Exception e){
            log.error("MonitorController.queryPhysicalDeviceResource exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 运行系统资源查询
     * @param req
     * @return
     */
    @RequestMapping (value = "/querySystemResource",method = RequestMethod.POST)
    @SystemLog(opermodul = "监控模块",operDesc = "运行系统资源",operType = "查询")
    public Result<QuerySystemResourceResp> querySystemResource(@RequestBody QueryPhysicalDevicesResourceReq req){
        try {
            return  monitorService.querySystemResource(req);
        }catch (Exception e){
            log.error("MonitorController.querySystemResource exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }



//    /**
//     * 监控轮询展示查询(废弃)
//     * @author hexu
//     * @param req
//     * @return
//     */
//    @RequestMapping (value = "/queryMonitor",method = RequestMethod.POST)
//    @SystemLog(opermodul = "监控模块",operDesc = "监控轮询展示查询",operType = "查询")
//    @RequiresPermissions("sys:monitor:query")
//    public Result<QueryMonitorResp> queryMonitor(@RequestBody QueryMonitorReq req){
//        try {
//            return  monitorService.queryMonitor(req);
//        }catch (Exception e){
//            log.error("MonitorController.queryMonitor exception:{}",e.getMessage());
//            return new Result(false,e.getMessage());
//        }
//    }

//    /**
//     * 总带宽负载变化查询（废弃）
//     * @author hexu
//     * @param req
//     * @return
//     */
//    @RequestMapping (value = "/totalLoadChange",method = RequestMethod.POST)
//    @SystemLog(opermodul = "监控模块",operDesc = "总带宽负载变化查询",operType = "查询")
//    @RequiresPermissions("sys:monitor:totalload")
//    public Result<TotalLoadChangeResp> totalLoadChange(@RequestBody TotalLoadChangeReq req){
//        try {
//            return  monitorService.totalLoadChange(req);
//        }catch (Exception e){
//            log.error("MonitorController.totalLoadChange exception:{}",e.getMessage());
//            return new Result(false,e.getMessage());
//        }
//    }
}
