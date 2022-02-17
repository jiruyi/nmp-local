package com.matrictime.network.domain.impl;

import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.domain.NmplUserDomainService;
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
public class NmplUserDomainServiceImpl implements NmplUserDomainService {
    @Override
    public List<NmplUser> getAllUserInfo() {
        return null;
    }

    @Override
    public int getUserCountByPhone(String phone) {
        return 1;
    }
}
