package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.OutlinePcDomainService;
import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.dao.model.NmplOutlineSorterInfo;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplOutlinePcInfoVo;
import com.matrictime.network.request.CompanyInfoRequest;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.request.OutlineSorterReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.OutlinePcService;
import com.matrictime.network.util.CommonCheckUtil;
import com.matrictime.network.util.CsvUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class OutlinePcServiceImpl extends SystemBaseService implements OutlinePcService {
    @Resource
    OutlinePcDomainService outlinePcDomainService;

    @Override
    public Result save(OutlinePcReq outlinePcReq) {
        Result<Integer> result;
        try {
            if(!parmLenthCheck(outlinePcReq)){
                throw new SystemException(ErrorMessageContants.PARAM_LENTH_ERROR_MSG);
            }
            NmplUser nmplUser = RequestContext.getUser();
            outlinePcReq.setCreateUser(nmplUser.getNickName());
            result =  buildResult(outlinePcDomainService.save(outlinePcReq));
        }catch (SystemException e){
            log.info("创建异常",e.getMessage());
            result = failResult("");
        }catch (Exception e){
            log.info("创建异常",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result modify(OutlinePcReq outlinePcReq) {
        Result<Integer> result;
        try {
            if(!parmLenthCheck(outlinePcReq)){
                throw new SystemException(ErrorMessageContants.PARAM_LENTH_ERROR_MSG);
            }
            NmplUser nmplUser = RequestContext.getUser();
            outlinePcReq.setUpdateUser(nmplUser.getNickName());
            result =  buildResult(outlinePcDomainService.modify(outlinePcReq));
        }catch (SystemException e){
            log.info("修改异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("修改异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result delete(OutlinePcReq outlinePcReq) {
        Result<Integer> result;
        try {
            NmplUser nmplUser = RequestContext.getUser();
            outlinePcReq.setUpdateUser(nmplUser.getNickName());
            result =  buildResult(outlinePcDomainService.delete(outlinePcReq));
        }catch (SystemException e){
            log.info("删除异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("删除异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<PageInfo> queryByConditon(OutlinePcReq outlinePcReq) {
        Result<PageInfo> result = null;
        try {
            //多条件查询
            PageInfo<NmplOutlinePcInfoVo> pageResult =  new PageInfo<>();
            pageResult = outlinePcDomainService.query(outlinePcReq);
            result = buildResult(pageResult);
        }catch (SystemException e){
            log.error("查询异常：",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("查询异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result upload(MultipartFile file) {
        Result result;
        try {
            File tmp = new File(System.getProperty("user.dir")+File.separator+file.getName()+".csv");
            log.info(System.getProperty("user.dir")+File.separator+file.getName());
            file.transferTo(tmp);
            List<NmplOutlinePcInfo> nmplOutlinePcInfoList = CsvUtils.readCsvToPc(tmp);
            tmp.delete();
            result = buildResult(outlinePcDomainService.batchInsert(nmplOutlinePcInfoList));
        }catch (SystemException e){
            log.error("查询异常：",e.getMessage());
            result = failResult(e);
        } catch (Exception e) {
            log.error("查询异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    private boolean parmLenthCheck(OutlinePcReq outlinePcReq){
        boolean flag = true;
        if(outlinePcReq.getStationNetworkId()!=null){
            flag = CommonCheckUtil.checkStringLength(outlinePcReq.getStationNetworkId(), null, 32);
        }
        if(outlinePcReq.getDeviceName()!=null){
            flag = CommonCheckUtil.checkStringLength(outlinePcReq.getDeviceName(), null, 16);
        }
        if(outlinePcReq.getRemark()!=null){
            flag = CommonCheckUtil.checkStringLength(outlinePcReq.getRemark(), null, 256);
        }
        return flag;
    }
}
