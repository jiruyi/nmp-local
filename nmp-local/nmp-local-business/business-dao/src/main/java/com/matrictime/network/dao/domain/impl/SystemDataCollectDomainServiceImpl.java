package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.enums.DataCollectEnum;
import com.matrictime.network.base.enums.DeviceTypeEnum;
import com.matrictime.network.dao.domain.SystemDataCollectDomainService;
import com.matrictime.network.dao.mapper.NmplDataCollectMapper;
import com.matrictime.network.dao.mapper.extend.NmplSystemDataCollectExtMapper;
import com.matrictime.network.dao.model.NmplDataCollect;
import com.matrictime.network.dao.model.NmplDataCollectExample;
import com.matrictime.network.modelVo.BaseStationDataVo;
import com.matrictime.network.modelVo.BorderBaseStationDataVo;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.KeyCenterDataVo;
import com.matrictime.network.request.DataCollectReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private NmplDataCollectMapper nmplDataCollectMapper;

    @Resource
    private NmplSystemDataCollectExtMapper nmplSystemDataCollectExtMapper;

    @Override
    public BaseStationDataVo selectBaseStationData() {
        BaseStationDataVo baseStationDataVo;
        List<String> list = new ArrayList<>();
        //创建枚举list
        list.add(DataCollectEnum.COMM_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.COMM_LOAD_DOWN_FLOW.getCode());
        list.add( DataCollectEnum.FORWARD_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.FORWARD_LOAD_DOWN_FLOW.getCode());
        list.add(DataCollectEnum.KEY_DISTRIBUTE_UP_LOAD.getCode());list.add(DataCollectEnum.KEY_DISTRIBUTE_DOWN_LOAD.getCode());

        List<Double> dataList = getData(list, DeviceTypeEnum.BASE_STATION.getCode());
        baseStationDataVo = getBaseStationData(dataList);
        return baseStationDataVo;
    }

    @Override
    public BorderBaseStationDataVo selectBorderBaseStationData() {
        BorderBaseStationDataVo borderBaseStationDataVo;
        List<String> list = new ArrayList<>();
        //创建枚举list
        list.add(DataCollectEnum.COMM_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.COMM_LOAD_DOWN_FLOW.getCode());
        list.add( DataCollectEnum.FORWARD_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.FORWARD_LOAD_DOWN_FLOW.getCode());
        list.add(DataCollectEnum.KEY_DISTRIBUTE_UP_LOAD.getCode());list.add(DataCollectEnum.KEY_DISTRIBUTE_DOWN_LOAD.getCode());

        List<Double> dataList = getData(list, DeviceTypeEnum.BORDER_BASE_STATION.getCode());
        //数据转换
        borderBaseStationDataVo = getBorderBaseStationData(dataList);
        return borderBaseStationDataVo;
    }

    @Override
    public KeyCenterDataVo selectKeyCenterData() {
        KeyCenterDataVo keyCenterDataVo;
        List<String> list = new ArrayList<>();
        //创建枚举list
        list.add(DataCollectEnum.COMM_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.COMM_LOAD_DOWN_FLOW.getCode());
        list.add( DataCollectEnum.FORWARD_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.FORWARD_LOAD_DOWN_FLOW.getCode());
        list.add(DataCollectEnum.KEY_DISTRIBUTE_UP_LOAD.getCode());list.add(DataCollectEnum.KEY_DISTRIBUTE_DOWN_LOAD.getCode());

        List<Double> dataList = getData(list, DeviceTypeEnum.DISPENSER.getCode());
        //数据转换
        keyCenterDataVo = getKeyCenterData(dataList);
        return keyCenterDataVo;
    }

    @Transactional
    @Override
    public int insertSystemData(DataCollectReq dataCollectReq) {
        List<DataCollectVo> dataCollectVoList = dataCollectReq.getDataCollectVoList();
        for(DataCollectVo dataCollectVo: dataCollectVoList){
            NmplDataCollect nmplDataCollect = new NmplDataCollect();
            BeanUtils.copyProperties(dataCollectVo,nmplDataCollect);
            nmplDataCollectMapper.insertSelective(nmplDataCollect);
        }
        return 1;
    }

    /**
     * 获得该设备类型下的不通类型的流量值
     * @param dataCodeList
     * @param deviceType
     * @return
     */
    private List<Double> getData(List<String> dataCodeList,String deviceType){
        List<Double> list = new ArrayList<>();
        for(int i = 0;i< dataCodeList.size();i++){
            DataCollectReq dataCollectReq = new DataCollectReq();
            dataCollectReq.setDeviceType(deviceType);
            dataCollectReq.setDataItemCode(dataCodeList.get(i));
            List<NmplDataCollect> nmplDataCollects = nmplSystemDataCollectExtMapper.distinctSystemData(dataCollectReq);
            Double dataSum = getDataSum(nmplDataCollects);
            list.add(dataSum);
        }
        return list;
    }

    /**
     * 获取不通类型流量的总和
     * @param list
     * @return
     */
    private Double getDataSum(List<NmplDataCollect> list){
        Double dataSum = 0d;
        for (int i = 0;i< list.size();i++){
            NmplDataCollectExample nmplDataCollectExample = new NmplDataCollectExample();
            NmplDataCollectExample.Criteria criteria = nmplDataCollectExample.createCriteria();
            nmplDataCollectExample.setOrderByClause("upload_time desc");
            criteria.andDeviceIdEqualTo(list.get(i).getDeviceId());
            List<NmplDataCollect> nmplDataCollects = nmplDataCollectMapper.selectByExample(nmplDataCollectExample);
            dataSum = dataSum + Double.parseDouble(nmplDataCollects.get(0).getDataItemValue());
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
