package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.AlarmPhyConTypeEnum;
import com.matrictime.network.base.enums.LevelEnum;
import com.matrictime.network.dao.domain.LocalBaseStationDomainService;
import com.matrictime.network.dao.domain.SystemHeartbeatDomainService;
import com.matrictime.network.dao.domain.TerminalUserDomainService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.dao.model.NmplTerminalData;
import com.matrictime.network.dao.model.extend.DeviceInfo;
import com.matrictime.network.facade.AlarmDataFacade;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.request.*;
import com.matrictime.network.response.SystemHeartbeatResponse;
import com.matrictime.network.response.TerminalUserResponse;
import com.matrictime.network.service.TaskService;
import com.matrictime.network.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

import static com.matrictime.network.base.constant.DataConstants.HEART_REPORT_SPACE;
import static com.matrictime.network.constant.BusinessConsts.*;
import static com.matrictime.network.constant.DataConstants.IS_EXIST;
import static com.matrictime.network.constant.DataConstants.KEY_SPLIT_UNDERLINE;

@Service
@Slf4j
@PropertySource(value = "classpath:/businessConfig.properties",encoding = "UTF-8")
public class TaskServiceImpl implements TaskService {

    @Resource
    private NmplStationHeartInfoMapper nmplStationHeartInfoMapper;

    @Resource
    private NmplKeycenterHeartInfoMapper nmplKeycenterHeartInfoMapper;

    @Resource
    private NmplDataCollectMapper nmplDataCollectMapper;

    @Resource
    private NmplErrorPushLogMapper nmplErrorPushLogMapper;

    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Resource
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Resource
    private NmplLocalBaseStationInfoMapper nmplLocalBaseStationInfoMapper;

    @Resource
    private NmplLocalDeviceInfoMapper nmplLocalDeviceInfoMapper;

    @Resource
    private SystemHeartbeatDomainService systemHeartbeatDomainService;

    @Resource
    private TerminalUserDomainService terminalUserDomainService;
    
    @Resource
    private NmplAlarmInfoMapper alarmInfoMapper;

    @Resource
    private NmplTerminalDataMapper terminalDataMapper;

    @Resource
    private LocalBaseStationDomainService localBaseStationDomainService;


    @Autowired
    private AlarmDataFacade alarmDataFacade;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${local.ip}")
    private String localIp;

    @Value("${error.cpu}")
    private String errorCpu;

    @Value("${warn.cpu}")
    private String warnCpu;

    @Value("${info.cpu}")
    private String infoCpu;

    @Value("${error.mem}")
    private String errorMem;

    @Value("${warn.mem}")
    private String warnMem;

    @Value("${info.mem}")
    private String infoMem;

    @Value("${error.disk}")
    private String errorDisk;

    @Value("${warn.disk}")
    private String warnDisk;

    @Value("${info.disk}")
    private String infoDisk;


    @Value("${error.network.load}")
    private Long errorLoad;

    @Value("${warn.network.load}")
    private Long warnLoad;

    @Value("${info.network.load}")
    private Long infoLoad;

    private static final String DATACOLLECT_PUSH_LAST_MAXI_ID= ":datacollect_last_push_max_id";


    /**
     * 站点状态上报
     */
    @Override
    public void heartReport(Date excuteTime) {
        // 查询基站的状态上报数据
        NmplStationHeartInfoExample stationExample = new NmplStationHeartInfoExample();
        stationExample.setOrderByClause("create_time desc");
        List<NmplStationHeartInfo> stationHeartInfos = nmplStationHeartInfoMapper.selectByExample(stationExample);
        if (!CollectionUtils.isEmpty(stationHeartInfos)){
            NmplStationHeartInfo heartInfo = stationHeartInfos.get(NumberUtils.INTEGER_ZERO);
            Date lastUploadTime = DateUtils.addSecondsForDate(heartInfo.getCreateTime(), HEART_REPORT_SPACE);

            // 判断最新数据是否有效，并上报网管中心站点状态
            checkHeartReport(excuteTime,lastUploadTime,heartInfo.getRemark(),heartInfo.getStationId());

            // 清除基站历史状态上报数据
            stationExample.createCriteria().andCreateTimeLessThanOrEqualTo(stationHeartInfos.get(NumberUtils.INTEGER_ZERO).getCreateTime());
            int deleteStation = nmplStationHeartInfoMapper.deleteByExample(stationExample);
            log.info("TaskServiceImpl.heartReport deleteStation:{}",deleteStation);
        }

        // 查询设备的心跳上报数据
        NmplKeycenterHeartInfoExample keycenterExample = new NmplKeycenterHeartInfoExample();
        keycenterExample.setOrderByClause("create_time desc");
        List<NmplKeycenterHeartInfo> keycenterHeartInfos = nmplKeycenterHeartInfoMapper.selectByExample(keycenterExample);
        if (!CollectionUtils.isEmpty(keycenterHeartInfos)){
            NmplKeycenterHeartInfo heartInfo = keycenterHeartInfos.get(NumberUtils.INTEGER_ZERO);
            Date lastUploadTime = DateUtils.addSecondsForDate(heartInfo.getCreateTime(), HEART_REPORT_SPACE);

            // 判断最新数据是否有效，并上报网管中心站点状态
            checkHeartReport(excuteTime,lastUploadTime,heartInfo.getRemark(),heartInfo.getDeviceId());

            // 清除密钥中心历史状态上报数据
            keycenterExample.createCriteria().andCreateTimeLessThanOrEqualTo(keycenterHeartInfos.get(NumberUtils.INTEGER_ZERO).getCreateTime());
            int deleteKeycenter = nmplKeycenterHeartInfoMapper.deleteByExample(keycenterExample);
            log.info("TaskServiceImpl.heartReport deleteKeycenter:{}",deleteKeycenter);
        }
    }

    /**
     * 站点状态上报网管中心
     * @param excuteTime
     * @param lastUploadTime
     * @param status
     * @param deviceId
     */
    private void checkHeartReport(Date excuteTime,Date lastUploadTime,String status,String deviceId){
        // 判断最新一次上报的时间是否在30秒以内，是则上报，否则为无效数据不上报
        if (excuteTime.compareTo(lastUploadTime) == -1){
            CheckHeartReq req = new CheckHeartReq();
            req.setStatus(status);
            req.setDeviceId(deviceId);

            // 上报中心网管
            Result result = alarmDataFacade.checkHeart(req);
            log.info("TaskServiceImpl.checkHeartReport alarmDataFacade.checkHeart:{}",result.toString());
        }
    }

    /**
     * 统计数据推送
     * @param url
     * @param localIp
     */
    @Override
    public void dataCollectPush(String url,String localIp) {
        try {
            NmplDataCollectExample nmplDataCollectExample = new NmplDataCollectExample();
            NmplDataCollectExample.Criteria criteria = nmplDataCollectExample.createCriteria();
            Object lastMaxId = redisTemplate.opsForValue().get(localIp+DATACOLLECT_PUSH_LAST_MAXI_ID);
            if(Objects.nonNull(lastMaxId)){
                //删除上次推送之前的数据
                criteria.andIdLessThanOrEqualTo(Long.valueOf(lastMaxId.toString()));
                int thisCount = nmplDataCollectMapper.deleteByExample(nmplDataCollectExample);
                nmplDataCollectExample.clear();
                log.info("ip is:{} last alarmPush lastMaxId is:{} deletecount is:{} ",localIp,lastMaxId,thisCount);
            }
            // 查询所有的统计数据
            nmplDataCollectExample.setOrderByClause("id desc");
            List<NmplDataCollect> nmplDataCollectList = nmplDataCollectMapper.selectByExample(nmplDataCollectExample);
            nmplDataCollectExample.clear();

            // 补充数据
            DataCollectReq dataCollectReq = new DataCollectReq();
            List<DataCollectVo> dataCollectVoList = new ArrayList<>();
            for (NmplDataCollect nmplDataCollect : nmplDataCollectList) {
                nmplDataCollect.setDeviceIp(localIp);
                DataCollectVo dataCollectVo = new DataCollectVo();
                BeanUtils.copyProperties(nmplDataCollect,dataCollectVo);
                dataCollectVoList.add(dataCollectVo);
            }
            dataCollectReq.setDataCollectVoList(dataCollectVoList);

            Result result = alarmDataFacade.insertSystemData(dataCollectReq);
            if(ObjectUtils.isEmpty(result) ||  !result.isSuccess()){
                return;
            }
            Long maxId = nmplDataCollectList.get(0).getId();
            criteria.andIdLessThanOrEqualTo(maxId);
            nmplDataCollectMapper.deleteByExample(nmplDataCollectExample);
        }catch (Exception e){
            log.error("DataPushService dataCollectPush exception:{}",e.getMessage());
        }
    }


    /**
     * 物理设备心跳上报服务
     * @param uploadTime
     */
    @Override
    public void physicalDeviceHeartbeat(Date uploadTime) {
        // 获取所有物理设备ip列表
        List<String> ips = getIps();

        List<PhysicalDeviceHeartbeatVo> reqList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ips)){
            for (String ip : ips){
                try {
                    PhysicalDeviceHeartbeatVo vo = new PhysicalDeviceHeartbeatVo();

                    // 比较ip大小拼接主键，小ip在前，大ip在后，用下划线拼接，例：192.168.72.14_192.168.68.125
                    int i = CompareUtil.compareIp(localIp, ip);
                    if (i==0){
                        continue;
                    }else if (i>0){
                        vo.setIp1Ip2(ip+KEY_SPLIT_UNDERLINE+localIp);
                    }else if (i<0){
                        vo.setIp1Ip2(localIp+KEY_SPLIT_UNDERLINE+ip);
                    }

                    // 获取两个ip ping的结果
                    boolean ping = SystemUtils.getPingResult(ip);
                    if (ping){
                        vo.setStatus(CONNECT_STATUS_SUCCESS);
                    }else {
                        vo.setStatus(CONNECT_STATUS_FAIL);
                    }
                    reqList.add(vo);
                } catch (Exception e) {
                    log.warn("TaskServiceImpl.physicalDeviceHeartbeat exception:{}",e);
                }
            }
            Boolean flag = false;
            Result result = null;
            String data = "";
            String msg =null;
            if (!CollectionUtils.isEmpty(reqList)){
                try {
                    // 拼接上报数据并上报网管中心
                    PhysicalDeviceHeartbeatReq req = new PhysicalDeviceHeartbeatReq();
                    req.setHeartbeatList(reqList);
                    req.setUploadTime(uploadTime);
                    log.info("alarmDataFacade.physicalDeviceHeartbeat req:{}",req.toString());
                    result = alarmDataFacade.physicalDeviceHeartbeat(req);
                    data = req.toString();
                }catch (Exception e){
                    flag = true;
                    msg = e.getMessage();
                    log.info("physicalDeviceHeartbeat push Exception:{}",e.getMessage());
                }finally {
                    logError(result,"/monitor/physicalDeviceHeartbeat",data,flag,msg);
                }
            }
        }


    }


    /**
     * 物理设备资源情况上报服务
     * @param uploadTime
     */
    @Override
    public void physicalDeviceResource(Date uploadTime) {
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            // 拼接上报数据并上报网管中心
            PhysicalDeviceResourceReq req = new PhysicalDeviceResourceReq();
            req.setPdrList(getPdrList(uploadTime));
            log.info("alarmDataFacade.physicalDeviceResource req:{}",req.toString());
            result = alarmDataFacade.physicalDeviceResource(req);
            data = req.toString();
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("physicalDeviceHeartResource push Exception:{}",e.getMessage());
        }finally {
            logError(result,"/monitor/physicalDeviceResource",data,flag,msg);
        }

    }

    /**
     * 运行系统资源上报服务
     * @param uploadTime
     */
    @Override
    public void systemResource(Date uploadTime) {
        // 获取本机所有基站及设备列表
        List<DeviceInfo> infos = getLocalDeviceInfo();

        if (CollectionUtils.isEmpty(infos)){
            return;
        }

        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            // 拼接上报数据并上报网管中心
            SystemResourceReq req = new SystemResourceReq();
            req.setSrList(getSrList(uploadTime,infos));
            log.info("alarmDataFacade.systemResource req:{}",req.toString());
            result = alarmDataFacade.systemResource(req);
            data = req.toString();
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("systemResource push Exception:{}",e.getMessage());
        }finally {
            logError(result,"/monitor/systemResource",data,flag,msg);
        }
    }

    @Override
    public void systemHeartbeat(String url) {
        SystemHeartbeatResponse systemHeartbeatResponse = systemHeartbeatDomainService.selectSystemHeartbeat();
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            result = alarmDataFacade.systemHeartbeatResource(systemHeartbeatResponse);
            log.info("SystemHeartbeat push result:{}",result);
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("SystemHeartbeat push Exception:{}",e.getMessage());
        }finally {
            logError(result,url,data,flag,msg);
        }
    }

    @Override
    public void terminalUser(String url) {
        TerminalUserResponse terminalUserResponse = terminalUserDomainService.selectTerminalUser();
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            result = alarmDataFacade.terminalUserResource(terminalUserResponse);
            log.info("TerminalUser push result:{}",result);
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("TerminalUser push Exception:{}",e.getMessage());
        }finally {
            logError(result,url,data,flag,msg);
        }
    }

    @Override
    public void collectTerminalData(String url) {
        NmplTerminalDataExample nmplTerminalDataExample = new NmplTerminalDataExample();
        NmplTerminalDataExample.Criteria criteria = nmplTerminalDataExample.createCriteria();
        nmplTerminalDataExample.setOrderByClause("id desc");
        List<NmplTerminalData> terminalDataList = terminalDataMapper.selectByExample(nmplTerminalDataExample);
        //数据转换
        List<TerminalDataVo> dataVoList = new ArrayList<>();
        TerminalDataListRequest dataListRequest = new TerminalDataListRequest();
        if(!CollectionUtils.isEmpty(terminalDataList)){
            for(NmplTerminalData nmplTerminalData: terminalDataList){
                TerminalDataVo terminalDataVo = new TerminalDataVo();
                BeanUtils.copyProperties(nmplTerminalData,terminalDataVo);
                dataVoList.add(terminalDataVo);
            }
        }
        dataListRequest.setList(dataVoList);
        //上传数据
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        if(!CollectionUtils.isEmpty(terminalDataList)){
            Long maxId = terminalDataList.get(0).getId();
            try {
                data = dataListRequest.toString();
                result = alarmDataFacade.collectTerminalDataResource(dataListRequest);
            }catch (Exception e){
                flag = true;
                msg = e.getMessage();
                log.info("collectTerminalData push Exception:{}",e.getMessage());
            }finally {
                //记录失败数据
                logError(result,"/terminalData/collectTerminalData",data,flag,msg);
            }
            criteria.andIdLessThanOrEqualTo(maxId);
            terminalDataMapper.deleteByExample(nmplTerminalDataExample);
        }

    }

    @Override
    public void updateCurrentConnectCount(String url) {
        CurrentCountRequest currentCountRequest = localBaseStationDomainService.selectLocalBaseStation();
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            result = alarmDataFacade.updateCurrentConnectCount(currentCountRequest);
            log.info("updateCurrentConnectCount push result:{}",result);
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("updateCurrentConnectCount push Exception:{}",e.getMessage());
        }finally {
            logError(result,url,data,flag,msg);
        }
    }


    /**
     * @param post http返回体
     * @param url  推送路径
     * @param data 推送数据
     * @param flag 是否出现异常
     * @param msg  异常信息
     */
    private void logError(String post,String url,String data,Boolean flag,String msg){
        Boolean sucess = true;
        if(post!=null){
            JSONObject resp = JSONObject.parseObject(post);
            if (resp.containsKey("success")){
                sucess = (Boolean)resp.get("success");
            }
            if(null == msg){
                if (resp.containsKey("errorMsg")){
                    msg = (String) resp.get("errorMsg");
                }
            }
        }
        if(msg!=null&&msg.length()> DataConstants.ERROR_MSG_MAXLENGTH){
            msg = msg.substring(DataConstants.ZERO,DataConstants.ERROR_MSG_MAXLENGTH);
        }
        //推送失败或出现异常时记录
        if(!sucess||flag){
            NmplErrorPushLog nmplErrorPushLog = new NmplErrorPushLog();
            nmplErrorPushLog.setUrl(url);
            nmplErrorPushLog.setData(data);
            nmplErrorPushLog.setErrorMsg(msg);
            nmplErrorPushLogMapper.insertSelective(nmplErrorPushLog);
        }
    }

    /**
     * @param result http返回体
     * @param url  推送路径
     * @param data 推送数据
     * @param flag 是否出现异常
     * @param msg  异常信息
     */
    private void logError(Result result,String url,String data,Boolean flag,String msg){
        Boolean sucess = true;
        if(result!=null){
            sucess = result.isSuccess();
            if(null == msg){
                msg = result.getErrorMsg();
            }
        }
        if(msg!=null&&msg.length()> DataConstants.ERROR_MSG_MAXLENGTH){
            msg = msg.substring(DataConstants.ZERO,DataConstants.ERROR_MSG_MAXLENGTH);
        }
        //推送失败或出现异常时记录
        if(!sucess||flag){
            NmplErrorPushLog nmplErrorPushLog = new NmplErrorPushLog();
            nmplErrorPushLog.setUrl(url);
            nmplErrorPushLog.setData(data);
            nmplErrorPushLog.setErrorMsg(msg);
            nmplErrorPushLogMapper.insertSelective(nmplErrorPushLog);
        }
    }

    /**
     * 获取所有关联物理设备的ip列表
     * @return
     */
    private List<String> getIps(){
        List<String> ips = new ArrayList<>();

        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        baseStationInfoExample.createCriteria().andIsExistEqualTo(IS_EXIST);
        List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(baseStationInfoExample);

        if (!CollectionUtils.isEmpty(stationInfos)){
            for (NmplBaseStationInfo stationInfo: stationInfos){
                String lanIp = stationInfo.getLanIp();
                if (!localIp.equals(lanIp)){
                    ips.add(lanIp);
                }
            }
        }

        NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
        deviceInfoExample.createCriteria().andIsExistEqualTo(IS_EXIST);
        List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceInfoExample);

        if (!CollectionUtils.isEmpty(deviceInfos)){
            for (NmplDeviceInfo deviceInfo: deviceInfos){
                String lanIp = deviceInfo.getLanIp();
                if (!localIp.equals(lanIp)){
                    ips.add(lanIp);
                }
            }
        }
        return ips;
    }

    /**
     * 获取物理设备资源情况请求体
     * @param uploadTime
     * @return
     */
    private List<PhysicalDeviceResourceVo> getPdrList(Date uploadTime){
        List<PhysicalDeviceResourceVo> pdrList = new ArrayList<>();

        // 获取cpu信息
        PhysicalDeviceResourceVo cpu = new PhysicalDeviceResourceVo();
        cpu.setDeviceIp(localIp);
        cpu.setResourceType(AlarmPhyConTypeEnum.CPU.getContentType());
        cpu.setResourceValue(String.valueOf(SystemUtils.getCPUcores()));
        cpu.setResourcePercent(SystemUtils.getCPUusePercent());
        cpu.setUploadTime(uploadTime);
        pdrList.add(cpu);
        insertAlarmInfo(cpu);

        // 获取内存信息
        PhysicalDeviceResourceVo memory = new PhysicalDeviceResourceVo();
        memory.setDeviceIp(localIp);
        memory.setResourceType(AlarmPhyConTypeEnum.MEM.getContentType());
        long totalMemory = SystemUtils.getTotalMemory();
        long availMemory = SystemUtils.getAvailableMemory();
        String[] totalMemArray = FormatUtil.formatBy1024(totalMemory);
        memory.setResourceValue(totalMemArray[0]);
        memory.setResourceUnit(totalMemArray[1]);
        memory.setResourcePercent(FormatUtil.formatUnits(totalMemory-availMemory,totalMemory));
        memory.setUploadTime(uploadTime);
        pdrList.add(memory);
        insertAlarmInfo(memory);

        // 获取磁盘信息
        PhysicalDeviceResourceVo disk = new PhysicalDeviceResourceVo();
        disk.setDeviceIp(localIp);
        disk.setResourceType(AlarmPhyConTypeEnum.DISK.getContentType());
        long totalDisk = SystemUtils.getTotalFileSys();
        long availDisk = SystemUtils.getUsableFileSys();
        String[] totalDiskArray = FormatUtil.formatBy1024(totalDisk);
        disk.setResourceValue(totalDiskArray[0]);
        disk.setResourceUnit(totalDiskArray[1]);
        disk.setResourcePercent(FormatUtil.formatUnits(totalDisk-availDisk,totalDisk));
        disk.setUploadTime(uploadTime);
        pdrList.add(disk);
        insertAlarmInfo(disk);

        // 获取网络流量信息
        PhysicalDeviceResourceVo netLoad = new PhysicalDeviceResourceVo();
        netLoad.setDeviceIp(localIp);
        netLoad.setResourceType(AlarmPhyConTypeEnum.FLOW.getContentType());
        long load = SystemUtils.getNetLoad(localIp);
        netLoad.setResourceValue(String.valueOf(load));
        netLoad.setUploadTime(uploadTime);
        insertAlarmInfo(netLoad);

        return pdrList;
    }

    /**
     * 插入告警信息表
     * @param dto
     */
    private void insertAlarmInfo(PhysicalDeviceResourceVo dto){
        log.info("PhysicalDeviceResourceVo :{}",dto.toString());
        try{
            boolean isAlarm = false;
            NmplAlarmInfo alarmInfo = new NmplAlarmInfo();
            String percent = dto.getResourcePercent();
            if (AlarmPhyConTypeEnum.CPU.getContentType().equals(dto.getResourceType())){// 判断资源类型是否为cpu
                if (CompareUtil.compareShortStr(percent,infoCpu)>0){
                    isAlarm = true;
                    alarmInfo.setAlarmContent(AlarmPhyConTypeEnum.CPU.getDesc());

                    // 判断cpu是否超过严重、紧急、一般的阈值
                    if (CompareUtil.compareShortStr(percent,errorCpu)>0){
                        alarmInfo.setAlarmLevel(LevelEnum.SERIOUS.getLevel());
                    }else if (CompareUtil.compareShortStr(percent,warnCpu)>0){
                        alarmInfo.setAlarmLevel(LevelEnum.EMERG.getLevel());
                    }else {
                        alarmInfo.setAlarmLevel(LevelEnum.SAMEAS.getLevel());
                    }
                }
            }else if (AlarmPhyConTypeEnum.MEM.getContentType().equals(dto.getResourceType())){// 判断资源类型是否为内存
                if (CompareUtil.compareShortStr(percent,infoMem)>0){
                    isAlarm = true;
                    alarmInfo.setAlarmContent(AlarmPhyConTypeEnum.MEM.getDesc());

                    // 判断内存是否超过严重、紧急、一般的阈值
                    if (CompareUtil.compareShortStr(percent,errorMem)>0){
                        alarmInfo.setAlarmLevel(LevelEnum.SERIOUS.getLevel());
                    }else if (CompareUtil.compareShortStr(percent,warnMem)>0){
                        alarmInfo.setAlarmLevel(LevelEnum.EMERG.getLevel());
                    }else {
                        alarmInfo.setAlarmLevel(LevelEnum.SAMEAS.getLevel());
                    }
                }
            }else if (AlarmPhyConTypeEnum.DISK.getContentType().equals(dto.getResourceType())){// 判断资源类型是否为磁盘
                if (CompareUtil.compareShortStr(percent,infoDisk)>0){
                    isAlarm = true;
                    alarmInfo.setAlarmContent(AlarmPhyConTypeEnum.DISK.getDesc());

                    // 判断磁盘是否超过严重、紧急、一般的阈值
                    if (CompareUtil.compareShortStr(percent,errorDisk)>0){
                        alarmInfo.setAlarmLevel(LevelEnum.SERIOUS.getLevel());
                    }else if (CompareUtil.compareShortStr(percent,warnDisk)>0){
                        alarmInfo.setAlarmLevel(LevelEnum.EMERG.getLevel());
                    }else {
                        alarmInfo.setAlarmLevel(LevelEnum.SAMEAS.getLevel());
                    }
                }
            }else if (AlarmPhyConTypeEnum.FLOW.getContentType().equals(dto.getResourceType())){// 判断资源类型是否为流量
                Long value = Long.valueOf(dto.getResourceValue());
                if (infoLoad.compareTo(value) == -1){
                    isAlarm = true;
                    alarmInfo.setAlarmContent(AlarmPhyConTypeEnum.FLOW.getDesc());

                    // 判断流量是否超过严重、紧急、一般的阈值
                    if (errorLoad.compareTo(value) == -1){
                        alarmInfo.setAlarmLevel(LevelEnum.SERIOUS.getLevel());
                    }else if (warnLoad.compareTo(value) == -1){
                        alarmInfo.setAlarmLevel(LevelEnum.EMERG.getLevel());
                    }else {
                        alarmInfo.setAlarmLevel(LevelEnum.SAMEAS.getLevel());
                    }
                }
            }
            // 超过阈值则插入告警表
            if (isAlarm){
                log.info("PhysicalDeviceResourceVo :{}",alarmInfo.toString());
                alarmInfo.setAlarmContentType(dto.getResourceType());
                alarmInfo.setAlarmSourceIp(dto.getDeviceIp());
                alarmInfo.setAlarmUploadTime(dto.getUploadTime());
                alarmInfo.setAlarmSourceType(ALARM_SOURCE_TYPE_RESOURCE);
                alarmInfoMapper.insertSelective(alarmInfo);
            }
        }catch (Exception e){
            log.warn("insertAlarmInfo Exception:{}",e);
        }
    }

    /**
     * 获取本地所有基站及设备列表
     * @return
     */
    private List<DeviceInfo> getLocalDeviceInfo(){
        List<DeviceInfo> resList = new ArrayList<>();
        NmplLocalBaseStationInfoExample localBaseStationInfoExample = new NmplLocalBaseStationInfoExample();
        localBaseStationInfoExample.createCriteria().andIsExistEqualTo(IS_EXIST);
        List<NmplLocalBaseStationInfo> localBaseStationInfos = nmplLocalBaseStationInfoMapper.selectByExample(localBaseStationInfoExample);
        if (!CollectionUtils.isEmpty(localBaseStationInfos)){
            for (NmplLocalBaseStationInfo info : localBaseStationInfos){
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.setSystemId(info.getStationId());
                deviceInfo.setSystemType(info.getStationType());
                deviceInfo.setLanPort(info.getLanPort());
                resList.add(deviceInfo);
            }
        }

        NmplLocalDeviceInfoExample localDeviceInfoExample = new NmplLocalDeviceInfoExample();
        localDeviceInfoExample.createCriteria().andIsExistEqualTo(IS_EXIST);
        List<NmplLocalDeviceInfo> localDeviceInfos = nmplLocalDeviceInfoMapper.selectByExample(localDeviceInfoExample);
        if (!CollectionUtils.isEmpty(localDeviceInfos)){
            for (NmplLocalDeviceInfo info : localDeviceInfos){
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.setSystemId(info.getDeviceId());
                deviceInfo.setSystemType(info.getDeviceType());
                deviceInfo.setLanPort(info.getLanPort());
                resList.add(deviceInfo);
            }
        }
        return resList;
    }


    /**
     * 获取运行系统资源请求体
     * @param uploadTime
     * @param infos
     * @return
     */
    private List<SystemResourceVo> getSrList(Date uploadTime, List<DeviceInfo> infos){
        List<SystemResourceVo> resList = new ArrayList<>();
        for (DeviceInfo info : infos){
            // 根据端口获取进程的pid
            Integer pid = SystemUtils.getPID(info.getLanPort());
            // 判断系统端口所在进程是否存在
            if (pid.equals(-1)){
                continue;
            }else {
                // 获取对应应用的信息
                Map<String, String> systemInfo = SystemUtils.getSystemInfo(pid);
                SystemResourceVo resource = new SystemResourceVo();
                resource.setSystemId(info.getSystemId());
                resource.setSystemType(info.getSystemType());
                resource.setStartTime(DateUtils.formatStringToDate(systemInfo.get("startTime")));
                resource.setRunTime(Long.valueOf(systemInfo.get("runTime")));
                resource.setCpuPercent(systemInfo.get("cpuPercent"));
                resource.setMemoryPercent(systemInfo.get("memoryPercent"));
                resource.setUploadTime(uploadTime);
                resList.add(resource);
            }
        }
        return resList;
    }

}
