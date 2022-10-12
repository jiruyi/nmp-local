package com.matrictime.network.service.impl;

import com.matrictime.network.base.enums.DeviceStatusEnum;
import com.matrictime.network.dao.domain.OutlinePcDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.CenterNmplOutlinePcInfoVo;
import com.matrictime.network.modelVo.OutlinePcVo;
import com.matrictime.network.request.BaseStationInfoRequest;
import com.matrictime.network.request.OutlinePcListRequest;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.service.OutlinePcService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2022/9/15.
 */
@Slf4j
@Service
public class OutlinePcServiceImpl implements OutlinePcService {

    @Resource
    private OutlinePcDomainService outlinePcDomainService;

    @Override
    public Result<Integer> updateOutlinePc(OutlinePcReq outlinePcReq) {
        Result result = new Result<>();
        try {
            result.setResultObj(outlinePcDomainService.updateOutlinePc(outlinePcReq));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("系统异常");
            log.info("updateOutlinePc:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> batchInsertOutlinePc(OutlinePcListRequest listRequest) {
        Result result = new Result<>();
        try {
            result.setResultObj(outlinePcDomainService.batchInsertOutlinePc(listRequest));
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg("系统异常");
            log.info("batchInsertOutlinePc:{}",e.getMessage());
        }
        return result;
    }

    @Override
    public void initInfo(List<CenterNmplOutlinePcInfoVo> list) {
        for(CenterNmplOutlinePcInfoVo centerNmplOutlinePcInfoVo : list){
            BaseStationInfoRequest baseStationInfoRequest = new BaseStationInfoRequest();
            baseStationInfoRequest.setStationId(centerNmplOutlinePcInfoVo.getDeviceId());
            List<BaseStationInfoVo> baseStationInfoVos = outlinePcDomainService.
                    selectBaseStation(baseStationInfoRequest);
            //station表中没有该数据
            if(baseStationInfoVos.size() <= NumberUtils.INTEGER_ZERO ||
                    !isActive(baseStationInfoVos.get(NumberUtils.INTEGER_ZERO))){
                outlinePcDomainService.insertOutlinePc(changeData(centerNmplOutlinePcInfoVo));
            }
            //station表中有该数据
            if(baseStationInfoVos.size() > NumberUtils.INTEGER_ZERO &&
                    isActive(baseStationInfoVos.get(NumberUtils.INTEGER_ZERO))){
                compareData(centerNmplOutlinePcInfoVo);
            }
        }
    }

    /**
     * 比较一体机中是否有该数据然后选择插入还是更新
     * @param centerNmplOutlinePcInfoVo
     */
    private void compareData(CenterNmplOutlinePcInfoVo centerNmplOutlinePcInfoVo){
        List<OutlinePcVo> outlinePcVos = outlinePcDomainService.
                selectOutlinePc(changeData(centerNmplOutlinePcInfoVo));
        if(outlinePcVos.size() > NumberUtils.INTEGER_ZERO){
            outlinePcDomainService.updateOutlinePc(changeData(centerNmplOutlinePcInfoVo));
        }else {
            outlinePcDomainService.insertOutlinePc(changeData(centerNmplOutlinePcInfoVo));
        }
    }

    /**
     * 判断station是否激活
     * @param baseStationInfoVo
     * @return
     */
    private boolean isActive(BaseStationInfoVo baseStationInfoVo){
        if(DeviceStatusEnum.ACTIVE.equals(baseStationInfoVo.getStationStatus())){
            return true;
        }
        return false;
    }

    /**
     * 将CenterNmplOutlinePcInfoVo转换成OutlinePcReq
     * @param centerNmplOutlinePcInfoVo
     * @return
     */
    private OutlinePcReq changeData(CenterNmplOutlinePcInfoVo centerNmplOutlinePcInfoVo){
        OutlinePcReq outlinePcReq = new OutlinePcReq();
        BeanUtils.copyProperties(centerNmplOutlinePcInfoVo,outlinePcReq);
        return outlinePcReq;
    }


}
