package com.matrictime.network.dao.mapper.ext;

import com.matrictime.network.api.modelVo.AddUserRequestVo;
import com.matrictime.network.api.request.AddUserRequestReq;

import java.util.List;

public interface AddUserRequestExtMapper {
    int addFriends(AddUserRequestReq addUserRequestReq);

    List<AddUserRequestVo> getAddUserInfo(AddUserRequestReq addUserRequestReq);
}
