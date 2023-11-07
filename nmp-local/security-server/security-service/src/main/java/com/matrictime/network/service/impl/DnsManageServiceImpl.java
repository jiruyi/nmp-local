package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.dao.mapper.NmpsDnsManageMapper;
import com.matrictime.network.dao.mapper.extend.NmpsDnsManageExtMapper;
import com.matrictime.network.dao.model.NmpsCaManage;
import com.matrictime.network.dao.model.NmpsDnsManage;
import com.matrictime.network.dao.model.NmpsDnsManageExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CaManageVo;
import com.matrictime.network.modelVo.DnsManageVo;
import com.matrictime.network.req.DnsManageRequest;
import com.matrictime.network.resp.DnsManageResp;
import com.matrictime.network.service.DnsManageService;
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
 * @date 2023/11/3.
 */
@Slf4j
@Service
public class DnsManageServiceImpl implements DnsManageService {

    @Resource
    private NmpsDnsManageExtMapper dnsManageExtMapper;

    @Resource
    private NmpsDnsManageMapper dnsManageMapper;

    @Value("${security-proxy.context-path}")
    private String securityProxyPath;

    @Value("${security-proxy.port}")
    private String securityProxyPort;

    @Override
    public Result<Integer> insertDnsManage(DnsManageRequest dnsManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            //唯一性校验
            NmpsDnsManageExample dnsManageExample = new NmpsDnsManageExample();
            NmpsDnsManageExample.Criteria criteria = dnsManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(dnsManageRequest.getNetworkId());
            List<NmpsDnsManage> nmpsDnsManages = dnsManageMapper.selectByExample(dnsManageExample);
            if(!CollectionUtils.isEmpty(nmpsDnsManages)){
                return new Result<>(false,"入网id不唯一");
            }
            NmpsDnsManage dnsManage = new NmpsDnsManage();
            BeanUtils.copyProperties(dnsManageRequest,dnsManage);
            int i = dnsManageMapper.insertSelective(dnsManage);
            //代理推送
            if(i == 1){
                syncProxy(dnsManageRequest,"insert");
            }
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertDnsManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> deleteDnsManage(DnsManageRequest dnsManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            NmpsDnsManage nmpsDnsManage = new NmpsDnsManage();
            BeanUtils.copyProperties(dnsManageRequest,nmpsDnsManage);
            NmpsDnsManageExample dnsManageExample = new NmpsDnsManageExample();
            NmpsDnsManageExample.Criteria criteria = dnsManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(dnsManageRequest.getNetworkId());
            nmpsDnsManage.setIsExist(false);
            int i = dnsManageMapper.updateByExampleSelective(nmpsDnsManage, dnsManageExample);
            //代理推送
            if(i == 1){
                syncProxy(dnsManageRequest,"delete");
            }
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("deleteDnsManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<DnsManageResp> selectDnsManage(DnsManageRequest dnsManageRequest) {
        Result<DnsManageResp> result = new Result<>();
        DnsManageResp dnsManageResp = new DnsManageResp();
        try {
            List<DnsManageVo> dnsManageVos = dnsManageExtMapper.selectDnsManage(dnsManageRequest);
            dnsManageResp.setList(dnsManageVos);
            result.setSuccess(true);
            result.setResultObj(dnsManageResp);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("selectDnsManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> updateDnsManage(DnsManageRequest dnsManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            //唯一性校验
            NmpsDnsManageExample dnsManageExample = new NmpsDnsManageExample();
            NmpsDnsManageExample.Criteria criteria = dnsManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(dnsManageRequest.getNetworkId());
            criteria.andIdNotEqualTo(dnsManageRequest.getId());
            List<NmpsDnsManage> nmpsDnsManages = dnsManageMapper.selectByExample(dnsManageExample);
            if(!CollectionUtils.isEmpty(nmpsDnsManages)){
                return new Result<>(false,"入网id不唯一");
            }
            //构建更新条件
            NmpsDnsManageExample manageExample = new NmpsDnsManageExample();
            NmpsDnsManageExample.Criteria manageExampleCriteria = manageExample.createCriteria();
            manageExampleCriteria.andNetworkIdEqualTo(dnsManageRequest.getNetworkId());
            NmpsDnsManage dnsManage = new NmpsDnsManage();
            BeanUtils.copyProperties(dnsManageRequest,dnsManage);
            int i = dnsManageMapper.updateByExampleSelective(dnsManage, manageExample);
            //代理推送
            if(i == 1){
                syncProxy(dnsManageRequest,"insert");
            }
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.error("updateDnsManage: {}",e.getMessage());
        }
        return result;
    }

    /**
     * 推送到代理端
     * @param dnsManageRequest
     * @param flag
     */
    private void syncProxy(DnsManageRequest dnsManageRequest, String flag){
        try {
            String urlString = "";
            DnsManageVo vo = new DnsManageVo();
            BeanUtils.copyProperties(dnsManageRequest,vo);
            JSONObject jsonParam = new JSONObject();
            jsonParam.put(JSON_KEY_EDITTYPE,flag);
            jsonParam.put("dnsManageVo",vo);
            if(flag.equals("insert")){
                urlString = DNS_MANAGE_INSERT_URL;
            }else {
                urlString = DNS_MANAGE_DELETE_URL;
            }
            String url = HttpClientUtil.getUrl(vo.getComIp(), securityProxyPort, securityProxyPath + urlString);
            HttpClientUtil.post(url,jsonParam.toJSONString());
        }catch (Exception e){
            log.warn("DnsManageServiceImpl.syncProxy Exception:{}",e);
        }
    }
}
