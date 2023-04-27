package com.matrictime.network.modelVo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DisplayVo implements Serializable {

    private static final long serialVersionUID = 4262136472071848220L;

    private String date;

    private String value1;

    private String value2;
}
