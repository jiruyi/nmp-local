package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.NmplSignalIoMapper;
import com.matrictime.network.dao.mapper.NmplSignalMapper;
import com.matrictime.network.dao.mapper.extend.NmplSignalExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.dao.model.extend.NmplDeviceInfoExt;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;

import static com.matrictime.network.base.constant.DataConstants.*;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AsyncService asyncService;

    @Value("${signal.tableHeaderArr}")
    private String[] tableHeaderArr;

    @Value("${thread.maxPoolSize}")
    private Integer maxPoolSize;

    // TODO: 2022/4/1 上线前需要确定等待时间
    @Value("${signal.io.time.out}")
    private int signalIoTimeOut;

    @Value("${device.path.signal}")
    private String signalPath;

    @Override
    public Result<EditSignalResp> editSignal(EditSignalReq req) {
        Result result;
        try {
            EditConfigResp resp = null;
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
        try {
            SignalIoResp resp = new SignalIoResp();
            checkSignalIoParam(req);
            List<NmplDeviceVo> vos = req.getDeviceVos();
            List<String> successIds = new ArrayList<>(vos.size());
            List<String> failIds = new ArrayList<>();
            List<Map<String,String>> httpList = new ArrayList<>();
            switch (req.getIoType()){
                case com.matrictime.network.base.constant.DataConstants.IOTYPE_O:
                    for(NmplDeviceVo vo : vos){
                        Map<String, String> map = sendSignalByBigType(req.getUserId(), vo, req.getIoType());
                        httpList.add(map);
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
                        Map<String, String> map = sendSignalByBigType(req.getUserId(), vo, req.getIoType());
                        httpList.add(map);
                    }
                    break;
                default:
                    throw new SystemException(ErrorCode.PARAM_EXCEPTION, "ioType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }

            if (!CollectionUtils.isEmpty(httpList)){
                List<Future> futures = new ArrayList<>();
                if (httpList.size()>maxPoolSize){
                    List<List<Map<String, String>>> splitList = splitList(httpList);
                    for (List<Map<String, String>> tmpList : splitList){
                        Future<Map<String, List<String>>> mapFuture = asyncService.httpSignalIo(tmpList);
                        futures.add(mapFuture);
                    }
                }else {
                    Future<Map<String, List<String>>> mapFuture = asyncService.httpSignalIo(httpList);
                    futures.add(mapFuture);
                }

                Date timeout = DateUtils.addMinuteForDate(new Date(), signalIoTimeOut);
                while (true) {
                    if (!CollectionUtils.isEmpty(futures)) {
                        boolean isAllDone = true;
                        for (Future future : futures) {
                            if (null == future || !future.isDone()) {
                                isAllDone = false;
                            }else {
                                try {
                                    Map<String, List<String>> msg =  (Map<String, List<String>>) future.get();
                                    if (!CollectionUtils.isEmpty(msg)) {
                                        if (!CollectionUtils.isEmpty(msg.get(KEY_SUCCESS_IDS))){
                                            successIds.addAll(msg.get(KEY_SUCCESS_IDS));
                                        }
                                        if (!CollectionUtils.isEmpty(msg.get(KEY_FAIL_IDS))){
                                            failIds.addAll(msg.get(KEY_FAIL_IDS));
                                        }
                                    }
                                } catch (Exception e) {
                                    log.info("启停追踪线程池处理单个批次配置出错！error:{}",e.getMessage());
                                }
                            }
                        }
                        if (isAllDone || new Date().after(timeout)) {
                            break;
                        }
                    }else {
                        break;
                    }
                }
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
    public Result addSignal(NmplSignalVo req) {
        Result result;
        try {
            // check param is legal
            checkAddSignalParam(req);
            // if user logout,stop adding signal to this user
            boolean isOn = checkUserIsOn(req.getDeviceId());
            if (isOn){
                EditSignalReq signalReq = new EditSignalReq();
                List<NmplSignalVo> vos = new ArrayList<>(1);
                vos.add(req);
                signalReq.setEditType(DataConstants.EDIT_TYPE_ADD);
                signalReq.setNmplSignalVos(vos);
                Result<EditSignalResp> editSignal = editSignal(signalReq);
                if (editSignal.isSuccess()){
                    result = buildResult(null);
                }else {
                    result = failResult(editSignal.getErrorCode(),editSignal.getErrorMsg());
                }
            }else {
                result = buildResult(null);
            }
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
    public Result<CleanSignalResp> cleanSignal(CleanSignalReq req) {
        Result result;
        try {
            CleanSignalResp resp = null;
            checkCleanSignalParam(req);
            for(String id : req.getDeviceIds()){
                NmplSignalIoExample example = new NmplSignalIoExample();
                example.createCriteria().andDeviceIdEqualTo(id).andStatusEqualTo(com.matrictime.network.base.constant.DataConstants.IOTYPE_I).andIsExistEqualTo(DataConstants.IS_EXIST);
                List<NmplSignalIo> signalIos = nmplSignalIoMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(signalIos)){
                    throw new SystemException(ErrorMessageContants.CLEAN_SIGNAL_FAIL+signalIos.get(0).getDeviceId());
                }
            }
            NmplSignal signal = new NmplSignal();
            signal.setIsExist(DataConstants.IS_NOT_EXIST);
            for (String id : req.getDeviceIds()){
                NmplSignalExample example = new NmplSignalExample();
                example.createCriteria().andDeviceIdEqualTo(id).andIsExistEqualTo(DataConstants.IS_EXIST);
                nmplSignalMapper.updateByExampleSelective(signal,example);
            }

            result = buildResult(resp);
        }catch (Exception e){
            log.error("SignalServiceImpl.cleanSignal Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<PageInfo> querySignalByPage(QuerySignalByPageReq req) {
        Result result;
        try {
            checkQuerySignalByPageParam(req);

            Page page = PageHelper.startPage(req.getPageNo(),req.getPageSize());
            List<NmplSignal> nmplSignals = nmplSignalExtMapper.selectPagesByUserId(req.getUserId());
            PageInfo<NmplSignal> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), nmplSignals);

            result = buildResult(pageResult);
        }catch (Exception e){
            log.error("SignalServiceImpl.querySignalByPage Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<QuerySignalSelectDeviceIdsResp> querySignalSelectDeviceIds(QuerySignalSelectDeviceIdsReq req) {
        Result result;
        try {
            QuerySignalSelectDeviceIdsResp resp = new QuerySignalSelectDeviceIdsResp();
            checkQuerySignalSelectDeviceIdsParam(req);
            List<String> deviceIds = nmplSignalExtMapper.selectDeviceIdsByUserId(req.getUserId());

            resp.setDeviceIds(deviceIds);
            result = buildResult(resp);
        }catch (Exception e){
            log.error("SignalServiceImpl.querySignalSelectDeviceIds Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<ExportSignalResp> exportSignal(ExportSignalReq req) {
        Result result;
        try {
            ExportSignalResp resp = new ExportSignalResp();
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
                        obj[7] = DateUtils.formatDateToString(signal.getUploadTime());
                        obj[8] = DateUtils.formatDateToString(signal.getCreateTime());
                        obj[9] = DateUtils.formatDateToString(signal.getUpdateTime());
                        cellList.add(obj);
                    }
                }
            }
            byte[] bytes = ExportCSVUtil.writeCsvAfterToBytes(tableHeaderArr, cellList);

            resp.setBytes(bytes);
            result = buildResult(resp);
        }catch (Exception e){
            log.error("SignalServiceImpl.exportSignal Exception:{}",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result<SelectDevicesForSignalResp> selectDevicesForSignal() {
        Result result;
        try {
            SelectDevicesForSignalResp resp = new SelectDevicesForSignalResp();
            List<NmplDeviceInfoExt> infoExts = nmplSignalExtMapper.selectDevices();
            if (!CollectionUtils.isEmpty(infoExts)){
                List<NmplDeviceVo> vos = new ArrayList<>(infoExts.size());
                for (NmplDeviceInfoExt ext: infoExts){
                    NmplDeviceVo vo = new NmplDeviceVo();
                    BeanUtils.copyProperties(ext,vo);
                    vos.add(vo);
                }
                resp.setVos(vos);
            }
            result = buildResult(resp);
        }catch (Exception e){
            log.error("SignalServiceImpl.selectDevicesForSignal Exception:{}",e.getMessage());
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

    private Map<String, String> sendSignalByBigType(String userId,NmplDeviceVo vo, String ioType){
        Map<String,String> map = new HashMap<>(4);
        String id = vo.getDeviceId();
        String bigType = vo.getDeviceBigType();

        if (com.matrictime.network.base.constant.DataConstants.DEVICE_BIG_TYPE_0.equals(bigType)){
            NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
            example.createCriteria().andStationIdEqualTo(id).andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(DataConstants.IS_EXIST);
            List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(nmplBaseStationInfos)){
                NmplBaseStationInfo info = nmplBaseStationInfos.get(0);
                // TODO: 2022/4/7 path需要提供
                map.put(KEY_URL,HttpClientUtil.getUrl(info.getLanIp(),info.getLanPort(),signalPath));
                map.put(KEY_IO_TYPE,ioType);
                map.put(KEY_USER_ID,userId);
                map.put(KEY_DEVICE_ID,id);
            }
        }else if (com.matrictime.network.base.constant.DataConstants.DEVICE_BIG_TYPE_1.equals(bigType)){
            NmplDeviceInfoExample example = new NmplDeviceInfoExample();
            example.createCriteria().andDeviceIdEqualTo(id).andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(DataConstants.IS_EXIST);
            List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(nmplDeviceInfos)){
                NmplDeviceInfo info = nmplDeviceInfos.get(0);
                // TODO: 2022/4/7 path需要提供
                map.put(KEY_URL,HttpClientUtil.getUrl(info.getLanIp(),info.getLanPort(),signalPath));
                map.put(KEY_IO_TYPE,ioType);
                map.put(KEY_USER_ID,userId);
                map.put(KEY_DEVICE_ID,id);
            }
        }
        return map;
    }

    private boolean checkUserIsOn(String deviceId){
        boolean result = true;
        NmplSignalIoExample example = new NmplSignalIoExample();
        example.createCriteria().andDeviceIdEqualTo(deviceId).andStatusEqualTo(com.matrictime.network.base.constant.DataConstants.IOTYPE_I).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<NmplSignalIo> nmplSignalIos = nmplSignalIoMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(nmplSignalIos)){
                String userId = nmplSignalIos.get(0).getUpdateUser();
                Object redisToken  = redisTemplate.opsForValue().get(userId + com.matrictime.network.base.constant.DataConstants.USER_LOGIN_JWT_TOKEN);
                if (redisToken == null){
                    result = false;
                    SignalIoReq req = new SignalIoReq();
                    req.setUserId(userId);
                    req.setIoType(com.matrictime.network.base.constant.DataConstants.IOTYPE_O);
                    List<NmplDeviceVo> signalIoVos = getSignalIoVos(userId);
                    req.setDeviceVos(signalIoVos);
                    signalIo(req);
                }
        }
        return result;
    }

    private List<NmplDeviceVo> getSignalIoVos(String userId){
        NmplSignalIoExample example = new NmplSignalIoExample();
        example.createCriteria().andUpdateUserEqualTo(userId).andStatusEqualTo(com.matrictime.network.base.constant.DataConstants.IOTYPE_I).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<NmplSignalIo> nmplSignalIos = nmplSignalIoMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(nmplSignalIos)){
            List<NmplDeviceVo> nmplDeviceVos = new ArrayList<>(nmplSignalIos.size());
            for (NmplSignalIo signalIo : nmplSignalIos){
                String deviceId = signalIo.getDeviceId();
                NmplDeviceVo vo = new NmplDeviceVo();
                vo.setDeviceId(deviceId);
                NmplBaseStationInfoExample bexample = new NmplBaseStationInfoExample();
                bexample.createCriteria().andStationIdEqualTo(deviceId);
                List<NmplBaseStationInfo> binfos = nmplBaseStationInfoMapper.selectByExample(bexample);
                if (CollectionUtils.isEmpty(binfos)){
                    NmplDeviceInfoExample dexample = new NmplDeviceInfoExample();
                    dexample.createCriteria().andDeviceIdEqualTo(deviceId);
                    List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(dexample);
                    if (CollectionUtils.isEmpty(deviceInfos)){
                        log.info("SignalServiceImpl.getSignalIoVos deviceId:{},基站表和非基站表没有记录",deviceId);
                        continue;
                    }else {
                        vo.setDeviceBigType(com.matrictime.network.base.constant.DataConstants.DEVICE_BIG_TYPE_1);
                    }
                }else {
                    vo.setDeviceBigType(com.matrictime.network.base.constant.DataConstants.DEVICE_BIG_TYPE_0);
                }
                nmplDeviceVos.add(vo);
            }
            return nmplDeviceVos;
        }
        return null;
    }


    private List<List<Map<String,String>>> splitList(List<Map<String,String>> list){
        int length = list.size();
        /**
         * num 可以分成的组数
         **/
        int num = ( length + maxPoolSize - 1 )/maxPoolSize ;
        //用于存放最后结果
        List<List<Map<String,String>>> resultList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            // 开始位置
            int fromIndex = i * maxPoolSize;
            // 结束位置
            int toIndex = (i+1) * maxPoolSize < length ? ( i+1 ) * maxPoolSize : length ;
            resultList.add(list.subList(fromIndex,toIndex)) ;
        }
        return resultList;
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

    private void checkQuerySignalSelectDeviceIdsParam(QuerySignalSelectDeviceIdsReq req){
        if (ParamCheckUtil.checkVoStrBlank(req.getUserId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "userId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkExportSignalParam(ExportSignalReq req){
        if (CollectionUtils.isEmpty(req.getDeviceIds())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkAddSignalParam(NmplSignalVo req){
        if (ParamCheckUtil.checkVoStrBlank(req.getDeviceId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "DeviceId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
//        if (ParamCheckUtil.checkVoStrBlank(req.getSignalName())){
//            throw new SystemException(ErrorCode.PARAM_IS_NULL, "SignalName"+ErrorMessageContants.PARAM_IS_NULL_MSG);
//        }
//        if (ParamCheckUtil.checkVoStrBlank(req.getSendIp())){
//            throw new SystemException(ErrorCode.PARAM_IS_NULL, "SendIp"+ErrorMessageContants.PARAM_IS_NULL_MSG);
//        }
//        if (ParamCheckUtil.checkVoStrBlank(req.getReceiveIp())){
//            throw new SystemException(ErrorCode.PARAM_IS_NULL, "ReceiveIp"+ErrorMessageContants.PARAM_IS_NULL_MSG);
//        }
        if (ParamCheckUtil.checkVoStrBlank(req.getSignalContent())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "SignalContent"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getBusinessModule())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "BusinessModule"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getUploadTime() == null){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "UploadTime"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }


}
