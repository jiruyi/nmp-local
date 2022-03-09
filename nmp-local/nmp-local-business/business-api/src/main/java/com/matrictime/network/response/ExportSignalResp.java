package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExportSignalResp implements Serializable {
    private static final long serialVersionUID = 865929678761204958L;

    private byte[] bytes;
}
