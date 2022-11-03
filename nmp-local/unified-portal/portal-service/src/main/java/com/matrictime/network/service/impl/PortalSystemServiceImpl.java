package com.matrictime.network.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.enums.SystemTypeEnum;
import com.matrictime.network.dao.mapper.PortalSystemMapper;
import com.matrictime.network.dao.model.PortalSystem;
import com.matrictime.network.dao.model.PortalSystemExample;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.PortalSystemVo;
import com.matrictime.network.request.*;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.PortalSystemService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.constant.DataConstants.*;

@Slf4j
@Service
public class PortalSystemServiceImpl extends SystemBaseService implements PortalSystemService {

    @Resource
    private PortalSystemMapper portalSystemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addSystem(AddSystemReq req) {
        Result result;
        try {
            addSystemParamCheck(req);
            PortalSystem portalSystem = new PortalSystem();
            BeanUtils.copyProperties(req,portalSystem);
            int i = portalSystemMapper.insertSelective(portalSystem);
            result = buildResult(i);
        }catch (SystemException e){
            log.error("PortalSystemServiceImpl.addSystem SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("PortalSystemServiceImpl.addSystem Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delSystem(DelSystemReq req) {
        Result result;
        try {
            delSystemParamCheck(req);
            PortalSystem portalSystem = new PortalSystem();
            portalSystem.setId(req.getId());
            portalSystem.setIsExist(IS_NOT_EXIST);
            int i = portalSystemMapper.updateByPrimaryKeySelective(portalSystem);
            result = buildResult(i);
        }catch (SystemException e){
            log.error("PortalSystemServiceImpl.delSystem SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("PortalSystemServiceImpl.delSystem Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updSystem(UpdSystemReq req) {
        Result result;
        try {
            updSystemParamCheck(req);
            PortalSystem portalSystem = new PortalSystem();
            BeanUtils.copyProperties(req,portalSystem);
            int i = portalSystemMapper.updateByPrimaryKeySelective(portalSystem);
            result = buildResult(i);
        }catch (SystemException e){
            log.error("PortalSystemServiceImpl.updSystem SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("PortalSystemServiceImpl.updSystem Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
        }

        return result;
    }

    @Override
    public Result<PageInfo> querySystemByPage(QuerySystemByPageReq req) {
        Result result;
        try {

            Page page = PageHelper.startPage(req.getPageNo(),req.getPageSize());
            PortalSystemExample example = new PortalSystemExample();
            PortalSystemExample.Criteria criteria = example.createCriteria();
            example.setOrderByClause(UPDTIME_DESC);
            criteria.andIsExistEqualTo(IS_EXIST);

            if (!ParamCheckUtil.checkVoStrBlank(req.getSysName())){
                StringBuffer sb = new StringBuffer(KEY_PERCENT);
                sb.append(req.getSysName()).append(KEY_PERCENT);
                criteria.andSysNameLike(sb.toString());
            }
            if (!ParamCheckUtil.checkVoStrBlank(req.getSysType())){
                criteria.andSysTypeEqualTo(req.getSysType());
            }
            if (req.getStartTime() != null){
                criteria.andCreateTimeGreaterThanOrEqualTo(req.getStartTime());
            }
            if (req.getEndTime() != null){
                criteria.andCreateTimeLessThanOrEqualTo(req.getEndTime());
            }

            List<PortalSystem> portalSystems = portalSystemMapper.selectByExample(example);
            PageInfo<PortalSystem> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), portalSystems);

            result = buildResult(pageResult);
        }catch (Exception e){
            log.error("PortalSystemServiceImpl.querySystemByPage Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }


    @Override
    public Result<List<PortalSystemVo>> querySystem(QuerySystemReq req) {
        Result result;
        try {
            querySystemParamCheck(req);

            PortalSystemExample example = new PortalSystemExample();
            PortalSystemExample.Criteria criteria = example.createCriteria();
            example.setOrderByClause(UPDTIME_DESC);
            criteria.andIsExistEqualTo(IS_EXIST);
            criteria.andIsDisplayEqualTo(IS_EXIST);

            if (!ParamCheckUtil.checkVoStrBlank(req.getSysType())){
                criteria.andSysTypeEqualTo(req.getSysType());
            }

            List<PortalSystem> portalSystems = portalSystemMapper.selectByExample(example);
            List<PortalSystemVo> portalSystemVos = new ArrayList<>();
            for (PortalSystem portalSystem : portalSystems){
                PortalSystemVo vo = new PortalSystemVo();
                BeanUtils.copyProperties(portalSystem,vo);
                portalSystemVos.add(vo);
            }

            result = buildResult(portalSystemVos);
        }catch (Exception e){
            log.error("PortalSystemServiceImpl.querySystem Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    private void addSystemParamCheck(AddSystemReq req) throws Exception{
        if (req == null){
            throw new Exception("req"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getSysType())){
            throw new SystemException("系统类型"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getSysLogo())){
            throw new SystemException("logo"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getSysName())){
            throw new SystemException("系统名称"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getSysUrl())){
            throw new SystemException("链接地址"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getIsDisplay() == null){
            throw new SystemException("前端展示"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void delSystemParamCheck(DelSystemReq req) throws Exception{
        if (req == null){
            throw new Exception("req"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getId() == null){
            throw new Exception("系统id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void updSystemParamCheck(UpdSystemReq req) throws Exception{
        if (req == null){
            throw new Exception("req"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getId() == null){
            throw new Exception("系统id"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getSysType())){
            throw new SystemException("系统类型"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getSysLogo())){
            throw new SystemException("logo"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getSysName())){
            throw new SystemException("系统名称"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getSysUrl())){
            throw new SystemException("链接地址"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getIsDisplay() == null){
            throw new SystemException("前段展示"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void querySystemParamCheck(QuerySystemReq req) throws Exception{
        if (req == null){
            throw new Exception("req"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (!ParamCheckUtil.checkVoStrBlank(req.getSysType())){
            if (!(SystemTypeEnum.YUNYING.getCode().equals(req.getSysType()) || SystemTypeEnum.YUNWEI.getCode().equals(req.getSysType()) || SystemTypeEnum.YUNKONG.getCode().equals(req.getSysType()))){
                throw new Exception("系统类型"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }

        }

    }

}
