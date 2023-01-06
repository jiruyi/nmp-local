package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.mapper.NmpEncryptConfMapper;
import com.matrictime.network.dao.mapper.extend.KeyInfoMapper;
import com.matrictime.network.dao.model.NmpEncryptConf;
import com.matrictime.network.dao.model.NmpKeyInfo;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.EncryptConfVo;
import com.matrictime.network.request.QueryKeyDataReq;
import com.matrictime.network.request.UpdEncryptConfReq;
import com.matrictime.network.resp.QueryKeyDataResp;
import com.matrictime.network.service.EncryptManageSevice;
import com.matrictime.network.util.DateUtils;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.*;

@Slf4j
@Service
public class EncryptManageSeviceImpl extends SystemBaseService implements EncryptManageSevice {

    @Resource
    private NmpEncryptConfMapper nmpEncryptConfMapper;

    @Resource
    private KeyInfoMapper keyInfoMapper;

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
            result = failResult(e);
        }catch (Exception e){
            log.error("EncryptConfSeviceImpl.queryEncryptConf Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    @Override
    public Result<QueryKeyDataResp> queryKeyData(QueryKeyDataReq req) {
        Result result;
        try {
            checkQueryKeyDataParam(req);
            QueryKeyDataResp resp = new QueryKeyDataResp();
            List<String> dateTime = new ArrayList<>();
            List<Long> dataValue = new ArrayList<>();
            if (req.getBeginTime() == null){
                req.setBeginTime(DateUtils.getCurrentDayStartTime());
            }
            if (req.getEndTime() == null){
                req.setEndTime(DateUtils.getCurrentDayLastTime());
            }
            String titleValue = "1846";
            List<NmpKeyInfo> nmpKeyInfos = keyInfoMapper.selectDataList(req.getDataType(), req.getBeginTime(), req.getEndTime());
            if (!CollectionUtils.isEmpty(nmpKeyInfos)){
                for (NmpKeyInfo info:nmpKeyInfos){
                    dataValue.add(info.getDataValue());
                    dateTime.add(DateUtils.dateToString(info.getCreateTime(), DateUtils.MINUTE_TIME_FORMAT));
                }
            }

            resp.setDataValue(dataValue);
            resp.setDateTime(dateTime);
            resp.setTitleValue(titleValue);
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("EncryptConfSeviceImpl.queryKeyData SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("EncryptConfSeviceImpl.queryKeyData Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    @Override
    public Result flushKey() {
        Result result;
        try {
            result = buildResult(null);
        }catch (SystemException e){
            log.error("ServerServiceImpl.start SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.start Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    private void checkQueryKeyDataParam(QueryKeyDataReq req) throws Exception{
        String dataType = req.getDataType();
        if(ParamCheckUtil.checkVoStrBlank(dataType)){
            throw new Exception("dataType"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (!LAST_UP_DATA_VALUE.equals(dataType) && !USED_UP_DATA_VALUE.equals(dataType)
        && !LAST_DOWN_DATA_VALUE.equals(dataType) && !USED_DOWN_DATA_VALUE.equals(dataType)){
            throw new Exception("dataType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
        }
    }
}
