package com.matrictime.network.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CompanyInfoRequest extends BaseRequest implements Serializable {


    private static final long serialVersionUID = 1855455628511537134L;
    /**
     * 单位id
     */
    private Long companyId;

    /**
     * 单位名称
     */
    private String companyName;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 1:存在 0:删除
     */
    private Boolean isExist;
}
