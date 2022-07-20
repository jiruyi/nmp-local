package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.domain.BillDomainService;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.modelVo.NmplRoleVo;
import com.matrictime.network.request.BillRequest;
import com.matrictime.network.response.MenuResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BillService;
import com.matrictime.network.service.MenuService;
import com.matrictime.network.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class BillServiceImpl extends SystemBaseService implements BillService {
    @Autowired
    BillDomainService billDomainService;

    @Override
    public Result<PageInfo> queryByConditon(BillRequest billRequest) {
        Result<PageInfo> result = null;
        try {
            //多条件查询
            PageInfo<NmplBillVo> pageResult =  new PageInfo<>();
            pageResult = billDomainService.queryByConditions(billRequest);
            result = buildResult(pageResult);
        }catch (SystemException e){
            log.error("查询话单异常：",e.getMessage());
            result = failResult(e);
        } catch (Exception e){
            log.error("查询话单异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Async("taskExecutor")
    @Override
    @Transactional
    public Future<Result> save(BillRequest billRequest) {
        Result<Integer> result;
        try {
            result = buildResult(billDomainService.save(billRequest));
        }catch (SystemException e){
            log.info("创建话单异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("创建话单异常",e.getMessage());
            result = failResult("e");
        }
        return new AsyncResult<>(result);
    }

}
