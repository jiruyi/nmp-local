package com.matrictime.network.facade;

import com.matrictime.network.model.Result;
import com.matrictime.network.request.PhysicalDeviceHeartbeatReq;
import com.matrictime.network.request.PhysicalDeviceResourceReq;
import com.matrictime.network.request.SystemResourceReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value = "nmp-local-business",path = "nmp-local-business")
public interface MonitorFacade {

    @RequestMapping(value= "/monitor/physicalDeviceHeartbeat",method = RequestMethod.POST)
    Result physicalDeviceHeartbeat(PhysicalDeviceHeartbeatReq req);

    @RequestMapping(value= "/monitor/physicalDeviceResource",method = RequestMethod.POST)
    Result physicalDeviceResource(PhysicalDeviceResourceReq req);

    @RequestMapping(value= "/monitor/systemResource",method = RequestMethod.POST)
    Result systemResource(SystemResourceReq req);
}
