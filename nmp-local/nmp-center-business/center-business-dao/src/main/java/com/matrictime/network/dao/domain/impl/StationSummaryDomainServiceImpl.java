package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.StationSummaryDomainService;
import com.matrictime.network.dao.mapper.NmplStationSummaryMapper;
import com.matrictime.network.dao.model.NmplStationSummary;
import com.matrictime.network.dao.model.NmplStationSummaryExample;
import com.matrictime.network.request.StationSummaryRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
@Service
public class StationSummaryDomainServiceImpl implements StationSummaryDomainService {

    @Resource
    private NmplStationSummaryMapper stationSummaryMapper;

    @Override
    public int insertStationSummary(StationSummaryRequest stationSummaryRequest) {
        NmplStationSummary stationSummary = new NmplStationSummary();
        BeanUtils.copyProperties(stationSummaryRequest,stationSummary);
        return stationSummaryMapper.insertSelective(stationSummary);
    }

    @Override
    public int updateStationSummary(StationSummaryRequest stationSummaryRequest) {
        //构建更新条件
        NmplStationSummaryExample stationSummaryExample = new NmplStationSummaryExample();
        NmplStationSummaryExample.Criteria criteria = stationSummaryExample.createCriteria();
        criteria.andCompanyNetworkIdEqualTo(stationSummaryRequest.getCompanyNetworkId());
        criteria.andSumTypeEqualTo(stationSummaryRequest.getSumType());
        //构建更新体
        NmplStationSummary stationSummary = new NmplStationSummary();
        BeanUtils.copyProperties(stationSummaryRequest,stationSummary);
        return stationSummaryMapper.updateByExampleSelective(stationSummary,stationSummaryExample);
    }

    @Override
    public List<NmplStationSummary> selectStationSummary(StationSummaryRequest stationSummaryRequest) {
        //构造查询条件
        NmplStationSummaryExample stationSummaryExample = new NmplStationSummaryExample();
        NmplStationSummaryExample.Criteria criteria = stationSummaryExample.createCriteria();
        criteria.andCompanyNetworkIdEqualTo(stationSummaryRequest.getCompanyNetworkId());
        criteria.andSumTypeEqualTo(stationSummaryRequest.getSumType());
        List<NmplStationSummary> nmplStationSummaries = stationSummaryMapper.selectByExample(stationSummaryExample);
        return nmplStationSummaries;
    }


}
