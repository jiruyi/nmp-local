package com.matrictime.network.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.dao.mapper.NmplBaseStationMapper;
import com.matrictime.network.dao.mapper.NmplDeviceMapper;
import com.matrictime.network.dao.mapper.NmplVersionInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplBaseStationExtMapper;
import com.matrictime.network.dao.mapper.extend.NmplDeviceExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.modelVo.VersionInfoVo;
import com.matrictime.network.request.VersionReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.VersionHttpResultRes;
import com.matrictime.network.service.VersionControlService;
import com.matrictime.network.service.VersionService;
import com.matrictime.network.util.CommonCheckUtil;
import com.matrictime.network.util.ListSplitUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import static com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_1;
import static com.matrictime.network.base.exception.ErrorMessageContants.PARAM_IS_NULL_MSG;
import static com.matrictime.network.base.exception.ErrorMessageContants.PARAM_LENTH_ERROR_MSG;

@Slf4j
@Service
public class VersionControlServiceImpl extends SystemBaseService implements VersionControlService {

    @Autowired
    private AsyncService asyncService;

    @Value("${thread.maxPoolSize}")
    private Integer maxPoolSize;
    @Value("${thread.pushPoolSize}")
    private Integer pushPoolSize;

    @Value("${proxy.port}")
    private String port;

    @Value("${proxy.context-path}")
    private String contextPath;

    @Resource
    private NmplBaseStationMapper nmplBaseStationMapper;

    @Resource
    private NmplDeviceMapper nmplDeviceMapper;

    @Resource
    private NmplVersionInfoMapper nmplVersionInfoMapper;

    @Resource
    private NmplBaseStationExtMapper nmplBaseStationExtMapper;

    @Resource
    private NmplDeviceExtMapper nmplDeviceExtMapper;


    /**
     * 加载版本文件
     * @param req
     * @return
     */
    @Override
    public Result loadVersionFile(VersionReq req){
        try {
            //校验字段长度以及必传字段
            checkParam(req);
            //判断有无文件记录
            NmplVersionInfoExample nmplVersionInfoExample = new NmplVersionInfoExample();
            nmplVersionInfoExample.createCriteria().andIdEqualTo(Long.valueOf(req.getVersionId())).andIsDeleteEqualTo(true);
            List<NmplVersionInfo> nmplVersionInfos = nmplVersionInfoMapper.selectByExample(nmplVersionInfoExample);

            if(CollectionUtils.isEmpty(nmplVersionInfos)){
                throw new SystemException("此版本已过期，请重新刷新页面后加载版本");
            }
            NmplVersionInfo nmplVersionInfo = nmplVersionInfos.get(0);
            //检查版本文件是否存在
            File file = new File(nmplVersionInfo.getFilePath()+File.separator+nmplVersionInfo.getFileName());
            if(!file.exists()){
               throw new SystemException("版本文件不存在,请重新上传版本文件");
            }
            CountDownLatch  countDownLatch = null;
            if(SYSTEM_ID_1.equals(req.getDeviceType())){
                //设备表更新 全部推送或选择推送
                List<NmplDevice> nmplDevices = new ArrayList<>();
                if(!req.getTotal()){
                    List<String> deviceIds = req.getDeviceIds();
                    NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
                    nmplDeviceExample.createCriteria().andDeviceIdIn(deviceIds);
                    nmplDevices = nmplDeviceMapper.selectByExample(nmplDeviceExample);
                }else {
                    NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
                    nmplDeviceExample.createCriteria().andIsExistEqualTo(true).andDeviceTypeEqualTo(req.getDeviceType());
                    nmplDevices = nmplDeviceMapper.selectByExample(nmplDeviceExample);
                }
                if(CollectionUtils.isEmpty(nmplDevices)){
                    throw new SystemException("不存在设备信息");
                }
                List<List<NmplDevice>> data = ListSplitUtil.split(nmplDevices,pushPoolSize);
                countDownLatch = new CountDownLatch(data.size());
                for (List<NmplDevice> list : data) {
                  //文件推送，通过获取版本文件id获取文件路径，
                    Map<String,String> map = new HashMap<>();
                    for (NmplDevice nmplDevice : list) {
                        map.put(nmplDevice.getDeviceId(),nmplDevice.getLanIp());
                    }
                    asyncService.httpPushLoadFile(port+contextPath+DataConstants.LOAD_FILE,req.getVersionId(),map,countDownLatch);
                }
                countDownLatch.await();
            }else {
                //基站表 全部推送或选择推送
                List< NmplBaseStation> nmplBaseStations = new ArrayList<>();
                if(!req.getTotal()){
                    List<String> deviceIds = req.getDeviceIds();
                    NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
                    nmplBaseStationExample.createCriteria().andStationIdIn(deviceIds);
                    nmplBaseStations =  nmplBaseStationMapper.selectByExample(nmplBaseStationExample);
                }else {
                    NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
                    nmplBaseStationExample.createCriteria().andIsExistEqualTo(true).andStationTypeEqualTo(req.getDeviceType());
                    nmplBaseStations =  nmplBaseStationMapper.selectByExample(nmplBaseStationExample);
                }
                if(CollectionUtils.isEmpty(nmplBaseStations)){
                    throw new SystemException("不存在基站信息");
                }
                List<List<NmplBaseStation>> data = ListSplitUtil.split(nmplBaseStations,pushPoolSize);
                countDownLatch = new CountDownLatch(data.size());
                for (List<NmplBaseStation> list : data) {
                    //文件推送，通过获取版本文件id获取文件路径，
                    Map<String,String> map = new HashMap<>();
                    for (NmplBaseStation nmplBaseStation : list) {
                        map.put(nmplBaseStation.getStationId(),nmplBaseStation.getLanIp());
                    }
                    asyncService.httpPushLoadFile(port+contextPath+DataConstants.LOAD_FILE,req.getVersionId(),map,countDownLatch);
                }
                countDownLatch.await();
            }
            return buildResult("后台正在推送中，请稍等");
        }catch (SystemException e){
            log.error("VersionControlServiceImpl.loadVersionFile SystemException:{}",e.getMessage());
            return failResult(e.getMessage());
        }
        catch (Exception e){
            log.error("VersionControlServiceImpl.loadVersionFile SystemException:{}",e.getMessage());
            return failResult("");
        }
    }


    /**
     * 分页查询加载版本列表
     * @param req
     * @return
     */
    @Override
    public Result<PageInfo> queryLoadVersion(VersionReq req) {
        checkParam(req);
        PageInfo<VersionInfoVo> pageResult =  new PageInfo<>();
        Page page;
        List<VersionInfoVo> versionInfoVoList = new ArrayList<>();
        if(!SYSTEM_ID_1.equals(req.getDeviceType())){
            page = PageHelper.startPage(req.getPageNo(),req.getPageSize());
            versionInfoVoList = nmplBaseStationExtMapper.queryLoadDataByCondition(req);
        }else {
            page = PageHelper.startPage(req.getPageNo(),req.getPageSize());
            versionInfoVoList = nmplDeviceExtMapper.queryLoadDataByCondition(req);
        }
        pageResult.setList(versionInfoVoList);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return buildResult(pageResult);
    }

    /**
     * 分页查询运行版本列表
     * @param req
     * @return
     */
    @Override
    public Result<PageInfo> queryRunVersion(VersionReq req) {
        checkParam(req);
        PageInfo<VersionInfoVo> pageResult =  new PageInfo<>();
        Page page;
        List<VersionInfoVo> versionInfoVoList = new ArrayList<>();
        if(!SYSTEM_ID_1.equals(req.getDeviceType())){
            page = PageHelper.startPage(req.getPageNo(),req.getPageSize());
            versionInfoVoList = nmplBaseStationExtMapper.queryRunDataByCondition(req);
        }else {
            page = PageHelper.startPage(req.getPageNo(),req.getPageSize());
            versionInfoVoList = nmplDeviceExtMapper.queryRunDataByCondition(req);
        }
        pageResult.setList(versionInfoVoList);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return buildResult(pageResult);
    }

    /**
     * 加载启动版本文件
     * @param req
     * @return
     */
    @Override
    public Result runLoadVersionFile(VersionReq req) {
        try {
            //校验字段长度以及必传字段
            checkParam(req);
            String updateRunStatus = DataConstants.VERSION_RUN_STATUS;
            List<VersionHttpResultRes> result =  new ArrayList<>();
            if(SYSTEM_ID_1.equals(req.getDeviceType())){
                //设备表更新 全部或选择加载启动
                List<NmplDevice> nmplDevices = new ArrayList<>();
                if(!req.getTotal()){
                    List<String> deviceIds = req.getDeviceIds();
                    NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
                    nmplDeviceExample.createCriteria().andDeviceIdIn(deviceIds);
                    nmplDevices = nmplDeviceMapper.selectByExample(nmplDeviceExample);
                }else {
                    NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
                    nmplDeviceExample.createCriteria().andIsExistEqualTo(true).andDeviceTypeEqualTo(req.getDeviceType()).andRunVersionStatusNotEqualTo(DataConstants.VERSION_RUN_STATUS);
                    nmplDevices = nmplDeviceMapper.selectByExample(nmplDeviceExample);
                }
                //过滤无加载版本或已运行的设备
                List<NmplDevice> nmplDeviceList = filterRunningDevice(nmplDevices);
                if(CollectionUtils.isEmpty(nmplDeviceList)){
                    throw new SystemException(ErrorMessageContants.NO_LOAD_VERSION);
                }
                List<List<NmplDevice>> data = ListSplitUtil.split(nmplDeviceList,maxPoolSize);
                for (List<NmplDevice> list : data) {
                    //文件推送，通过获取版本文件id获取文件路径，
                    Map<String,String> ipmap = new HashMap<>();
                    Map<String,Long> versionMap = new HashMap<>();
                    for (NmplDevice nmplDevice : list) {
                        ipmap.put(nmplDevice.getDeviceId(),nmplDevice.getLanIp());
                        versionMap.put(nmplDevice.getDeviceId(),nmplDevice.getLoadVersionId());
                    }
                    Future<Map<String, Boolean>> mapFuture = asyncService.httpUpdateVersionStatus
                            (port + contextPath + DataConstants.LOAD_RUN_FILE, ipmap, versionMap, updateRunStatus);
                    //清空版本加载信息
                    Map<String, Boolean> stringBooleanMap = mapFuture.get();
                    for (NmplDevice nmplDevice : list) {
                        VersionHttpResultRes versionHttpResultRes = new VersionHttpResultRes();
                        versionHttpResultRes.setDeviceId(nmplDevice.getDeviceId());
                        versionHttpResultRes.setDeviceName(nmplDevice.getDeviceName());
                        versionHttpResultRes.setSuccess(stringBooleanMap.get(nmplDevice.getDeviceId()));
                        if(stringBooleanMap.get(nmplDevice.getDeviceId())) {
                            NmplDevice device = nmplDeviceMapper.selectByPrimaryKey(nmplDevice.getId());
                            device.setLoadVersionNo(null);
                            device.setLoadVersionId(null);
                            device.setLoadVersionOperTime(new Date());
                            device.setLoadFileName(null);
                            nmplDeviceMapper.updateByPrimaryKey(device);
                        }
                        result.add(versionHttpResultRes);
                    }
                }
            }else {
                //基站表 全部或选择加载启动
                List<NmplBaseStation> nmplBaseStations = new ArrayList<>();
                if (!req.getTotal()) {
                    List<String> deviceIds = req.getDeviceIds();
                    NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
                    nmplBaseStationExample.createCriteria().andStationIdIn(deviceIds);
                    nmplBaseStations = nmplBaseStationMapper.selectByExample(nmplBaseStationExample);
                } else {
                    NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
                    nmplBaseStationExample.createCriteria().andIsExistEqualTo(true).andStationTypeEqualTo(req.getDeviceType()).andRunVersionStatusNotEqualTo(DataConstants.VERSION_RUN_STATUS);
                    nmplBaseStations = nmplBaseStationMapper.selectByExample(nmplBaseStationExample);
                }
                //过滤无加载版本或已运行的设备
                List<NmplBaseStation> nmplBaseStationList = filterRunningDevice(nmplBaseStations);
                if(CollectionUtils.isEmpty(nmplBaseStationList)){
                    throw new SystemException(ErrorMessageContants.NO_LOAD_VERSION);
                }
                List<List<NmplBaseStation>> data = ListSplitUtil.split(nmplBaseStationList, maxPoolSize);
                for (List<NmplBaseStation> list : data) {
                    //文件推送，通过获取版本文件id获取文件路径，
                    Map<String,String> ipmap = new HashMap<>();
                    Map<String,Long> versionMap = new HashMap<>();
                    for (NmplBaseStation nmplBaseStation : list) {
                        ipmap.put(nmplBaseStation.getStationId(),nmplBaseStation.getLanIp());
                        versionMap.put(nmplBaseStation.getStationId(),nmplBaseStation.getLoadVersionId());
                    }
                    Future<Map<String, Boolean>> mapFuture = asyncService.httpUpdateVersionStatus
                            (port + contextPath + DataConstants.LOAD_RUN_FILE, ipmap, versionMap, updateRunStatus);
                    //清空版本加载信息
                    Map<String, Boolean> stringBooleanMap = mapFuture.get();
                    for (NmplBaseStation nmplBaseStation : list) {
                        VersionHttpResultRes versionHttpResultRes = new VersionHttpResultRes();
                        versionHttpResultRes.setDeviceId(nmplBaseStation.getStationId());
                        versionHttpResultRes.setDeviceName(nmplBaseStation.getStationName());
                        versionHttpResultRes.setSuccess(stringBooleanMap.get(nmplBaseStation.getStationId()));
                        result.add(versionHttpResultRes);
                        if (stringBooleanMap.get(nmplBaseStation.getStationId())) {
                            NmplBaseStation station = nmplBaseStationMapper.selectByPrimaryKey(nmplBaseStation.getId());
                            station.setLoadVersionNo(null);
                            station.setLoadVersionId(null);
                            station.setLoadVersionOperTime(new Date());
                            station.setLoadFileName(null);
                            nmplBaseStationMapper.updateByPrimaryKey(station);
                        }
                    }
                }
            }
            return buildResult(result);
        }catch (SystemException e){
            log.error("VersionControlServiceImpl.runLoadVersionFile SystemException:{}",e.getMessage());
            return failResult(e.getMessage());
        }
        catch (Exception e){
            log.error("VersionControlServiceImpl.runLoadVersionFile SystemException:{}",e.getMessage());
            return failResult("");
        }
    }


    /**
     * 运行已停止的版本
     * @param req
     * @return
     */
    @Override
    public Result runVersion(VersionReq req) {
        try {
            //校验字段长度以及必传字段
            checkParam(req);
            String updateRunStatus = DataConstants.VERSION_RUN_STATUS;
            String filterStatus = DataConstants.VERSION_STOP_STATUS;
            String urlSuffix =  DataConstants.RUN_FILE;
            return flowStatus(req,updateRunStatus,filterStatus,urlSuffix);
        }catch (SystemException e){
            log.error("VersionControlServiceImpl.runVersion SystemException:{}",e.getMessage());
            return failResult(e.getMessage());
        }
        catch (Exception e){
            log.error("VersionControlServiceImpl.runVersion SystemException:{}",e.getMessage());
            return failResult("");
        }
    }

    /**
     * 停止已运行的版本
     * @param req
     * @return
     */
    @Override
    public Result stopRunVersion(VersionReq req) {
        try {
            //校验字段长度以及必传字段
            checkParam(req);
            String updateRunStatus = DataConstants.VERSION_STOP_STATUS;
            String filterStatus = DataConstants.VERSION_RUN_STATUS;
            String urlSuffix =  DataConstants.STOP_FILE;
            return flowStatus(req,updateRunStatus,filterStatus,urlSuffix);
        }catch (SystemException e){
            log.error("VersionControlServiceImpl.runVersion SystemException:{}",e.getMessage());
            return failResult(e.getMessage());
        }
        catch (Exception e){
            log.error("VersionControlServiceImpl.runVersion SystemException:{}",e.getMessage());
            return failResult("");
        }
    }

    /**
     * 卸载已停止的版本
     * @param req
     * @return
     */
    @Override
    public Result uninstallRunVersion(VersionReq req) {
        try {
            //校验字段长度以及必传字段
            checkParam(req);
            String updateRunStatus = DataConstants.VERSION_INIT_STATUS;
            String filterStatus = DataConstants.VERSION_STOP_STATUS;
            String urlSuffix =  DataConstants.UNINSTALL_FILE;
            return flowStatus(req,updateRunStatus,filterStatus,urlSuffix);
        }catch (SystemException e){
            log.error("VersionControlServiceImpl.runVersion SystemException:{}",e.getMessage());
            return failResult(e.getMessage());
        }
        catch (Exception e){
            log.error("VersionControlServiceImpl.runVersion SystemException:{}",e.getMessage());
            return failResult("");
        }
    }


    private void checkParam(VersionReq req){
        if(!StringUtils.isEmpty(req.getDeviceName())){
            if(!CommonCheckUtil.checkStringLength(req.getDeviceName(),null,16)){
                throw new com.matrictime.network.base.SystemException(PARAM_LENTH_ERROR_MSG);
            }
        }
        if(!StringUtils.isEmpty(req.getLoadVersionNo())){
            if(!CommonCheckUtil.checkStringLength(req.getLoadVersionNo(),null,16)){
                throw new com.matrictime.network.base.SystemException(PARAM_LENTH_ERROR_MSG);
            }
        }
        if(StringUtils.isEmpty(req.getDeviceType())){
            throw new com.matrictime.network.base.SystemException(PARAM_IS_NULL_MSG);
        }
    }

    /**
     * 过滤无加载版本或已运行的设备
     * @param objList
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> List<T> filterRunningDevice(List<T>objList)throws Exception{
        if(!CollectionUtils.isEmpty(objList)){
            Iterator<T> iterator = objList.iterator();
            while (iterator.hasNext()) {
                T next = iterator.next();
                Method statusMethod = next.getClass().getMethod("getRunVersionStatus");
                //通过get方法获取属性值
                String runVersionStatus = (String) statusMethod.invoke(next);
                Method lodaVersionMethod = next.getClass().getMethod("getLoadVersionId");
                Object loadVersionId = lodaVersionMethod.invoke(next);
                if (runVersionStatus.equals(DataConstants.VERSION_RUN_STATUS)||loadVersionId==null) {
                    iterator.remove();
                }
            }
        }
        return objList;
    }

    /**
     * 过滤非某个状态的设备
     * @param objList
     * @param status
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> List<T> filterStatusDevice(List<T>objList,String status)throws Exception{
        if(!CollectionUtils.isEmpty(objList)){
            Iterator<T> iterator = objList.iterator();
            while (iterator.hasNext()) {
                T next = iterator.next();
                Method statusMethod = next.getClass().getMethod("getRunVersionStatus");
                //通过get方法获取属性值
                String runVersionStatus = (String) statusMethod.invoke(next);
                if (!runVersionStatus.equals(status)) {
                    iterator.remove();
                }
            }
        }
        return objList;
    }

    /**
     * 版本操作 状态流转
     * @param req
     * @param updateRunStatus 更新的状态
     * @param filterStatus 过滤状态条件
     * @param urlSuffix url后缀
     * @return
     * @throws Exception
     */
    private Result flowStatus(VersionReq req,String updateRunStatus,String filterStatus,String urlSuffix)throws Exception{
        List<VersionHttpResultRes> result =  new ArrayList<>();
        if(SYSTEM_ID_1.equals(req.getDeviceType())){
            //设备表更新 全部或选择加载启动
            List<NmplDevice> nmplDevices = new ArrayList<>();
            if(!req.getTotal()){
                List<String> deviceIds = req.getDeviceIds();
                NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
                nmplDeviceExample.createCriteria().andDeviceIdIn(deviceIds);
                nmplDevices = nmplDeviceMapper.selectByExample(nmplDeviceExample);
            }else {
                NmplDeviceExample nmplDeviceExample = new NmplDeviceExample();
                nmplDeviceExample.createCriteria().andIsExistEqualTo(true).andDeviceTypeEqualTo(req.getDeviceType()).andRunVersionStatusEqualTo(filterStatus);
                nmplDevices = nmplDeviceMapper.selectByExample(nmplDeviceExample);
            }
            List<NmplDevice> nmplDeviceList = filterStatusDevice(nmplDevices,filterStatus);
            if(CollectionUtils.isEmpty(nmplDeviceList)){
                throw new SystemException(ErrorMessageContants.NO_DEVICE_CAN_RUN);
            }
            List<List<NmplDevice>> data = ListSplitUtil.split(nmplDeviceList,maxPoolSize);
            for (List<NmplDevice> list : data) {
                //文件推送，通过获取版本文件id获取文件路径，
                Map<String,String> ipmap = new HashMap<>();
                Map<String,Long> versionMap = new HashMap<>();
                for (NmplDevice nmplDevice : list) {
                    ipmap.put(nmplDevice.getDeviceId(),nmplDevice.getLanIp());
                    versionMap.put(nmplDevice.getDeviceId(),nmplDevice.getRunVersionId());
                }
                Future<Map<String, Boolean>> mapFuture = asyncService.httpUpdateVersionStatus
                        (port + contextPath + urlSuffix, ipmap, versionMap, updateRunStatus);
                Map<String, Boolean> stringBooleanMap = mapFuture.get();

                for (NmplDevice nmplDevice : list) {
                    VersionHttpResultRes versionHttpResultRes = new VersionHttpResultRes();
                    versionHttpResultRes.setDeviceId(nmplDevice.getDeviceId());
                    versionHttpResultRes.setDeviceName(nmplDevice.getDeviceName());
                    versionHttpResultRes.setSuccess(stringBooleanMap.get(nmplDevice.getDeviceName()));
                    result.add(versionHttpResultRes);
                }

            }
        }else {
            //基站表 全部或选择加载启动
            List<NmplBaseStation> nmplBaseStations = new ArrayList<>();
            if (!req.getTotal()) {
                List<String> deviceIds = req.getDeviceIds();
                NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
                nmplBaseStationExample.createCriteria().andStationIdIn(deviceIds);
                nmplBaseStations = nmplBaseStationMapper.selectByExample(nmplBaseStationExample);
            } else {
                NmplBaseStationExample nmplBaseStationExample = new NmplBaseStationExample();
                nmplBaseStationExample.createCriteria().andIsExistEqualTo(true).andStationTypeEqualTo(req.getDeviceType()).andRunVersionStatusEqualTo(filterStatus);
                nmplBaseStations = nmplBaseStationMapper.selectByExample(nmplBaseStationExample);
            }
            List<NmplBaseStation> nmplBaseStationList = filterStatusDevice(nmplBaseStations,filterStatus);
            if(CollectionUtils.isEmpty(nmplBaseStationList)){
                throw new SystemException(ErrorMessageContants.NO_DEVICE_CAN_RUN);
            }
            List<List<NmplBaseStation>> data = ListSplitUtil.split(nmplBaseStationList, maxPoolSize);
            for (List<NmplBaseStation> list : data) {
                //文件推送，通过获取版本文件id获取文件路径，
                Map<String,String> ipmap = new HashMap<>();
                Map<String,Long> versionMap = new HashMap<>();
                for (NmplBaseStation nmplBaseStation : list) {
                    ipmap.put(nmplBaseStation.getStationId(),nmplBaseStation.getLanIp());
                    versionMap.put(nmplBaseStation.getStationId(),nmplBaseStation.getRunVersionId());
                }
                Future<Map<String, Boolean>> mapFuture = asyncService.httpUpdateVersionStatus
                        (port + contextPath + urlSuffix, ipmap, versionMap, updateRunStatus);
                Map<String, Boolean> stringBooleanMap = mapFuture.get();
                for (NmplBaseStation nmplBaseStation : list) {
                    VersionHttpResultRes versionHttpResultRes = new VersionHttpResultRes();
                    versionHttpResultRes.setDeviceId(nmplBaseStation.getStationId());
                    versionHttpResultRes.setDeviceName(nmplBaseStation.getStationName());
                    versionHttpResultRes.setSuccess(stringBooleanMap.get(nmplBaseStation.getStationId()));
                    result.add(versionHttpResultRes);
                }
            }
        }
        return buildResult(result);
    }

}
