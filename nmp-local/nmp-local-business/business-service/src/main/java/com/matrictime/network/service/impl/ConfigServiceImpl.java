package com.matrictime.network.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplConfigVo;
import com.matrictime.network.modelVo.NmplReportBusinessVo;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;
import com.matrictime.network.service.ConfigService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.*;
import static com.matrictime.network.constant.BusinessConsts.SWITCH_CONFIGCODE;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.exception.ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG;


@Slf4j
@Service
public class ConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Autowired(required = false)
    private NmplConfigMapper nmplConfigMapper;

    @Autowired(required = false)
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Autowired(required = false)
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Autowired(required = false)
    private NmplReportBusinessMapper nmplReportBusinessMapper;

    @Autowired
    private AsyncService asyncService;

    @Value("${thread.maxPoolSize}")
    private Integer maxPoolSize;

    // TODO: 2022/4/1 上线前需要确定等待时间
    @Value("${config.async.time.out}")
    private int asyncTimeOut;

    @Value("${proxy.context-path}")
    private String proxyPath;

    @Value("${proxy.port}")
    private String proxyPort;

    @Value("${datacollect.context-path}")
    private String dataCollectPath;

    @Value("${datacollect.port}")
    private String dataCollectPort;

    /**
     * 系统设置查询接口（支持分页查询）
     * @param req
     * @return
     */
    @Override
    public Result<PageInfo> queryConfigByPages(QueryConfigByPagesReq req) {
        Result result;

        try {
            Page page = PageHelper.startPage(req.getPageNo(),req.getPageSize());

            // 根据系统类型（必传）和配置名称（非必传）查询配置列表
            NmplConfigExample configExample = new NmplConfigExample();
            NmplConfigExample.Criteria criteria = configExample.createCriteria();
            configExample.setOrderByClause("update_time desc");
            if (!ParamCheckUtil.checkVoStrBlank(req.getDeviceType())){
                criteria.andDeviceTypeEqualTo(req.getDeviceType());
            }
            if (!ParamCheckUtil.checkVoStrBlank(req.getConfigName())){
                StringBuffer sb = new StringBuffer(KEY_PERCENT).append(req.getConfigName()).append(KEY_PERCENT);
                criteria.andConfigNameLike(sb.toString());
            }
            criteria.andConfigCodeNotEqualTo(SWITCH_CONFIGCODE);
            List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(configExample);

            // 列表分页
            PageInfo<NmplConfig> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), nmplConfigs);

            result = buildResult(pageResult);
        }catch (Exception e){
            log.error("ConfigServiceImpl.queryConfigByPages Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    /**
     * 编辑系统设置
     * @param req
     * @return
     */
    @Override
    @Transactional
    public Result<SyncConfigResp> sync(SyncConfigReq req) {
        Result result;

        try {
            SyncConfigResp resp = new SyncConfigResp();

            result = buildResult(resp);
        }catch (Exception e){
            log.error("ConfigServiceImpl.sync Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    /**
     * 同步采集业务配置
     * @param req
     * @return
     */
    @Override
    @Transactional
    public Result editDataCollectConfig(EditDataCollectConfigReq req) {
        Result result;

        try {
            // check param is legal
            checkEditDataCollectConfigParam(req);
            for (NmplReportBusinessVo vo : req.getNmplReportBusinessVos()){
                // 校验id是否为空
                if (vo.getId() == null){
                    throw new Exception("nmplReportBusinessVo.id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                }
                NmplReportBusiness reportBusiness = new NmplReportBusiness();
                BeanUtils.copyProperties(vo,reportBusiness);
                nmplReportBusinessMapper.updateByPrimaryKeySelective(reportBusiness);
            }
            for (NmplConfigVo vo : req.getNmplConfigVos()){
                // 校验id是否为空
                if (vo.getId() == null){
                    throw new Exception("nmplConfigVos.id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
                }
                NmplConfig config = new NmplConfig();
                BeanUtils.copyProperties(vo,config);
                config.setStatus(SYNC_KEY_YES);
                nmplConfigMapper.updateByPrimaryKeySelective(config);
            }
            result = buildResult(null);
        }catch (Exception e){
            log.error("ConfigServiceImpl.editDataCollectConfig Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }


    /**
     * 数据采集参数配置查询
     * @return
     */
    @Override
    public Result<QueryDataCollectResp> queryDataCollect() {
        Result result;

        try {
            QueryDataCollectResp resp = new QueryDataCollectResp();

            // 查询数据采集开关配置
            NmplConfigExample configExample = new NmplConfigExample();
            configExample.setOrderByClause("update_time desc");
            NmplConfigExample.Criteria criteria = configExample.createCriteria();
            criteria.andConfigCodeEqualTo(SWITCH_CONFIGCODE).andIsExistEqualTo(IS_EXIST);
            List<NmplConfig> nmplConfigs = nmplConfigMapper.selectByExample(configExample);
            if (!CollectionUtils.isEmpty(nmplConfigs)){
                NmplConfigVo configVo = new NmplConfigVo();
                BeanUtils.copyProperties(nmplConfigs.get(0),configVo);
                resp.setCollectSwitch(configVo);
            }else {
                throw new Exception("数据采集开关配置未配置");
            }

            // 查询数据采集上报业务配置
            NmplReportBusinessExample reportExample = new NmplReportBusinessExample();
            reportExample.createCriteria().andIsExistEqualTo(IS_EXIST);
            List<NmplReportBusiness> nmplReportBusinesses = nmplReportBusinessMapper.selectByExample(reportExample);
            List<NmplReportBusinessVo> reportBusinessVos = new ArrayList<>();
            for (NmplReportBusiness reportBusiness : nmplReportBusinesses){
                NmplReportBusinessVo vo = new NmplReportBusinessVo();
                BeanUtils.copyProperties(reportBusiness,vo);
                reportBusinessVos.add(vo);
            }
            resp.setReportBusinessVos(reportBusinessVos);

            result = buildResult(resp);
        }catch (Exception e){
            log.error("ConfigServiceImpl.queryDataCollect Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    private void checkSyncConfigParam(SyncConfigReq req) throws Exception{
        if (CollectionUtils.isEmpty(req.getConfigVos())){
            throw new Exception("ConfigVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkEditDataCollectConfigParam(EditDataCollectConfigReq req) throws Exception{
        // 校验入参是否合法
        if (CollectionUtils.isEmpty(req.getNmplReportBusinessVos())){
            throw new Exception("NmplReportBusinessVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (CollectionUtils.isEmpty(req.getNmplConfigVos())){
            throw new Exception("NmplConfigVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }


    private void checkResetDefaultConfigParam(ResetDefaultConfigReq req) throws Exception{
        String editRange = req.getEditRange();
        // 校验数据操作范围入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(editRange)){
            throw new Exception("EditRange"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 非全量修改时校验id列表入参是否合法
        if (EDIT_RANGE_PART.equals(editRange) && CollectionUtils.isEmpty(req.getIds())){
            throw new Exception("Ids"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 全量修改时校验设备类型入参是否合法
        if (EDIT_RANGE_ALL.equals(editRange)){
            String deviceType = req.getDeviceType();
            if (ParamCheckUtil.checkVoStrBlank(deviceType)){
                throw new Exception("DeviceType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }else {
                if (!DeviceTypeEnum.STATION_INSIDE.getCode().equals(deviceType) &&
                        !DeviceTypeEnum.STATION_BOUNDARY.getCode().equals(deviceType) &&
                        !DeviceTypeEnum.DEVICE_DISPENSER.getCode().equals(deviceType) &&
                        !DeviceTypeEnum.DATA_BASE.getCode().equals(deviceType)){
                    throw new Exception("DeviceType"+ PARAM_IS_UNEXPECTED_MSG);
                }
            }
        }
    }

}
