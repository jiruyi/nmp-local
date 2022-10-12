package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProxyLinkRelationVo implements Serializable {
    private static final long serialVersionUID = 6903465360988289723L;
    private Long id;

    private String linkName;

    private String linkType;

    private String mainDeviceId;

    private String followDeviceId;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String isExist;

    private String noticeDeviceType;
}
