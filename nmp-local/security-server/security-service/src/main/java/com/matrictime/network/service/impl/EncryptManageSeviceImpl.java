package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.mapper.NmpEncryptConfMapper;
import com.matrictime.network.dao.model.NmpEncryptConf;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.EncryptConfVo;
import com.matrictime.network.request.UpdEncryptConfReq;
import com.matrictime.network.service.EncryptManageSevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class EncryptManageSeviceImpl extends SystemBaseService implements EncryptManageSevice {

    @Resource
    private NmpEncryptConfMapper nmpEncryptConfMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result updEncryptConf(UpdEncryptConfReq req) {
        Result result;
        try {
            int i;
            NmpEncryptConf nmpEncryptConf = new NmpEncryptConf();
            BeanUtils.copyProperties(req,nmpEncryptConf);
            List<NmpEncryptConf> nmpEncryptConfs = nmpEncryptConfMapper.selectByExample(null);
            if (!CollectionUtils.isEmpty(nmpEncryptConfs)){
                nmpEncryptConf.setId(nmpEncryptConfs.get(0).getId());
                i = nmpEncryptConfMapper.updateByPrimaryKeySelective(nmpEncryptConf);
            }else {
                i = nmpEncryptConfMapper.insertSelective(nmpEncryptConf);
            }
            result = buildResult(i);
        }catch (SystemException e){
            log.error("EncryptConfSeviceImpl.updEncryptConf SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("EncryptConfSeviceImpl.updEncryptConf Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
        }

        return result;
    }

    @Override
    public Result<EncryptConfVo> queryEncryptConf() {
        Result result;
        try {
            EncryptConfVo encryptConfVo = new EncryptConfVo();
            List<NmpEncryptConf> nmpEncryptConfs = nmpEncryptConfMapper.selectByExample(null);
            if (!CollectionUtils.isEmpty(nmpEncryptConfs)){
                BeanUtils.copyProperties(nmpEncryptConfs.get(0),encryptConfVo);
            }
            result = buildResult(encryptConfVo);
        }catch (SystemException e){
            log.error("EncryptConfSeviceImpl.queryEncryptConf SystemException:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult(e);
        }catch (Exception e){
            log.error("EncryptConfSeviceImpl.queryEncryptConf Exception:{}",e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result = failResult("");
        }

        return result;
    }
}
