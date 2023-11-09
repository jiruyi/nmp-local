package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.SecurityServerEnum;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.mapper.NmpsNetworkCardMapper;
import com.matrictime.network.dao.mapper.NmpsSecurityServerInfoMapper;
import com.matrictime.network.dao.mapper.NmpsStationManageMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.HeartInfoVo;
import com.matrictime.network.modelVo.NetworkCardVo;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.modelVo.SecurityServerInfoVo;
import com.matrictime.network.req.EditServerReq;
import com.matrictime.network.req.HeartReportReq;
import com.matrictime.network.req.QueryServerReq;
import com.matrictime.network.req.StartServerReq;
import com.matrictime.network.service.ServerService;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.exception.ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG;

@Service
@Slf4j
public class ServerServiceImpl extends SystemBaseService implements ServerService {

    @Value("${health.deadline.time}")
    private Long healthDeadlineTime;

    @Value("${security-proxy.context-path}")
    private String securityProxyPath;

    @Value("${security-proxy.port}")
    private String securityProxyPort;

    @Resource
    private NmpsSecurityServerInfoMapper serverInfoMapper;

    @Resource
    private NmpsNetworkCardMapper networkCardMapper;

    @Resource
    private NmpsStationManageMapper stationManageMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询安全服务器列表（分页）
     * @return
     */
    @Override
    public Result<PageInfo<SecurityServerInfoVo>> queryServerByPage(QueryServerReq req){
        Result result;
        try {

            Page page = PageHelper.startPage(req.getPageNo(),req.getPageSize());

            // 根据条件查询密钥中心分配列表
            NmpsSecurityServerInfoExample example = new NmpsSecurityServerInfoExample();
            NmpsSecurityServerInfoExample.Criteria criteria = example.createCriteria();
            if (!ParamCheckUtil.checkVoStrBlank(req.getServerName())){
                criteria.andServerNameEqualTo(req.getServerName());
            }
            if (!ParamCheckUtil.checkVoStrBlank(req.getNetworkId())){
                criteria.andNetworkIdEqualTo(req.getNetworkId());
            }
            if (!ParamCheckUtil.checkVoStrBlank(req.getComIp())){
                criteria.andComIpEqualTo(req.getComIp());
            }
            criteria.andIsExistEqualTo(IS_EXIST);

            List<NmpsSecurityServerInfo> serverInfos = serverInfoMapper.selectByExample(example);
            List<SecurityServerInfoVo> serverInfoVos = new ArrayList<>();
            for (NmpsSecurityServerInfo info : serverInfos){
                SecurityServerInfoVo vo = new SecurityServerInfoVo();
                BeanUtils.copyProperties(info,vo);
                NmpsNetworkCardExample cardExample = new NmpsNetworkCardExample();
                NmpsNetworkCardExample.Criteria cardExampleCriteria = cardExample.createCriteria();
                if (!ParamCheckUtil.checkVoStrBlank(req.getAdapterName())){
                    cardExampleCriteria.andAdapterNameEqualTo(req.getAdapterName());
                }
                if (!ParamCheckUtil.checkVoStrBlank(req.getNetCardType())){
                    cardExampleCriteria.andNetCardTypeEqualTo(req.getNetCardType());
                }
                cardExampleCriteria.andIsExistEqualTo(IS_EXIST);
                List<NmpsNetworkCard> networkCards = networkCardMapper.selectByExample(cardExample);
                List<NetworkCardVo> networkCardVos = new ArrayList<>();
                for (NmpsNetworkCard card : networkCards){
                    NetworkCardVo cardVo = new NetworkCardVo();
                    BeanUtils.copyProperties(card,cardVo);
                    networkCardVos.add(cardVo);
                }
                vo.setNetworkCardVos(networkCardVos);
                serverInfoVos.add(vo);
            }

            PageInfo<SecurityServerInfoVo> pageResult = new PageInfo<>();
            pageResult.setList(serverInfoVos);
            pageResult.setCount((int) page.getTotal());
            pageResult.setPages(page.getPages());
            result = buildResult(pageResult);
        }catch (SystemException e){
            log.error("ServerServiceImpl.queryServerByPage SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.queryServerByPage Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 查询安全服务器列表（不分页）
     * @return
     */
    @Override
    public Result<List<SecurityServerInfoVo>> queryServer(QueryServerReq req){
        Result result;
        try {
            // 根据条件查询密钥中心分配列表
            NmpsSecurityServerInfoExample example = new NmpsSecurityServerInfoExample();
            NmpsSecurityServerInfoExample.Criteria criteria = example.createCriteria();
            if (!ParamCheckUtil.checkVoStrBlank(req.getServerName())){
                criteria.andServerNameEqualTo(req.getServerName());
            }
            if (!ParamCheckUtil.checkVoStrBlank(req.getNetworkId())){
                criteria.andNetworkIdEqualTo(req.getNetworkId());
            }
            if (!ParamCheckUtil.checkVoStrBlank(req.getComIp())){
                criteria.andComIpEqualTo(req.getComIp());
            }
            criteria.andIsExistEqualTo(IS_EXIST);

            List<NmpsSecurityServerInfo> serverInfos = serverInfoMapper.selectByExample(example);
            List<SecurityServerInfoVo> serverInfoVos = new ArrayList<>();
            for (NmpsSecurityServerInfo info : serverInfos){
                SecurityServerInfoVo vo = new SecurityServerInfoVo();
                BeanUtils.copyProperties(info,vo);
                NmpsNetworkCardExample cardExample = new NmpsNetworkCardExample();
                NmpsNetworkCardExample.Criteria cardExampleCriteria = cardExample.createCriteria();
                if (!ParamCheckUtil.checkVoStrBlank(req.getAdapterName())){
                    cardExampleCriteria.andAdapterNameEqualTo(req.getAdapterName());
                }
                if (!ParamCheckUtil.checkVoStrBlank(req.getNetCardType())){
                    cardExampleCriteria.andNetCardTypeEqualTo(req.getNetCardType());
                }
                cardExampleCriteria.andIsExistEqualTo(IS_EXIST);
                List<NmpsNetworkCard> networkCards = networkCardMapper.selectByExample(cardExample);
                List<NetworkCardVo> networkCardVos = new ArrayList<>();
                for (NmpsNetworkCard card : networkCards){
                    NetworkCardVo cardVo = new NetworkCardVo();
                    BeanUtils.copyProperties(card,cardVo);
                    networkCardVos.add(cardVo);
                }
                vo.setNetworkCardVos(networkCardVos);
                serverInfoVos.add(vo);
            }
            result = buildResult(serverInfoVos);
        }catch (SystemException e){
            log.error("ServerServiceImpl.queryServer SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.queryServer Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 编辑安全服务器
     * @param req
     * @return
     */
    @Override
    @Transactional
    public Result editServer(EditServerReq req){
        Result result;
        try {
            // check param is legal
            checkEditServerParam(req);
            Date now = new Date();
            switch (req.getEditType()){
                // 批量插入
                case EDIT_TYPE_ADD:
                    for (SecurityServerInfoVo vo : req.getSecurityServerInfoVos()){
                        checkAddServerParam(vo);
                        initSecurityServerVo(vo,now,EDIT_TYPE_ADD);
                        NmpsSecurityServerInfo serverInfo = new NmpsSecurityServerInfo();
                        BeanUtils.copyProperties(vo,serverInfo);
                        // 插入安全服务器信息表
                        int addServer = serverInfoMapper.insertSelective(serverInfo);
                        vo.setId(serverInfo.getId());
                        log.info("插入安全服务器信息表:{}",addServer);

                        // 插入安全服务器关联网卡表
                        int addCards = 0;
                        List<NetworkCardVo> cardVos = new ArrayList<>();
                        List<NetworkCardVo> networkCardVos = vo.getNetworkCardVos();
                        for (int i=0;i<networkCardVos.size();i++){
                            NetworkCardVo cardVo = networkCardVos.get(i);
                            initNetworkCardVo(cardVo,now,EDIT_TYPE_ADD);
                            NmpsNetworkCard card = new NmpsNetworkCard();
                            BeanUtils.copyProperties(cardVo,card);
                            addCards = addCards + networkCardMapper.insertSelective(card);
                            cardVo.setId(card.getId());
                            cardVos.add(cardVo);
                        }
                        log.info("插入安全服务器关联网卡表:{}",addCards);

                        // 同步代理
                        vo.setNetworkCardVos(cardVos);
                        syncProxy(vo,EDIT_TYPE_ADD);
                    }
                    break;
                case EDIT_TYPE_UPD://批量修改
                    for (SecurityServerInfoVo vo : req.getSecurityServerInfoVos()){
                        // 校验id是否为空
                        if (vo.getId() == null){
                            throw new Exception("SecurityServerInfoVos.id"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmpsSecurityServerInfo serverInfo = serverInfoMapper.selectByPrimaryKey(vo.getId());
                        if (serverInfo == null){
                            throw new Exception("serverInfo"+ ErrorMessageContants.DATA_CANNOT_FIND_INDB);
                        }
                        String networkId = serverInfo.getNetworkId();
                        checkUpdateServerParam(vo);

                        // 更新安全服务器信息表
                        NmpsSecurityServerInfo server = new NmpsSecurityServerInfo();
                        vo.setNetworkId(networkId);
                        initSecurityServerVo(vo,now,EDIT_TYPE_UPD);
                        BeanUtils.copyProperties(vo,server);
                        int updServer = serverInfoMapper.updateByPrimaryKeySelective(server);
                        log.info("更新安全服务器信息表:{}",updServer);

                        // 更新安全服务器关联网卡表（先删后增）
                        NmpsNetworkCardExample deleteExample = new NmpsNetworkCardExample();
                        deleteExample.createCriteria().andNetworkIdNotEqualTo(networkId).andIsExistEqualTo(IS_EXIST);
                        NmpsNetworkCard networkCard = new NmpsNetworkCard();
                        networkCard.setIsExist(IS_NOT_EXIST);
                        int delCards = networkCardMapper.updateByExampleSelective(networkCard, deleteExample);
                        log.info("更新安全服务器关联网卡表（先删后增）:{}",delCards);

                        int updCards = 0;
                        List<NetworkCardVo> cardVos = new ArrayList<>();
                        List<NetworkCardVo> networkCardVos = vo.getNetworkCardVos();
                        for (int i=0;i<networkCardVos.size();i++){
                            NetworkCardVo cardVo = networkCardVos.get(i);
                            initNetworkCardVo(cardVo,now,EDIT_TYPE_UPD);
                            NmpsNetworkCard card = new NmpsNetworkCard();
                            BeanUtils.copyProperties(cardVo,card);
                            updCards = updCards + networkCardMapper.insertSelective(card);
                            cardVo.setId(card.getId());
                            cardVos.add(cardVo);
                        }
                        log.info("更新安全服务器关联网卡表（先删后增）:{}",updCards);

                        // 同步代理
                        vo.setNetworkCardVos(cardVos);
                        syncProxy(vo,EDIT_TYPE_UPD);
                    }
                    break;
                case EDIT_TYPE_DEL:// 逻辑删除
                    for (SecurityServerInfoVo vo : req.getSecurityServerInfoVos()){
                        // 校验id是否为空
                        if (vo.getId() == null){
                            throw new Exception("SecurityServerInfoVos.id"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmpsSecurityServerInfo serverInfo = serverInfoMapper.selectByPrimaryKey(vo.getId());
                        if (serverInfo == null){
                            throw new Exception("serverInfo"+ ErrorMessageContants.DATA_CANNOT_FIND_INDB);
                        }

                        // 判断是否关联了相关基站
                        checkServerIsRelStation(serverInfo.getNetworkId());
                        // 逻辑删除安全服务器信息表
                        NmpsSecurityServerInfo server = new NmpsSecurityServerInfo();
                        initSecurityServerVo(vo,now,EDIT_TYPE_DEL);
                        vo.setNetworkId(serverInfo.getNetworkId());
                        BeanUtils.copyProperties(vo,server);
                        int delServer = serverInfoMapper.updateByPrimaryKeySelective(server);
                        log.info("逻辑删除安全服务器信息表:{}",delServer);

                        // 逻辑删除安全服务器关联网卡表
                        NmpsNetworkCardExample deleteExample = new NmpsNetworkCardExample();
                        deleteExample.createCriteria().andNetworkIdNotEqualTo(serverInfo.getNetworkId()).andIsExistEqualTo(IS_EXIST);
                        NmpsNetworkCard networkCard = new NmpsNetworkCard();
                        networkCard.setIsExist(IS_NOT_EXIST);
                        int delCards = networkCardMapper.updateByExampleSelective(networkCard, deleteExample);
                        log.info("逻辑删除安全服务器关联网卡表:{}",delCards);

                        // 同步代理
                        syncProxy(vo,EDIT_TYPE_PHY_DEL);
                    }
                    break;
                default:
                    throw new Exception("EditType"+ PARAM_IS_UNEXPECTED_MSG);
            }
            result = buildResult(null);
        }catch (SystemException e){
            log.error("ServerServiceImpl.editServer SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.editServer Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    /**
     * 启动安全服务器
     * @param req
     * @return
     */
    @Override
    public Result startServer(StartServerReq req){
        Result result;
        try {
            if(!CollectionUtils.isEmpty(req.getIds())){
                throw new Exception("Ids"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            for (Long id : req.getIds()){
                NmpsSecurityServerInfo serverInfo = serverInfoMapper.selectByPrimaryKey(id);
                if (serverInfo == null){
                    throw new Exception("serverInfo"+ ErrorMessageContants.DATA_CANNOT_FIND_INDB);
                }
                JSONObject jsonParam = new JSONObject();
                jsonParam.put(JSON_KEY_NETWORKID,serverInfo.getNetworkId());
                String url = HttpClientUtil.getUrl(serverInfo.getComIp(), securityProxyPort, securityProxyPath + SERVER_STARTSERVER_URL);
                String post = HttpClientUtil.post(url, jsonParam.toJSONString());
                log.info("ServerServiceImpl.startServer httpPost param:{};result:{}",jsonParam.toJSONString(),post);
            }

            result = buildResult(null);
        }catch (SystemException e){
            log.error("ServerServiceImpl.startServer SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.startServer Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 安全服务器状态上报
     * @param req
     * @return
     */
    @Override
    public Result heartReport(HeartReportReq req) {
        Result result;
        try {
            // 校验入参
            checkHeartReportParam(req);
            for (HeartInfoVo vo :req.getHeartInfoVos()){
                // 校验入参是否为空
                if (ParamCheckUtil.checkVoStrBlank(vo.getNetworkId()) || vo.getServerStatus() == null || vo.getCreateTime() == null){
                    log.warn("ServerServiceImpl.heartReport param is blank warn,param:{}",vo);
                    continue;
                }

                // 插入缓存
                StringBuffer sb = new StringBuffer(SYSTEM_SS);
                sb.append(KEY_SPLIT_MIDLINE);
                sb.append(HEART_REPORT_NETWORKID);
                sb.append(vo.getNetworkId());
                redisTemplate.opsForValue().set(sb.toString(),vo.getServerStatus(),healthDeadlineTime, TimeUnit.SECONDS);

                // 更新安全服务器状态
                NmpsSecurityServerInfo serverInfo = new NmpsSecurityServerInfo();
                serverInfo.setServerStatus(SecurityServerEnum.STATUS_ONLINE.getCode());
                NmpsSecurityServerInfoExample serverInfoExample = new NmpsSecurityServerInfoExample();
                serverInfoExample.createCriteria().andNetworkIdEqualTo(vo.getNetworkId()).andIsExistEqualTo(IS_EXIST);
                int updSeverStatus = serverInfoMapper.updateByExampleSelective(serverInfo, serverInfoExample);

                log.info("ServerServiceImpl.heartReport updateServerStatus result:{}",updSeverStatus);
            }


            result = buildResult(null);
        }catch (Exception e){
            log.error("ServerServiceImpl.heartReport Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }


    /**
     * 安全服务器信息同步代理
     * @param vo
     * @param editType
     */
    private void syncProxy(SecurityServerInfoVo vo,String editType){
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put(JSON_KEY_EDITTYPE,editType);
            List<SecurityServerInfoVo> vos = new ArrayList<>();
            vos.add(vo);
            jsonParam.put(JSON_KEY_SECURITYSERVERINFOVOS,vos);

            String url = HttpClientUtil.getUrl(vo.getComIp(), securityProxyPort, securityProxyPath + SERVER_UPDATE_URL);
            String post = HttpClientUtil.post(url, jsonParam.toJSONString());
            log.info("ServerServiceImpl.syncProxy httpPost url:{};param:{};result:{}",url,jsonParam.toJSONString(),post);
        }catch (Exception e){
            log.warn("ServerServiceImpl.syncProxy Exception:{}",e);
        }
    }

    private void checkEditServerParam(EditServerReq req) throws Exception{
        // 校验操作类型入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new Exception("EditType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    /**
     * 校验新增安全服务器信息参数是否合法
     * @param vo
     * @throws SystemException
     */
    private void checkAddServerParam(SecurityServerInfoVo vo) throws Exception{
        // 校验插入安全服务器信息表数据是否合法开始
        if (ParamCheckUtil.checkVoStrBlank(vo.getServerName())){
            throw new Exception("ServerName"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getNetworkId())){
            throw new Exception("NetworkId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getComIp())){
            throw new Exception("ComIp"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getConnectType())){
            throw new Exception("ConnectType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }

        if (CollectionUtils.isEmpty(vo.getNetworkCardVos())){
            throw new Exception("NetworkCardVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }

        NmpsSecurityServerInfoExample checkServerNameExample = new NmpsSecurityServerInfoExample();
        checkServerNameExample.createCriteria().andServerNameEqualTo(vo.getServerName()).andIsExistEqualTo(IS_EXIST);
        List<NmpsSecurityServerInfo> serverNameInfos = serverInfoMapper.selectByExample(checkServerNameExample);
        if (!CollectionUtils.isEmpty(serverNameInfos)){
            throw new SystemException("服务器名称已存在，请重新输入");
        }

        NmpsSecurityServerInfoExample checkNetworkIdExample = new NmpsSecurityServerInfoExample();
        checkNetworkIdExample.createCriteria().andNetworkIdEqualTo(vo.getNetworkId()).andIsExistEqualTo(IS_EXIST);
        List<NmpsSecurityServerInfo> networkIdInfos = serverInfoMapper.selectByExample(checkNetworkIdExample);
        if (!CollectionUtils.isEmpty(networkIdInfos)){
            throw new SystemException("入网码ID已存在，请重新输入");
        }

        NmpsSecurityServerInfoExample checkComIpExample = new NmpsSecurityServerInfoExample();
        checkComIpExample.createCriteria().andComIpEqualTo(vo.getComIp()).andIsExistEqualTo(IS_EXIST);
        List<NmpsSecurityServerInfo> comIpInfos = serverInfoMapper.selectByExample(checkComIpExample);
        if (!CollectionUtils.isEmpty(comIpInfos)){
            throw new SystemException("该通信ip已配置安全服务器，请重新输入");
        }
        // 校验插入安全服务器信息表数据是否合法结束

        // 校验插入安全服务器关联网卡信息表数据是否合法开始
        List<String> ipv4s = new ArrayList<>();
        List<String> ipv6s = new ArrayList<>();
        for (NetworkCardVo cardVo : vo.getNetworkCardVos()){
            if (ParamCheckUtil.checkVoStrBlank(cardVo.getNetCardType())){
                throw new Exception("NetCardType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (ParamCheckUtil.checkVoStrBlank(cardVo.getAdapterName())){
                throw new Exception("AdapterName"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (ParamCheckUtil.checkVoStrBlank(cardVo.getIpv4()) && ParamCheckUtil.checkVoStrBlank(cardVo.getIpv6())){
                throw new Exception("Ipv4|Ipv6"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (!ParamCheckUtil.checkVoStrBlank(cardVo.getIpv4())){
                ipv4s.add(cardVo.getIpv4());
            }
            if (!ParamCheckUtil.checkVoStrBlank(cardVo.getIpv6())){
                ipv6s.add(cardVo.getIpv6());
            }
        }

        NmpsNetworkCardExample cardExample = new NmpsNetworkCardExample();
        cardExample.or().andIpv4In(ipv4s).andIsExistEqualTo(IS_EXIST);
        cardExample.or().andIpv6In(ipv6s).andIsExistEqualTo(IS_EXIST);
        List<NmpsNetworkCard> networkCards = networkCardMapper.selectByExample(cardExample);
        if (!CollectionUtils.isEmpty(networkCards)){
            throw new SystemException("ipv4或ipv6已存在，请重新输入");
        }
        // 校验插入安全服务器关联网卡信息表数据是否合法结束
    }


    /**
     * 校验更新安全服务器信息参数是否合法
     * @param vo
     * @throws SystemException
     */
    private void checkUpdateServerParam(SecurityServerInfoVo vo) throws Exception{
        // 校验插入安全服务器信息表数据是否合法开始
        if (ParamCheckUtil.checkVoStrBlank(vo.getServerName())){
            throw new Exception("ServerName"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getConnectType())){
            throw new Exception("ConnectType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }

        if (CollectionUtils.isEmpty(vo.getNetworkCardVos())){
            throw new Exception("NetworkCardVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }

        NmpsSecurityServerInfoExample checkServerNameExample = new NmpsSecurityServerInfoExample();
        checkServerNameExample.createCriteria().andServerNameEqualTo(vo.getServerName()).andNetworkIdNotEqualTo(vo.getNetworkId()).andIsExistEqualTo(IS_EXIST);
        List<NmpsSecurityServerInfo> serverNameInfos = serverInfoMapper.selectByExample(checkServerNameExample);
        if (!CollectionUtils.isEmpty(serverNameInfos)){
            throw new SystemException("服务器名称已存在，请重新输入");
        }
        // 校验插入安全服务器信息表数据是否合法结束

        // 校验插入安全服务器关联网卡信息表数据是否合法开始
        List<String> ipv4s = new ArrayList<>();
        List<String> ipv6s = new ArrayList<>();
        for (NetworkCardVo cardVo : vo.getNetworkCardVos()){
            if (ParamCheckUtil.checkVoStrBlank(cardVo.getNetCardType())){
                throw new Exception("NetCardType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (ParamCheckUtil.checkVoStrBlank(cardVo.getAdapterName())){
                throw new Exception("AdapterName"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (ParamCheckUtil.checkVoStrBlank(cardVo.getIpv4()) && ParamCheckUtil.checkVoStrBlank(cardVo.getIpv6())){
                throw new Exception("Ipv4|Ipv6"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (!ParamCheckUtil.checkVoStrBlank(cardVo.getIpv4())){
                ipv4s.add(cardVo.getIpv4());
            }
            if (!ParamCheckUtil.checkVoStrBlank(cardVo.getIpv6())){
                ipv6s.add(cardVo.getIpv6());
            }
        }

        NmpsNetworkCardExample cardExample = new NmpsNetworkCardExample();
        cardExample.or().andIpv4In(ipv4s).andNetworkIdNotEqualTo(vo.getNetworkId()).andIsExistEqualTo(IS_EXIST);
        cardExample.or().andIpv6In(ipv6s).andNetworkIdNotEqualTo(vo.getNetworkId()).andIsExistEqualTo(IS_EXIST);
        List<NmpsNetworkCard> networkCards = networkCardMapper.selectByExample(cardExample);
        if (!CollectionUtils.isEmpty(networkCards)){
            throw new SystemException("ipv4或ipv6已存在，请重新输入");
        }
        // 校验插入安全服务器关联网卡信息表数据是否合法结束
    }

    // 安全服务器状态上报入参校验
    private void checkHeartReportParam(HeartReportReq req) {
        if (CollectionUtils.isEmpty(req.getHeartInfoVos())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "HeartInfoVos"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    /**
     * 校验安全服务器是否配置了基站
     * @param networkId
     */
    private void checkServerIsRelStation(String networkId){
        NmpsStationManageExample example = new NmpsStationManageExample();
        example.createCriteria().andNetworkIdEqualTo(networkId).andIsExistEqualTo(IS_EXIST);
        List<NmpsStationManage> stationManages = stationManageMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(stationManages)){
            throw new SystemException("当前安全服务器有关联基站，请先删除基站信息");
        }
    }

    /**
     * 设置安全服务器默认值
     * @param vo
     * @param operDate
     * @param editType
     */
    private void initSecurityServerVo(SecurityServerInfoVo vo,Date operDate,String editType){
        switch (editType){
            case EDIT_TYPE_ADD:
                vo.setCreateTime(operDate);
                vo.setUpdateTime(operDate);
                vo.setServerStatus(SecurityServerEnum.STATUS_OFFLINE.getCode());
                vo.setCreateUser(RequestContext.getUser().getUserId().toString());
                vo.setUpdateUser(RequestContext.getUser().getUserId().toString());
                vo.setIsExist(IS_EXIST);
                break;
            case EDIT_TYPE_UPD:
                vo.setCreateTime(null);
                vo.setIsExist(null);
                vo.setComIp(null);
                vo.setUpdateTime(operDate);
                vo.setUpdateUser(RequestContext.getUser().getUserId().toString());
            case EDIT_TYPE_DEL:
                vo.setUpdateTime(operDate);
                vo.setUpdateUser(RequestContext.getUser().getUserId().toString());
                vo.setIsExist(IS_NOT_EXIST);
            default:
                break;
        }
    }

    /**
     * 设置安全服务器关联网卡默认值
     * @param vo
     * @param operDate
     * @param editType
     */
    private void initNetworkCardVo(NetworkCardVo vo,Date operDate,String editType){
        switch (editType){
            case EDIT_TYPE_ADD:
                vo.setCreateTime(operDate);
                vo.setUpdateTime(operDate);
                vo.setCreateUser(RequestContext.getUser().getUserId().toString());
                vo.setUpdateUser(RequestContext.getUser().getUserId().toString());
                vo.setIsExist(IS_EXIST);
                break;
            case EDIT_TYPE_UPD:
                vo.setId(null);
                vo.setCreateTime(null);
                vo.setIsExist(null);
                vo.setUpdateTime(operDate);
                vo.setUpdateUser(RequestContext.getUser().getUserId().toString());
            case EDIT_TYPE_DEL:
                vo.setUpdateTime(operDate);
                vo.setUpdateUser(RequestContext.getUser().getUserId().toString());
                vo.setIsExist(IS_NOT_EXIST);
            default:
                break;
        }
    }

}
