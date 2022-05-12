package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WsSendVo implements Serializable {
    private static final long serialVersionUID = 8407625086365023365L;

    private String from;

    private String businessCode;

    private Object data;

    private String sendObject;
}
