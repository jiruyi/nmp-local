package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class GroupVo implements Serializable {
    private static final long serialVersionUID = -6252329811888594093L;

    /**
     * 主键 组id
     */
    private Long groupId;

    /**
     * 组名称
     */
    private String groupName;

    /**
     *  0 删除  1 存在
     */
    private Boolean isExist;

    /**
     * 组所属人
     */
    private String owner;

    private Date createTime;

    private List<UserGroupVo> userGroupVoList;

}
