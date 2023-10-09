package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.LocalLinkDisplayVo;
import com.matrictime.network.modelVo.LocalLinkVo;
import com.matrictime.network.request.*;
import com.matrictime.network.response.BaseStationInfoResponse;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BaseStationInfoService;
import com.matrictime.network.service.DeviceService;
import com.matrictime.network.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.matrictime.network.constant.DataConstants.*;


/**
 * 链路模块
 */
@RequestMapping(value = "/link")
@RestController
@Slf4j
public class LinkController {

    @Autowired
    private BaseStationInfoService baseStationInfoService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private LinkService linkService;

    /**
     * 链路查询基站设备
     * @param baseStationInfoRequest
     * @return
     */
    @SystemLog(opermodul = "链路管理模块",operDesc = "链路查询基站设备",operType = "链路查询基站设备")
    @RequestMapping(value = "/selectBaseStation",method = RequestMethod.POST)
    public Result<BaseStationInfoResponse> selectBaseStation(@RequestBody BaseStationInfoRequest baseStationInfoRequest){
        try {
            return baseStationInfoService.selectForRoute(baseStationInfoRequest);
        }catch (Exception e){
            log.error("LinkController.selectBaseStation exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 链路查询设备
     * @param deviceInfoRequest
     * @return
     */
    @SystemLog(opermodul = "链路管理模块",operDesc = "链路查询设备",operType = "链路查询设备")
    @RequestMapping(value = "/selectDevice",method = RequestMethod.POST)
    public Result<DeviceResponse> selectDevice(@RequestBody DeviceInfoRequest deviceInfoRequest){
        try {
            return deviceService.selectDeviceForLinkRelation(deviceInfoRequest);
        }catch (Exception e){
            log.error("LinkController.selectDevice exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 插入链路信息
     * @param req
     * @return
     */
    @RequiresPermissions("sys:link:save")
    @SystemLog(opermodul = "链路管理模块",operDesc = "插入链路信息",operType = "插入链路信息")
    @RequestMapping(value = "/insertLink",method = RequestMethod.POST)
    public Result<Integer> insertLink(@RequestBody EditLinkReq req){
        try {
            req.setEditType(EDIT_TYPE_ADD);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.insertLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 删除链路信息
     * @param req
     * @return
     */
    @RequiresPermissions("sys:link:delete")
    @SystemLog(opermodul = "链路管理模块",operDesc = "删除链路信息",operType = "删除链路信息",operLevl = "2")
    @RequestMapping(value = "/deleteLink",method = RequestMethod.POST)
    public Result<Integer> deleteLink(@RequestBody EditLinkReq req){
        try {
            req.setEditType(EDIT_TYPE_DEL);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.deleteLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 更新链路信息
     * @param req
     * @return
     */
    @RequiresPermissions("sys:link:update")
    @SystemLog(opermodul = "链路管理模块",operDesc = "更新链路信息",operType = "更新链路信息",operLevl = "2")
    @RequestMapping(value = "/updateLink",method = RequestMethod.POST)
    public Result<Integer> updateLink(@RequestBody EditLinkReq req){
        try {
            req.setEditType(EDIT_TYPE_UPD);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.updateLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 启停链路信息
     * @param req
     * @return
     */
    @RequiresPermissions("sys:link:switch")
    @SystemLog(opermodul = "链路管理模块",operDesc = "禁用链路信息",operType = "禁用链路信息",operLevl = "2")
    @RequestMapping(value = "/closeLink",method = RequestMethod.POST)
    public Result<Integer> closeLink(@RequestBody EditLinkReq req){
        try {
            req.setEditType(EDIT_TYPE_UPD);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.closeLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 查询链路信息
     * @param req
     * @return
     */
    @RequiresPermissions("sys:link:query")
    @SystemLog(opermodul = "链路管理模块",operDesc = "查询链路信息",operType = "查询链路信息")
    @RequestMapping(value = "/queryLink",method = RequestMethod.POST)
    public Result<PageInfo<LocalLinkDisplayVo>> queryLink(@RequestBody QueryLinkReq req){
        try {
            return linkService.queryLink(req);
        }catch (Exception e){
            log.error("LinkController.selectLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 插入密钥中心分配
     * @param req
     * @return
     */
    @RequiresPermissions("sys:keyCenterAllocate:insert")
    @SystemLog(opermodul = "密钥中心分配模块",operDesc = "插入密钥中心分配",operType = "插入密钥中心分配")
    @RequestMapping(value = "/insertKeycenterLink",method = RequestMethod.POST)
    public Result<Integer> insertKeycenterLink(@RequestBody EditLinkReq req){
        try {
            req.setEditType(EDIT_TYPE_ADD);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.insertKeycenterLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 删除密钥中心分配
     * @param req
     * @return
     */
    @RequiresPermissions("sys:keyCenterAllocate:delete")
    @SystemLog(opermodul = "密钥中心分配模块",operDesc = "删除密钥中心分配",operType = "删除密钥中心分配",operLevl = "2")
    @RequestMapping(value = "/deleteKeycenterLink",method = RequestMethod.POST)
    public Result<Integer> deleteKeycenterLink(@RequestBody EditLinkReq req){
        try {
            req.setEditType(EDIT_TYPE_DEL);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.deleteKeycenterLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 更新密钥中心分配
     * @param req
     * @return
     */
    @RequiresPermissions("sys:keyCenterAllocate:modify")
    @SystemLog(opermodul = "密钥中心分配模块",operDesc = "更新密钥中心分配",operType = "更新密钥中心分配",operLevl = "2")
    @RequestMapping(value = "/updateKeycenterLink",method = RequestMethod.POST)
    public Result<Integer> updateKeycenterLink(@RequestBody EditLinkReq req){
        try {
            req.setEditType(EDIT_TYPE_UPD);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.updateKeycenterLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 启停密钥中心分配
     * @param req
     * @return
     */
    @RequiresPermissions("sys:keyCenterAllocate:switch")
    @SystemLog(opermodul = "密钥中心分配模块",operDesc = "禁用密钥中心分配",operType = "禁用密钥中心分配",operLevl = "2")
    @RequestMapping(value = "/closeKeycenterLink",method = RequestMethod.POST)
    public Result<Integer> closeKeycenterLink(@RequestBody EditLinkReq req){
        try {
            req.setEditType(EDIT_TYPE_UPD);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.closeKeycenterLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 查询密钥中心分配
     * @param req
     * @return
     */
    @RequiresPermissions("sys:keyCenterAllocate:query")
    @SystemLog(opermodul = "密钥中心分配模块",operDesc = "查询密钥中心分配",operType = "查询密钥中心分配")
    @RequestMapping(value = "/queryKeycenterLink",method = RequestMethod.POST)
    public Result<PageInfo<LocalLinkDisplayVo>> queryKeycenterLink(@RequestBody QueryLinkReq req){
        try {
            return linkService.queryKeycenterLink(req);
        }catch (Exception e){
            log.error("LinkController.queryKeycenterLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }
}
