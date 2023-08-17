package com.matrictime.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/3/1 0001 11:08
 * @desc
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends BaseRequest{
    //用户标识
    private  String userId;
    //老密码
    private String oldPassword;
    //新密码
    private String newPassword;
    //确认密码
    private String confirmPassword;
    //1 表示个人信息的修改密码  2 表示列表的重置密码
    private String type;
}
