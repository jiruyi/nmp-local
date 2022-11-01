package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadImgResp implements Serializable {

    private static final long serialVersionUID = -8165350931937468417L;

    /**
     * 文件路径
     */
    private String filePath;
}
