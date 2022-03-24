package com.matrictime.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = -2777337116972869483L;
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 1:全部菜单权限 2：自定义
     */
    private String menuScope;

    /**
     * 角色状态（1正常 0停用）
     */
    private Byte status;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 1正常 0删除
     */
    private Byte isExist;

    private boolean isAdmin;
    /**
     * 菜单id
     */
    private List<Long> menuId;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;



}
