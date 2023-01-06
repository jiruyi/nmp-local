package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.base.enums.StationTypeEnum;
import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.DeviceInfoVo;
import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.request.DataCollectReq;
import com.matrictime.network.request.MonitorReq;
import com.matrictime.network.response.DeviceResponse;
import com.matrictime.network.response.MonitorResp;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.DataCollectService;
import com.matrictime.network.util.PropertiesUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.concurrent.Future;

import static com.matrictime.network.base.constant.DataConstants.INTERNET_BROADBAND_LOAD_CODE;
import static com.matrictime.network.base.constant.DataConstants.INTRANET_BROADBAND_LOAD_CODE;

@Service
@Slf4j
@PropertySource(value = "classpath:/businessConfig.properties",encoding = "UTF-8")
public class DataCollectServiceImpl extends SystemBaseService implements DataCollectService {
    @Autowired
    DataCollectDomainService dataCollectDomainService;

    @Value("${data.delayTime}")
    private Integer delayTime;

    @Resource
    NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Resource
    NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Override
    public Result<PageInfo> queryByConditon(DataCollectReq dataCollectReq) {
        Result<PageInfo> result = null;
        try {
            //多条件查询
            PageInfo<DataCollectVo> pageResult = new PageInfo<>();
            pageResult = dataCollectDomainService.queryByConditions(dataCollectReq);
            result = buildResult(pageResult);
        }catch (SystemException e){
            log.error("统计数据查询异常：",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.error("统计数据查询异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Async("taskExecutor")
    @Override
    @Transactional
    public Future<Result> save(DataCollectReq dataCollectReq) {
        Result<Integer> result;
        try {
            if(dataCollectReq.getDataCollectVoList()!=null){
                List<DataCollectVo> dataCollectVoLoadList = new ArrayList<>();
                Map<String,String> map = PropertiesUtil.paramMap;
                for (DataCollectVo dataCollectVo : dataCollectReq.getDataCollectVoList()) {
                    String name = "data."+dataCollectVo.getDataItemCode()+".name";
                    String unit = "data."+dataCollectVo.getDataItemCode()+".unit";
                    dataCollectVo.setDataItemName(map.get(name));
                    dataCollectVo.setUnit(map.get(unit));
                    if (INTRANET_BROADBAND_LOAD_CODE.equals(dataCollectVo.getDataItemCode()) || INTERNET_BROADBAND_LOAD_CODE.equals(dataCollectVo.getDataItemCode())) {
                        dataCollectVoLoadList.add(dataCollectVo);
                    }
                }
                dataCollectReq.setDataCollectVoLoadList(dataCollectVoLoadList);
            }
            result = buildResult(dataCollectDomainService.save(dataCollectReq));
        }catch (SystemException e){
            log.info("存储统计数据异常",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.info("存储统计数据异常",e.getMessage());
            result = failResult("");
        }
        return new AsyncResult<>(result);
    }

    @Override
    public Result monitorData(MonitorReq monitorReq) {
        Result result= null;
        try {
            //1.根据小区id和当前时间找到最近的统计数据
            monitorReq.setCurrentTime(LocalTimeToUdate(LocalTime.now().minusMinutes(15)));
            List<NmplDataCollect> dataCollectList = dataCollectDomainService.queryMonitorData(monitorReq);
            //2.根据设备类型分组
            Integer userNumber = 0;
            double totalBandwidth=0.0;
            double dispenserSecretKey=0.0;
            double generatorSecretKey=0.0;
            double cacheSecretKey=0.0;
            String totalBandwidthUnit;

            for (NmplDataCollect nmplDataCollect : dataCollectList) {
                BigDecimal bigDecimal = new BigDecimal(nmplDataCollect.getDataItemValue());
                double value = 0.0;
                if(nmplDataCollect.getDataItemCode().equals("10006")){
                    value = bigDecimal.divide(new BigDecimal(1000.0*1000.0),2,BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                if(nmplDataCollect.getDataItemCode().equals("10007")){
                    value = bigDecimal.divide(new BigDecimal(1024.0*1024.0),2,BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                switch (nmplDataCollect.getDeviceType()){
                    case "00":
                        if(nmplDataCollect.getDataItemCode().equals("10000")){
                            userNumber+=Integer.valueOf(nmplDataCollect.getDataItemValue());
                        }
                        if(nmplDataCollect.getDataItemCode().equals("10006")){
                            totalBandwidth+=value;
                        }
                        break;
                    case "11":
                        if(nmplDataCollect.getDataItemCode().equals("10007")){
                            dispenserSecretKey+=value;
                        }
                        if(nmplDataCollect.getDataItemCode().equals("10006")){
                            totalBandwidth+=value;
                        }
                        break;
                    case "12":
                        if(nmplDataCollect.getDataItemCode().equals("10007")){
                            generatorSecretKey+=value;
                        }
                        if(nmplDataCollect.getDataItemCode().equals("10006")){
                            totalBandwidth+=value;
                        }
                        break;
                    case "13":
                        if(nmplDataCollect.getDataItemCode().equals("10007")){
                            cacheSecretKey+=value;
                        }
                        if(nmplDataCollect.getDataItemCode().equals("10006")){
                            totalBandwidth+=value;
                        }
                        break;
                    default:
                }
            }
            //统计用户数 表pc_data 去重
            userNumber = dataCollectDomainService.countDeviceNumber(monitorReq);

            //统计带宽 表 data_collect 累加 totalBandwidth  code=10006
            totalBandwidth = dataCollectDomainService.sumDataItemValue(monitorReq);

            //cacheSecretKey=dispenserSecretKey+random(1000)
            Random random = new Random(1000);
            double v = random.nextDouble();
            generatorSecretKey = dispenserSecretKey + v;

            //统一单位
            String [] totalBandwidthStr = dataChangeWithoutUnit(totalBandwidth,1);

            String dispenserSecretKeyStr = dataChange(dispenserSecretKey,0);

            String generatorSecretKeyStr = dataChange(generatorSecretKey,0);

            String cacheSecretKeyStr = dataChange(cacheSecretKey,0);


            MonitorResp monitorResp = new MonitorResp
                    (userNumber,totalBandwidthStr[0],totalBandwidthStr[1],dispenserSecretKeyStr,generatorSecretKeyStr,
                            cacheSecretKeyStr,new ArrayList<>());
            result = buildResult(monitorResp);
        } catch (SystemException e) {
            log.info("查询监控数据异常",e.getMessage());
            result = failResult(e);
        } catch (Exception e) {
            log.info("查询监控数据异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * @param data
     * 计算出来的数据
     * @param countFlag
     * 判断传过来的是否是基站带宽总量
     * 1 代表是基站带宽总量,0 代表其他
     * @return
     */
    private String dataChange(Double data, int countFlag){
        //转换成MB
        if(countFlag == 1){
            data = data/(1024.0*1024.0);
        }
        //转换成GB
        if(data >= 999.0){
            data = data/1024.0;
            //转换成TB
            if(data >= 999.0){
                data = data/1024.0;
                return String.format("%.2f", data) + "TB";
            }
            return String.format("%.2f", data) + "GB";
        }
        return String.format("%.2f", data) + "MB";
    }

    private String[] dataChangeWithoutUnit(Double data, int countFlag){
        String [] strings = new String[2];
        //转换成MB
        if(countFlag == 1){
            data = data/(1024.0*1024.0);
        }
        //转换成GB
        if(data >= 999.0){
            data = data/1024.0;
            //转换成TB
            if(data >= 999.0){
                data = data/1024.0;
                strings[0] = String.format("%.2f", data);
                strings[1] = "TB";
                return strings;
            }
            strings[0] = String.format("%.2f", data);
            strings[1] = "GB";
            return strings;
        }
        strings[0] = String.format("%.2f", data);
        strings[1] = "MB";
        return strings;
    }

    @Override
    public Result monitorDataTopTen(MonitorReq monitorReq) {
        Result result= null;
        try {
            monitorReq.setCurrentTime(LocalTimeToUdate(LocalTime.now().minusMinutes(delayTime)));
            result = buildResult(dataCollectDomainService.queryTopTen(monitorReq));
        }catch (SystemException e){
            log.info("查询排行前10数据异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("查询排行前10数据异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    public Date LocalTimeToUdate(LocalTime localTime) {
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }


    @Override
    public Result selectAllDevice(DataCollectReq dataCollectReq) {
        Result result =null;
        DeviceResponse deviceResponse = new DeviceResponse();
        List<DeviceInfoVo>deviceInfoVos = new ArrayList<>();
        try {
            if(dataCollectReq.getDeviceType().equals(StationTypeEnum.BASE.getCode())){
                NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
                nmplBaseStationInfoExample.createCriteria().andIsExistEqualTo(true);
                List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
                for (NmplBaseStationInfo nmplBaseStationInfo : nmplBaseStationInfos) {
                    String[]strings = nmplBaseStationInfo.getStationNetworkId().split("-");
                    nmplBaseStationInfo.setStationNetworkId(strings[strings.length-1]);
                    DeviceInfoVo deviceInfoVo = new DeviceInfoVo();
                    BeanUtils.copyProperties(nmplBaseStationInfo,deviceInfoVo);
                    deviceInfoVo.setDeviceName(nmplBaseStationInfo.getStationName());
                    deviceInfoVo.setDeviceId(nmplBaseStationInfo.getStationId());
                    deviceInfoVos.add(deviceInfoVo);
                }
            }else {
//                Map<String,String> map = new HashMap<>();
//                map.put("02","01");
//                map.put("03","02");
//                map.put("04","03");
                NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
                nmplDeviceInfoExample.createCriteria().andIsExistEqualTo(true).andDeviceTypeEqualTo(dataCollectReq.getDeviceType());
                List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);
                for (NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos) {
                    String[]strings = nmplDeviceInfo.getStationNetworkId().split("-");
                    nmplDeviceInfo.setStationNetworkId(strings[strings.length-1]);
                    DeviceInfoVo deviceInfoVo = new DeviceInfoVo();
                    BeanUtils.copyProperties(nmplDeviceInfo,deviceInfoVo);
                    deviceInfoVos.add(deviceInfoVo);
                }
            }
            deviceResponse.setDeviceInfoVos(deviceInfoVos);
            result = buildResult(deviceResponse);
        }catch (Exception e){
            log.info("查询设备异常",e.getMessage());
            result =  failResult("");
        }
       return result;
    }
}
