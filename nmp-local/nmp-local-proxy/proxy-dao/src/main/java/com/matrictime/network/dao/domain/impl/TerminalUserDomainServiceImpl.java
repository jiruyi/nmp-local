package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.TerminalUserDomainService;
import com.matrictime.network.dao.mapper.NmplTerminalUserMapper;
import com.matrictime.network.dao.model.NmplTerminalUser;
import com.matrictime.network.dao.model.NmplTerminalUserExample;
import com.matrictime.network.modelVo.TerminalUserVo;
import com.matrictime.network.response.TerminalUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/4/21.
 */
@Slf4j
@Service
public class TerminalUserDomainServiceImpl implements TerminalUserDomainService {

    @Resource
    private NmplTerminalUserMapper terminalUserMapper;

    @Override
    public TerminalUserResponse selectTerminalUser() {
        NmplTerminalUserExample nmplTerminalUserExample = new NmplTerminalUserExample();
        TerminalUserResponse terminalUserResponse = new TerminalUserResponse();
        List<TerminalUserVo> list = new ArrayList<>();
        List<NmplTerminalUser> nmplTerminalUsers = terminalUserMapper.selectByExample(nmplTerminalUserExample);
        if(!CollectionUtils.isEmpty(nmplTerminalUsers)){
            for(NmplTerminalUser terminalUser: nmplTerminalUsers){
                TerminalUserVo terminalUserVo = new TerminalUserVo();
                BeanUtils.copyProperties(terminalUser,terminalUserVo);
                list.add(terminalUserVo);
            }
        }
        terminalUserResponse.setList(list);
        return terminalUserResponse;
    }
}
