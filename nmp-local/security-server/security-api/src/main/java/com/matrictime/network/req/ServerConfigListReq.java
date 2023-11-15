package com.matrictime.network.req;

import lombok.Data;

import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/14.
 */
@Data
public class ServerConfigListReq {

    private List<ServerConfigRequest> list;
}
