package com.matrictime.network.domain.impl;

import com.matrictime.network.dao.mapper.NmplUserMapper;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.dao.model.NmplUserExample;
import com.matrictime.network.domain.NmplUserDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/10 0010 17:57
 * @desc
 */
@Service
@Slf4j
public class NmplUserDomainServiceImpl implements NmplUserDomainService {

    @Autowired
    private NmplUserMapper nmplUserMapper;

    @Override
    public List<NmplUser> getAllUserInfo() {
        NmplUserExample example = new NmplUserExample();
        List<NmplUser> userList = nmplUserMapper.selectByExample(example);
        log.info("UserDomainService.getAllUserInfo() result is :{}",userList);
        return userList;
    }

    @Override
    public int getUserCountByPhone(String phone) {
        return 1;
    }
}
