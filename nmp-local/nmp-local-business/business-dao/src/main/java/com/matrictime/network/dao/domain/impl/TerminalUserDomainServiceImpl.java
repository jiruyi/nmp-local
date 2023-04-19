package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.TerminalUserDomainService;
import com.matrictime.network.dao.mapper.NmplTerminalUserMapper;
import com.matrictime.network.dao.model.NmplTerminalUser;
import com.matrictime.network.dao.model.NmplTerminalUserExample;
import com.matrictime.network.modelVo.TerminalUserVo;
import com.matrictime.network.request.TerminalUserResquest;
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
public class TerminalUserDomainServiceImpl implements TerminalUserDomainService {

    @Resource
    private NmplTerminalUserMapper nmplTerminalUserMapper;

    @Override
    public int insertTerminalUser(TerminalUserResquest terminalUserResquest) {
        NmplTerminalUser nmplTerminalUser = new NmplTerminalUser();
        BeanUtils.copyProperties(terminalUserResquest,nmplTerminalUser);
        return nmplTerminalUserMapper.insertSelective(nmplTerminalUser);
    }

    @Override
    public int updateTerminalUser(TerminalUserResquest terminalUserResquest) {
        NmplTerminalUserExample nmplTerminalUserExample = new NmplTerminalUserExample();
        NmplTerminalUserExample.Criteria criteria = nmplTerminalUserExample.createCriteria();
        criteria.andTerminalNetworkIdEqualTo(terminalUserResquest.getTerminalNetworkId());
        NmplTerminalUser nmplTerminalUser = new NmplTerminalUser();
        BeanUtils.copyProperties(terminalUserResquest,nmplTerminalUser);
        return nmplTerminalUserMapper.updateByExampleSelective(nmplTerminalUser,nmplTerminalUserExample);
    }

    @Override
    public List<TerminalUserVo> selectTerminalUser(TerminalUserResquest terminalUserResquest) {
        NmplTerminalUserExample nmplTerminalUserExample = new NmplTerminalUserExample();
        NmplTerminalUserExample.Criteria criteria = nmplTerminalUserExample.createCriteria();
        criteria.andTerminalNetworkIdEqualTo(terminalUserResquest.getTerminalNetworkId());
        List<NmplTerminalUser> nmplTerminalUsers = nmplTerminalUserMapper.selectByExample(nmplTerminalUserExample);
        List<TerminalUserVo> list = new ArrayList<>();
        for(NmplTerminalUser nmplTerminalUser: nmplTerminalUsers){
            TerminalUserVo terminalUserVo = new TerminalUserVo();
            BeanUtils.copyProperties(nmplTerminalUser,terminalUserVo);
            list.add(terminalUserVo);
        }
        return list;
    }

    @Override
    public int countTerminalUser(TerminalUserResquest terminalUserResquest) {
        NmplTerminalUserExample nmplTerminalUserExample = new NmplTerminalUserExample();
        NmplTerminalUserExample.Criteria criteria = nmplTerminalUserExample.createCriteria();
        criteria.andTerminalStatusEqualTo(terminalUserResquest.getTerminalStatus());
        criteria.andParentDeviceIdEqualTo(terminalUserResquest.getParentDeviceId());
        List<NmplTerminalUser> nmplTerminalUsers = nmplTerminalUserMapper.selectByExample(nmplTerminalUserExample);
        return nmplTerminalUsers.size();
    }


}
