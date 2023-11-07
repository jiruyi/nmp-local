package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmpsNetworkCardMapper;
import com.matrictime.network.dao.mapper.NmpsSecurityServerInfoMapper;
import com.matrictime.network.dao.mapper.extend.NetworkCardMapperExt;
import com.matrictime.network.dao.model.NmpsNetworkCard;
import com.matrictime.network.dao.model.NmpsNetworkCardExample;
import com.matrictime.network.dao.model.NmpsSecurityServerInfo;
import com.matrictime.network.dao.model.NmpsSecurityServerInfoExample;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NetworkCardProxyVo;
import com.matrictime.network.modelVo.SecurityServerProxyVo;
import com.matrictime.network.req.EditServerProxyReq;
import com.matrictime.network.service.SecurityServerService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.constant.DataConstants.IS_EXIST;
import static com.matrictime.network.constant.DataConstants.IS_NOT_EXIST;
import static com.matrictime.network.exception.ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG;

@Service
@Slf4j
public class SecurityServerServiceImpl extends SystemBaseService implements SecurityServerService {

    @Resource
    private NmpsSecurityServerInfoMapper serverInfoMapper;

    @Resource
    private NetworkCardMapperExt networkCardMapperExt;

    @Resource
    private NmpsNetworkCardMapper networkCardMapper;


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

    /**
     * 安全服务器更新
     * @param req
     * @return
     */
    @Override
    @Transactional
    public Result updateServer(EditServerProxyReq req) {
        Result result;
        try {
            // check param is legal
            checkEditServerParam(req);
            switch (req.getEditType()){
                // 批量插入
                case DataConstants.EDIT_TYPE_ADD:
                    for (SecurityServerProxyVo vo : req.getSecurityServerInfoVos()){
                        checkAddServerParam(vo);
                        NmpsSecurityServerInfo serverInfo = new NmpsSecurityServerInfo();
                        BeanUtils.copyProperties(vo,serverInfo);
                        // 插入安全服务器信息表
                        int addServer = serverInfoMapper.insertSelective(serverInfo);
                        log.info("代理插入安全服务器信息表:{}",addServer);

                        // 插入安全服务器关联网卡表
                        int addCards = networkCardMapperExt.batchInsert(vo.getNetworkCardProxyVos());
                        log.info("代理插入安全服务器关联网卡表:{}",addCards);
                    }
                    break;
                case DataConstants.EDIT_TYPE_UPD://批量修改
                    for (SecurityServerProxyVo vo : req.getSecurityServerInfoVos()){
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
                        BeanUtils.copyProperties(vo,server);
                        server.setNetworkId(networkId);
                        server.setUpdateTime(null);
                        int updServer = serverInfoMapper.updateByPrimaryKeySelective(server);
                        log.info("更新安全服务器信息表:{}",updServer);

                        // 更新安全服务器关联网卡表（先删后增）
                        NmpsNetworkCardExample deleteExample = new NmpsNetworkCardExample();
                        deleteExample.createCriteria().andNetworkIdNotEqualTo(networkId).andIsExistEqualTo(IS_EXIST);
                        NmpsNetworkCard networkCard = new NmpsNetworkCard();
                        networkCard.setIsExist(IS_NOT_EXIST);
                        int delCards = networkCardMapper.updateByExampleSelective(networkCard, deleteExample);
                        log.info("更新安全服务器关联网卡表（先删后增）:{}",delCards);

                        int updCards = networkCardMapperExt.batchInsert(vo.getNetworkCardProxyVos());
                        log.info("更新安全服务器关联网卡表（先删后增）:{}",updCards);

                    }
                    break;
                case DataConstants.EDIT_TYPE_DEL:// 逻辑删除
                    for (SecurityServerProxyVo vo : req.getSecurityServerInfoVos()){
                        // 校验id是否为空
                        if (vo.getId() == null){
                            throw new Exception("SecurityServerInfoVos.id"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmpsSecurityServerInfo serverInfo = serverInfoMapper.selectByPrimaryKey(vo.getId());
                        if (serverInfo == null){
                            throw new Exception("serverInfo"+ ErrorMessageContants.DATA_CANNOT_FIND_INDB);
                        }

                        // 逻辑删除安全服务器信息表
                        NmpsSecurityServerInfo server = new NmpsSecurityServerInfo();
                        server.setId(vo.getId());
                        server.setIsExist(IS_NOT_EXIST);
                        int delServer = serverInfoMapper.updateByPrimaryKeySelective(server);
                        log.info("逻辑删除安全服务器信息表:{}",delServer);

                        // 逻辑删除安全服务器关联网卡表
                        NmpsNetworkCardExample deleteExample = new NmpsNetworkCardExample();
                        deleteExample.createCriteria().andNetworkIdNotEqualTo(serverInfo.getNetworkId()).andIsExistEqualTo(IS_EXIST);
                        NmpsNetworkCard networkCard = new NmpsNetworkCard();
                        networkCard.setIsExist(IS_NOT_EXIST);
                        int delCards = networkCardMapper.updateByExampleSelective(networkCard, deleteExample);
                        log.info("逻辑删除安全服务器关联网卡表:{}",delCards);
                    }
                    break;
                default:
                    throw new Exception("EditType"+ PARAM_IS_UNEXPECTED_MSG);
            }
            result = buildResult(null);
        }catch (SystemException e){
            log.error("SecurityServerServiceImpl.updateServer SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("SecurityServerServiceImpl.updateServer Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    private void checkEditServerParam(EditServerProxyReq req) throws Exception{
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
    private void checkAddServerParam(SecurityServerProxyVo vo) throws Exception{
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
        if (ParamCheckUtil.checkVoStrBlank(vo.getSignalPort())){
            throw new Exception("SignalPort"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getKeyPort())){
            throw new Exception("KeyPort"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getConnectType())){
            throw new Exception("ConnectType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }

        if (CollectionUtils.isEmpty(vo.getNetworkCardProxyVos())){
            throw new Exception("NetworkCardVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 校验插入安全服务器信息表数据是否合法结束

        // 校验插入安全服务器关联网卡信息表数据是否合法开始
        for (NetworkCardProxyVo cardVo : vo.getNetworkCardProxyVos()){
            if (ParamCheckUtil.checkVoStrBlank(cardVo.getNetCardType())){
                throw new Exception("NetCardType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (ParamCheckUtil.checkVoStrBlank(cardVo.getAdapterName())){
                throw new Exception("AdapterName"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (ParamCheckUtil.checkVoStrBlank(cardVo.getIpv4()) && ParamCheckUtil.checkVoStrBlank(cardVo.getIpv6())){
                throw new Exception("Ipv4|Ipv6"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }

        // 校验插入安全服务器关联网卡信息表数据是否合法结束
    }

    /**
     * 校验更新安全服务器信息参数是否合法
     * @param vo
     * @throws SystemException
     */
    private void checkUpdateServerParam(SecurityServerProxyVo vo) throws Exception{
        // 校验插入安全服务器信息表数据是否合法开始
        if (ParamCheckUtil.checkVoStrBlank(vo.getServerName())){
            throw new Exception("ServerName"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getSignalPort())){
            throw new Exception("SignalPort"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getKeyPort())){
            throw new Exception("KeyPort"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(vo.getConnectType())){
            throw new Exception("ConnectType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }

        if (CollectionUtils.isEmpty(vo.getNetworkCardProxyVos())){
            throw new Exception("NetworkCardVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 校验插入安全服务器信息表数据是否合法结束

        // 校验插入安全服务器关联网卡信息表数据是否合法开始
        for (NetworkCardProxyVo cardVo : vo.getNetworkCardProxyVos()){
            if (ParamCheckUtil.checkVoStrBlank(cardVo.getNetCardType())){
                throw new Exception("NetCardType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (ParamCheckUtil.checkVoStrBlank(cardVo.getAdapterName())){
                throw new Exception("AdapterName"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
            if (ParamCheckUtil.checkVoStrBlank(cardVo.getIpv4()) && ParamCheckUtil.checkVoStrBlank(cardVo.getIpv6())){
                throw new Exception("Ipv4|Ipv6"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }

        // 校验插入安全服务器关联网卡信息表数据是否合法结束
    }
}
