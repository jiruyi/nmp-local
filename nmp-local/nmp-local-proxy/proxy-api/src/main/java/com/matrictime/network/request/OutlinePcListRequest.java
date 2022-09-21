package com.matrictime.network.request;

import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2022/9/15.
 */
@Data
public class OutlinePcListRequest extends BaseRequest{
    private List<OutlinePcReq> list;
}
