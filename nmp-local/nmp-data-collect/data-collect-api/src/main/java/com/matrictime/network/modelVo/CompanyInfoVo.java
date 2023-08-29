package com.matrictime.network.modelVo;

import lombok.Data;

/**
 * @author by wangqiang
 * @date 2023/8/17.
 */
@Data
public class CompanyInfoVo {

    private Long id;

    /**
     * 单位id
     */
    private Long companyId;

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
}
