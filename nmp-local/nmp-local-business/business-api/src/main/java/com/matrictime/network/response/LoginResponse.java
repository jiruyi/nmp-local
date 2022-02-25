package com.matrictime.network.response;

import lombok.Builder;
import lombok.Data;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/6 0006 15:17
 * @desc
 */
@Data
@Builder
public class LoginResponse extends BaseResponse{

    private String token;

}
