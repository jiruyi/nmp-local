package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GroupVo implements Serializable {
    private static final long serialVersionUID = -6252329811888594093L;

    private Long groupId;

    private String groupName;

    private Boolean isExist;

    private Long owner;

    private Date createTime;
}