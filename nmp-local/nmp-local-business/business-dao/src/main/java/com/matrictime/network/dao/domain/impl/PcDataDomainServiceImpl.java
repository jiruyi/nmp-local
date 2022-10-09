package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.dao.domain.PcDataDomainService;
import com.matrictime.network.dao.mapper.extend.NmplPcDataExtMapper;
import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.modelVo.NmplPcDataVo;
import com.matrictime.network.request.PcDataReq;
import com.matrictime.network.response.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class PcDataDomainServiceImpl implements PcDataDomainService {

    @Resource
    NmplPcDataExtMapper nmplPcDataExtMapper;


    @Override
    public PageInfo<NmplPcDataVo> query(PcDataReq pcDataReq) {
        Page page = PageHelper.startPage(pcDataReq.getPageNo(),pcDataReq.getPageSize());
        List<NmplPcDataVo> pcDataVoList = nmplPcDataExtMapper.queryByCondition(pcDataReq);
        PageInfo<NmplPcDataVo> pageResult =  new PageInfo<>();
        pageResult.setList(pcDataVoList);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return pageResult;
    }

    @Override
    public Integer batchInsert(PcDataReq pcDataReq) {
        return nmplPcDataExtMapper.batchInsert(pcDataReq.getNmplPcDataVoList());
    }
}
