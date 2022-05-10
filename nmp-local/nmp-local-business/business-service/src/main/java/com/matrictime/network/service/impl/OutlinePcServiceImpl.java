package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.OutlinePcDomainService;
import com.matrictime.network.dao.model.NmplOutlinePcInfo;
import com.matrictime.network.dao.model.NmplOutlineSorterInfo;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplOutlinePcInfoVo;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.OutlinePcService;
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
            NmplUser nmplUser = RequestContext.getUser();
            outlinePcReq.setCreateUser(nmplUser.getNickName());
            result =  buildResult(outlinePcDomainService.save(outlinePcReq));
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
            NmplUser nmplUser = RequestContext.getUser();
            outlinePcReq.setUpdateUser(nmplUser.getNickName());
            result =  buildResult(outlinePcDomainService.modify(outlinePcReq));
        }catch (Exception e){
            log.info("修改异常",e.getMessage());
            result = failResult(e);
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
        }catch (Exception e){
            log.info("删除异常",e.getMessage());
            result = failResult(e);
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
        }catch (Exception e){
            log.error("查询异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    @Override
    public Result upload(MultipartFile file) {
        Result result;
        try {
            File tmp = new File(System.getProperty("user.dir")+"\\"+file.getName());
            FileUtils.copyInputStreamToFile(file.getInputStream(), tmp);
            List<NmplOutlinePcInfo> nmplOutlinePcInfoList = CsvUtils.readCsvToPc(tmp);
            tmp.delete();
            result = buildResult(outlinePcDomainService.batchInsert(nmplOutlinePcInfoList));
        } catch (IOException e) {
            log.error("查询异常：",e.getMessage());
            result = failResult(e);
        }
        return result;
    }
}
