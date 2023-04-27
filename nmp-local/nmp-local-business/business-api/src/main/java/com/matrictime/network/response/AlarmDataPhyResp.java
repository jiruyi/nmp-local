package com.matrictime.network.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/25 0025 14:41
 * @desc
 */
@Data
@Builder
public class AlarmDataPhyResp implements Serializable {
    private static final long serialVersionUID = -1224331548361442992L;

    private List<AlarmPhyTypeCount> phyTypeCountList;
}
