package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class VersionHttpResultRes implements Serializable {
    private static final long serialVersionUID = 5567338888972951822L;

    String deviceName;

    String deviceId;

    Boolean success;
}
