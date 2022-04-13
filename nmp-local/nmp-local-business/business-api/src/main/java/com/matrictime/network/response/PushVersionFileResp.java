package com.matrictime.network.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PushVersionFileResp implements Serializable {
    private static final long serialVersionUID = -5736229003300958926L;

    /**
     * 成功id列表
     */
    List<String> successIds;

    /**
     * 失败id列表
     */
    List<String> failIds;
}
