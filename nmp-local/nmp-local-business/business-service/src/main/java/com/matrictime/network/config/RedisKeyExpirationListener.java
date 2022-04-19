package com.matrictime.network.config;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.DeviceLevelEnum;
import com.matrictime.network.base.enums.DeviceStatusEnum;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplDeviceInfoMapper;
import com.matrictime.network.dao.mapper.NmplUserMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.VoiceCallRequest;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.KEY_DEVICE_ID;
import static com.matrictime.network.base.exception.ErrorMessageContants.DEVICE_NOT_EXIST_MSG;
import static com.matrictime.network.constant.DataConstants.IS_EXIST;

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

    @Autowired(required = false)
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Autowired(required = false)
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Autowired(required = false)
    private NmplUserMapper nmplUserMapper;

    @Autowired
    RestTemplate restTemplate;

    @LoadBalanced
    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }

    @Value("${voice.url}")
    private String url;

    @Value("${voice.code.down}")
    private String voiceCode;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        if(ObjectUtils.isEmpty(message)){
            return;
        }
        try {
            //1.0 寻找key值
            if(message.toString().contains(DataConstants.HEART_CHECK_DEVICE_ID)){
                log.info("过期的设备心跳key:{}",message.toString());
                //2.0设备状态修改
                String deviceId =(message.toString().split(":"))[NumberUtils.INTEGER_ONE];
                log.info("过期心跳的设备id是:{}",deviceId);
                // 更新设备状态
                int count = updateDeviceStatus(deviceId);

                // 查询设备创建人
                String userId = "";
                List<String> phoneNumbers = new ArrayList<>();
                NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
                example.createCriteria().andStationIdEqualTo(deviceId).andIsExistEqualTo(IS_EXIST);
                List<NmplBaseStationInfo> baseStationInfos = nmplBaseStationInfoMapper.selectByExample(example);
                if(!CollectionUtils.isEmpty(baseStationInfos)){
                    userId = baseStationInfos.get(0).getCreateUser();
                }else{
                    NmplDeviceInfoExample nmplDeviceInfoExample = new NmplDeviceInfoExample();
                    nmplDeviceInfoExample.createCriteria().andDeviceIdEqualTo(deviceId).andIsExistEqualTo(IS_EXIST);
                    List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(nmplDeviceInfoExample);
                    if(!CollectionUtils.isEmpty(nmplDeviceInfos)){
                        userId = nmplDeviceInfos.get(0).getCreateUser();
                    }
                }

                if (ParamCheckUtil.checkVoStrBlank(userId)) {
                    log.info("设备创建人为空");
                    return;
                }else{
                    NmplUser nmplUser = nmplUserMapper.selectByPrimaryKey(Long.parseLong(userId));
                    if(!ObjectUtils.isEmpty(nmplUser)){
                        String phoneNumber = nmplUser.getPhoneNumber();
                        phoneNumbers.add(phoneNumber);
                    }else {
                        log.info("设备创建人不存在");
                        return;
                    }

                }

                //语音呼叫
                JSONObject ttsParam = new JSONObject();
                //模板参数
                ttsParam.put(KEY_DEVICE_ID,deviceId);
                VoiceCallRequest voiceCallRequest =  VoiceCallRequest.builder()
                        .ttsCode(voiceCode)
                        .ttsParam(ttsParam.toJSONString())
                        .calledNumberList(phoneNumbers).build();

                restTemplate.postForEntity(url,voiceCallRequest,Result.class);


                log.info("修改过期设备：{},影响的行数：{}",deviceId,count);
            }
        }catch (Exception e){
            log.error("监听过期心跳{}，发生异常：{}",message,e.getMessage());
        }

    }

    private int updateDeviceStatus(String deviceId){
        NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
        example.createCriteria().andStationIdEqualTo(deviceId).andIsExistEqualTo(IS_EXIST);
        List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(stationInfos)){
            NmplDeviceInfoExample dExample = new NmplDeviceInfoExample();
            dExample.createCriteria().andDeviceIdEqualTo(deviceId).andIsExistEqualTo(IS_EXIST);
            List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(dExample);
            if (CollectionUtils.isEmpty(deviceInfos)){
                log.info("过期心跳的设备id是:{},"+DEVICE_NOT_EXIST_MSG,deviceId);
            }else {
                NmplDeviceInfo deviceInfo = new NmplDeviceInfo();
                deviceInfo.setId(deviceInfos.get(0).getId());
                deviceInfo.setStationStatus(DataConstants.STATION_STATUS_DOWN);
                return nmplDeviceInfoMapper.updateByPrimaryKeySelective(deviceInfo);
            }
        }else {
            NmplBaseStationInfo baseStationInfo = new NmplBaseStationInfo();
            baseStationInfo.setId(stationInfos.get(0).getId());
            baseStationInfo.setStationStatus(DataConstants.STATION_STATUS_DOWN);
            return nmplBaseStationInfoMapper.updateByPrimaryKeySelective(baseStationInfo);
        }
        return 0;
    }
}
