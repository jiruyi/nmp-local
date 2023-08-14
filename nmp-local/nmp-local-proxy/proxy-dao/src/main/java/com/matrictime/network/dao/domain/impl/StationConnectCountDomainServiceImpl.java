package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.StationConnectCountDomainService;
import com.matrictime.network.dao.mapper.NmplStationConnectCountMapper;
import com.matrictime.network.dao.model.NmplStationConnectCount;
import com.matrictime.network.dao.model.NmplStationConnectCountExample;
import com.matrictime.network.modelVo.StationConnectCountVo;
import com.matrictime.network.response.StationConnectCountResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/9.
 */
@Service
public class StationConnectCountDomainServiceImpl implements StationConnectCountDomainService {

    @Resource
    NmplStationConnectCountMapper stationConnectCountMapper;

    @Override
    public StationConnectCountResponse selectStationConnectCount() {
        StationConnectCountResponse stationConnectCountResponse = new StationConnectCountResponse();
        NmplStationConnectCountExample stationConnectCountExample = new NmplStationConnectCountExample();
        List<StationConnectCountVo> list = new ArrayList<>();
        List<NmplStationConnectCount> nmplStationConnectCounts = stationConnectCountMapper.selectByExample(stationConnectCountExample);
        for(NmplStationConnectCount nmplStationConnectCount: nmplStationConnectCounts){
            StationConnectCountVo stationConnectCountVo = new StationConnectCountVo();
            BeanUtils.copyProperties(nmplStationConnectCount,stationConnectCountVo);
            list.add(stationConnectCountVo);
        }
        stationConnectCountResponse.setList(list);
        return stationConnectCountResponse;
    }
}
