package com.matrictime.network.api.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GroupReq  extends BaseReq implements Serializable  {

    private static final long serialVersionUID = -6634266583253019235L;

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


    private String userId;

    private List <String> userIdList;

}
