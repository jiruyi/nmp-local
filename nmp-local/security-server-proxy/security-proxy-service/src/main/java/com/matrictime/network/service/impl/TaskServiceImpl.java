package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.InitDataEnum;
import com.matrictime.network.dao.mapper.NmpsDataInfoMapper;
import com.matrictime.network.dao.mapper.NmpsNetworkCardMapper;
import com.matrictime.network.dao.mapper.NmpsSecurityServerInfoMapper;
import com.matrictime.network.dao.mapper.NmpsServerHeartInfoMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.modelVo.HeartInfoProxyVo;
import com.matrictime.network.modelVo.NetworkCardProxyVo;
import com.matrictime.network.modelVo.SecurityServerProxyVo;
import com.matrictime.network.req.DataPushReq;
import com.matrictime.network.service.SecurityServerService;
import com.matrictime.network.service.TaskService;
import com.matrictime.network.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.SUCCESS_MSG;
import static com.matrictime.network.constant.DataConstants.ZERO;

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

    @Value("${security-server.context-path}")
    private String securityServerPath;

    @Value("${local.com-ip}")
    private String localComIp;

    @Resource
    private NmpsDataInfoMapper nmpsDataInfoMapper;

    @Resource
    private NmpsSecurityServerInfoMapper serverInfoMapper;

    @Resource
    private NmpsNetworkCardMapper networkCardMapper;

    @Resource
    private RedisTemplate redisTemplate;

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
        String url = HttpClientUtil.getUrl(securityServerIp,securityServerPort,securityServerPath+HEART_REPORT_URL);
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


    @Override
    public void dataPush() {
        try {
            String key = "";
            Object lastMaxId = redisTemplate.opsForValue().get(key);
            NmpsDataInfoExample nmpsDataInfoExample = new NmpsDataInfoExample();
            NmpsDataInfoExample.Criteria criteria = nmpsDataInfoExample.createCriteria();

            if(Objects.nonNull(lastMaxId)){
                //删除上次推送之前的数据
                criteria.andIdLessThanOrEqualTo(Long.valueOf(lastMaxId.toString()));
                int thisCount = nmpsDataInfoMapper.deleteByExample(nmpsDataInfoExample);
                nmpsDataInfoExample.clear();
                log.info(" last dataPush lastMaxId is:{} deletecount is:{} ",lastMaxId,thisCount);
            }

            // 查询所有的统计数据
            nmpsDataInfoExample.setOrderByClause("id desc");
            List<NmpsDataInfo> nmplDataCollectList = nmpsDataInfoMapper.selectByExample(nmpsDataInfoExample);
            nmpsDataInfoExample.clear();
            if(CollectionUtils.isEmpty(nmplDataCollectList)){
                return;
            }
            Long index = nmplDataCollectList.get(0).getId();
            DataPushReq dataPushReq = new DataPushReq();
            dataPushReq.setKey(key);
            dataPushReq.setIndex(String.valueOf(index));
            dataPushReq.setDataInfoVoList(new ArrayList<>());
//            Result result = alarmDataFacade.insertSystemData(dataCollectReq);
//            if(ObjectUtils.isEmpty(result) ||  !result.isSuccess()){
//                return;
//            }

//            criteria.andIdLessThanOrEqualTo(index);
//            nmpsDataInfoMapper.deleteByExample(nmpsDataInfoExample);
        }catch (Exception e){
            log.error("dataPush  exception:{}",e.getMessage());
        }
    }

    /**
     * 初始化本端代理安全服务器相关配置
     */
    @Override
    public void initData() {
        try {
            String initData = getInitData(localComIp);
            if (!ParamCheckUtil.checkVoStrBlank(initData)) {
                JSONObject resp = JSONObject.parseObject(initData);
                if (resp.containsKey(SUCCESS_MSG) && (Boolean) resp.get(SUCCESS_MSG)) {
                    JSONObject resultObj = resp.getJSONObject(RESULT_OBJ_MSG);

                    // 获取代理端安全服务器信息
                    JSONArray serverVos = resultObj.getJSONArray(InitDataEnum.SECURITY_SERVER.getName());
                    List<SecurityServerProxyVo> serverProxyVos = serverVos.toJavaList(SecurityServerProxyVo.class);
                    if (!CollectionUtils.isEmpty(serverProxyVos)){

                        // 初始化安全服务器信息
                        int delServer = serverInfoMapper.deleteByExample(null);
                        log.info("TaskServiceImpl.initData delServer:{}",delServer);
                        for (SecurityServerProxyVo vo:serverProxyVos){
                            NmpsSecurityServerInfo info = new NmpsSecurityServerInfo();
                            BeanUtils.copyProperties(vo,info);
                            int addServer = serverInfoMapper.insertSelective(info);
                            log.info("TaskServiceImpl.initData addServer:{}",addServer);
                        }


                        // 初始化安全服务器关联网卡信息
                        int delNetworkCard = networkCardMapper.deleteByExample(null);
                        log.info("TaskServiceImpl.initData delNetworkCard:{}",delNetworkCard);
                        JSONArray networkCardVos = resultObj.getJSONArray(InitDataEnum.NETWORK_CARD.getName());
                        List<NetworkCardProxyVo> networkCardProxyVos = networkCardVos.toJavaList(NetworkCardProxyVo.class);
                        if (!CollectionUtils.isEmpty(networkCardProxyVos)){
                            for (NetworkCardProxyVo vo:networkCardProxyVos){
                                NmpsNetworkCard card = new NmpsNetworkCard();
                                BeanUtils.copyProperties(vo,card);
                                int addCard = networkCardMapper.insertSelective(card);
                                log.info("TaskServiceImpl.initData addCard:{}",addCard);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("TaskServiceImpl.initData Exception:{}",e.getMessage());
        }
    }

    /**
     * 从安全服务器管理中心获取初始化数据
     * @param localComIp
     * @return
     * @throws Exception
     */
    private String getInitData(String localComIp) throws Exception{
        String url = HttpClientUtil.getUrl(securityServerIp,securityServerPort,securityServerPath+HEART_INIT_URL);
        JSONObject jsonParam = new JSONObject();
        jsonParam.put(KEY_COM_IP,localComIp);
        String post = HttpClientUtil.post(url, jsonParam.toJSONString());
        log.info("TaskServiceImpl.getInitData http post url:{},req:{},resp:{}",url,jsonParam.toJSONString(),post);
        return post;
    }
}
