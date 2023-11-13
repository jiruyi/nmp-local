package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.mapper.NmpsNetworkCardMapper;
import com.matrictime.network.dao.mapper.NmpsSecurityServerInfoMapper;
import com.matrictime.network.dao.model.NmpsNetworkCard;
import com.matrictime.network.dao.model.NmpsNetworkCardExample;
import com.matrictime.network.dao.model.NmpsSecurityServerInfo;
import com.matrictime.network.dao.model.NmpsSecurityServerInfoExample;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NetworkCardVo;
import com.matrictime.network.modelVo.SecurityServerInfoVo;
import com.matrictime.network.req.InitReq;
import com.matrictime.network.resp.InitResp;
import com.matrictime.network.service.CommonService;
import com.matrictime.network.util.ParamCheckUtil;
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
public class CommonServiceImpl extends SystemBaseService implements CommonService {

    @Resource
    private NmpsSecurityServerInfoMapper serverInfoMapper;

    @Resource
    private NmpsNetworkCardMapper networkCardMapper;
    /**
     * 获取对应代理端安全服务器的相关信息
     * @param req
     * @return
     */
    @Override
    public Result<InitResp> init(InitReq req) {
        Result result;
        try{
            String comIp = req.getComIp();
            if (ParamCheckUtil.checkVoStrBlank(comIp)){
                throw new Exception("ComIp"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            InitResp resp = new InitResp();
            List<String> networkIds = new ArrayList<>();

            NmpsSecurityServerInfoExample serverInfoExample = new NmpsSecurityServerInfoExample();
            serverInfoExample.createCriteria().andComIpEqualTo(comIp).andIsExistEqualTo(IS_EXIST);
            List<NmpsSecurityServerInfo> serverInfos = serverInfoMapper.selectByExample(serverInfoExample);

            List<SecurityServerInfoVo> serverInfoVos = new ArrayList<>();
            for (NmpsSecurityServerInfo info : serverInfos){
                SecurityServerInfoVo vo = new SecurityServerInfoVo();
                BeanUtils.copyProperties(info,vo);
                serverInfoVos.add(vo);
                networkIds.add(info.getNetworkId());
            }
            resp.setInitServerInfoVos(serverInfoVos);

            List<NetworkCardVo> networkCardVos = new ArrayList<>();
            if (!CollectionUtils.isEmpty(networkIds)){
                NmpsNetworkCardExample networkCardExample = new NmpsNetworkCardExample();
                networkCardExample.createCriteria().andNetworkIdIn(networkIds).andIsExistEqualTo(IS_EXIST);
                List<NmpsNetworkCard> networkCards = networkCardMapper.selectByExample(networkCardExample);

                for (NmpsNetworkCard card : networkCards){
                    NetworkCardVo vo = new NetworkCardVo();
                    BeanUtils.copyProperties(card,vo);
                    networkCardVos.add(vo);
                }
            }
            resp.setInitNetworkCardVos(networkCardVos);

            result = buildResult(resp);
        }catch (Exception e){
            log.error("CommonServiceImpl.init Exception:{}",e);
            result = failResult("");
        }
        return result;
    }
}
