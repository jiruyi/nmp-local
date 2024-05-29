package com.matrictime.network.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2024/4/24 0024 16:15
 * @desc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisMsg {

    /**
     * 业务类型在redis中存储key
     */
    private String redis_key;

    /**
     * 变动类型
     * 1:数据增加
     * 2:数据删除
     * 3:数据修改
     */
    private String change_type;

    /**
     * 数据主键id
     */
    private String id;

}
