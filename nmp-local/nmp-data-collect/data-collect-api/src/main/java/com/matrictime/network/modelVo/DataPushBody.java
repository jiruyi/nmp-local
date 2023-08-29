package com.matrictime.network.modelVo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/29 0029 9:24
 * @desc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataPushBody {

    private String businessCode;

    private String tableName;

    private String busiDataJsonStr;
}
