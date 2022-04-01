package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class GroupReq implements Serializable {

    private static final long serialVersionUID = -6634266583253019235L;

    private Long groupId;

    private String groupName;

    private Boolean isExist;

    private Long owner;

}
