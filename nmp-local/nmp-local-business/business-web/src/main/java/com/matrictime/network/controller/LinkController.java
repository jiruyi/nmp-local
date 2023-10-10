package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.exception.ErrorMessageContants;
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
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.matrictime.network.constant.DataConstants.*;


/**
 * 链路模块
 */
@RequestMapping(value = "/link")
@RestController
@Slf4j
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 链路查询设备
     * @param req
     * @return
     */
    @SystemLog(opermodul = "链路管理模块",operDesc = "链路查询设备",operType = "链路查询设备")
    @RequestMapping(value = "/selectDevice",method = RequestMethod.POST)
    public Result<DeviceResponse> selectDevice(@RequestBody QueryDeviceForLinkReq req){
        try {
            return linkService.queryDeviceForLink(req);
        }catch (Exception e){
            log.error("LinkController.queryDeviceForLink exception:{}",e.getMessage());
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
            checkInsertLinkParam(req);
            req.setEditType(EDIT_TYPE_ADD);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.insertLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 插入链路信息入参校验
     * @param req
     * @throws Exception
     */
    private void checkInsertLinkParam(EditLinkReq req) throws Exception{
        // 校验操作类型入参是否合法
        List<LocalLinkVo> localLinkVos = req.getLocalLinkVos();
        if (CollectionUtils.isEmpty(localLinkVos)){
            log.error("LinkController.checkInsertLinkParam exception:{}","LocalLinkVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        LocalLinkVo vo = localLinkVos.get(0);
        if (ParamCheckUtil.checkVoStrBlank(vo.getLinkName())){
            log.error("LinkController.checkInsertLinkParam exception:{}","LinkName"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (vo.getLinkType() == null){
            log.error("LinkController.checkInsertLinkParam exception:{}","LinkType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getMainDeviceId())){
            log.error("LinkController.checkInsertLinkParam exception:{}","MainDeviceId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getMainDeviceType())){
            log.error("LinkController.checkInsertLinkParam exception:{}","MainDeviceType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getFollowNetworkId())){
            log.error("LinkController.checkInsertLinkParam exception:{}","FollowNetworkId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getFollowIp())){
            log.error("LinkController.checkInsertLinkParam exception:{}","FollowIp"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getFollowPort())){
            log.error("LinkController.checkInsertLinkParam exception:{}","FollowPort"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (vo.getActiveAuth() == null){
            log.error("LinkController.checkInsertLinkParam exception:{}","ActiveAuth"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
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
            checkDeleteLinkParam(req);
            req.setEditType(EDIT_TYPE_DEL);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.deleteLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 删除链路信息入参校验
     * @param req
     * @throws Exception
     */
    private void checkDeleteLinkParam(EditLinkReq req) throws Exception{
        // 校验操作类型入参是否合法
        List<LocalLinkVo> localLinkVos = req.getLocalLinkVos();
        if (CollectionUtils.isEmpty(localLinkVos)){
            log.error("LinkController.checkDeleteLinkParam exception:{}","LocalLinkVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        LocalLinkVo vo = localLinkVos.get(0);
        if (vo.getId() == null){
            log.error("LinkController.checkDeleteLinkParam exception:{}","Id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (vo.getIsExist() == null){
            log.error("LinkController.checkDeleteLinkParam exception:{}","IsExist"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
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
            checkUpdateLinkParam(req);
            req.setEditType(EDIT_TYPE_UPD);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.updateLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 更新链路信息入参校验
     * @param req
     * @throws Exception
     */
    private void checkUpdateLinkParam(EditLinkReq req) throws Exception{
        // 校验操作类型入参是否合法
        List<LocalLinkVo> localLinkVos = req.getLocalLinkVos();
        if (CollectionUtils.isEmpty(localLinkVos)){
            log.error("LinkController.checkUpdateLinkParam exception:{}","LocalLinkVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        LocalLinkVo vo = localLinkVos.get(0);
        if (vo.getId() == null){
            log.error("LinkController.checkUpdateLinkParam exception:{}","Id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    /**
     * 启停链路信息
     * @param req
     * @return
     */
    @RequiresPermissions("sys:link:switch")
    @SystemLog(opermodul = "链路管理模块",operDesc = "启停链路信息",operType = "启停链路信息",operLevl = "2")
    @RequestMapping(value = "/switchLink",method = RequestMethod.POST)
    public Result<Integer> switchLink(@RequestBody EditLinkReq req){
        try {
            req.setEditType(EDIT_TYPE_UPD);
            checkSwitchLinkParam(req);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.switchLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 启停链路信息入参校验
     * @param req
     * @throws Exception
     */
    private void checkSwitchLinkParam(EditLinkReq req) throws Exception{
        // 校验操作类型入参是否合法
        List<LocalLinkVo> localLinkVos = req.getLocalLinkVos();
        if (CollectionUtils.isEmpty(localLinkVos)){
            log.error("LinkController.checkSwitchLinkParam exception:{}","LocalLinkVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        LocalLinkVo vo = localLinkVos.get(0);
        if (vo.getId() == null){
            log.error("LinkController.checkSwitchLinkParam exception:{}","Id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (vo.getIsOn() == null){
            log.error("LinkController.checkSwitchLinkParam exception:{}","IsOn"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    /**
     * 查询链路信息（不分页）
     * @param req
     * @return
     */
    @RequiresPermissions("sys:link:query")
    @SystemLog(opermodul = "链路管理模块",operDesc = "查询链路信息",operType = "查询链路信息")
    @RequestMapping(value = "/queryLink",method = RequestMethod.POST)
    public Result<LocalLinkDisplayVo> queryLink(@RequestBody QueryLinkReq req){
        try {
            return linkService.queryLink(req);
        }catch (Exception e){
            log.error("LinkController.selectLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

//    /**
//     * 查询链路信息（分页）
//     * @param req
//     * @return
//     */
//    @RequiresPermissions("sys:link:query")
//    @SystemLog(opermodul = "链路管理模块",operDesc = "查询链路信息",operType = "查询链路信息")
//    @RequestMapping(value = "/queryLink",method = RequestMethod.POST)
//    public Result<PageInfo<LocalLinkDisplayVo>> queryLink(@RequestBody QueryLinkReq req){
//        try {
//            return linkService.queryLink(req);
//        }catch (Exception e){
//            log.error("LinkController.selectLink exception:{}",e.getMessage());
//            return new Result(false,e.getMessage());
//        }
//    }


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
            checkInsertKeycenterLinkParam(req);
            req.setEditType(EDIT_TYPE_ADD);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.insertKeycenterLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 插入密钥中心分配入参校验
     * @param req
     * @throws Exception
     */
    private void checkInsertKeycenterLinkParam(EditLinkReq req) throws Exception{
        // 校验操作类型入参是否合法
        List<LocalLinkVo> localLinkVos = req.getLocalLinkVos();
        if (CollectionUtils.isEmpty(localLinkVos)){
            log.error("LinkController.checkInsertLinkParam exception:{}","LocalLinkVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        LocalLinkVo vo = localLinkVos.get(0);
        if (ParamCheckUtil.checkVoStrBlank(vo.getLinkName())){
            log.error("LinkController.checkInsertLinkParam exception:{}","LinkName"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getMainDeviceId())){
            log.error("LinkController.checkInsertLinkParam exception:{}","MainDeviceId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getMainDeviceType())){
            log.error("LinkController.checkInsertLinkParam exception:{}","MainDeviceType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getFollowDeviceId())){
            log.error("LinkController.checkInsertLinkParam exception:{}","FollowDeviceId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
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
            checkDeleteKeycenterLinkParam(req);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.deleteKeycenterLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 删除密钥中心分配入参校验
     * @param req
     * @throws Exception
     */
    private void checkDeleteKeycenterLinkParam(EditLinkReq req) throws Exception{
        // 校验操作类型入参是否合法
        List<LocalLinkVo> localLinkVos = req.getLocalLinkVos();
        if (CollectionUtils.isEmpty(localLinkVos)){
            log.error("LinkController.checkDeleteKeycenterLinkParam exception:{}","LocalLinkVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        LocalLinkVo vo = localLinkVos.get(0);
        if (vo.getId() == null){
            log.error("LinkController.checkDeleteKeycenterLinkParam exception:{}","Id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (vo.getIsExist() == null){
            log.error("LinkController.checkDeleteKeycenterLinkParam exception:{}","IsExist"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
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
            checkUpdateKeycenterLinkParam(req);
            req.setEditType(EDIT_TYPE_UPD);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.updateKeycenterLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 更新密钥中心分配入参校验
     * @param req
     * @throws Exception
     */
    private void checkUpdateKeycenterLinkParam(EditLinkReq req) throws Exception{
        // 校验操作类型入参是否合法
        List<LocalLinkVo> localLinkVos = req.getLocalLinkVos();
        if (CollectionUtils.isEmpty(localLinkVos)){
            log.error("LinkController.checkUpdateKeycenterLinkParam exception:{}","LocalLinkVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        LocalLinkVo vo = localLinkVos.get(0);
        if (vo.getId() == null){
            log.error("LinkController.checkUpdateKeycenterLinkParam exception:{}","Id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    /**
     * 启停密钥中心分配
     * @param req
     * @return
     */
    @RequiresPermissions("sys:keyCenterAllocate:switch")
    @SystemLog(opermodul = "密钥中心分配模块",operDesc = "启停密钥中心分配",operType = "启停密钥中心分配",operLevl = "2")
    @RequestMapping(value = "/switchKeycenterLink",method = RequestMethod.POST)
    public Result<Integer> switchKeycenterLink(@RequestBody EditLinkReq req){
        try {
            checkSwitchKeycenterLinkParam(req);
            req.setEditType(EDIT_TYPE_UPD);
            return linkService.editLink(req);
        }catch (Exception e){
            log.error("LinkController.switchKeycenterLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

    /**
     * 启停链路信息入参校验
     * @param req
     * @throws Exception
     */
    private void checkSwitchKeycenterLinkParam(EditLinkReq req) throws Exception{
        // 校验操作类型入参是否合法
        List<LocalLinkVo> localLinkVos = req.getLocalLinkVos();
        if (CollectionUtils.isEmpty(localLinkVos)){
            log.error("LinkController.checkSwitchKeycenterLinkParam exception:{}","LocalLinkVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        LocalLinkVo vo = localLinkVos.get(0);
        if (vo.getId() == null){
            log.error("LinkController.checkSwitchKeycenterLinkParam exception:{}","Id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (vo.getIsOn() == null){
            log.error("LinkController.checkSwitchKeycenterLinkParam exception:{}","IsOn"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            throw new Exception(ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    /**
     * 查询密钥中心分配（不分页）
     * @param req
     * @return
     */
    @RequiresPermissions("sys:keyCenterAllocate:query")
    @SystemLog(opermodul = "密钥中心分配模块",operDesc = "查询密钥中心分配",operType = "查询密钥中心分配")
    @RequestMapping(value = "/queryKeycenterLink",method = RequestMethod.POST)
    public Result<LocalLinkDisplayVo> queryKeycenterLink(@RequestBody QueryLinkReq req){
        try {
            return linkService.queryKeycenterLink(req);
        }catch (Exception e){
            log.error("LinkController.queryKeycenterLink exception:{}",e.getMessage());
            return new Result(false,e.getMessage());
        }
    }

//    /**
//     * 查询密钥中心分配（分页）
//     * @param req
//     * @return
//     */
//    @RequiresPermissions("sys:keyCenterAllocate:query")
//    @SystemLog(opermodul = "密钥中心分配模块",operDesc = "查询密钥中心分配",operType = "查询密钥中心分配")
//    @RequestMapping(value = "/queryKeycenterLink",method = RequestMethod.POST)
//    public Result<PageInfo<LocalLinkDisplayVo>> queryKeycenterLink(@RequestBody QueryLinkReq req){
//        try {
//            return linkService.queryKeycenterLink(req);
//        }catch (Exception e){
//            log.error("LinkController.queryKeycenterLink exception:{}",e.getMessage());
//            return new Result(false,e.getMessage());
//        }
//    }
}
