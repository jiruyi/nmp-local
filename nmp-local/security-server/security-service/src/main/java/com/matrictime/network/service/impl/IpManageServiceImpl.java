package com.matrictime.network.service.impl;

import com.matrictime.network.dao.mapper.NmpsIpManageMapper;
import com.matrictime.network.dao.model.NmpsIpManage;
import com.matrictime.network.dao.model.NmpsIpManageExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.IpManageVo;
import com.matrictime.network.request.IpManageRequest;
import com.matrictime.network.resp.IpManageResp;
import com.matrictime.network.service.IpManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author by wangqiang
 * @date 2023/10/27.
 */
@Slf4j
@Service
public class IpManageServiceImpl implements IpManageService {

    @Resource
    private NmpsIpManageMapper ipManageMapper;

    @Override
    public Result<Integer> insertIpManage(IpManageRequest ipManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            //唯一性校验
            NmpsIpManageExample ipManageExample = new NmpsIpManageExample();
            NmpsIpManageExample.Criteria criteria = ipManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(ipManageRequest.getNetworkId());
            List<NmpsIpManage> nmpsIpManages = ipManageMapper.selectByExample(ipManageExample);
            if(!CollectionUtils.isEmpty(nmpsIpManages)){
                return new Result<>(false,"入网id不唯一");
            }
            NmpsIpManage ipManage = new NmpsIpManage();
            BeanUtils.copyProperties(ipManageRequest,ipManage);
            int i = ipManageMapper.insertSelective(ipManage);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public Result<IpManageResp> selectIpManage(IpManageRequest ipManageRequest) {
        Result<IpManageResp> result = new Result<>();
        IpManageResp ipManageResp = new IpManageResp();
        try {
            NmpsIpManageExample ipManageExample = new NmpsIpManageExample();
            NmpsIpManageExample.Criteria criteria = ipManageExample.createCriteria();
            if(!StringUtils.isEmpty(ipManageRequest.getSecuritySeverName())){
                criteria.andSecuritySeverNameEqualTo(ipManageRequest.getSecuritySeverName());
            }
            if(!StringUtils.isEmpty(ipManageRequest.getNetworkId())){
                criteria.andSecuritySeverNameEqualTo(ipManageRequest.getNetworkId());
            }
            if(!StringUtils.isEmpty(ipManageRequest.getAccessMethod())){
                criteria.andSecuritySeverNameEqualTo(ipManageRequest.getAccessMethod());
            }
            criteria.andManageTypeEqualTo(ipManageRequest.getManageType());
            criteria.andIsExistEqualTo(true);
            List<IpManageVo> list = new ArrayList<>();
            List<NmpsIpManage> nmpsIpManages = ipManageMapper.selectByExample(ipManageExample);
            for(NmpsIpManage nmpsIpManage: nmpsIpManages){
                IpManageVo ipManageVo = new IpManageVo();
                BeanUtils.copyProperties(nmpsIpManage,ipManageVo);
                list.add(ipManageVo);
            }
            ipManageResp.setList(list);
            result.setResultObj(ipManageResp);
            result.setSuccess(true);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("selectIpManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> updateIpManage(IpManageRequest ipManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            //唯一性校验
            NmpsIpManageExample ipManageExample = new NmpsIpManageExample();
            NmpsIpManageExample.Criteria criteria = ipManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(ipManageRequest.getNetworkId());
            criteria.andIdNotEqualTo(ipManageRequest.getId());
            List<NmpsIpManage> nmpsIpManages = ipManageMapper.selectByExample(ipManageExample);
            if(!CollectionUtils.isEmpty(nmpsIpManages)){
                return new Result<>(false,"入网id不唯一");
            }
            //构建更新条件
            NmpsIpManageExample manageExample = new NmpsIpManageExample();
            NmpsIpManageExample.Criteria manageExampleCriteria = manageExample.createCriteria();
            manageExampleCriteria.andNetworkIdEqualTo(ipManageRequest.getNetworkId());
            NmpsIpManage nmpsIpManage = new NmpsIpManage();
            BeanUtils.copyProperties(ipManageRequest,nmpsIpManage);
            int i = ipManageMapper.updateByExampleSelective(nmpsIpManage, manageExample);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.error("updateIpManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> deleteIpManage(IpManageRequest ipManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            NmpsIpManage nmpsIpManage = new NmpsIpManage();
            BeanUtils.copyProperties(ipManageRequest,nmpsIpManage);
            NmpsIpManageExample ipManageExample = new NmpsIpManageExample();
            NmpsIpManageExample.Criteria criteria = ipManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(ipManageRequest.getNetworkId());
            nmpsIpManage.setIsExist(false);
            int i = ipManageMapper.updateByExampleSelective(nmpsIpManage, ipManageExample);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("deleteIpManage: {}",e.getMessage());
        }
        return result;
    }
}
