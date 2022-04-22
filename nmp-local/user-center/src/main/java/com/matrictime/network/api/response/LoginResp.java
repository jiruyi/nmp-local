package com.matrictime.network.api.response;

import com.matrictime.network.api.modelVo.UserVo;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginResp implements Serializable {
    private static final long serialVersionUID = 4752492437076704960L;

    private UserVo user;
}
