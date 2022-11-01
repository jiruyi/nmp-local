package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class DelSystemReq implements Serializable {

    private static final long serialVersionUID = 7143539337692416471L;

    /**
     * 系统ID
     */
    private Long id;
}
