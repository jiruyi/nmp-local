package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplLoginDetail;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.request.LoginRequest;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/24 0024 13:42
 * @desc
 */
public interface UserDomainService {

    /**
      * @title getUserByParamter
      * @param [loginRequest]
      * @return List<NmplUser>
      * @description
      * @author jiruyi
      * @create 2022/2/24 0024 13:43
      */
    List<NmplUser> getUserByParamter(LoginRequest loginRequest);

    /**
      * @title insertLoginDetail
      * @param [logintDetail]
      * @return int
      * @description
      * @author jiruyi
      * @create 2022/2/25 0025 9:24
      */
    int insertLoginDetail(NmplLoginDetail loginDetail);
}
