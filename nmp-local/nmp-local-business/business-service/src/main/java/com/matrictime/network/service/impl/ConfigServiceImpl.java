package com.matrictime.network.service.impl;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplConfigMapper;
import com.matrictime.network.dao.mapper.extend.NmplConfigExtMapper;
import com.matrictime.network.dao.model.NmplConfig;
import com.matrictime.network.dao.model.NmplConfigExample;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplConfigVo;
import com.matrictime.network.request.EditConfigReq;
import com.matrictime.network.request.QueryConfigByPagesReq;
import com.matrictime.network.request.ResetDefaultConfigReq;
import com.matrictime.network.response.EditConfigResp;
import com.matrictime.network.response.QueryConfigByPagesResp;
import com.matrictime.network.response.ResetDefaultConfigResp;
import com.matrictime.network.service.ConfigService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class ConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Autowired(required = false)
    private NmplConfigExtMapper nmplConfigExtMapper;

    @Autowired(required = false)
    private NmplConfigMapper nmplConfigMapper;

    @Override
    public Result<QueryConfigByPagesResp> queryByPages(QueryConfigByPagesReq req) {
        Result result;
        try {
            PageInfo<NmplConfig> pageInfo = PageHelper.startPage(req.getPageNo(), req.getPageSize()).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    NmplConfig nmplConfig = new NmplConfig();
                    if (StringUtils.isNotBlank(req.getConfigName())){
                        StringBuffer sb = new StringBuffer("%").append(req.getConfigName()).append("%");
                        nmplConfig.setConfigName(sb.toString());
                    }
                    nmplConfigExtMapper.selectByExample(nmplConfig);
                }
            });

            QueryConfigByPagesResp resp = new QueryConfigByPagesResp();
            resp.setPageInfo(pageInfo);
            result = buildResult(resp);
        }catch (Exception e){
            log.error(e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    @Transactional
    public Result<EditConfigResp> editConfig(EditConfigReq req) {
        Result result;
        try {
            // check param is legal
            checkEditConfigParam(req);
            switch (req.getEditType()){
                case DataConstants.EDIT_TYPE_ADD:
                    for (NmplConfigVo vo : req.getNmplConfigVos()){
                        NmplConfig config = new NmplConfig();
                        BeanUtils.copyProperties(vo,config);
                        nmplConfigMapper.insertSelective(config);
                    }
                    break;
                case DataConstants.EDIT_TYPE_UPD:
                    for (NmplConfigVo vo : req.getNmplConfigVos()){
                        if (vo.getId() == null){
                            throw new SystemException(ErrorCode.PARAM_IS_NULL, "nmplConfigVos.id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmplConfig config = new NmplConfig();
                        BeanUtils.copyProperties(vo,config);
                        nmplConfigMapper.updateByPrimaryKeySelective(config);
                    }
                    break;
                case DataConstants.EDIT_TYPE_DEL:
                    for (Long id : req.getDelIds()){
                        nmplConfigMapper.deleteByPrimaryKey(id);
                    }
                    break;
                default:
                    throw new SystemException(ErrorCode.PARAM_EXCEPTION, "editType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }

            EditConfigResp resp = new EditConfigResp();
            result = buildResult(resp);
        }catch (SystemException e){
            log.error(e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error(e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result<ResetDefaultConfigResp> resetDefaultConfig(ResetDefaultConfigReq req) {
        Result result;
        try {
            checkResetDefaultConfigParam(req);
            List<Long> successIds = new ArrayList<>(req.getIds().size());
            List<Long> failIds = new ArrayList<>();
            switch (req.getEditRange()){
                case DataConstants.EDIT_RANGE_PART:
                    for (Long id : req.getIds()){
                        NmplConfig tmp = nmplConfigMapper.selectByPrimaryKey(id);
                        if (tmp != null){
                            NmplConfig nmplConfig = new NmplConfig();
                            nmplConfig.setId(id);
                            nmplConfig.setConfigValue(tmp.getDefaultValue());
                            int flag = nmplConfigMapper.updateByPrimaryKeySelective(nmplConfig);
                            if(flag > 0){
                                successIds.add(id);
                                continue;
                            }
                        }
                        failIds.add(id);
                    }
                    break;
                case DataConstants.EDIT_RANGE_ALL:
                    NmplConfigExample example = new NmplConfigExample();
                    List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(nmplConfigs)){
                        for (NmplConfig dto : nmplConfigs){
                            if (StringUtils.isNotEmpty(dto.getDefaultValue()) && !dto.getDefaultValue().equals(dto.getConfigValue())){
                                NmplConfig nmplConfig = new NmplConfig();
                                nmplConfig.setId(dto.getId());
                                nmplConfig.setConfigValue(dto.getDefaultValue());
                                int flag = nmplConfigMapper.updateByPrimaryKeySelective(nmplConfig);
                                if(flag > 0){
                                    successIds.add(dto.getId());
                                    continue;
                                }
                            }
                            failIds.add(dto.getId());
                        }
                    }
                    break;
                default:
                    throw new SystemException(ErrorCode.PARAM_EXCEPTION, "editRange"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
            ResetDefaultConfigResp resp = new ResetDefaultConfigResp();
            resp.setSuccessIds(successIds);
            resp.setFailIds(failIds);
            result = buildResult(resp);
        }catch (Exception e){
            log.error(e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    private void checkEditConfigParam(EditConfigReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "editType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (DataConstants.EDIT_TYPE_ADD.equals(req.getEditType()) || DataConstants.EDIT_TYPE_UPD.equals(req.getEditType())){
            if (CollectionUtils.isEmpty(req.getNmplConfigVos())){
                throw new SystemException(ErrorCode.PARAM_IS_NULL, "nmplConfigVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
        if (DataConstants.EDIT_TYPE_DEL.equals(req.getEditType()) && CollectionUtils.isEmpty(req.getDelIds())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "delIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkResetDefaultConfigParam(ResetDefaultConfigReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getEditRange())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "editRange"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (DataConstants.EDIT_RANGE_PART.equals(req.getEditRange()) && CollectionUtils.isEmpty(req.getIds())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "ids"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }
}
