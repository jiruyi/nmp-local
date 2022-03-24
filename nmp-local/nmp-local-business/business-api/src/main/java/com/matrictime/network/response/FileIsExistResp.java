package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileIsExistResp implements Serializable {
    private static final long serialVersionUID = 1897573095208363512L;

    /**
     * 文件是否存在（true:存在 false:不存在）
     */
    private Boolean isExist;
}
