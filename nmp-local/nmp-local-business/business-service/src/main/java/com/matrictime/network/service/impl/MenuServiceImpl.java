package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.MenuDomainService;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.modelVo.NmplMenuVo;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.MenuReq;
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
    public Result<MenuResponse> queryAllMenu(MenuReq menuReq) {
        Result<MenuResponse> result = null;
        try {
            NmplUser user = RequestContext.getUser();
            if(Integer.valueOf(user.getRoleId())== DataConstants.SUPER_ADMIN){
                menuReq.setPermission("1");
            }
            List<NmplMenuVo> menuList = menuDomainService.queryAllMenu(menuReq);
            MenuResponse menuResponse = new MenuResponse();
            menuResponse.setList(menuList);
            result = buildResult(menuResponse);
        }catch (Exception e){
            log.error("查询菜单异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }


    @Override
    public Result<MenuResponse> queryOwnerMenu(MenuReq menuReq) {
        Result<MenuResponse> result = null;
        try {
            NmplUser user = RequestContext.getUser();
            if(Integer.valueOf(user.getRoleId())== DataConstants.SUPER_ADMIN){
                menuReq.setPermission("1");
            }
            menuReq.setMyRoleId(user.getRoleId());
            List<NmplMenuVo> menuList = menuDomainService.queryAllMenu(menuReq);
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
