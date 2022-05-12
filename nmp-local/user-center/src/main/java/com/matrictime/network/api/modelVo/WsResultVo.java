package com.matrictime.network.api.modelVo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WsResultVo implements Serializable {
    private static final long serialVersionUID = 2150054543629920015L;

    private String destination;

    private String result;
}
