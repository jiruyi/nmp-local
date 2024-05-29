package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class ProxyReq implements Serializable {

    private static final long serialVersionUID = 7776495148980164489L;

    private String ip;

    private Set<String> ips;

}
