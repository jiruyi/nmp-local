package com.matrictime.network.response;

import com.matrictime.network.modelVo.DataCollectVo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MonitorResp extends BaseResponse{
    /**
     * 用户数
     */
    private Integer userNumber;
    /**
     * 总带宽
     */
    private double totalBandwidth;
    /**
     * 分发机秘钥量
     */
    private double dispenserSecretKey;
    /**
     * 生成机秘钥量
     */
    private double generatorSecretKey;
    /**
     * 缓存机秘钥量
     */
    private double cacheSecretKey;

    private List<DataCollectVo> dataCollectVoList;
}
