package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by wangqiang
 * @date 2022/9/23.
 */
@Data
public class OutlinePcVo implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备编号
     */
    private String deviceId;
}
