package com.matrictime.network.controller;

import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.EncryptConfVo;
import com.matrictime.network.request.QueryKeyDataReq;
import com.matrictime.network.request.UpdEncryptConfReq;
import com.matrictime.network.resp.FlushKeyStatusResp;
import com.matrictime.network.resp.QueryKeyDataResp;
import com.matrictime.network.service.EncryptManageSevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 加密密钥管理
 * @author hx
 */
@RequestMapping(value = "/encrypt")
@RestController
@Slf4j
public class EncryptController {

    @Resource
    private EncryptManageSevice encryptManageSevice;

    /**
     * 插入/更新加密配置
     * @param req
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/upd",method = RequestMethod.POST)
    public Result<Integer> updEncryptConf(@RequestBody UpdEncryptConfReq req){
        try {
            Result result = encryptManageSevice.updEncryptConf(req);
            return result;
        }catch (Exception e){
            log.error("EncryptController.upd exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 查询加密配置
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public Result<EncryptConfVo> queryEncryptConf(){
        try {
            Result<EncryptConfVo> result = encryptManageSevice.queryEncryptConf();
            return result;
        }catch (Exception e){
            log.error("EncryptController.query exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }


    /**
     * 查询密钥信息
     * @param req
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/queryKeyData",method = RequestMethod.POST)
    public Result<QueryKeyDataResp> queryKeyData(@RequestBody QueryKeyDataReq req){
        try {
            Result result = encryptManageSevice.queryKeyData(req);
            return result;
        }catch (Exception e){
            log.error("EncryptController.queryKeyData exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 更新密钥
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/flushKey",method = RequestMethod.POST)
    public Result flushKey(){
        try {
            Result result = encryptManageSevice.flushKey();
            return result;
        }catch (Exception e){
            log.error("EncryptController.flushKey exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    /**
     * 轮询获取更新密钥按钮的状态
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/getFlushKeyStatus",method = RequestMethod.POST)
    public Result<FlushKeyStatusResp> getFlushKeyStatus(){
        try {
            Result<FlushKeyStatusResp> result = encryptManageSevice.getFlushKeyStatus();
            return result;
        }catch (Exception e){
            log.error("EncryptController.getFlushKeyStatus exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }
}
