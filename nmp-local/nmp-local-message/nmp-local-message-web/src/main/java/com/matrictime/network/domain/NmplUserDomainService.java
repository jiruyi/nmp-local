package com.matrictime.network.domain;

import com.matrictime.network.dao.model.NmplUser;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/10 0010 15:11
 * @desc
 */
public interface NmplUserDomainService {

    /**
     * @param
     * @return
     * @description 获取所有人员
     * @author jiruyi@jzsg.com.cn
     * @create 2021/8/9 9:53
     */
    List<NmplUser> getAllUserInfo();
    
    /**
      * @title getUserCountByPhone
      * @param [phone]
      * @return int
      * @description 
      * @author jiruyi
      * @create 2022/2/10 0010 15:24
      */
    int getUserCountByPhone(String phone);
}
