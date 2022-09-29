package com.matrictime.network.modelVo;

import lombok.Data;

import java.util.Date;
@Data
public class CenterLinkRelationVo {
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