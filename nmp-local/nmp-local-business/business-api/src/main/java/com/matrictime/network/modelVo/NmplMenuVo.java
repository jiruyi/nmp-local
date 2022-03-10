package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.List;

@Data
public class NmplMenuVo {
    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Long parentMenuId;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 是否为外链（1是 0否）
     */
    private Byte isFrame;

    /**
     * 菜单类型（1目录 2菜单 3按钮）
     */
    private Byte menuType;

    /**
     * 菜单状态（1正常 0停用）
     */
    private Byte menuStatus;

    /**
     * 权限标识
     */
    private String permsCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 1正常 0删除
     */
    private Byte isExist;

    /**
     *  菜单子集
     */
    private List<NmplMenuVo> child;
}
