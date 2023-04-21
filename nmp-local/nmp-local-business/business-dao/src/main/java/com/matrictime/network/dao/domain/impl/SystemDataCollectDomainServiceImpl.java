package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.enums.DataCollectEnum;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        BaseStationDataVo baseStationDataVo = new BaseStationDataVo();
        List<String> list = new ArrayList<>();
        //创建枚举list
        list.add(DataCollectEnum.COMM_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.COMM_LOAD_DOWN_FLOW.getCode());
        list.add( DataCollectEnum.FORWARD_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.FORWARD_LOAD_DOWN_FLOW.getCode());
        list.add(DataCollectEnum.KEY_DISTRIBUTE_UP_LOAD.getCode());list.add(DataCollectEnum.KEY_DISTRIBUTE_DOWN_LOAD.getCode());

        List<Double> dataList = getData(list, "01");
        baseStationDataVo.setCommunicationsLoadUp(dataList.get(0) / 1800);
        baseStationDataVo.setCommunicationsLoadDown(dataList.get(1) / 1800);
        baseStationDataVo.setForwardingPayloadUp(dataList.get(2) / 1800);
        baseStationDataVo.setForwardingPayloadDown(dataList.get(3) / 1800);
        baseStationDataVo.setKeyRelayPayloadUp(dataList.get(4) / 1800);
        baseStationDataVo.setKeyRelayPayloadDown(dataList.get(5) / 1800);
        return baseStationDataVo;
    }

    @Override
    public BorderBaseStationDataVo selectBorderBaseStationData() {
        BorderBaseStationDataVo borderBaseStationDataVo = new BorderBaseStationDataVo();
        List<String> list = new ArrayList<>();
        //创建枚举list
        list.add(DataCollectEnum.COMM_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.COMM_LOAD_DOWN_FLOW.getCode());
        list.add( DataCollectEnum.FORWARD_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.FORWARD_LOAD_DOWN_FLOW.getCode());
        list.add(DataCollectEnum.KEY_DISTRIBUTE_UP_LOAD.getCode());list.add(DataCollectEnum.KEY_DISTRIBUTE_DOWN_LOAD.getCode());

        List<Double> dataList = getData(list, "02");
        borderBaseStationDataVo.setCommunicationsLoadUp(dataList.get(0) / 1800);
        borderBaseStationDataVo.setCommunicationsLoadDown(dataList.get(1) / 1800);
        borderBaseStationDataVo.setForwardingPayloadUp(dataList.get(2) / 1800);
        borderBaseStationDataVo.setForwardingPayloadDown(dataList.get(3) / 1800);
        borderBaseStationDataVo.setKeyRelayPayloadUp(dataList.get(4) / 1800);
        borderBaseStationDataVo.setKeyRelayPayloadDown(dataList.get(5) / 1800);
        return borderBaseStationDataVo;
    }

    @Override
    public KeyCenterDataVo selectKeyCenterData() {
        KeyCenterDataVo keyCenterDataVo = new KeyCenterDataVo();
        List<String> list = new ArrayList<>();
        //创建枚举list
        list.add(DataCollectEnum.COMM_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.COMM_LOAD_DOWN_FLOW.getCode());
        list.add( DataCollectEnum.FORWARD_LOAD_UP_FLOW.getCode());list.add(DataCollectEnum.FORWARD_LOAD_DOWN_FLOW.getCode());
        list.add(DataCollectEnum.KEY_DISTRIBUTE_UP_LOAD.getCode());list.add(DataCollectEnum.KEY_DISTRIBUTE_DOWN_LOAD.getCode());

        List<Double> dataList = getData(list, "11");
        keyCenterDataVo.setCommunicationsLoadUp(dataList.get(0) / 1800);
        keyCenterDataVo.setCommunicationsLoadDown(dataList.get(1) / 1800);
        keyCenterDataVo.setForwardingPayloadUp(dataList.get(2) / 1800);
        keyCenterDataVo.setForwardingPayloadDown(dataList.get(3) / 1800);
        keyCenterDataVo.setKeyDistributionPayloadUp(dataList.get(4) / 1800);
        keyCenterDataVo.setKeyDistributionPayloadDown(dataList.get(5) / 1800);
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
}
