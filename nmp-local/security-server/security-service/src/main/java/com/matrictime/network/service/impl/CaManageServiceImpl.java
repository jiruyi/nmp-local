package com.matrictime.network.service.impl;

import com.matrictime.network.dao.mapper.NmpsCaManageMapper;
import com.matrictime.network.dao.mapper.extend.NmpsCaManageExtMapper;
import com.matrictime.network.dao.model.NmpsCaManage;
import com.matrictime.network.dao.model.NmpsCaManageExample;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CaManageVo;
import com.matrictime.network.req.CaManageRequest;
import com.matrictime.network.resp.CaManageResp;
import com.matrictime.network.service.CaManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/11/2.
 */
@Slf4j
@Service
public class CaManageServiceImpl implements CaManageService {

    @Resource
    private NmpsCaManageMapper caManageMapper;

    @Resource
    private NmpsCaManageExtMapper caManageExtMapper;

    /**
     * 插入ca管理
     * @param caManageRequest
     * @return
     */
    @Override
    public Result<Integer> insertCaManage(CaManageRequest caManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            //唯一性校验
            NmpsCaManageExample caManageExample = new NmpsCaManageExample();
            NmpsCaManageExample.Criteria criteria = caManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(caManageRequest.getNetworkId());
            List<NmpsCaManage> nmpsCaManages = caManageMapper.selectByExample(caManageExample);
            if(!CollectionUtils.isEmpty(nmpsCaManages)){
                return new Result<>(false,"入网id不唯一");
            }
            NmpsCaManage nmpsCaManage = new NmpsCaManage();
            BeanUtils.copyProperties(caManageRequest,nmpsCaManage);
            int i = caManageMapper.insertSelective(nmpsCaManage);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertCaManage: {}",e.getMessage());
        }
        return result;
    }

    /**
     * 删除ca管理
     * @param caManageRequest
     * @return
     */
    @Override
    public Result<Integer> deleteCaManage(CaManageRequest caManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            NmpsCaManage nmpsCaManage = new NmpsCaManage();
            BeanUtils.copyProperties(caManageRequest,nmpsCaManage);
            NmpsCaManageExample caManageExample = new NmpsCaManageExample();
            NmpsCaManageExample.Criteria criteria = caManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(caManageRequest.getNetworkId());
            nmpsCaManage.setIsExist(false);
            int i = caManageMapper.updateByExampleSelective(nmpsCaManage, caManageExample);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("deleteCaManage: {}",e.getMessage());
        }
        return result;
    }

    /**
     * 查询ca管理
     * @param caManageRequest
     * @return
     */
    @Override
    public Result<CaManageResp> selectCaManage(CaManageRequest caManageRequest) {
        Result<CaManageResp> result = new Result<>();
        CaManageResp caManageResp = new CaManageResp();
        try {
            List<CaManageVo> caManageVos = caManageExtMapper.selectCaManage(caManageRequest);
            caManageResp.setList(caManageVos);
            result.setSuccess(true);
            result.setResultObj(caManageResp);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("selectIpManage: {}",e.getMessage());
        }
        return result;
    }

    /**
     * 更新ca管理
     * @param caManageRequest
     * @return
     */
    @Override
    public Result<Integer> updateCaManage(CaManageRequest caManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            //唯一性校验
            NmpsCaManageExample caManageExample = new NmpsCaManageExample();
            NmpsCaManageExample.Criteria criteria = caManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(caManageRequest.getNetworkId());
            criteria.andIdNotEqualTo(caManageRequest.getId());
            List<NmpsCaManage> nmpsCaManages = caManageMapper.selectByExample(caManageExample);
            if(!CollectionUtils.isEmpty(nmpsCaManages)){
                return new Result<>(false,"入网id不唯一");
            }
            //构建更新条件
            NmpsCaManageExample manageExample = new NmpsCaManageExample();
            NmpsCaManageExample.Criteria manageExampleCriteria = manageExample.createCriteria();
            manageExampleCriteria.andNetworkIdEqualTo(caManageRequest.getNetworkId());
            NmpsCaManage nmpsCaManage = new NmpsCaManage();
            BeanUtils.copyProperties(caManageRequest,nmpsCaManage);
            int i = caManageMapper.updateByExampleSelective(nmpsCaManage, manageExample);
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.error("updateCaManage: {}",e.getMessage());
        }
        return result;
    }



}
