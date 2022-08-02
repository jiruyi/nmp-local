package com.matrictime.network.api.response;

import com.matrictime.network.api.modelVo.UserFriendVo;

import java.io.Serializable;
import java.util.List;

public class UserFriendResp implements Serializable {

    private List<UserFriendVo> list;

    public List<UserFriendVo> getList() {
        return list;
    }

    public void setList(List<UserFriendVo> list) {
        this.list = list;
    }
}
