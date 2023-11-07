package com.matrictime.network.service.impl;

import com.matrictime.network.dao.mapper.NmpsCaManageMapper;
import com.matrictime.network.dao.model.NmpsCaManage;
import com.matrictime.network.dao.model.NmpsCaManageExample;
import com.matrictime.network.dao.model.NmpsDnsManage;
import com.matrictime.network.dao.model.NmpsDnsManageExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CaManageVo;
import com.matrictime.network.service.CaManageService;
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
public class CaManageServiceImpl implements CaManageService {

    @Resource
    private NmpsCaManageMapper caManageMapper;

    @Override
    public Result<Integer> insertCaManage(CaManageVo caManageVo) {
        Result<Integer> result = new Result<>();
        int insert = 0;
        try {
            NmpsCaManageExample caManageExample = new NmpsCaManageExample();
            NmpsCaManageExample.Criteria criteria = caManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(caManageVo.getNetworkId());
            criteria.andIsExistEqualTo(true);
            List<NmpsCaManage> nmpsCaManages = caManageMapper.selectByExample(caManageExample);
            NmpsCaManage caManage = new NmpsCaManage();
            BeanUtils.copyProperties(caManageVo,caManage);
            caManage.setIsExist(true);
            if(CollectionUtils.isEmpty(nmpsCaManages)){
                insert = caManageMapper.insertSelective(caManage);
            }else {
                insert = caManageMapper.updateByExampleSelective(caManage,caManageExample);
            }
            result.setSuccess(true);
            result.setResultObj(insert);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertCaManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> deleteCaManage(CaManageVo caManageVo) {
        Result<Integer> result = new Result<>();
        try {
            NmpsCaManageExample caManageExample = new NmpsCaManageExample();
            NmpsCaManageExample.Criteria criteria = caManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(caManageVo.getNetworkId());
            NmpsCaManage caManage = new NmpsCaManage();
            BeanUtils.copyProperties(caManageVo,caManage);
            caManage.setIsExist(false);
            int i = caManageMapper.updateByExampleSelective(caManage, caManageExample);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("deleteCaManage: {}",e.getMessage());
        }

        return result;
    }
}
