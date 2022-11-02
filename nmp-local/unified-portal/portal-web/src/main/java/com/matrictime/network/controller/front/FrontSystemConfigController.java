package com.matrictime.network.controller.front;

import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.service.PortalSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/front/sys")
@RestController
@Slf4j
public class FrontSystemConfigController {

    @Autowired
    private PortalSystemService systemService;

    /**
     * 按条件查询系统（前端不分页）
     * @param req
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = "/querySystem",method = RequestMethod.POST)
    public Result querySystem(@RequestBody QuerySystemReq req){
        try {
            Result result = systemService.querySystem(req);
            return result;
        }catch (Exception e){
            log.error("SystemConfigController.querySystem exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

}
