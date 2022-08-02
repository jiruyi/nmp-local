package com.matrictime.network.api.response;

import com.matrictime.network.api.modelVo.GroupVo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class GroupResp implements Serializable {
    List<GroupVo> groupVoList = new ArrayList<>();
}
