package com.matrictime.network.response;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuerySignalByPageResp implements Serializable {
    private static final long serialVersionUID = 3074131651038357836L;

    /**
     * 分页信息
     */
    private PageInfo pageInfo;

    /**
     * 选项列表
     */
    private List<String> deviceIds;
}
