package com.matrictime.network.response;

import com.matrictime.network.modelVo.NmplMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuResponse extends BaseResponse{
    List<NmplMenu> list=new ArrayList<>();
}
