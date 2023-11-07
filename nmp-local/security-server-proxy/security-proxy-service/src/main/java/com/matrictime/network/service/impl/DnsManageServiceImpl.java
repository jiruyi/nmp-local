package com.matrictime.network.service.impl;

import com.matrictime.network.dao.mapper.NmpsDnsManageMapper;
import com.matrictime.network.dao.model.NmpsCaManage;
import com.matrictime.network.dao.model.NmpsCaManageExample;
import com.matrictime.network.dao.model.NmpsDnsManage;
import com.matrictime.network.dao.model.NmpsDnsManageExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DnsManageVo;
import com.matrictime.network.service.DnsManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/6.
 */
@Service
@Slf4j
public class DnsManageServiceImpl implements DnsManageService {

    @Resource
    private NmpsDnsManageMapper dnsManageMapper;

    @Override
    public Result<Integer> insertDnsManage(DnsManageVo dnsManageVo) {
        Result<Integer> result = new Result<>();
        int insert = 0;
        try {
            NmpsDnsManageExample dnsManageExample = new NmpsDnsManageExample();
            NmpsDnsManageExample.Criteria criteria = dnsManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(dnsManageVo.getNetworkId());
            criteria.andIsExistEqualTo(true);
            List<NmpsDnsManage> nmpsDnsManages = dnsManageMapper.selectByExample(dnsManageExample);
            NmpsDnsManage dnsManage = new NmpsDnsManage();
            BeanUtils.copyProperties(dnsManageVo,dnsManage);
            dnsManage.setIsExist(true);
            if(CollectionUtils.isEmpty(nmpsDnsManages)){
                insert = dnsManageMapper.insertSelective(dnsManage);
            }else {
                insert = dnsManageMapper.updateByExampleSelective(dnsManage,dnsManageExample);
            }
            result.setSuccess(true);
            result.setResultObj(insert);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertDnsManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> deleteDnsManage(DnsManageVo dnsManageVo) {
        Result<Integer> result = new Result<>();
        try {
            NmpsDnsManageExample dnsManageExample = new NmpsDnsManageExample();
            NmpsDnsManageExample.Criteria criteria = dnsManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(dnsManageVo.getNetworkId());
            NmpsDnsManage dnsManage = new NmpsDnsManage();
            BeanUtils.copyProperties(dnsManageVo,dnsManage);
            dnsManage.setIsExist(false);
            int i = dnsManageMapper.updateByExampleSelective(dnsManage,dnsManageExample);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("deleteDnsManage: {}",e.getMessage());
        }

        return result;
    }
}
