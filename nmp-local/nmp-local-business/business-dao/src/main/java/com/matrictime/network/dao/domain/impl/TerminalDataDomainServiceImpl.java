package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.enums.TerminalDataEnum;
import com.matrictime.network.dao.domain.TerminalDataDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplTerminalDataMapper;
import com.matrictime.network.dao.mapper.extend.NmplTerminalDataExtMapper;
import com.matrictime.network.dao.model.NmplBaseStationInfo;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import com.matrictime.network.dao.model.NmplTerminalData;
import com.matrictime.network.dao.model.NmplTerminalDataExample;
import com.matrictime.network.modelVo.TerminalDataVo;
import com.matrictime.network.request.TerminalDataListRequest;
import com.matrictime.network.request.TerminalDataRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.TerminalDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;

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

    /**
     * 终端流量列表查询
     * @param terminalDataRequest
     * @return
     */
    @Override
    public PageInfo<TerminalDataVo> selectTerminalData(TerminalDataRequest terminalDataRequest) {
        NmplBaseStationInfoExample baseStationInfoExample = new NmplBaseStationInfoExample();
        NmplBaseStationInfoExample.Criteria criteria = baseStationInfoExample.createCriteria();
        criteria.andLanIpEqualTo(terminalDataRequest.getParenIp());
        //查询基站列表是否有该基站
        List<NmplBaseStationInfo> baseStationInfos = baseStationInfoMapper.selectByExample(baseStationInfoExample);
        if(CollectionUtils.isEmpty(baseStationInfos)){
            throw new RuntimeException("设备列表中不存在该设备");
        }
        terminalDataRequest.setParentId(baseStationInfos.get(0).getStationId());
        terminalDataRequest.setDataType(TerminalDataEnum.RESIDUE.getCode());
        //进行分页查询
        Page page = PageHelper.startPage(terminalDataRequest.getPageNo(),terminalDataRequest.getPageSize());
        List<TerminalDataVo> list = terminalDataExtMapper.distinctTerminalData(terminalDataRequest);
        PageInfo<TerminalDataVo> pageInfo = new PageInfo<>();
        pageInfo.setList(list);
        pageInfo.setCount((int) page.getTotal());
        pageInfo.setPages(page.getPages());
        return pageInfo;
    }

    /**
     * 终端流量收集
     * @param terminalDataVo
     * @return
     */
    @Override
    public int collectTerminalData(TerminalDataVo terminalDataVo) {
        NmplTerminalData nmplTerminalData = new NmplTerminalData();
        BeanUtils.copyProperties(terminalDataVo,nmplTerminalData);
        return terminalDataMapper.insertSelective(nmplTerminalData);
    }

}
