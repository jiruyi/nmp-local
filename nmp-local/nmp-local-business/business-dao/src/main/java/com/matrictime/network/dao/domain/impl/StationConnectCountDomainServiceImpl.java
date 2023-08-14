package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.StationConnectCountDomainService;
import com.matrictime.network.dao.mapper.NmplStationConnectCountMapper;
import com.matrictime.network.dao.mapper.extend.NmplStationConnectCountExtMapper;
import com.matrictime.network.dao.model.NmplStationConnectCount;
import com.matrictime.network.modelVo.StationConnectCountVo;
import com.matrictime.network.request.StationConnectCountRequest;
import com.matrictime.network.response.StationConnectCountResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/8.
 */
@Service
public class StationConnectCountDomainServiceImpl implements StationConnectCountDomainService {

    @Resource
    private NmplStationConnectCountExtMapper stationConnectCountExtMapper;

    @Resource
    private NmplStationConnectCountMapper stationConnectCountMapper;


    @Override
    public int selectConnectCount(StationConnectCountRequest stationConnectCountRequest) {
        List<NmplStationConnectCount> nmplStationConnectCounts = stationConnectCountExtMapper.selectConnectCount(stationConnectCountRequest);
        if(CollectionUtils.isEmpty(nmplStationConnectCounts)){
            return 0;
        }
        int sum = 0;
        for(NmplStationConnectCount stationConnectCount: nmplStationConnectCounts){
            if(StringUtils.isEmpty(stationConnectCount.getCurrentConnectCount())){
                stationConnectCount.setCurrentConnectCount("0");
            }
            sum = sum + Integer.parseInt(stationConnectCount.getCurrentConnectCount());
        }
        return sum;
    }

    @Transactional
    @Override
    public int insertConnectCount(StationConnectCountResponse stationConnectCountResponse) {
        int i = 0;
        List<StationConnectCountVo> list = stationConnectCountResponse.getList();
        for(StationConnectCountVo stationConnectCountVo: list){
            NmplStationConnectCount stationConnectCount = new NmplStationConnectCount();
            BeanUtils.copyProperties(stationConnectCountVo,stationConnectCount);
            i = stationConnectCountMapper.insertSelective(stationConnectCount);
        }
        return i;
    }
}
