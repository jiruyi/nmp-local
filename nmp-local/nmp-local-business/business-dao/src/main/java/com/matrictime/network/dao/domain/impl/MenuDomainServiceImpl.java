package com.matrictime.network.dao.domain.impl;

import com.matrictime.network.dao.domain.MenuDomainService;
import com.matrictime.network.dao.mapper.NmplMenuMapper;
import com.matrictime.network.modelVo.NmplMenu;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public List<NmplMenu> queryAllMenu() {
        List<NmplMenu> menuList = nmplMenuMapper.queryAllMenu();
        List<NmplMenu> first = new ArrayList<>();
        Map<Long,List<NmplMenu>> second = new HashMap<>();
        Map<Long,List<NmplMenu>> third = new HashMap<>();
        for (NmplMenu nmplMenu : menuList) {
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
        for (NmplMenu nmplMenu : first) {
            nmplMenu.setChild(second.get(nmplMenu.getMenuId()));
            for (NmplMenu menu : nmplMenu.getChild()) {
                menu.setChild(third.get(menu.getMenuId()));
            }
        }
        return first;
    }
}
