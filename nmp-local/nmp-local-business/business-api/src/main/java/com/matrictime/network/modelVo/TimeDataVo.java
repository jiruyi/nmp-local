package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TimeDataVo implements Serializable {
    private static final long serialVersionUID = 6534703530094627035L;

    private Date date;

    private Double value;

    private Double upValue;

    private Double downValue;

}
