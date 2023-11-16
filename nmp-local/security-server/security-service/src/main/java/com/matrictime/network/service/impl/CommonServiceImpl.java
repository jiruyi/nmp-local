package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.mapper.extend.NmpsServerConfigExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.req.InitReq;
import com.matrictime.network.req.ServerConfigRequest;
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

    @Resource
    private NmpsStationManageMapper stationManageMapper;

    @Resource
    private NmpsDnsManageMapper dnsManageMapper;

    @Resource
    private NmpsCaManageMapper caManageMapper;

    @Resource
    private NmpsServerConfigExtMapper serverConfigExtMapper;


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

            //基站管理查询
            List<StationManageVo> stationManageVos = new ArrayList<>();
            if(!CollectionUtils.isEmpty(networkIds)){
                NmpsStationManageExample stationManageExample = new NmpsStationManageExample();
                stationManageExample.createCriteria().andIsExistEqualTo(true).andNetworkIdIn(networkIds);
                List<NmpsStationManage> nmpsStationManages = stationManageMapper.selectByExample(stationManageExample);

                for(NmpsStationManage stationManage: nmpsStationManages){
                    StationManageVo stationManageVo = new StationManageVo();
                    BeanUtils.copyProperties(stationManage,stationManageVo);
                    stationManageVos.add(stationManageVo);
                }
            }
            resp.setInitStationManageVos(stationManageVos);

            //ca管理查询
            List<CaManageVo> caManageVos = new ArrayList<>();
            if(!CollectionUtils.isEmpty(networkIds)){
                NmpsCaManageExample caManageExample = new NmpsCaManageExample();
                caManageExample.createCriteria().andIsExistEqualTo(true).andNetworkIdIn(networkIds);
                List<NmpsCaManage> nmpsCaManages = caManageMapper.selectByExample(caManageExample);

                for(NmpsCaManage caManage: nmpsCaManages){
                    CaManageVo caManageVo = new CaManageVo();
                    BeanUtils.copyProperties(caManage,caManageVo);
                    caManageVos.add(caManageVo);
                }
            }
            resp.setInitCaManageVos(caManageVos);

            //dns管理查询
            List<DnsManageVo> dnsManageVos = new ArrayList<>();
            if(!CollectionUtils.isEmpty(networkIds)){
                NmpsDnsManageExample dnsManageExample = new NmpsDnsManageExample();
                dnsManageExample.createCriteria().andIsExistEqualTo(true).andNetworkIdIn(networkIds);
                List<NmpsDnsManage> nmpsDnsManages = dnsManageMapper.selectByExample(dnsManageExample);

                for(NmpsDnsManage dnsManage: nmpsDnsManages){
                    DnsManageVo dnsManageVo = new DnsManageVo();
                    BeanUtils.copyProperties(dnsManage,dnsManageVo);
                    dnsManageVos.add(dnsManageVo);
                }
            }
            resp.setInitDnsManageVo(dnsManageVos);

            //配置管理查询
            List<ServerConfigVo> serverConfigVos = new ArrayList<>();
            if(!CollectionUtils.isEmpty(networkIds)){
                ServerConfigRequest serverConfigRequest = new ServerConfigRequest();
                serverConfigRequest.setNetworkId(networkIds.get(0));
                serverConfigVos = serverConfigExtMapper.selectConfig(serverConfigRequest);
            }
            resp.setInitServerConfigVo(serverConfigVos);
            result = buildResult(resp);
        }catch (Exception e){
            log.error("CommonServiceImpl.init Exception:{}",e);
            result = failResult("");
        }
        return result;
    }
}
