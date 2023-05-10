package com.matrictime.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/4/25 0025 14:43
 * @desc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmPhyTypeCount implements Serializable {
    private static final long serialVersionUID = -5568226664685235967L;

    private Long cpuCount = 0L;

    private Long memCount = 0L;

    private Long diskCount= 0L;

    private Long flowCount= 0L;

    private  String phyIp;
    public AlarmPhyTypeCount(String phyIp){
        this.phyIp = phyIp;
    }
}
