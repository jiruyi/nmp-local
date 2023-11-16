package com.matrictime.network.base.enums;

public enum InitDataEnum {
    SECURITY_SERVER("initServerInfoVos","初始化安全服务器信息列表"),
    SERVER_CONFIG("initServerConfigVo","初始化配置管理信息列表"),
    STATION_MANAGE("initStationManageVos","初始化基站管理信息列表"),
    DNS_MANAGE("initDnsManageVo","初始化DNS管理信息列表"),
    CA_MANAGE("initCaManageVos","初始化CA信息列表信息列表"),

    NETWORK_CARD("initNetworkCardVos","初始化安全服务器关联网卡信息列表");

    private String name;
    private String desc;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    InitDataEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
