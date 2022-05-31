package com.matrictime.network.response;

import com.matrictime.network.modelVo.NmplRoleVo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoleResp{
    List<NmplRoleVo> list = new ArrayList<>();
}
