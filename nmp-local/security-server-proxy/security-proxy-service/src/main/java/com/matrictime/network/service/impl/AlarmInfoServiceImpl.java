package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.domain.AlarmDomainService;
import com.matrictime.network.dao.model.NmpsAlarmInfo;
import com.matrictime.network.model.Result;
import com.matrictime.network.service.AlarmInfoService;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.SystemUtils;
import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/11/10 0010 14:04
 * @desc
 */
@Slf4j
@Service
public class AlarmInfoServiceImpl implements AlarmInfoService {

    @Value("${security-server.ip}")
    private String securityServerIp;

    @Value("${security-server.port}")
    private String securityServerPort;

    @Value("${security-server.context-path}")
    private String securityServerPath;

    @Value("${local.com-ip}")
    private String proxyLocalIp;

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private AlarmDomainService alarmDomainService;

    private static final String ALARM_PUSH_URI = "/alarm/accept";
    private static final String ALARM_JSON_KEY = "alarmInfoList";
    private static final String REDIS_KEY = "redisKey";

    @Override
    public void alarmInfoPush() {
        try {
            //获取本机ip作为redis key 标识
            String redisKey = SystemUtils.getCPUProcessorID() + DataConstants.KEY_SPLIT_UNDERLINE + proxyLocalIp
                    + DataConstants.ALARM_PUSH_LAST_MAXI_ID;
            log.info("获取本机cpu ip作为redis key 标识 {}", redisKey);
            Object lastMaxId = redisTemplate.opsForValue().get(redisKey);
            if (Objects.nonNull(lastMaxId)) {
                //删除上次推送之前的数据
                int thisCount = alarmDomainService.deleteThisTimePushData(Long.valueOf(lastMaxId.toString()));
                log.info("redisKey is:{} last alarmPush lastMaxId is:{} delete count is:{} ", redisKey, lastMaxId, thisCount);
            }
            //查询数据
            List<NmpsAlarmInfo> alarmInfoList = alarmDomainService.queryAlarmList();
            if (CollectionUtils.isEmpty(alarmInfoList)) {
                log.info(" alarmInfoPush alarmInfoList is empty");
                XxlJobHelper.log("alarmInfoPush alarmInfoList is empty");
                return;
            }
            //推送数据
            if(!httpPost(alarmInfoList,redisKey)){
                return;
            }
            //删除此次推送之前的数据 防止redis查询maxId失败
            Long maxAlarmId = alarmInfoList.stream().max(Comparator.comparingLong(NmpsAlarmInfo::getAlarmId)).get().getAlarmId();
            log.info("alarmPush this time maxAlarmId ：{}", maxAlarmId);
            XxlJobHelper.log("alarmPush this time maxAlarmId ：{}", maxAlarmId);
            int deleteCount = alarmDomainService.deleteThisTimePushData(maxAlarmId);
            log.info("alarmPush this time delete data count：{}", deleteCount);
        } catch (Exception e) {
            log.error("DataPushService alarmPush exception:{}", e.getMessage());
            XxlJobHelper.handleFail(e.getMessage());
        }
    }

    /**
     * @title httpPost
     * @param [alarmInfoList]
     * @return void
     * @description
     * @author jiruyi
     * @create 2023/11/13 0013 10:31
     */
    public boolean httpPost(List<NmpsAlarmInfo> alarmInfoList,String redisKey) {
        try {
            String url = HttpClientUtil.getUrl(securityServerIp, securityServerPort, securityServerPath + ALARM_PUSH_URI);
            String post = "";
            JSONObject jsonParam = new JSONObject();
            jsonParam.put(ALARM_JSON_KEY, alarmInfoList);
            jsonParam.put(REDIS_KEY, redisKey);
            post = HttpClientUtil.post(url, jsonParam.toJSONString());
            Result pushResult = JSONObject.parseObject(post, Result.class);
            log.info("AlarmInfoServiceImpl.alarmInfoPush httpPost :{} result :{}",post,pushResult);
            if(ObjectUtils.isEmpty(pushResult) ||  !pushResult.isSuccess()){
                return false;
            }
        } catch (IOException e) {
            log.info("AlarmInfoServiceImpl.alarmInfoPush httpPost error :{}", e);
            return false;
        }
        return true;

    }
}
