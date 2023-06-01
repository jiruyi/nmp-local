package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.enums.DataCollectEnum;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.dao.domain.SystemDataCollectDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDataCollectMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplDataCollectExtMapper;
import com.matrictime.network.dao.mapper.extend.NmplDeviceExtMapper;
import com.matrictime.network.dao.mapper.extend.NmplSystemDataCollectExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.request.DataCollectReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.base.constant.DataConstants.RESERVE_DIGITS;

/**
 * @author by wangqiang
 * @date 2023/4/20.
 */
@Service
@Slf4j
public class SystemDataCollectDomainServiceImpl implements SystemDataCollectDomainService {

    @Resource
    private NmplSystemDataCollectExtMapper nmplSystemDataCollectExtMapper;

    @Resource
    private NmplDataCollectExtMapper dataCollectExtMapper;

    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Resource
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    /**
     * 查询接入基站流量数据
     * @param dataCollectReq
     * @return
     */
    @Override
    public BaseStationDataVo selectBaseStationData(DataCollectReq dataCollectReq) {
        BaseStationDataVo baseStationDataVo;
        List<String> list = new ArrayList<>();
        //创建枚举list
        list.add(DataCollectEnum.COMM_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.COMM_LOAD_DOWN_FLOW.getCode());
        list.add( DataCollectEnum.FORWARD_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.FORWARD_LOAD_DOWN_FLOW.getCode());
        list.add(DataCollectEnum.KEY_RELAY_UP_LOAD.getCode());list.add(DataCollectEnum.KEY_RELAY_DOWN_LOAD.getCode());
        dataCollectReq.setDeviceType(DeviceTypeEnum.BASE_STATION.getCode());
        List<Double> dataList = getData(list, dataCollectReq);
        baseStationDataVo = getBaseStationData(dataList);
        return baseStationDataVo;
    }

    /**
     * 查询边界基站流量数据
     * @param dataCollectReq
     * @return
     */
    @Override
    public BorderBaseStationDataVo selectBorderBaseStationData(DataCollectReq dataCollectReq) {
        BorderBaseStationDataVo borderBaseStationDataVo;
        List<String> list = new ArrayList<>();
        //创建枚举list
        list.add(DataCollectEnum.COMM_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.COMM_LOAD_DOWN_FLOW.getCode());
        list.add( DataCollectEnum.FORWARD_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.FORWARD_LOAD_DOWN_FLOW.getCode());
        list.add(DataCollectEnum.KEY_RELAY_UP_LOAD.getCode());list.add(DataCollectEnum.KEY_RELAY_DOWN_LOAD.getCode());
        dataCollectReq.setDeviceType(DeviceTypeEnum.BORDER_BASE_STATION.getCode());
        List<Double> dataList = getData(list, dataCollectReq);
        //数据转换
        borderBaseStationDataVo = getBorderBaseStationData(dataList);
        return borderBaseStationDataVo;
    }

    /**
     * 查询密钥中心流量数据
     * @param dataCollectReq
     * @return
     */
    @Override
    public KeyCenterDataVo selectKeyCenterData(DataCollectReq dataCollectReq) {
        KeyCenterDataVo keyCenterDataVo;
        List<String> list = new ArrayList<>();
        //创建枚举list
        list.add(DataCollectEnum.COMM_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.COMM_LOAD_DOWN_FLOW.getCode());
        list.add( DataCollectEnum.FORWARD_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.FORWARD_LOAD_DOWN_FLOW.getCode());
        list.add(DataCollectEnum.KEY_DISTRIBUTE_UP_LOAD.getCode());list.add(DataCollectEnum.KEY_DISTRIBUTE_DOWN_LOAD.getCode());
        dataCollectReq.setDeviceType(DeviceTypeEnum.DISPENSER.getCode());
        List<Double> dataList = getDeviceData(list, dataCollectReq);
        //数据转换
        keyCenterDataVo = getKeyCenterData(dataList);
        return keyCenterDataVo;
    }

    /**
     * 业务数据收集
     * @param dataCollectVoList
     * @return
     */
    @Override
    public int insertSystemData(List<DataCollectVo> dataCollectVoList) {
        Map<String,String> deviceMap = new HashMap<>();
        List<DataCollectVo> dataCollectVos = new ArrayList<>();
        //查询基站列表
        NmplBaseStationInfoExample nmplBaseStationInfoExample = new NmplBaseStationInfoExample();
        nmplBaseStationInfoExample.createCriteria().andIsExistEqualTo(true);
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(nmplBaseStationInfoExample);
        //查询设备列表
        NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
        nmplDeviceInfoExample.createCriteria().andIsExistEqualTo(true);
        List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);
        //创建基站列表映射
        for (NmplBaseStationInfo nmplBaseStationInfo : nmplBaseStationInfos) {
            deviceMap.put(nmplBaseStationInfo.getStationId(),nmplBaseStationInfo.getStationName());
        }
        //创建设备列表映射
        for (NmplDeviceInfo nmplDeviceInfo : nmplDeviceInfos) {
            deviceMap.put(nmplDeviceInfo.getDeviceId(),nmplDeviceInfo.getDeviceName());
        }
        //根据映射拼和传过来的数据拼接入库数据
        for (DataCollectVo dataCollectVo : dataCollectVoList) {
            String name = DataCollectEnum.getMap().get(dataCollectVo.getDataItemCode()).getConditionDesc();
            String unit = DataCollectEnum.getMap().get(dataCollectVo.getDataItemCode()).getUnit();
            if (deviceMap.get(dataCollectVo.getDeviceId()) != null) {
                dataCollectVo.setDeviceName(deviceMap.get(dataCollectVo.getDeviceId()));
            }
            dataCollectVo.setDataItemName(name);
            dataCollectVo.setUnit(unit);
            dataCollectVos.add(dataCollectVo);
        }
        return dataCollectExtMapper.batchInsertDataCollect(dataCollectVos);
    }

    /**
     * 获取该类型设备下的不通流量值
     * @param dataCodeList
     * @param dataCollectReq
     * @return
     */
    private List<Double> getDeviceData(List<String> dataCodeList,DataCollectReq dataCollectReq){
        List<Double> list = new ArrayList<>();
        for(int i = 0;i< dataCodeList.size();i++){
            Double dataSum = 0d;
            DataCollectReq req = new DataCollectReq();
            req.setDeviceType(dataCollectReq.getDeviceType());
            req.setDataItemCode(dataCodeList.get(i));
            req.setRelationOperatorId(dataCollectReq.getRelationOperatorId());
            List<DataCollectVo> dataCollectVos = nmplSystemDataCollectExtMapper.distinctSystemDeviceData(req);
            if(!CollectionUtils.isEmpty(dataCollectVos)){
                dataSum = getDeviceDataSum(dataCollectVos,dataCodeList.get(i));
            }
            list.add(dataSum);
        }
        return list;
    }

    /**
     * 获得该基站类型下的不通类型的流量值
     * @param dataCodeList
     * @param dataCollectReq
     * @return
     */
    private List<Double> getData(List<String> dataCodeList,DataCollectReq dataCollectReq){
        List<Double> list = new ArrayList<>();
        for(int i = 0;i< dataCodeList.size();i++){
            Double dataSum = 0d;
            DataCollectReq req = new DataCollectReq();
            req.setDeviceType(dataCollectReq.getDeviceType());
            req.setDataItemCode(dataCodeList.get(i));
            req.setRelationOperatorId(dataCollectReq.getRelationOperatorId());
            List<StationVo> stationVos = nmplSystemDataCollectExtMapper.distinctSystemData(req);
            if(!CollectionUtils.isEmpty(stationVos)){
                dataSum = getDataSum(stationVos,dataCodeList.get(i));
            }
            list.add(dataSum);
        }
        return list;
    }

    /**
     * 获取不通类型流量的总和
     * @param list
     * @return
     */
    private Double getDataSum(List<StationVo> list,String code){
        List<String> deviceIdList = new ArrayList<>();
        Map dataMap = new HashMap();
        for(StationVo stationVo: list){
            deviceIdList.add(stationVo.getDeviceId());
        }
        dataMap.put("idList",deviceIdList);
        dataMap.put("code",code);
        List<DataCollectVo> dataCollectVos = nmplSystemDataCollectExtMapper.selectDataItemValue(dataMap);
        Double dataSum = 0d;
        for (int i = 0;i< dataCollectVos.size();i++){
            dataSum = dataSum + Double.parseDouble(dataCollectVos.get(i).getDataItemValue());
        }
        return dataSum;
    }

    /**
     * 获取密钥中心流量总和
     * @param list
     * @return
     */
    private Double getDeviceDataSum(List<DataCollectVo> list,String code){
        List<String> deviceIdList = new ArrayList<>();
        HashMap dataMap = new HashMap();
        for(DataCollectVo dataCollectVo: list){
            deviceIdList.add(dataCollectVo.getDeviceId());
        }
        dataMap.put("idList",deviceIdList);
        dataMap.put("code",code);
        List<DataCollectVo> dataCollectVos = nmplSystemDataCollectExtMapper.selectDataItemValue(dataMap);
        Double dataSum = 0d;
        for (int i = 0;i< dataCollectVos.size();i++){
            dataSum = dataSum + Double.parseDouble(dataCollectVos.get(i).getDataItemValue());
        }
        return dataSum;
    }



    private BaseStationDataVo getBaseStationData( List<Double> dataList){
        BaseStationDataVo baseStationDataVo = new BaseStationDataVo();
        //换算数值
        if(!ObjectUtils.isEmpty(dataList.get(0))){
            BigDecimal communicationsLoadUp = new BigDecimal(dataList.get(0));
            baseStationDataVo.setCommunicationsLoadUp(communicationsLoadUp.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(!ObjectUtils.isEmpty(dataList.get(1))){
            BigDecimal communicationsLoadDown = new BigDecimal(dataList.get(1));
            baseStationDataVo.setCommunicationsLoadDown(communicationsLoadDown.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(!ObjectUtils.isEmpty(dataList.get(2))){
            BigDecimal forwardingPayloadUp = new BigDecimal(dataList.get(2));
            baseStationDataVo.setForwardingPayloadUp(forwardingPayloadUp.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(!ObjectUtils.isEmpty(dataList.get(3))){
            BigDecimal forwardingPayloadDown = new BigDecimal(dataList.get(3));
            baseStationDataVo.setForwardingPayloadDown(forwardingPayloadDown.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(!ObjectUtils.isEmpty(dataList.get(4))){
            BigDecimal keyRelayPayloadUp = new BigDecimal(dataList.get(4));
            baseStationDataVo.setKeyRelayPayloadUp(keyRelayPayloadUp.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(!ObjectUtils.isEmpty(dataList.get(5))){
            BigDecimal keyRelayPayloadDown = new BigDecimal(dataList.get(5));
            baseStationDataVo.setKeyRelayPayloadDown(keyRelayPayloadDown.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        return baseStationDataVo;
    }

    private BorderBaseStationDataVo getBorderBaseStationData(List<Double> dataList){
        BorderBaseStationDataVo borderBaseStationDataVo = new BorderBaseStationDataVo();
        if(!ObjectUtils.isEmpty(dataList.get(0))){
            BigDecimal communicationsLoadUp = new BigDecimal(dataList.get(0));
            borderBaseStationDataVo.setCommunicationsLoadUp(communicationsLoadUp.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(!ObjectUtils.isEmpty(dataList.get(1))){
            BigDecimal communicationsLoadDown = new BigDecimal(dataList.get(1));
            borderBaseStationDataVo.setCommunicationsLoadDown(communicationsLoadDown.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(!ObjectUtils.isEmpty(dataList.get(2))){
            BigDecimal forwardingPayloadUp = new BigDecimal(dataList.get(2));
            borderBaseStationDataVo.setForwardingPayloadUp(forwardingPayloadUp.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(!ObjectUtils.isEmpty(dataList.get(3))){
            BigDecimal forwardingPayloadDown = new BigDecimal(dataList.get(3));
            borderBaseStationDataVo.setForwardingPayloadDown(forwardingPayloadDown.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(!ObjectUtils.isEmpty(dataList.get(4))){
            BigDecimal keyRelayPayloadUp = new BigDecimal(dataList.get(4));
            borderBaseStationDataVo.setKeyRelayPayloadUp(keyRelayPayloadUp.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(!ObjectUtils.isEmpty(dataList.get(5))){
            BigDecimal keyRelayPayloadDown = new BigDecimal(dataList.get(5));
            borderBaseStationDataVo.setKeyRelayPayloadDown(keyRelayPayloadDown.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        return borderBaseStationDataVo;
    }

    private KeyCenterDataVo getKeyCenterData(List<Double> dataList){
        KeyCenterDataVo keyCenterDataVo = new KeyCenterDataVo();
        if(!ObjectUtils.isEmpty(dataList.get(0))){
            BigDecimal communicationsLoadUp = new BigDecimal(dataList.get(0));
            keyCenterDataVo.setCommunicationsLoadUp(communicationsLoadUp.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        if(!ObjectUtils.isEmpty(dataList.get(1))){
            BigDecimal communicationsLoadDown = new BigDecimal(dataList.get(1));
            keyCenterDataVo.setCommunicationsLoadDown(communicationsLoadDown.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        if(!ObjectUtils.isEmpty(dataList.get(2))){
            BigDecimal forwardingPayloadUp = new BigDecimal(dataList.get(2));
            keyCenterDataVo.setForwardingPayloadUp(forwardingPayloadUp.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        if(!ObjectUtils.isEmpty(dataList.get(3))){
            BigDecimal forwardingPayloadDown = new BigDecimal(dataList.get(3));
            keyCenterDataVo.setForwardingPayloadDown(forwardingPayloadDown.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        if(!ObjectUtils.isEmpty(dataList.get(4))){
            BigDecimal keyDistributionPayloadUp = new BigDecimal(dataList.get(4));
            keyCenterDataVo.setKeyDistributionPayloadUp(keyDistributionPayloadUp.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }

        if(!ObjectUtils.isEmpty(dataList.get(5))){
            BigDecimal keyDistributionPayloadDown = new BigDecimal(dataList.get(5));
            keyCenterDataVo.setKeyDistributionPayloadDown(keyDistributionPayloadDown.divide(
                    new BigDecimal(BASE_NUMBER*BASE_NUMBER*HALF_HOUR_SECONDS/BYTE_TO_BPS),
                    RESERVE_DIGITS,BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        return keyCenterDataVo;
    }

}
