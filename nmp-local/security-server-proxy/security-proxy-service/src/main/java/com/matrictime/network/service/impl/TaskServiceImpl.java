package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.dao.mapper.NmpsSecurityServerInfoMapper;
import com.matrictime.network.dao.mapper.NmpsServerHeartInfoMapper;
import com.matrictime.network.dao.model.NmpsServerHeartInfo;
import com.matrictime.network.dao.model.NmpsServerHeartInfoExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.HeartInfoProxyVo;
import com.matrictime.network.modelVo.SecurityServerProxyVo;
import com.matrictime.network.service.SecurityServerService;
import com.matrictime.network.service.TaskService;
import com.matrictime.network.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static com.matrictime.network.base.constant.DataConstants.HEART_REPORT_SPACE;
import static com.matrictime.network.base.constant.DataConstants.KEY_HEART_INFO_VOS;
import static com.matrictime.network.constant.BusinessConsts.*;
import static com.matrictime.network.constant.DataConstants.*;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Resource
    private NmpsServerHeartInfoMapper serverHeartInfoMapper;

    @Autowired
    private SecurityServerService serverService;

    @Value("${security-server.ip}")
    private String securityServerIp;

    @Value("${security-server.port}")
    private String securityServerPort;

    @Value("${security-server.path}")
    private String securityServerPath;



    /**
     * 安全服务器状态上报
     */
    @Override
    public void heartReport(Date excuteTime) {
        // 获取当前代理上配置的安全服务器列表
        List<SecurityServerProxyVo> localServerVos = serverService.getLocalServerVos();
        if (!CollectionUtils.isEmpty(localServerVos)){
            List<HeartInfoProxyVo> heartInfos = new ArrayList<>();

            for (SecurityServerProxyVo serverProxyVo : localServerVos){
                // 查询安全服务器上报状态信息
                NmpsServerHeartInfoExample serverHeartInfoExample = new NmpsServerHeartInfoExample();
                serverHeartInfoExample.setOrderByClause(CREATE_TIME_DESC);
                Date minUploadTime = DateUtils.addSecondsForDate(excuteTime, -HEART_REPORT_SPACE);
                serverHeartInfoExample.createCriteria().andNetworkIdEqualTo(serverProxyVo.getNetworkId()).andCreateTimeLessThan(excuteTime).andCreateTimeGreaterThanOrEqualTo(minUploadTime);
                List<NmpsServerHeartInfo> serverHeartInfos = serverHeartInfoMapper.selectByExample(serverHeartInfoExample);
                if (!CollectionUtils.isEmpty(serverHeartInfos)){
                    HeartInfoProxyVo vo = new HeartInfoProxyVo();
                    BeanUtils.copyProperties(serverHeartInfos.get(ZERO),vo);
                    heartInfos.add(vo);
                }
            }

            // 上报安全服务器管理中心
            if (!CollectionUtils.isEmpty(heartInfos)){
                heartReportToServer(heartInfos);
            }else {
                log.info("TaskServiceImpl.heartReport:当前代理上没有查询到安全服务器有效心跳信息");
            }
        }else {
            log.info("TaskServiceImpl.heartReport:当前代理上没有安全服务器节点信息配置");
        }

        // 清除基站历史状态上报数据
        NmpsServerHeartInfoExample heartInfoExample = new NmpsServerHeartInfoExample();
        heartInfoExample.createCriteria().andCreateTimeLessThan(excuteTime);
        int delete = serverHeartInfoMapper.deleteByExample(heartInfoExample);
        log.info("TaskServiceImpl.heartReport delete:{}",delete);
    }

    /**
     * 安全服务器状态上报安全服务器管理中心
     * @param serverHeartInfos
     */
    private void heartReportToServer(List<HeartInfoProxyVo> serverHeartInfos){
        String url = HttpClientUtil.getUrl(securityServerIp,securityServerPort,securityServerPath);
        String post = "";
        JSONObject jsonParam = new JSONObject();
        jsonParam.put(KEY_HEART_INFO_VOS,serverHeartInfos);
        try {
            post = HttpClientUtil.post(url, jsonParam.toJSONString());
        } catch (IOException e) {
            log.info("TaskServiceImpl.heartReportToServer :{}",post);
        }
        log.info("TaskServiceImpl.heartReportToServer:{}",post);
    }



}
