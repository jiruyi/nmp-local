package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.dao.mapper.NmpsDnsManageMapper;
import com.matrictime.network.dao.mapper.extend.NmpsDnsManageExtMapper;
import com.matrictime.network.dao.model.NmpsCaManage;
import com.matrictime.network.dao.model.NmpsDnsManage;
import com.matrictime.network.dao.model.NmpsDnsManageExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CaManageVo;
import com.matrictime.network.modelVo.DnsManageVo;
import com.matrictime.network.modelVo.PageInfo;
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

    /**
     * dns管理插入
     * @param dnsManageRequest
     * @return
     */
    @Override
    public Result<Integer> insertDnsManage(DnsManageRequest dnsManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            //唯一性校验
            NmpsDnsManageExample dnsManageExample = new NmpsDnsManageExample();
            NmpsDnsManageExample.Criteria criteria = dnsManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(dnsManageRequest.getNetworkId());
            criteria.andIsExistEqualTo(true);
            List<NmpsDnsManage> nmpsDnsManages = dnsManageMapper.selectByExample(dnsManageExample);
            if(!CollectionUtils.isEmpty(nmpsDnsManages)){
                return new Result<>(false,"入网id不唯一");
            }
            NmpsDnsManage dnsManage = new NmpsDnsManage();
            BeanUtils.copyProperties(dnsManageRequest,dnsManage);
            int i = dnsManageMapper.insertSelective(dnsManage);
            //代理推送
            if(i == 1){
                syncProxy(dnsManageRequest,DNS_MANAGE_INSERT_URL);
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

    /**
     * dns删除
     * @param dnsManageRequest
     * @return
     */
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
                syncProxy(dnsManageRequest,DNS_MANAGE_DELETE_URL);
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

    /**
     * dns管理查询
     * @param dnsManageRequest
     * @return
     */
    @Override
    public Result<PageInfo<DnsManageVo>> selectDnsManage(DnsManageRequest dnsManageRequest) {
        Result<PageInfo<DnsManageVo>> result = new Result<>();
        PageInfo<DnsManageVo> pageInfo = new PageInfo<>();
        try {
            //分页查询数据
            Page page = PageHelper.startPage(dnsManageRequest.getPageNo(),dnsManageRequest.getPageSize());
            List<DnsManageVo> dnsManageVos = dnsManageExtMapper.selectDnsManage(dnsManageRequest);
            pageInfo.setList(dnsManageVos);
            pageInfo.setCount((int) page.getTotal());
            pageInfo.setPages(page.getPages());
            result.setSuccess(true);
            result.setResultObj(pageInfo);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("selectDnsManage: {}",e.getMessage());
        }
        return result;
    }

    /**
     * dns管理更新
     * @param dnsManageRequest
     * @return
     */
    @Override
    public Result<Integer> updateDnsManage(DnsManageRequest dnsManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            //唯一性校验
            NmpsDnsManageExample dnsManageExample = new NmpsDnsManageExample();
            NmpsDnsManageExample.Criteria criteria = dnsManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(dnsManageRequest.getNetworkId());
            criteria.andIdNotEqualTo(dnsManageRequest.getId());
            criteria.andIsExistEqualTo(true);
            List<NmpsDnsManage> nmpsDnsManages = dnsManageMapper.selectByExample(dnsManageExample);
            if(!CollectionUtils.isEmpty(nmpsDnsManages)){
                return new Result<>(false,"入网id不唯一");
            }
            //构建更新条件
            NmpsDnsManageExample manageExample = new NmpsDnsManageExample();
            NmpsDnsManageExample.Criteria manageExampleCriteria = manageExample.createCriteria();
            manageExampleCriteria.andNetworkIdEqualTo(dnsManageRequest.getNetworkId());
            manageExampleCriteria.andIsExistEqualTo(true);
            NmpsDnsManage dnsManage = new NmpsDnsManage();
            BeanUtils.copyProperties(dnsManageRequest,dnsManage);
            int i = dnsManageMapper.updateByExampleSelective(dnsManage, manageExample);
            //代理推送
            if(i == 1){
                syncProxy(dnsManageRequest,DNS_MANAGE_INSERT_URL);
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
            DnsManageVo vo = new DnsManageVo();
            BeanUtils.copyProperties(dnsManageRequest,vo);
            String url = HttpClientUtil.getUrl(vo.getComIp(), securityProxyPort, securityProxyPath + flag);
            HttpClientUtil.post(url,JSONObject.toJSONString(vo));
        }catch (Exception e){
            log.warn("DnsManageServiceImpl.syncProxy Exception:{}",e);
        }
    }
}
