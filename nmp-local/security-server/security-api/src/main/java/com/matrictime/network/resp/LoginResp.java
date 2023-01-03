package com.matrictime.network.resp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResp {

    String token;
}
