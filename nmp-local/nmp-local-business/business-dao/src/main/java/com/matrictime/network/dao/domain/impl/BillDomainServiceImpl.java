package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.dao.domain.BillDomainService;
import com.matrictime.network.dao.mapper.NmplBillMapper;
import com.matrictime.network.dao.mapper.extend.NmplBillExtMapper;
import com.matrictime.network.dao.model.NmplBill;
import com.matrictime.network.dao.model.NmplRole;
import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.modelVo.NmplRoleVo;
import com.matrictime.network.request.BillRequest;
import com.matrictime.network.request.CompanyInfoRequest;
import com.matrictime.network.response.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BillDomainServiceImpl implements BillDomainService {

    @Autowired
    NmplBillExtMapper nmplBillExtMapper;
    @Autowired
    NmplBillMapper nmplBillMapper;


    @Override
    public PageInfo<NmplBillVo> queryByConditions(BillRequest billRequest) {
        Page page = PageHelper.startPage(billRequest.getPageNo(),billRequest.getPageSize());
        List<NmplBillVo> billVoList = nmplBillExtMapper.queryByCondition(billRequest);
        PageInfo<NmplBillVo> pageResult =  new PageInfo<>();
        pageResult.setList(billVoList);
        pageResult.setCount((int) page.getTotal());
        pageResult.setPages(page.getPages());
        return pageResult;
    }


    @Override
    public Integer save(BillRequest billRequest) {
        if (CollectionUtils.isEmpty(billRequest.getNmplBillVoList())){
            NmplBill nmplBill = new NmplBill();
            BeanUtils.copyProperties(billRequest,nmplBill);
            return nmplBillMapper.insert(nmplBill);
        }else {
            return nmplBillExtMapper.batchInsert(billRequest.getNmplBillVoList());
        }
    }
}
