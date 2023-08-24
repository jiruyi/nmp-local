package com.matrictime.network.response;

import com.matrictime.network.modelVo.CompanyInfoVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QueryCompanyUserResp implements Serializable {

    private static final long serialVersionUID = 8835379203573390163L;

    /**
     * 小区列表
     */
    private List<CompanyInfoVo> companyInfo;

    /**
     * 在线用户数列表
     */
    private List<String> onlineUser;

    /**
     * 接入用户数列表
     */
    private List<String> accessUser;
}
