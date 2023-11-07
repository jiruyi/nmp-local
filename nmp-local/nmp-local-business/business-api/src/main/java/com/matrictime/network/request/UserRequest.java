package com.matrictime.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/28 0028 10:39
 * @desc
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest extends BaseRequest {

    private String userId;

    private String villageId;

    private String loginAccount;

    private String nickName;

    private String userType;

    private String email;

    private String phoneNumber;

    private String password;

    private String roleId;

    private String roleName;

    private Boolean status;

    private Boolean isExist;

    private String createUser;

    private String createTime;

    private String updateUser;

    private String updateTime;

    private String remark;

    private Boolean isAdmin =false;

    /**
     * 创建者名字
     */
    private String createUserName;
}
