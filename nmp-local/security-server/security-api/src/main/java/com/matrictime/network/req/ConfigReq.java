package com.matrictime.network.req;

import lombok.Data;

@Data
public class ConfigReq {

    private Integer configMode;

//-------------密区配置-------------------------------------------
    /**
     * 当前ip
     */
    private String localIp;

    /**
     * 入网id
     */
    private String networkId;

    /**
     * 网卡类型（1：物理网卡 2：虚拟网卡）
     */
    private String netCardType;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 输入ip
     */
    private String inputIp;
//-----------通信配置------------------------------------------
    /**
     * 通信ip
     */
    private String commIp;

    /**
     * 端口
     */
    private String port;
//------------基站配置-------------------------------------------
    /**
     * 主基站接入方式（1：固定接入 2：动态接入）
     */
    private String mainAccessType;

    /**
     * 主基站通信ip
     */
    private String mainCommIp;

    /**
     * 主基站域名ip
     */
    private String mainDomainName;

    /**
     * 主基站端口
     */
    private String mainPort;

    /**
     * 备用基站接入方式（1：固定接入 2：动态接入）
     */
    private String spareAccessType;

    /**
     * 备用基站通信ip
     */
    private String spareCommIp;

    /**
     * 备用基站域名ip
     */
    private String spareDomainName;

    /**
     * 备用基站端口
     */
    private String sparePort;
//---------CA配置-----------------------------------------------------------
    /**
     * 接入方式（1：固定接入 2：动态接入）
     */
    private String accessType;

    /**
     * 通信ip
     */


    /**
     * 域名ip
     */
    private String domainName;

    /**
     * 端口
     */


    /**
     * 隐私ip
     */
    private String secretIp;

    /**
     * 入网id
     */

//-------DNS配置---------------------------------------------------------------
    /**
     * 通信ip
     */


    /**
     * 隐私ip
     */


    /**
     * 入网id
     */

}
