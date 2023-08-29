package com.matrictime.network.dao.domain;

public interface ConfigDomainService {

    /**
     * 判断业务是否上报（true:上报 false:不上报）
     * @param businessCode
     * @return
     */
    Boolean isReport(String businessCode);
}
