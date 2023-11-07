package com.matrictime.network.config;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.SecurityServerEnum;
import com.matrictime.network.dao.mapper.NmpsSecurityServerInfoMapper;
import com.matrictime.network.dao.model.NmpsSecurityServerInfo;
import com.matrictime.network.dao.model.NmpsSecurityServerInfoExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import static com.matrictime.network.base.constant.DataConstants.HEART_REPORT_NETWORKID;
import static com.matrictime.network.constant.DataConstants.*;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/10 0010 16:21
 * @desc
 */
@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Resource
    private NmpsSecurityServerInfoMapper serverInfoMapper;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 监听redis key值变化
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        if(ObjectUtils.isEmpty(message)){
            return;
        }
        try {
            // 寻找key值
            StringBuffer sb = new StringBuffer(SYSTEM_SS);
            sb.append(KEY_SPLIT_MIDLINE);
            sb.append(HEART_REPORT_NETWORKID);
            if(message.toString().contains(sb.toString())){
                log.info("过期的安全服务器心跳key:{}",message);
                // 设备状态修改
                String networkId =(message.toString().split(":"))[NumberUtils.INTEGER_ONE];
                log.info("过期心跳的安全服务器networkId是:{}",networkId);
                // 更新设备状态
                int count = updateServerStatus(networkId);

                log.info("修改过期安全服务器：{},影响的行数：{}",networkId,count);
            }
        }catch (Exception e){
            log.error("监听过期心跳{}，发生异常：{}",message,e.getMessage());
        }

    }

    /**
     * 安全服务器心跳过期，更新设备状态
     * @param networkId
     * @return
     */
    private int updateServerStatus(String networkId){
        NmpsSecurityServerInfo serverInfo = new NmpsSecurityServerInfo();
        serverInfo.setServerStatus(SecurityServerEnum.STATUS_OFFLINE.getCode());
        NmpsSecurityServerInfoExample serverInfoExample = new NmpsSecurityServerInfoExample();
        serverInfoExample.createCriteria().andNetworkIdEqualTo(networkId).andIsExistEqualTo(IS_EXIST);
        int updSeverStatus = serverInfoMapper.updateByExampleSelective(serverInfo, serverInfoExample);
        return updSeverStatus;
    }
}
