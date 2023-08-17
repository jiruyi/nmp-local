package com.matrictime.network.dao.model;

import lombok.Data;

/**
 * 
 * @author   hexu
 * @date   2023-08-07
 */
@Data
public class NmplRoleMenuRelation {
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单权限id
     */
    private Long menuId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}