package com.matrictime.network.controller;

import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CaManageVo;
import com.matrictime.network.service.CaManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by wangqiang
 * @date 2023/11/6.
 */
@RestController
@RequestMapping(value = "/caManage",method = RequestMethod.POST)
@Slf4j
public class CaManageController {

    @Resource
    private CaManageService caManageService;

    @RequestMapping(value = "/insertCaManage",method = RequestMethod.POST)
    public Result insertCaManage(@RequestBody CaManageVo caManageVo){
        try {
            Result result = caManageService.insertCaManage(caManageVo);
            return result;
        }catch (Exception e){
            log.error("insertCaManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/deleteCaManage",method = RequestMethod.POST)
    public Result deleteCaManage(@RequestBody CaManageVo caManageVo){
        try {
            Result result = caManageService.deleteCaManage(caManageVo);
            return result;
        }catch (Exception e){
            log.error("deleteCaManage exception:{}",e.getMessage());
            return new Result(false, e.getMessage());
        }
    }
}
