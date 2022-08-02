package com.matrictime.network.api.response;

import com.matrictime.network.api.modelVo.UserGroupVo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserGroupResp implements Serializable {
   List<UserGroupVo> userGroupVos = new ArrayList<>();
}
