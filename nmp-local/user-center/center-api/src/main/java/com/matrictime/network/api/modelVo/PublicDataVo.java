package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PublicDataVo implements Serializable {

    private static final long serialVersionUID = -3613248419557284077L;

    private String cert;
    private String expire;
    private String now;
    private String OTCA;
    private String sign;
}
