package com.matrictime.network.response;

import com.matrictime.network.request.UserRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/28 0028 16:38
 * @desc
 */
@Data
@Builder
public class UserListResponse  extends BaseResponse{

    private List<UserRequest> list;
}
