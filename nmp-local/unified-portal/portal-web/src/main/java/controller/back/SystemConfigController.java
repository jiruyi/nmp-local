package controller.back;

import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.AddSystemReq;
import com.matrictime.network.service.PortalSystemService;
import controller.aop.MonitorRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/sysConfig")
@RestController
@Slf4j
public class SystemConfigController {

    @Autowired
    private PortalSystemService systemService;

    @MonitorRequest
    @RequestMapping(value = "/addSystem")
    public Result addSystem(@RequestBody AddSystemReq req){
        try {
            Result result = systemService.addSystem(req);
            return result;
        }catch (Exception e){
            log.error("SystemConfigController.addSystem exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

}
