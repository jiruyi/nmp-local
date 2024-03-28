package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskVo implements Serializable {

    private static final long serialVersionUID = 6310175681159871767L;

    private String data;

    private String ip;

    private String deviceType;
}
