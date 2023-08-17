package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.util.NetworkIdUtil;
import com.matrictime.network.dao.domain.DataCollectDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.extend.NmplDataCollectExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DataCollectEnum;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.modelVo.DataCollectVo;
import com.matrictime.network.modelVo.NmplDataCollectVo;
import com.matrictime.network.request.DataCollectRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author by wangqiang
 * @date 2023/8/16.
 */
@Service
public class DataCollectDomainServiceImpl implements DataCollectDomainService {

    @Resource
    private NmplDataCollectExtMapper dataCollectExtMapper;

    @Resource
    private NmplBaseStationInfoMapper baseStationInfoMapper;

    @Resource
    private NmplDeviceInfoMapper deviceInfoMapper;


    @Override
    public List<DataCollectVo> selectDataCollect() {
        List<NmplDataCollect> nmplDataCollects = dataCollectExtMapper.selectDataCollect();
        if(CollectionUtils.isEmpty(nmplDataCollects)){
            return null;
        }
        //查询小区Id唯一标识符
        String stationNetworkId = getStationNetworkId(nmplDataCollects.get(0));
        //切割小区唯一标识符
        String networkIdString = NetworkIdUtil.splitNetworkId(stationNetworkId);
        List<DataCollectVo> list = new ArrayList<>();
        Map<String, DataCollectEnum> map = DataCollectEnum.getMap();
        Set<String> strings = map.keySet();
        //按照流量类型求流量总和
        for(String code: strings){
            double sum = 0d;
            BigDecimal sumBig = new BigDecimal(Double.toString(sum));
            DataCollectVo dataCollectVo = new DataCollectVo();
            //根据流量类型进行过滤
            for(NmplDataCollect nmplDataCollect: nmplDataCollects){
                if(code.equals(nmplDataCollect.getDataItemCode())){
                    BigDecimal itemValue = new BigDecimal(nmplDataCollect.getDataItemValue());
                    sumBig = sumBig.add(itemValue);
                    dataCollectVo.setUploadTime(nmplDataCollect.getUploadTime());
                }
            }
            dataCollectVo.setDataItemCode(code);
            dataCollectVo.setSumNumber(String.valueOf(sumBig));
            dataCollectVo.setCompanyNetworkId(networkIdString);
            list.add(dataCollectVo);
        }
        return list;
    }

    /**
     * 获取小区唯一标识
     * @param nmplDataCollect
     * @return
     */
    public String getStationNetworkId(NmplDataCollect nmplDataCollect){
        String stationNetworkId = "";
        if(Integer.parseInt(nmplDataCollect.getDeviceType())
                < Integer.parseInt(DeviceTypeEnum.DEVICE_DISPENSER.getCode())){
            NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
            NmplBaseStationInfoExample.Criteria criteria = baseStationInfoExample.createCriteria();
            criteria.andStationIdEqualTo(nmplDataCollect.getDeviceId());
            List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(baseStationInfoExample);
            stationNetworkId = baseStationInfos.get(0).getStationNetworkId();
        }else {
            NmplDeviceInfoExample deviceInfoExample = new NmplDeviceInfoExample();
            NmplDeviceInfoExample.Criteria criteria = deviceInfoExample.createCriteria();
            criteria.andDeviceIdEqualTo((nmplDataCollect.getDeviceId()));
            List<NmplDeviceInfo> nmplDeviceInfoList = deviceInfoMapper.selectByExample(deviceInfoExample);
            stationNetworkId = nmplDeviceInfoList.get(0).getStationNetworkId();
        }

        return stationNetworkId;
    }

}
