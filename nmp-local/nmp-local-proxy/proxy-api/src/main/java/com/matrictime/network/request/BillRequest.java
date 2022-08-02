package com.matrictime.network.request;

import com.matrictime.network.modelVo.NmplBillVo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BillRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = -8897605799075175452L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 账单用户id
     */
    private String ownerId;

    /**
     * 流id
     */
    private String streamId;

    /**
     * 消耗流量
     */
    private String flowNumber;

    /**
     * 时长
     */
    private String timeLength;

    /**
     * 消耗密钥量
     */
    private String keyNum;

    /**
     *
     */
    private String hybridFactor;

    /**
     * 创建时间
     */
    private Date uploadTime;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


    private List<NmplBillVo> nmplBillVoList =new ArrayList<>();
}
