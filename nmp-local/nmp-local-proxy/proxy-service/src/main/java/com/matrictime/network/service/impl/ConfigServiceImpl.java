package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplConfigMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplConfigVo;
import com.matrictime.network.request.EditConfigReq;
import com.matrictime.network.response.EditConfigResp;
import com.matrictime.network.service.ConfigService;
import com.matrictime.network.service.UpdateInfoService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.NMPL_CONFIG;
import static com.matrictime.network.constant.DataConstants.*;


@Slf4j
@Service
public class ConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Autowired(required = false)
    private NmplConfigMapper nmplConfigMapper;

    @Autowired
    private UpdateInfoService updateInfoService;


    /**
     * 编辑系统设置
     * @param req
     * @return
     */
    @Override
    @Transactional
    public Result<EditConfigResp> editConfig(EditConfigReq req) {
        Result result;

        try {
            EditConfigResp resp = null;
            // check param is legal
            checkEditConfigParam(req);
            Date createTime = new Date();
            switch (req.getEditType()){
                // 批量插入（暂未使用）
                case EDIT_TYPE_ADD:
                    for (NmplConfigVo vo : req.getConfigVos()){
                        NmplConfig config = new NmplConfig();
                        BeanUtils.copyProperties(vo,config);
                        nmplConfigMapper.insertSelective(config);
                    }
                    break;
                //批量修改
                case EDIT_TYPE_UPD:
                    List<NmplConfigVo> configVos = req.getConfigVos();
                    for (NmplConfigVo vo : configVos){
                        // 校验id是否为空
                        if (vo.getId() == null){
                            throw new SystemException(ErrorCode.PARAM_IS_NULL, "configVos.id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmplConfig config = new NmplConfig();
                        BeanUtils.copyProperties(vo,config);
                        config.setUpdateTime(createTime);
                        nmplConfigMapper.updateByPrimaryKeySelective(config);

                    }
                    // 通知对应设备信息修改表
                    updateInfoService.updateInfo(req.getDeviceType(),NMPL_CONFIG,req.getEditType(),SYSTEM_NM,createTime);
                    break;
                // 批量删除(暂未使用)
                case EDIT_TYPE_DEL:
                    for (Long id : req.getDelIds()){
                        nmplConfigMapper.deleteByPrimaryKey(id);
                    }
                    break;
                default:
                    throw new Exception("EditType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("ConfigServiceImpl.editConfig SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("ConfigServiceImpl.editConfig Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    /**
     * 同步系统配置
     * @param req
     * @return
     */
    @Override
    public Result syncConfig(EditConfigReq req) {
        Result result;

        try {
            // check param is legal
            checkSyncConfigParam(req);
            Date createTime = new Date();

            List<NmplConfigVo> configVos = req.getConfigVos();
            boolean addFlag = false;// 判断设备是否进行插入通知
            boolean updateFlag = false;// 判断设备是否进行更新通知
            for (NmplConfigVo vo : configVos){
                Long id = vo.getId();
                // 校验id是否为空
                if (id == null){
                    throw new SystemException(ErrorCode.PARAM_IS_NULL, "configVos.id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                }
                NmplConfig config = nmplConfigMapper.selectByPrimaryKey(id);
                if (config != null){// 库里原本存在则比对修改
                    if (!vo.getConfigValue().equals(config.getConfigValue())){
                        BeanUtils.copyProperties(vo,config);
                        config.setUpdateTime(createTime);
                        nmplConfigMapper.updateByPrimaryKeySelective(config);
                        updateFlag = true;
                    }
                }else {// 库里不存在则插入
                    NmplConfig addConfig = new NmplConfig();
                    BeanUtils.copyProperties(vo,addConfig);
                    config.setUpdateTime(createTime);
                    nmplConfigMapper.insert(addConfig);
                    addFlag = true;
                }
            }
            // 通知对应设备信息修改表
            if (addFlag){
                updateInfoService.updateInfo(req.getDeviceType(),NMPL_CONFIG,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
            }
            if (updateFlag){
                updateInfoService.updateInfo(req.getDeviceType(),NMPL_CONFIG,EDIT_TYPE_UPD,SYSTEM_NM,createTime);
            }
            result = buildResult(null);
        }catch (SystemException e){
            log.error("ConfigServiceImpl.syncConfig SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("ConfigServiceImpl.syncConfig Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }


    private void checkEditConfigParam(EditConfigReq req) throws Exception{
        // 校验操作类型入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new Exception("editType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 校验操作类型为新增时入参是否合法
        if (EDIT_TYPE_ADD.equals(req.getEditType()) || DataConstants.EDIT_TYPE_UPD.equals(req.getEditType())){
            if (CollectionUtils.isEmpty(req.getConfigVos())){
                throw new Exception("configVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
        // 校验操作类型为删除时入参是否合法
        if (DataConstants.EDIT_TYPE_DEL.equals(req.getEditType()) && CollectionUtils.isEmpty(req.getDelIds())){
            throw new Exception("delIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkSyncConfigParam(EditConfigReq req) throws Exception{
        // 校验操作类型为新增时入参是否合法
        if (CollectionUtils.isEmpty(req.getConfigVos())){
            throw new Exception("configVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getDeviceType())){
            throw new Exception("deviceType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

}
