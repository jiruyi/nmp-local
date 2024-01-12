package com.matrictime.network.dao.domain;

import com.matrictime.network.dao.model.NmplProxyConfig;

import java.util.List;

public interface ProxyConfigDomainService {

    int addOrUpdateProxyConfig(NmplProxyConfig config);

    List<String> getLocalIps();
}
