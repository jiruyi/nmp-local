package com.matrictime.network.api.response;

import com.matrictime.network.api.modelVo.AddUserRequestVo;

import java.io.Serializable;
import java.util.List;

public class AddUserRequestResp implements Serializable {

    private List<AddUserRequestVo> list;

    public List<AddUserRequestVo> getList() {
        return list;
    }

    public void setList(List<AddUserRequestVo> list) {
        this.list = list;
    }
}
