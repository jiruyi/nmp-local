package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.domain.MenuDomainService;
import com.matrictime.network.modelVo.NmplMenu;
import com.matrictime.network.model.Result;
import com.matrictime.network.response.MenuResponse;
import com.matrictime.network.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MenuServiceImpl extends SystemBaseService implements MenuService {

    @Autowired
    MenuDomainService menuDomainService;

    @Override
    public Result<MenuResponse> queryAllMenu() {
        Result<MenuResponse> result = null;
        try {
           List<NmplMenu> menuList = menuDomainService.queryAllMenu();
           MenuResponse menuResponse = new MenuResponse();
           menuResponse.setList(menuList);
           result = buildResult(menuResponse);
        }catch (Exception e){
            log.error("查询菜单异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }
}
