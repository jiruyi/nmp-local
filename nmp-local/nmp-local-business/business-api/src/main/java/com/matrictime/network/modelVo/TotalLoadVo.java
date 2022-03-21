package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TotalLoadVo implements Serializable {
    private static final long serialVersionUID = -8825066942382197284L;

    /**
     * 统计时间点
     */
    private Date Time;

    /**
     * 统计数据
     */
    private Long data;
}
