package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.domain.MenuDomainService;
import com.matrictime.network.dao.mapper.NmplMenuMapper;
import com.matrictime.network.dao.mapper.NmplRoleMenuRelationMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.modelVo.NmplMenuVo;
import com.matrictime.network.request.MenuReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Slf4j
public class MenuDomainServiceImpl  implements MenuDomainService {
    @Autowired
    NmplMenuMapper nmplMenuMapper;
    @Autowired
    NmplRoleMenuRelationMapper nmplRoleMenuRelationMapper;
    @Override
    public List<NmplMenuVo> queryAllMenu(MenuReq menuReq) {
        //获取该用户能查看的权限
        NmplMenuExample nmplMenuExample = new NmplMenuExample();
        NmplMenuExample.Criteria criteria = nmplMenuExample.createCriteria();
        if(menuReq.getPermission()!=null){
            nmplMenuExample.or().andPermissionEqualTo(menuReq.getPermission());
        }
        criteria.andIsExistEqualTo((byte) 1).andPermissionEqualTo("0");
        List<NmplMenu> menus = nmplMenuMapper.selectByExample(nmplMenuExample);
        //复用接口 如果参数中myRoleId不为空 则将用户没有的权限过滤
        if(menuReq.getMyRoleId()!=null){
//            若需求变更为 超管赋予某些权限给普通用户 业务管理员也无法查看
//            NmplMenuExample nmplMenuExample1 = new NmplMenuExample();
//            nmplMenuExample1.createCriteria().andIsExistEqualTo((byte) 1);
//            menus = nmplMenuMapper.selectByExample(nmplMenuExample1);
            //该用户非管理员用户
            if(!(Long.valueOf(menuReq.getMyRoleId()) == DataConstants.SUPER_ADMIN)&&!(Long.valueOf(menuReq.getMyRoleId()) == DataConstants.COMMON_ADMIN)){
                List<Long> myMenuIds = nmplMenuMapper.queryAllPermId(Long.valueOf(menuReq.getMyRoleId()));
                List<NmplMenu> myMenus = new ArrayList<>();
                for (NmplMenu menu : menus) {
                    if(myMenuIds.contains(menu.getMenuId())){
                        myMenus.add(menu);
                    }
                }
                menus = myMenus;
            }
        }
        List<NmplMenuVo> menuList  =new ArrayList<>();
        //获取角色拥有的权限
        List<Long> menuIds = new ArrayList<>();
        if(menuReq.getRoleId()!=null){
            if(Long.valueOf(menuReq.getRoleId()) == DataConstants.SUPER_ADMIN||Long.valueOf(menuReq.getRoleId()) == DataConstants.COMMON_ADMIN){
                for (NmplMenu menu : menus) {
                    menuIds.add(menu.getMenuId());
                }
            }else {
                menuIds = nmplMenuMapper.queryAllPermId(Long.valueOf(menuReq.getRoleId()));
            }
        }
        for (NmplMenu menu : menus) {
            NmplMenuVo menuVo = new NmplMenuVo();
            BeanUtils.copyProperties(menu,menuVo);
            if(menuIds.contains(menu.getMenuId())){
                menuVo.setOwn(true);
            }else {
                menuVo.setOwn(false);
            }
            menuList.add(menuVo);
        }
        log.info("menu sort");
        List<NmplMenuVo> first = new ArrayList<>();
        Map<Long,List<NmplMenuVo>> second = new HashMap<>();
        Map<Long,List<NmplMenuVo>> third = new HashMap<>();
        for (NmplMenuVo nmplMenu : menuList) {
            if (nmplMenu.getMenuType()==0){
                first.add(nmplMenu);
            }
            if (nmplMenu.getMenuType()==1){
                if (second.get(nmplMenu.getParentMenuId())==null){
                    second.put(nmplMenu.getParentMenuId(),new ArrayList<>());
                }
                second.get(nmplMenu.getParentMenuId()).add(nmplMenu);
            }
            if (nmplMenu.getMenuType()==2){
                if (third.get(nmplMenu.getParentMenuId())==null){
                    third.put(nmplMenu.getParentMenuId(),new ArrayList<>());
                }
                third.get(nmplMenu.getParentMenuId()).add(nmplMenu);
            }
        }
        for (NmplMenuVo nmplMenu : first) {
            if(second.get(nmplMenu.getMenuId())!=null){
                nmplMenu.setChild(second.get(nmplMenu.getMenuId()));
            }else {
                nmplMenu.setChild(new ArrayList<>());
            }
            for (NmplMenuVo menu : nmplMenu.getChild()) {
                if(third.get(menu.getMenuId())!=null){
                    menu.setChild(third.get(menu.getMenuId()));
                }else {
                    menu.setChild(new ArrayList<>());
                }
            }
        }
        return first;
    }
}
