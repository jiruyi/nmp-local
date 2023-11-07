package com.matrictime.network.service.impl;

import com.matrictime.network.dao.mapper.NmpsStationManageMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.StationManageVo;
import com.matrictime.network.service.StationManageService;
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
public class StationManageServiceImpl implements StationManageService {

    @Resource
    private NmpsStationManageMapper stationManageMapper;

    @Override
    public Result<Integer> insertStationManage(StationManageVo stationManageVo) {
        Result<Integer> result = new Result<>();
        int insert = 0;
        try {
            NmpsStationManageExample stationManageExample = new NmpsStationManageExample();
            NmpsStationManageExample.Criteria criteria = stationManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(stationManageVo.getNetworkId());
            criteria.andIsExistEqualTo(true);
            List<NmpsStationManage> nmpsStationManages = stationManageMapper.selectByExample(stationManageExample);
            NmpsStationManage stationManage = new NmpsStationManage();
            BeanUtils.copyProperties(stationManageVo,stationManage);
            stationManage.setIsExist(true);
            if(CollectionUtils.isEmpty(nmpsStationManages)){
                insert = stationManageMapper.insertSelective(stationManage);
            }else {
                insert = stationManageMapper.updateByExampleSelective(stationManage,stationManageExample);
            }
            result.setSuccess(true);
            result.setResultObj(insert);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertStationManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> deleteStationManage(StationManageVo stationManageVo) {
        Result<Integer> result = new Result<>();
        try {
            NmpsStationManageExample stationManageExample = new NmpsStationManageExample();
            NmpsStationManageExample.Criteria criteria = stationManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(stationManageVo.getNetworkId());
            NmpsStationManage stationManage = new NmpsStationManage();
            BeanUtils.copyProperties(stationManageVo,stationManage);
            stationManage.setIsExist(false);
            int i = stationManageMapper.updateByExampleSelective(stationManage,stationManageExample);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("deleteStationManage: {}",e.getMessage());
        }

        return result;
    }
}
