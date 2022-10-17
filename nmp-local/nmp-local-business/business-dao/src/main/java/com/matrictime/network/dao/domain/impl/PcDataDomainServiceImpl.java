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
import java.text.DecimalFormat;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.BYTE_TO_KB;

@Service
public class PcDataDomainServiceImpl implements PcDataDomainService {

    @Resource
    NmplPcDataExtMapper nmplPcDataExtMapper;


    @Override
    public PageInfo<NmplPcDataVo> query(PcDataReq pcDataReq) {
        Page page = PageHelper.startPage(pcDataReq.getPageNo(),pcDataReq.getPageSize());
        List<NmplPcDataVo> pcDataVoList = nmplPcDataExtMapper.queryByCondition(pcDataReq);
        DecimalFormat format = new DecimalFormat("#.00");
        for (NmplPcDataVo nmplPcDataVo : pcDataVoList) {
            String upKeyNum = format.format(nmplPcDataVo.getUpKeyNum()/BYTE_TO_KB);
            String downKeyNum = format.format(nmplPcDataVo.getDownKeyNum()/BYTE_TO_KB);
            nmplPcDataVo.setUpKeyNum(Double.parseDouble(upKeyNum));
            nmplPcDataVo.setDownKeyNum(Double.parseDouble(downKeyNum));
        }
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
