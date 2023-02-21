package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmpEncryptConfMapper;
import com.matrictime.network.dao.mapper.NmpKeyInfoMapper;
import com.matrictime.network.dao.mapper.NmpOperateServerInfoMapper;
import com.matrictime.network.dao.mapper.extend.KeyInfoMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.EncryptConfVo;
import com.matrictime.network.request.QueryKeyDataReq;
import com.matrictime.network.request.UpdEncryptConfReq;
import com.matrictime.network.resp.FlushKeyStatusResp;
import com.matrictime.network.resp.QueryKeyDataResp;
import com.matrictime.network.service.EncryptManageSevice;
import com.matrictime.network.util.DateUtils;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.base.exception.ErrorMessageContants.PLEASE_WAIT;
import static com.matrictime.network.constant.DataConstants.UPDTIME_DESC;
import static com.matrictime.network.exception.ErrorMessageContants.CONFIG_IS_NOT_EXIST;

@Slf4j
@Service
public class EncryptManageSeviceImpl extends SystemBaseService implements EncryptManageSevice {

    @Resource
    private NmpEncryptConfMapper nmpEncryptConfMapper;

    @Resource
    private KeyInfoMapper keyInfoMapper;

    @Resource
    private NmpKeyInfoMapper nmpKeyInfoMapper;

    @Resource
    private NmpOperateServerInfoMapper nmpOperateServerInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Integer> updEncryptConf(UpdEncryptConfReq req) {
        Result<Integer> result;
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
            List<String> dateTime = KEY_DATA_TIME;
            List<Long> dataValue = new ArrayList<>();

            Date now = new Date();
            String titleValue = getTitleValue(req.getDataType());

            String cacheKey = DataConstants.SECURITY_SERVER + DateUtils.formatDateToInteger(now);
            Object cacheValue = redisTemplate.opsForValue().get(cacheKey);

            // 查询缓存有值
            if (cacheValue != null && cacheValue instanceof JSONObject){
                JSONObject value = (JSONObject) cacheValue;
                Date tmpEndTime = value.getDate(END_DATE_TIME);
                List<Long> valueList = value.getObject(VALUE_LIST, List.class);

                // 需要追加数据
                if (DateUtils.checkTimeGreaterValue(tmpEndTime,now,KEY_INFO_DISPLAY_GAP)){
                    Date endTime = getEndTime(now);
                    req.setBeginTime(tmpEndTime);
                    req.setEndTime(endTime);
                    List<Long> newList = getDataValue(req);
                    if (!CollectionUtils.isEmpty(newList)){
                        valueList.addAll(newList);
                    }
                    value.put(END_DATE_TIME,endTime);
                    value.put(VALUE_LIST,valueList);
                    redisTemplate.opsForValue().set(cacheKey,value, NumberUtils.INTEGER_ONE, TimeUnit.DAYS);
                }
                dataValue = valueList;

            }else {// 缓存没值直接查询数据库并存入缓存
                Date endTime = getEndTime(now);
                req.setBeginTime(DateUtils.getCurrentDayStartTime());
                req.setEndTime(endTime);
                dataValue = getDataValue(req);
                JSONObject value = new JSONObject();
                value.put(END_DATE_TIME,endTime);
                value.put(VALUE_LIST,dataValue);
                redisTemplate.opsForValue().set(cacheKey,value, NumberUtils.INTEGER_ONE, TimeUnit.DAYS);
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

    private List<Long> getDataValue(QueryKeyDataReq req){
        List<Long> dataValue = new ArrayList<>();
        List<NmpKeyInfo> nmpKeyInfos = keyInfoMapper.selectDataList(req.getDataType(), req.getBeginTime(), req.getEndTime());
        if (!CollectionUtils.isEmpty(nmpKeyInfos)){
            Long tempSum = 0L;
            Date timeNode = DateUtils.addMinuteForDate(req.getBeginTime(),KEY_INFO_DISPLAY_GAP);
            int i=0;
            while (i<nmpKeyInfos.size()){
                NmpKeyInfo keyInfo = nmpKeyInfos.get(i);
                Date updateTime = keyInfo.getUpdateTime();
                if (updateTime !=null){
                    if (timeNode.after(updateTime)){
                        tempSum = tempSum + keyInfo.getDataValue();
                        i++;
                    }else {
                        dataValue.add(tempSum);
                        tempSum = 0L;
                        timeNode = DateUtils.addMinuteForDate(timeNode,KEY_INFO_DISPLAY_GAP);
                    }
                }else {
                    i++;
                    log.warn("上报数据异常：id["+keyInfo.getId()+"],updateTime为空");
                }
            }
        }
        return dataValue;
    }

    private Date getEndTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int min = calendar.get(Calendar.MINUTE);
        // TODO 适配优化
        if (min < KEY_INFO_DISPLAY_GAP){
            calendar.set(Calendar.MINUTE,0);
        }else {
            calendar.set(Calendar.MINUTE,30);
        }
        return calendar.getTime();
    }

    private String getTitleValue(String dataType){
        String titleValue = "0MB";
        switch (dataType){
            case LAST_UP_DATA_VALUE:
            case LAST_DOWN_DATA_VALUE:
                NmpKeyInfoExample example = new NmpKeyInfoExample();
                example.setOrderByClause(UPDTIME_DESC);
                example.createCriteria().andDataTypeEqualTo(dataType).andUpdateTimeGreaterThanOrEqualTo(DateUtils.addMinuteForDate(new Date(),-15));
                List<NmpKeyInfo> nmpKeyInfos = nmpKeyInfoMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(nmpKeyInfos)){
                    titleValue = dataChange(nmpKeyInfos.get(0).getDataValue());
                }
                break;
            case USED_UP_DATA_VALUE:
            case USED_DOWN_DATA_VALUE:
                BigDecimal dataValueSum = keyInfoMapper.getDataValueSum(dataType);
                titleValue = dataChangeBigDecimal(dataValueSum);
                break;
        }
        return titleValue;
    }

    @Override
    public Result flushKey() {
        Result result;
        try {
            checkFlushKeyStatus();
            NmpOperateServerInfoExample example = new NmpOperateServerInfoExample();
            example.createCriteria().andOperateTypeEqualTo(OPERATE_TYPE_UPDATE_KEY);
            NmpOperateServerInfo serverInfo = new NmpOperateServerInfo();
            serverInfo.setOperateStatus(OPERATE_STATUS_WAIT);
            serverInfo.setOperateRate(NumberUtils.SHORT_ZERO);
            serverInfo.setCreateTime(new Date());
            result = buildResult(nmpOperateServerInfoMapper.updateByExampleSelective(serverInfo,example));
        }catch (SystemException e){
            log.error("EncryptConfSeviceImpl.flushKey SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("EncryptConfSeviceImpl.flushKey Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    @Override
    public Result<FlushKeyStatusResp> getFlushKeyStatus() {
        Result<FlushKeyStatusResp> result;
        try {
            NmpOperateServerInfoExample example = new NmpOperateServerInfoExample();
            example.createCriteria().andOperateTypeEqualTo(OPERATE_TYPE_UPDATE_KEY);
            List<NmpOperateServerInfo> nmpOperateServerInfos = nmpOperateServerInfoMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(nmpOperateServerInfos)){
                throw new Exception(CONFIG_IS_NOT_EXIST);
            }else {
                FlushKeyStatusResp resp = new FlushKeyStatusResp();
                NmpOperateServerInfo serverInfo = nmpOperateServerInfos.get(0);
//                if (serverInfo.getOperateRate() == null){
//                    resp.setOperateRate(NumberUtils.SHORT_ZERO);
//                    resp.setOperateStatus(OPERATE_STATUS_INIT);
//                    resp.setCreateTime(null);
//                }else {
                    resp.setOperateStatus(serverInfo.getOperateStatus());
                    resp.setOperateRate(serverInfo.getOperateRate());
                    resp.setCreateTime(serverInfo.getCreateTime());
//                }
                result = buildResult(resp);
            }

        }catch (SystemException e){
            log.error("EncryptConfSeviceImpl.getFlushKeyStatus SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("EncryptConfSeviceImpl.getFlushKeyStatus Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    private void checkFlushKeyStatus() throws Exception {
        NmpOperateServerInfoExample example = new NmpOperateServerInfoExample();
        example.createCriteria().andOperateTypeEqualTo(OPERATE_TYPE_UPDATE_KEY);
        List<NmpOperateServerInfo> nmpOperateServerInfos = nmpOperateServerInfoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(nmpOperateServerInfos)){
            throw new Exception(CONFIG_IS_NOT_EXIST);
        }else {
            NmpOperateServerInfo serverInfo = nmpOperateServerInfos.get(0);
            if (OPERATE_STATUS_WAIT.compareTo(serverInfo.getOperateStatus()) == 0){
                throw new SystemException(PLEASE_WAIT);
            }
        }
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

    private String dataChange(Long data){
        //转换成GB
        Double resData = data/(1024.0*1024.0);
        if(resData >= 999.9){
            resData = resData/1024.0;
            //转换成TB
            if(resData >= 999.9){
                resData = resData/1024.0;
                return String.format("%.2f", resData) + "TB";
            }
            return String.format("%.2f", resData) + "GB";
        }
        return String.format("%.2f", resData) + "MB";
    }

    private String dataChangeBigDecimal(BigDecimal data){
        //转换成GB
        BigDecimal divideNum = new BigDecimal(1024);
        BigDecimal compare = new BigDecimal("999.9");
        BigDecimal resData = data.divide(divideNum).divide(divideNum).setScale(2, RoundingMode.DOWN);
        if(resData.compareTo(compare) == 1){
            resData = resData.divide(divideNum).setScale(2, RoundingMode.DOWN);
            //转换成TB
            if(resData.compareTo(compare) == 1){
                resData = resData.divide(divideNum).setScale(2, RoundingMode.DOWN);
                return resData.toPlainString() + "TB";
            }
            return resData.toPlainString() + "GB";
        }
        return resData.toPlainString() + "MB";
    }


}
