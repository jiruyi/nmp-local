package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.NmplSignalIoMapper;
import com.matrictime.network.dao.mapper.NmplSignalMapper;
import com.matrictime.network.dao.mapper.extend.NmplSignalExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplDeviceVo;
import com.matrictime.network.modelVo.NmplSignalVo;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;
import com.matrictime.network.service.SignalService;
import com.matrictime.network.util.DateUtils;
import com.matrictime.network.util.ExportCSVUtil;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@PropertySource(value = "classpath:/businessConfig.properties",encoding = "UTF-8")
public class SignalServiceImpl extends SystemBaseService implements SignalService {

    @Autowired(required = false)
    private NmplSignalMapper nmplSignalMapper;

    @Autowired(required = false)
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Autowired(required = false)
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Autowired(required = false)
    private NmplSignalIoMapper nmplSignalIoMapper;

    @Autowired(required = false)
    private NmplSignalExtMapper nmplSignalExtMapper;

    @Value("${signal.tableHeaderArr}")
    private String[] tableHeaderArr;

    @Override
    public Result<EditSignalResp> editSignal(EditSignalReq req) {
        Result result;
        try {
            // check param is legal
            checkEditSignalParam(req);
            switch (req.getEditType()){
                case DataConstants.EDIT_TYPE_ADD:
                    for (NmplSignalVo vo : req.getNmplSignalVos()){
                        NmplSignal config = new NmplSignal();
                        BeanUtils.copyProperties(vo,config);
                        nmplSignalMapper.insertSelective(config);
                    }
                    break;
                case DataConstants.EDIT_TYPE_UPD:
                    for (NmplSignalVo vo : req.getNmplSignalVos()){
                        if (vo.getId() == null){
                            throw new SystemException(ErrorCode.PARAM_IS_NULL, "nmplSignalVos.id"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmplSignal config = new NmplSignal();
                        BeanUtils.copyProperties(vo,config);
                        nmplSignalMapper.updateByPrimaryKeySelective(config);
                    }
                    break;
                case DataConstants.EDIT_TYPE_DEL:
                    for (Long id : req.getDelIds()){
                        nmplSignalMapper.deleteByPrimaryKey(id);
                    }
                    break;
                default:
                    throw new SystemException(ErrorCode.PARAM_EXCEPTION, "editType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }

            EditConfigResp resp = new EditConfigResp();
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("SignalServiceImpl.editSignal SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("SignalServiceImpl.editSignal Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result<SignalIoResp> signalIo(SignalIoReq req) {
        Result result;
        SignalIoResp resp = new SignalIoResp();
        try {
            checkSignalIoParam(req);
            List<NmplDeviceVo> vos = req.getDeviceVos();
            List<String> successIds = new ArrayList<>(vos.size());
            List<String> failIds = new ArrayList<>();
            switch (req.getIoType()){
                case com.matrictime.network.base.constant.DataConstants.IOTYPE_O:
                    for(NmplDeviceVo vo : vos){
                        boolean flag = sendSignalByBigType(req.getUserId(), vo, req.getIoType());
                        if (flag){
                            successIds.add(vo.getDeviceId());
                        }else {
                            failIds.add(vo.getDeviceId());
                        }
                    }
                    break;
                case com.matrictime.network.base.constant.DataConstants.IOTYPE_I:
                    // 判断是否有设备被其他用户追踪中
                    for(NmplDeviceVo vo : vos){
                        NmplSignalIoExample example = new NmplSignalIoExample();
                        example.createCriteria().andDeviceIdEqualTo(vo.getDeviceId()).andStatusEqualTo(com.matrictime.network.base.constant.DataConstants.IOTYPE_I).andIsExistEqualTo(DataConstants.IS_EXIST);
                        List<NmplSignalIo> signalIos = nmplSignalIoMapper.selectByExample(example);
                        if (!CollectionUtils.isEmpty(signalIos)){
                            throw new SystemException(ErrorMessageContants.DEVICE_IS_ON_TRACK+signalIos.get(0).getDeviceId());
                        }
                    }
                    for(NmplDeviceVo vo : vos){
                        boolean flag = sendSignalByBigType(req.getUserId(),vo,req.getIoType());
                        if (flag){
                            successIds.add(vo.getDeviceId());
                        }else {
                            failIds.add(vo.getDeviceId());
                        }
                    }
                    break;
                default:
                    throw new SystemException(ErrorCode.PARAM_EXCEPTION, "ioType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }
            resp.setSuccessIds(successIds);
            resp.setFailIds(failIds);
            result = buildResult(resp);
        }catch (Exception e){
            log.error("SignalServiceImpl.signalIo Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<CleanSignalResp> cleanSignal(CleanSignalReq req) {
        Result result;
        try {
            checkCleanSignalParam(req);
            for(String id : req.getDeviceIds()){
                NmplSignalIoExample example = new NmplSignalIoExample();
                example.createCriteria().andDeviceIdEqualTo(id).andStatusEqualTo(com.matrictime.network.base.constant.DataConstants.IOTYPE_I).andIsExistEqualTo(DataConstants.IS_EXIST);
                List<NmplSignalIo> signalIos = nmplSignalIoMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(signalIos)){
                    throw new SystemException(ErrorMessageContants.DEVICE_IS_ON_TRACK+signalIos.get(0).getDeviceId());
                }
            }
            NmplSignal signal = new NmplSignal();
            signal.setIsExist(DataConstants.IS_NOT_EXIST);
            for (String id : req.getDeviceIds()){
                NmplSignalExample example = new NmplSignalExample();
                example.createCriteria().andDeviceIdEqualTo(id).andIsExistEqualTo(DataConstants.IS_EXIST);
                nmplSignalMapper.updateByExampleSelective(signal,example);
            }
            CleanSignalResp resp = new CleanSignalResp();
            result = buildResult(resp);
        }catch (Exception e){
            log.error("SignalServiceImpl.cleanSignal Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<QuerySignalByPageResp> querySignalByPage(QuerySignalByPageReq req) {
        Result result;
        try {
            checkQuerySignalByPageParam(req);
            List<String> deviceIds = nmplSignalExtMapper.selectDeviceIdsByUserId(req.getUserId());
            PageInfo<NmplSignal> pageInfo = PageHelper.startPage(req.getPageNo(), req.getPageSize()).doSelectPageInfo(() -> {
                nmplSignalExtMapper.selectPagesByUserId(req.getUserId());
            });
            QuerySignalByPageResp resp = new QuerySignalByPageResp();
            resp.setDeviceIds(deviceIds);
            resp.setPageInfo(pageInfo);
            result = buildResult(resp);
        }catch (Exception e){
            log.error("SignalServiceImpl.querySignalByPage Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<ExportSignalResp> exportSignal(ExportSignalReq req) {
        Result result;
        try {
            checkExportSignalParam(req);
            List<Object[]> cellList = new ArrayList<>();
            for (String deviceId : req.getDeviceIds()){
                NmplSignalExample example = new NmplSignalExample();
                example.setOrderByClause("update_time desc");
                example.createCriteria().andDeviceIdEqualTo(deviceId).andIsExistEqualTo(DataConstants.IS_EXIST);
                List<NmplSignal> nmplSignals = nmplSignalMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(nmplSignals)){
                    for (NmplSignal signal : nmplSignals){
                        Object[] obj = new Object[10];
                        obj[0] = signal.getId();
                        obj[1] = signal.getDeviceId();
                        obj[2] = signal.getSignalName();
                        obj[3] = signal.getSendIp();
                        obj[4] = signal.getReceiveIp();
                        obj[5] = signal.getSignalContent();
                        obj[6] = signal.getBusinessModule();
                        obj[7] = signal.getUploadTime();
                        obj[8] = signal.getCreateTime();
                        obj[9] = signal.getUpdateTime();
                        cellList.add(obj);
                    }
                }
            }
            byte[] bytes = ExportCSVUtil.writeCsvAfterToBytes(tableHeaderArr, cellList);
            ExportSignalResp resp = new ExportSignalResp();
            resp.setBytes(bytes);
            result = buildResult(resp);
        }catch (Exception e){
            log.error("SignalServiceImpl.exportSignal Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    /**
     * 发送信令到站点
     */
    private boolean sendSignalIo(String ip, String port, Map<String,String> param){
        boolean flag = false;
        try {
            String postResp = HttpClientUtil.post(ip + port, param);
            JSONObject jsonObject = JSONObject.parseObject(postResp);
            if (jsonObject != null){
                Object success = jsonObject.get("isSuccess");
                if (success != null && success instanceof Boolean){
                    if ((Boolean)success){
                        flag = true;
                    }
                }
            }
            log.info("SignalServiceImpl.sendSignalIo http postResp:{},ip:{},port:{}",postResp,ip,port);
        }catch (IOException e){
            log.warn("SignalServiceImpl.sendSignalIo IOException:{}",e.getMessage());
        }
        return flag;
    }

    private void addSignalIo(String userId,String deviceId,String status){
        NmplSignalIoExample example = new NmplSignalIoExample();
        example.createCriteria().andDeviceIdEqualTo(deviceId).andUpdateUserEqualTo(userId);
        List<NmplSignalIo> nmplSignalIos = nmplSignalIoMapper.selectByExample(example);

        NmplSignalIo io = new NmplSignalIo();


        if (!CollectionUtils.isEmpty(nmplSignalIos)){
            io = nmplSignalIos.get(0);
            io.setStatus(status);
            io.setIsExist(DataConstants.IS_EXIST);
            nmplSignalIoMapper.updateByPrimaryKey(io);
        }else {
            io.setDeviceId(deviceId);
            io.setUpdateUser(userId);
            io.setStatus(status);
            nmplSignalIoMapper.insertSelective(io);
        }
    }

    private boolean sendSignalByBigType(String userId,NmplDeviceVo vo, String ioType) throws IOException{
        String id = vo.getDeviceId();
        String bigType = vo.getDeviceBigType();
        boolean io = false;

        // 测试挡板，联调上线需要修改
        io = true;
        if(!io){

        if (com.matrictime.network.base.constant.DataConstants.DEVICE_BIG_TYPE_0.equals(bigType)){
            NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
            example.createCriteria().andStationIdEqualTo(id).andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(DataConstants.IS_EXIST);
            List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(nmplBaseStationInfos)){
                NmplBaseStationInfo info = nmplBaseStationInfos.get(0);
                String ip = info.getPublicNetworkIp();
                String port = info.getPublicNetworkPort();
                Map<String,String> map = new HashMap<>(2);
                map.put("opType",ioType);
                io = sendSignalIo(ip, port, map);
            }
        }else if (com.matrictime.network.base.constant.DataConstants.DEVICE_BIG_TYPE_1.equals(bigType)){
            NmplDeviceInfoExample example = new NmplDeviceInfoExample();
            example.createCriteria().andDeviceIdEqualTo(id).andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(DataConstants.IS_EXIST);
            List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(nmplDeviceInfos)){
                NmplDeviceInfo info = nmplDeviceInfos.get(0);
                String ip = info.getPublicNetworkIp();
                String port = info.getPublicNetworkPort();
                Map<String,String> map = new HashMap<>(2);
                map.put("opType",ioType);
                io = sendSignalIo(ip, port, map);
            }
        }

        }


        // 调用站点接口成功则进行记录
        if (io){
            addSignalIo(userId,id,ioType);
        }
        return io;
    }

    private void checkEditSignalParam(EditSignalReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "editType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (DataConstants.EDIT_TYPE_ADD.equals(req.getEditType()) || DataConstants.EDIT_TYPE_UPD.equals(req.getEditType())){
            if (CollectionUtils.isEmpty(req.getNmplSignalVos())){
                throw new SystemException(ErrorCode.PARAM_IS_NULL, "nmplSignalVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
        if (DataConstants.EDIT_TYPE_DEL.equals(req.getEditType()) && CollectionUtils.isEmpty(req.getDelIds())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "delIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkSignalIoParam(SignalIoReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getUserId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "userId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getIoType())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "ioType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (CollectionUtils.isEmpty(req.getDeviceVos())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 开启按钮只有该机器没有开启状态才允许开启
        if (com.matrictime.network.base.constant.DataConstants.IOTYPE_I.equals(req.getIoType())){
            for (NmplDeviceVo vo : req.getDeviceVos()){
                checkNmplDeviceVoParam(vo);
                NmplSignalIoExample example = new NmplSignalIoExample();
                example.createCriteria().andDeviceIdEqualTo(vo.getDeviceId()).andIsExistEqualTo(DataConstants.IS_EXIST).andStatusEqualTo(com.matrictime.network.base.constant.DataConstants.IOTYPE_I);
                List<NmplSignalIo> nmplSignalIos = nmplSignalIoMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(nmplSignalIos)){
                    throw new SystemException(com.matrictime.network.base.exception.ErrorCode.SIGNAL_I_EXCEPTION,vo.getDeviceId()+ com.matrictime.network.base.exception.ErrorMessageContants.SIGNAL_I_EXCEPTION_MSG);
                }
            }
        }else if (com.matrictime.network.base.constant.DataConstants.IOTYPE_O.equals(req.getIoType())){// 关闭按钮只能关闭自己开启的机器
            for (NmplDeviceVo vo : req.getDeviceVos()){
                checkNmplDeviceVoParam(vo);
                NmplSignalIoExample example = new NmplSignalIoExample();
                example.createCriteria().andDeviceIdEqualTo(vo.getDeviceId()).andUpdateUserEqualTo(req.getUserId()).andIsExistEqualTo(DataConstants.IS_EXIST).andStatusEqualTo(com.matrictime.network.base.constant.DataConstants.IOTYPE_I);
                List<NmplSignalIo> nmplSignalIos = nmplSignalIoMapper.selectByExample(example);
                if (CollectionUtils.isEmpty(nmplSignalIos)){
                    throw new SystemException(com.matrictime.network.base.exception.ErrorCode.SIGNAL_O_EXCEPTION,vo.getDeviceId()+ com.matrictime.network.base.exception.ErrorMessageContants.SIGNAL_O_EXCEPTION_MSG);
                }
            }
        }
    }

    private void checkNmplDeviceVoParam(NmplDeviceVo vo){
        if (ParamCheckUtil.checkVoStrBlank(vo.getDeviceId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getDeviceBigType())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceBigType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkCleanSignalParam(CleanSignalReq req){
        if (CollectionUtils.isEmpty(req.getDeviceIds())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkQuerySignalByPageParam(QuerySignalByPageReq req){
        if (ParamCheckUtil.checkVoStrBlank(req.getUserId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "userId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkExportSignalParam(ExportSignalReq req){
        if (CollectionUtils.isEmpty(req.getDeviceIds())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }
}
