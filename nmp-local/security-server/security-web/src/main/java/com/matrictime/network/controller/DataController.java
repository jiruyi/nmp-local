package com.matrictime.network.controller;


import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.model.Result;
import com.matrictime.network.req.DataInfoReq;
import com.matrictime.network.req.DataReq;
import com.matrictime.network.service.DataService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"/data"})
@RestController
public class DataController {

    @Resource
    private DataService dataService;

    /**
     * 查询波形图数据
     * @param dataReq
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = {"/queryWaveformData"}, method = {RequestMethod.POST})
    public Result queryWaveformData(@RequestBody DataReq dataReq) {
        return this.dataService.queryWaveformData(dataReq);
    }

    /**
     * 查询最新数据
     * @param dataReq
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = {"/queryCurrentData"}, method = {RequestMethod.POST})
    public Result queryCurrentData(@RequestBody DataReq dataReq) {
        return this.dataService.queryCurrentData(dataReq);
    }


    /**
     * 新增统计数据
     * @param req
     * @return
     */
    @MonitorRequest
    @RequestMapping(value = {"/insert"}, method = {RequestMethod.POST})
    public Result insert(@RequestBody DataInfoReq req) {
        return this.dataService.insert(req);
    }
}
