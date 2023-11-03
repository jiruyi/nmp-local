package com.matrictime.network.service.impl;

import com.matrictime.network.dao.mapper.NmpsStationManageMapper;
import com.matrictime.network.dao.mapper.extend.NmpsStationManageExtMapper;
import com.matrictime.network.dao.model.NmpsDnsManage;
import com.matrictime.network.dao.model.NmpsDnsManageExample;
import com.matrictime.network.dao.model.NmpsStationManage;
import com.matrictime.network.dao.model.NmpsStationManageExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.DnsManageVo;
import com.matrictime.network.modelVo.StationManageVo;
import com.matrictime.network.req.StationManageRequest;
import com.matrictime.network.resp.DnsManageResp;
import com.matrictime.network.resp.StationManageResp;
import com.matrictime.network.service.StationManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/3.
 */
@Slf4j
@Service
public class StationManageServiceImpl implements StationManageService {

    @Resource
    private NmpsStationManageMapper stationManageMapper;

    @Resource
    private NmpsStationManageExtMapper stationManageExtMapper;

    @Override
    public Result<Integer> insertStationManage(StationManageRequest stationManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            //唯一性校验
            NmpsStationManageExample stationManageExample = new NmpsStationManageExample();
            NmpsStationManageExample.Criteria criteria = stationManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(stationManageRequest.getNetworkId());
            List<NmpsStationManage> nmpsStationManages = stationManageMapper.selectByExample(stationManageExample);
            if(!CollectionUtils.isEmpty(nmpsStationManages)){
                return new Result<>(false,"入网id不唯一");
            }
            NmpsStationManage nmpsStationManage = new NmpsStationManage();
            BeanUtils.copyProperties(stationManageRequest,nmpsStationManage);
            int i = stationManageMapper.insertSelective(nmpsStationManage);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertStationManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> deleteStationManage(StationManageRequest stationManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            NmpsStationManage nmpsStationManage = new NmpsStationManage();
            BeanUtils.copyProperties(stationManageRequest,nmpsStationManage);
            //构建条件
            NmpsStationManageExample stationManageExample = new NmpsStationManageExample();
            NmpsStationManageExample.Criteria criteria = stationManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(stationManageRequest.getNetworkId());
            nmpsStationManage.setIsExist(false);
            int i = stationManageMapper.updateByExampleSelective(nmpsStationManage, stationManageExample);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("deleteStationManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<StationManageResp> selectStationManage(StationManageRequest stationManageRequest) {
        Result<StationManageResp> result = new Result<>();
        StationManageResp stationManageResp = new StationManageResp();
        try {
            List<StationManageVo> stationManageVos = stationManageExtMapper.selectStationManage(stationManageRequest);
            stationManageResp.setList(stationManageVos);
            result.setSuccess(true);
            result.setResultObj(stationManageResp);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("selectStationManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> updateStationManage(StationManageRequest stationManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            //唯一性校验
            NmpsStationManageExample stationManageExample = new NmpsStationManageExample();
            NmpsStationManageExample.Criteria criteria = stationManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(stationManageRequest.getNetworkId());
            criteria.andIdNotEqualTo(stationManageRequest.getId());
            List<NmpsStationManage> nmpsStationManages = stationManageMapper.selectByExample(stationManageExample);
            if(!CollectionUtils.isEmpty(nmpsStationManages)){
                return new Result<>(false,"入网id不唯一");
            }
            //构建更新条件
            NmpsStationManageExample manageExample = new NmpsStationManageExample();
            NmpsStationManageExample.Criteria manageExampleCriteria = manageExample.createCriteria();
            manageExampleCriteria.andNetworkIdEqualTo(stationManageRequest.getNetworkId());
            NmpsStationManage nmpsStationManage = new NmpsStationManage();
            BeanUtils.copyProperties(stationManageRequest,nmpsStationManage);
            int i = stationManageMapper.updateByExampleSelective(nmpsStationManage, manageExample);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.error("updateStationManage: {}",e.getMessage());
        }
        return result;
    }
}
