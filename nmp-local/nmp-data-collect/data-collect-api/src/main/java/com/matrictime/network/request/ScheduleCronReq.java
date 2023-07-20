package com.matrictime.network.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/7/20 0020 10:52
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCronReq extends CollectBaseRequest{
    private static final long serialVersionUID = 3493176636181864857L;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 单位
     */
    private String unit;
}
