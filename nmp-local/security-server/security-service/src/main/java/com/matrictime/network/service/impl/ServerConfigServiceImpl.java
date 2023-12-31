package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.dao.mapper.NmpsServerConfigMapper;
import com.matrictime.network.dao.mapper.extend.NmpsServerConfigExtMapper;
import com.matrictime.network.dao.model.NmpsCaManage;
import com.matrictime.network.dao.model.NmpsServerConfig;
import com.matrictime.network.dao.model.NmpsServerConfigExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CaManageVo;
import com.matrictime.network.modelVo.ServerConfigVo;
import com.matrictime.network.req.ServerConfigListReq;
import com.matrictime.network.req.ServerConfigRequest;
import com.matrictime.network.resp.ServerConfigResp;
import com.matrictime.network.service.ServerConfigService;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.*;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@Slf4j
@Service
public class ServerConfigServiceImpl implements ServerConfigService {

    @Resource
    private NmpsServerConfigExtMapper serverConfigExtMapper;

    @Resource
    private NmpsServerConfigMapper serverConfigMapper;

    @Value("${security-proxy.context-path}")
    private String securityProxyPath;

    @Value("${security-proxy.port}")
    private String securityProxyPort;

    @Override
    public Result<Integer> insertServerConfig(ServerConfigRequest serverConfigRequest) {
        Result<Integer> result = new Result<>();
        try {
            int i = 0;
            NmpsServerConfig serverConfig = new NmpsServerConfig();
            BeanUtils.copyProperties(serverConfigRequest,serverConfig);
            //构建条件
            NmpsServerConfigExample serverConfigExample = new NmpsServerConfigExample();
            NmpsServerConfigExample.Criteria criteria = serverConfigExample.createCriteria();
            criteria.andNetworkIdEqualTo(serverConfigRequest.getNetworkId());
            criteria.andConfigCodeEqualTo(serverConfigRequest.getConfigCode());
            criteria.andIsExistEqualTo(true);
            //数据查询
            List<NmpsServerConfig> nmpsServerConfigs = serverConfigMapper.selectByExample(serverConfigExample);
            if(CollectionUtils.isEmpty(nmpsServerConfigs)){
                i = serverConfigMapper.insertSelective(serverConfig);
            }else {
                i = serverConfigMapper.updateByExampleSelective(serverConfig,serverConfigExample);
            }
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertServerConfig:{}",e.getMessage());
        }
        return result;
    }

    /**
     * 删除配置接口
     * @param serverConfigRequest
     * @return
     */
    @Override
    public Result<Integer> deleteServerConfig(ServerConfigRequest serverConfigRequest) {
        Result<Integer> result = new Result<>();
        try {
            NmpsServerConfigExample serverConfigExample = new NmpsServerConfigExample();
            NmpsServerConfigExample.Criteria criteria = serverConfigExample.createCriteria();
            criteria.andNetworkIdEqualTo(serverConfigRequest.getNetworkId());
            criteria.andConfigCodeEqualTo(serverConfigRequest.getConfigCode());
            NmpsServerConfig serverConfig = new NmpsServerConfig();
            BeanUtils.copyProperties(serverConfigRequest,serverConfig);
            serverConfig.setIsExist(false);
            int i = serverConfigMapper.updateByExampleSelective(serverConfig, serverConfigExample);
            result.setResultObj(i);
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setExtendMsg(e.getMessage());
            log.error("deleteServerConfig:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<ServerConfigResp> selectServerConfig(ServerConfigRequest serverConfigRequest) {
        Result<ServerConfigResp> result = new Result<>();
        try {
            List<ServerConfigVo> serverConfigVos = serverConfigExtMapper.selectConfig(serverConfigRequest);
            ServerConfigResp serverConfigResp = new ServerConfigResp();
            serverConfigResp.setList(serverConfigVos);
            result.setResultObj(serverConfigResp);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.error("selectServerConfig:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> synConfig(ServerConfigRequest serverConfigRequest) {
        Result<Integer> result = new Result<>();
        try {
            syncProxy(serverConfigRequest);
            result.setSuccess(true);
            result.setResultObj(1);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> synConfigList(ServerConfigListReq listReq) {
        Result<Integer> result = new Result<>();
        try {
            for (ServerConfigRequest serverConfigRequest: listReq.getList()){
                syncProxy(serverConfigRequest);
            }
            result.setSuccess(true);
            result.setResultObj(1);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> insertBatchServerConfig(ServerConfigListReq listReq) {
        Result<Integer> result = new Result<>();
        try {
            for(ServerConfigRequest request: listReq.getList()){
                int i = 0;
                NmpsServerConfig serverConfig = new NmpsServerConfig();
                BeanUtils.copyProperties(request,serverConfig);
                //构建条件
                NmpsServerConfigExample serverConfigExample = new NmpsServerConfigExample();
                NmpsServerConfigExample.Criteria criteria = serverConfigExample.createCriteria();
                criteria.andNetworkIdEqualTo(request.getNetworkId());
                criteria.andConfigCodeEqualTo(request.getConfigCode());
                criteria.andIsExistEqualTo(true);
                //数据查询
                List<NmpsServerConfig> nmpsServerConfigs = serverConfigMapper.selectByExample(serverConfigExample);
                if(CollectionUtils.isEmpty(nmpsServerConfigs)){
                    i = serverConfigMapper.insertSelective(serverConfig);
                }else {
                    i = serverConfigMapper.updateByExampleSelective(serverConfig,serverConfigExample);
                }
                result.setSuccess(true);
                result.setResultObj(i);
            }
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertBatchServerConfig:{}",e.getMessage());
        }
        return result;
    }

    /**
     * 推送代理
     * @param serverConfigRequest
     */
    private void syncProxy(ServerConfigRequest serverConfigRequest){
        try {
            ServerConfigVo vo = new ServerConfigVo();
            BeanUtils.copyProperties(serverConfigRequest,vo);
//            JSONObject jsonParam = new JSONObject();
//            jsonParam.put("ServerConfigVo",vo);
            String url = HttpClientUtil.getUrl(vo.getComIp(), securityProxyPort, securityProxyPath + SERVER_CONFIG_INSERT_URL);
            HttpClientUtil.post(url,JSONObject.toJSONString(vo));
        }catch (Exception e){
            log.warn("ServerConfigServiceImpl.syncProxy Exception:{}",e);
        }
    }
}
