package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.TerminalDataDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplTerminalDataMapper;
import com.matrictime.network.dao.mapper.extend.NmplTerminalDataExtMapper;
import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import com.matrictime.network.dao.model.NmplTerminalData;
import com.matrictime.network.dao.model.NmplTerminalDataExample;
import com.matrictime.network.modelVo.TerminalDataVo;
import com.matrictime.network.request.TerminalDataRequest;
import com.matrictime.network.response.TerminalDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/19.
 */
@Service
@Slf4j
public class TerminalDataDomainServiceImpl implements TerminalDataDomainService {

    @Resource
    private NmplTerminalDataMapper terminalDataMapper;

    @Resource
    private NmplBaseStationInfoMapper baseStationInfoMapper;

    @Resource
    private NmplTerminalDataExtMapper terminalDataExtMapper;

    @Override
    public TerminalDataResponse selectTerminalData(TerminalDataRequest terminalDataRequest) {
        TerminalDataResponse terminalDataResponse = new TerminalDataResponse();
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = baseStationInfoExample.createCriteria();
        criteria.andLanIpEqualTo(terminalDataRequest.getParenIp());
        List<NmplBaseStationInfo> baseStationList = baseStationInfoMapper.selectByExample(baseStationInfoExample);
        //查询终端流量列表
        List<TerminalDataVo> list = getDataList(baseStationList);
        terminalDataResponse.setList(list);
        return terminalDataResponse;
    }

    /**
     * 查询去重后的终端设备Id
     * @param list
     * @return
     */
    private List<TerminalDataVo> getDataList(List<NmplBaseStationInfo> list){
        List<TerminalDataVo> terminalDataVoList = new ArrayList<>();
        for(int stationIndex = 0;stationIndex< list.size();stationIndex++){
            TerminalDataRequest terminalDataRequest = new TerminalDataRequest();
            terminalDataRequest.setParentId(list.get(stationIndex).getStationId());
            terminalDataRequest.setDataType("01");
            List<NmplTerminalData> terminalDataList = terminalDataExtMapper.distinctTerminalData(terminalDataRequest);
            for(int i = 0;i< terminalDataList.size();i++){
                TerminalDataVo terminalDataVo = getTerminalDataInfo(terminalDataList.get(i));
                terminalDataVoList.add(terminalDataVo);
            }
        }
        return terminalDataVoList;
    }

    /**
     * 获取终端数据信息
     * @param terminalData
     * @return
     */
    private TerminalDataVo getTerminalDataInfo(NmplTerminalData terminalData){
        NmplTerminalDataExample terminalDataExample = new NmplTerminalDataExample();
        NmplTerminalDataExample.Criteria criteria = terminalDataExample.createCriteria();
        terminalDataExample.setOrderByClause("upload_time desc");
        criteria.andTerminalNetworkIdEqualTo(terminalData.getTerminalNetworkId());
        List<NmplTerminalData> terminalDataList = terminalDataMapper.selectByExample(terminalDataExample);

        TerminalDataVo terminalDataVo = new TerminalDataVo();
        BeanUtils.copyProperties(terminalDataList.get(0),terminalDataVo);
        return terminalDataVo;
    }
}
