package com.matrictime.network.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/4/8 0008 9:07
 * @desc
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteFriendReq extends BaseReq implements Serializable {

    private static final long serialVersionUID = -8160436091947711566L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 好友id
     */
    private String  friendUserId;
    /**
     * 群组id
     */
    private String groupId;
}
