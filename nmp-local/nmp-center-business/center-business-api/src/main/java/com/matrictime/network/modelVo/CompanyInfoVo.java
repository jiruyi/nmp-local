package com.matrictime.network.modelVo;

import lombok.Data;

/**
 * @author by wangqiang
 * @date 2023/8/7.
 */
@Data
public class CompanyInfoVo {

    /**
     * 小区唯一编号Id
     */
    private String companyNetworkId;

    /**
     * 单位名称
     */
    private String companyName;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 国家码
     */
    private String countryCode;

    /**
     * 单位编码
     */
    private String companyCode;

    /**
     * 00:运营商 01:大区 02：小区
     */
    private String companyType;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 1:正常 0停用
     */
    private Boolean status;

    /**
     * 联系地址
     */
    private String addr;

    /**
     * 父单位编码
     */
    private String parentCode;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 经纬度位置
     */
    private String position;


    /**
     * 1:存在 0:删除
     */
    private Boolean isExist;

    /**
     * 严重警告
     */
    private String seriousCount;

    /**
     * 紧急警告
     */
    private String emergentCount;

    /**
     * 一般警告
     */
    private String sameAsCount;

    /**
     * 接入带宽
     */
    private String accessBandwith;

    /**
     * 接入带宽单位
     */
    private String accessBandwithUnit;

    /**
     * 区间带宽
     */
    private String intervalBandwith;

    /**
     * 区间带宽单位
     */
    private String intervalBandwithUnit;

    /**
     * 网元设备
     */
    private String netDevices;

    /**
     * 终端设备
     */
    private String terminalDevices;

}
