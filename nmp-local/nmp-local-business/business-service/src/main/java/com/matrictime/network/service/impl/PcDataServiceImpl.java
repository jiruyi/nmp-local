package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.domain.BillDomainService;
import com.matrictime.network.dao.domain.PcDataDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.modelVo.NmplPcDataVo;
import com.matrictime.network.request.PcDataReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BillService;
import com.matrictime.network.service.PcDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.Future;
@Service
@Slf4j
public class PcDataServiceImpl extends SystemBaseService implements PcDataService {

    @Resource
    PcDataDomainService pcDataDomainService;

    @Override
    public Result<PageInfo> queryByConditon(PcDataReq pcDataReq) {
        Result<PageInfo> result = null;
        try {
            //多条件查询
            PageInfo<NmplPcDataVo> pageResult =  new PageInfo<>();
            pageResult = pcDataDomainService.query(pcDataReq);
            result = buildResult(pageResult);
        }catch (SystemException e){
            log.error("查询一体机数据异常：",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.error("查询一体机数据异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Async("taskExecutor")
    @Override
    @Transactional
    public Future<Result> save(PcDataReq pcDataReq) {
        Result<Integer> result;
        try {
            result = buildResult(pcDataDomainService.batchInsert(pcDataReq));
        }catch (SystemException e){
            log.info("创建一体机数据异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("创建一体机数据异常",e.getMessage());
            result = failResult("e");
        }
        return new AsyncResult<>(result);
    }
}
