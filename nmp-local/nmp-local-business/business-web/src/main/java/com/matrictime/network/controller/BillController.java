package com.matrictime.network.controller;

import com.matrictime.network.annotation.SystemLog;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplBillVo;
import com.matrictime.network.request.BillRequest;
import com.matrictime.network.request.RoleRequest;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.BillService;
import com.matrictime.network.util.ListSplitUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 话单管理
 */
@RequestMapping(value = "/bill")
@Api(value = "话单管理",tags = "话单管理")
@RestController
@Slf4j
public class BillController {
    @Autowired
    BillService billService;

    /**
     * 话单查询接口
     * @param billRequest
     * @return
     */
    @ApiOperation(value = "话单多条件查询接口",notes = "话单多条件查询接口")
    @RequestMapping(value = "/queryByCondition",method = RequestMethod.POST)
    @RequiresPermissions("sys:bill:query")
    @SystemLog(opermodul = "话单管理模块",operDesc = "查询话单",operType = "查询")
    public Result<PageInfo> queryBillByConditon(@RequestBody BillRequest billRequest){
        return billService.queryByConditon(billRequest);
    }

    /**
     * 话单创建接口
     * @param billRequest
     * @return
     */
    @ApiOperation(value = "话单创建接口",notes = "话单创建")
    @RequestMapping(value = "/saveBill",method = RequestMethod.POST)
    public Result saveRole(@RequestBody BillRequest billRequest) throws ExecutionException, InterruptedException {
        //根据具体需求修改  现可执行单个数据以及批量数据插入  如果数据量再大的情况采用线程池实现
        if (billRequest.getNmplBillVoList()!=null&&billRequest.getNmplBillVoList().size()>10){
            List<Result>futureList= new ArrayList<>();
            List<List<NmplBillVo>> list = ListSplitUtil.split(billRequest.getNmplBillVoList(),10);
            for (int i=0;i<list.size();i++){
                BillRequest request = new BillRequest();
                request.setNmplBillVoList(list.get(i));
                futureList.add(billService.save(request).get());
            }
            Result result = new Result();
            result.setSuccess(true);
            result.setResultObj(futureList);
            return result;
        }else {
           return billService.save(billRequest).get();
        }
    }

}
