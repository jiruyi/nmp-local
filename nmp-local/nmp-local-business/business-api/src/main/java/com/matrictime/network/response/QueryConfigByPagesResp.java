package com.matrictime.network.response;


import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryConfigByPagesResp implements Serializable {

    private static final long serialVersionUID = -5342675573581391086L;

    /**
     * 分页信息
     */
    private PageInfo pageInfo;
}
