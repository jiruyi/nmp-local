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
import static com.matrictime.network.constant.DataConstants.IS_EXIST;
import static com.matrictime.network.constant.DataConstants.SYSTEM_NM;


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
                case DataConstants.EDIT_TYPE_ADD:
                    for (NmplConfigVo vo : req.getConfigVos()){
                        NmplConfig config = new NmplConfig();
                        BeanUtils.copyProperties(vo,config);
                        nmplConfigMapper.insertSelective(config);
                    }
                    break;
                //批量修改
                case DataConstants.EDIT_TYPE_UPD:
                    List<NmplConfigVo> configVos = req.getConfigVos();
                    List<Long> voIds = new ArrayList<>();
                    boolean addFlag = false;
                    boolean updateFlag = false;
                    for (NmplConfigVo voId : configVos){
                        voIds.add(voId.getId());
                    }
                    NmplConfigExample configExample = new NmplConfigExample();
                    configExample.createCriteria().andIdIn(voIds).andIsExistEqualTo(IS_EXIST);
                    List<NmplConfig> localConfigs = nmplConfigMapper.selectByExample(configExample);
                    for (NmplConfigVo vo : configVos){
                        // 校验id是否为空
                        if (vo.getId() == null){
                            throw new SystemException(ErrorCode.PARAM_IS_NULL, "configVos.id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        for (NmplConfig localVo:localConfigs){
                            if (vo.getId().compareTo(localVo.getId()) == 0 &&
                                (!vo.getConfigValue().equals(localVo.getConfigValue()) ||
                                !vo.getDefaultValue().equals(localVo.getDefaultValue()) ||
                                !vo.getUnit().equals(localVo.getUnit()))){
                                NmplConfig config = new NmplConfig();
                                BeanUtils.copyProperties(vo,config);
                                config.setUpdateTime(createTime);
                                nmplConfigMapper.updateByPrimaryKeySelective(config);
                            }else {
                                NmplConfig config = new NmplConfig();
                                BeanUtils.copyProperties(vo,config);
                                config.setUpdateTime(createTime);
                                nmplConfigMapper.insert(config);
                            }
                        }
                    }
                    // 通知对应设备信息修改表
                    updateInfoService.updateInfo(req.getDeviceType(),NMPL_CONFIG,req.getEditType(),SYSTEM_NM,createTime);
                    break;
                // 批量删除(暂未使用)
                case DataConstants.EDIT_TYPE_DEL:
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


    private void checkEditConfigParam(EditConfigReq req) throws Exception{
        // 校验操作类型入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new Exception("editType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 校验操作类型为新增时入参是否合法
        if (DataConstants.EDIT_TYPE_ADD.equals(req.getEditType()) || DataConstants.EDIT_TYPE_UPD.equals(req.getEditType())){
            if (CollectionUtils.isEmpty(req.getConfigVos())){
                throw new Exception("configVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
        // 校验操作类型为删除时入参是否合法
        if (DataConstants.EDIT_TYPE_DEL.equals(req.getEditType()) && CollectionUtils.isEmpty(req.getDelIds())){
            throw new Exception("delIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

}
