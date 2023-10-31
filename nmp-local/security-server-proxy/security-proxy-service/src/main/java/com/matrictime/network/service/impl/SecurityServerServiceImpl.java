package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.mapper.NmpsSecurityServerInfoMapper;
import com.matrictime.network.dao.model.NmpsSecurityServerInfo;
import com.matrictime.network.dao.model.NmpsSecurityServerInfoExample;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.SecurityServerProxyVo;
import com.matrictime.network.service.SecurityServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.constant.DataConstants.IS_EXIST;

@Service
@Slf4j
public class SecurityServerServiceImpl extends SystemBaseService implements SecurityServerService {

    @Resource
    private NmpsSecurityServerInfoMapper serverInfoMapper;


    /**
     * 获取当前代理上配置的安全服务器列表
     * @return
     */
    @Override
    public List<SecurityServerProxyVo> getLocalServerVos() {
        List<SecurityServerProxyVo> vos = new ArrayList<>();
        try {
            NmpsSecurityServerInfoExample example = new NmpsSecurityServerInfoExample();
            example.createCriteria().andIsExistEqualTo(IS_EXIST);
            List<NmpsSecurityServerInfo> serverInfos = serverInfoMapper.selectByExample(example);
            for (NmpsSecurityServerInfo info:serverInfos){
                SecurityServerProxyVo vo = new SecurityServerProxyVo();
                BeanUtils.copyProperties(info,vo);
                vos.add(vo);
            }
        }catch (Exception e){
            log.error("SecurityServerServiceImpl.getLocalServerVos Exception:{}",e.getMessage());
        }
        return vos;
    }
}
