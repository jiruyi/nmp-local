package com.matrictime.network.dao.domain;

import com.matrictime.network.modelVo.NmplMenuVo;
import com.matrictime.network.request.MenuReq;

import java.util.List;

public interface MenuDomainService {
    public List<NmplMenuVo> queryAllMenu(MenuReq menuReq);

}
