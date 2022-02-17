package com.matrictime.network.domain.impl;

import com.matrictime.network.dao.mapper.NmplSmsDetailMapper;
import com.matrictime.network.dao.model.NmplSmsDetail;
import com.matrictime.network.domain.NmplSmsDetailDomainService;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/16 0016 11:10
 * @desc
 */
@Service
public class NmplSmsDetailDomainServiceImpl implements NmplSmsDetailDomainService {


    @Autowired
    private NmplSmsDetailMapper smsDetailMapper;
    /**
     * @title insertSmsDetail
     * @param [smsDetail]
     * @return int
     * @description  插入短信发送明细
     * @author jiruyi
     * @create 2021/9/14 0014 16:42
     */
    @Override
    public int insertSmsDetail(NmplSmsDetail smsDetail) {
        if(ObjectUtils.isEmpty(smsDetail)){
            return NumberUtils.INTEGER_ZERO.intValue();
        }
        return smsDetailMapper.insertSelective(smsDetail);
    }
}
