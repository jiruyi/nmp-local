package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.UploadSingleFileReq;
import com.matrictime.network.response.QueryVersionFileResp;
import com.matrictime.network.response.UploadSingleFileResp;
import com.matrictime.network.service.UploadFileService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
@PropertySource(value = "classpath:/businessConfig.properties",encoding = "UTF-8")
public class UploadFileServiceImpl extends SystemBaseService implements UploadFileService {

    @Value("${upload.base.path}")
    private String uploadBasePath;

    /**
     * 上传文件通用接口
     * 上传路径为：uploadBasePath + req.getModuleName() + req.getUploadPath()
     * @param req
     * @return
     */
    @Override
    public Result<UploadSingleFileResp> uploadSingleFile(UploadSingleFileReq req) {
        Result result;
        try {
            // check param is legal
            checkUploadSingleFileParam(req);

            String filePath = req.getModuleName()+req.getUploadPath();
            File destFilePath = new File(filePath);
            if (!destFilePath.exists()){
                destFilePath.mkdirs();
            }
            String fileName = filePath+req.getFileName();
            File destFile = new File(fileName);
            req.getFile().transferTo(destFile);

            UploadSingleFileResp resp = new UploadSingleFileResp();
            resp.setFileName(filePath);
            resp.setFileName(req.getFileName());
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("uploadSingleFile SystemException" + e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("uploadSingleFile Exception" + e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    private void checkUploadSingleFileParam(UploadSingleFileReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getUploadPath())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "systemId"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getFileName())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "fileName"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getModuleName())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "moduleName"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getFile() == null || req.getFile().isEmpty()){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "file"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }


}
