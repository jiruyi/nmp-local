package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.MenuReq;
import com.matrictime.network.response.MenuResponse;

public interface MenuService {

    Result<MenuResponse> queryAllMenu(MenuReq menuReq);

    Result<MenuResponse> queryOwnerMenu(MenuReq menuReq);
}
