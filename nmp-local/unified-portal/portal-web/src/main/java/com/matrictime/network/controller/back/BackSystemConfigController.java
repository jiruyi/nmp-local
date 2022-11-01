package com.matrictime.network.controller.back;

import com.matrictime.network.controller.aop.MonitorRequest;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.AddSystemReq;
import com.matrictime.network.request.DelSystemReq;
import com.matrictime.network.request.QuerySystemByPageReq;
import com.matrictime.network.request.UpdSystemReq;
import com.matrictime.network.response.UploadImgResp;
import com.matrictime.network.service.FileService;
import com.matrictime.network.service.PortalSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.matrictime.network.base.exception.ErrorMessageContants.UPLOAD_FILE_FAIL;

@RequestMapping(value = "/back/sys")
@RestController
@Slf4j
public class BackSystemConfigController {

    @Value("${upload.image.path}")
    private String imagePath;

    @Autowired
    private PortalSystemService systemService;

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public Result<UploadImgResp> uploadFile(MultipartFile file){
        try {
            return fileService.uploadImg(file,imagePath);
        }catch (Exception e){
            log.info("SystemConfigController.uploadFile:{}",e.getMessage());
            return new Result<>(false, UPLOAD_FILE_FAIL);
        }

    }


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

    @MonitorRequest
    @RequestMapping(value = "/delSystem")
    public Result delSystem(@RequestBody DelSystemReq req){
        try {
            Result result = systemService.delSystem(req);
            return result;
        }catch (Exception e){
            log.error("SystemConfigController.delSystem exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    @MonitorRequest
    @RequestMapping(value = "/updSystem")
    public Result updSystem(@RequestBody UpdSystemReq req){
        try {
            Result result = systemService.updSystem(req);
            return result;
        }catch (Exception e){
            log.error("SystemConfigController.updSystem exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

    @MonitorRequest
    @RequestMapping(value = "/querySystemByPage")
    public Result querySystemByPage(@RequestBody QuerySystemByPageReq req){
        try {
            Result result = systemService.querySystemByPage(req);
            return result;
        }catch (Exception e){
            log.error("SystemConfigController.querySystemByPage exception:{}",e.getMessage());
            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
        }
    }

}
