package com.matrictime.network.response;

import lombok.Data;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/24 0024 17:44
 * @desc
 */
@Data
public class AlarmSysLevelCount {
    //严重警告条数
    private Long seriousCount;
    //紧急警告条数
    private Long emergentCount;
    //一般警告条数
    private Long sameAsCount;
}
