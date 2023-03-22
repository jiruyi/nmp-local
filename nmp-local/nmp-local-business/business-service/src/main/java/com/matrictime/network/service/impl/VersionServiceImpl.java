package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.util.UploadFileUtils;
import com.matrictime.network.dao.domain.FileVersionDomainService;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.response.VersionFileResponse;
import com.matrictime.network.service.VersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

@Slf4j
@Service
@PropertySource(value = "classpath:/businessConfig.properties",encoding = "UTF-8")
public class VersionServiceImpl extends SystemBaseService implements VersionService {

    @Resource
    private FileVersionDomainService fileVersionDomainService;

    @Value("${version.path}")
    private String versionPath;

    @Override
    public Result<Integer> insertVersionFile(UploadVersionFileReq uploadVersionFileReq) {
        Result<Integer> result = new Result<>();
        try {
            //生成文件路径
            String filePath = getFilePath(uploadVersionFileReq);
//            String filePath = "C:\\Users\\LENOVO\\Desktop\\" + uploadVersionFileReq.getSystemType() + "\\" +
//                    uploadVersionFileReq.getVersionNo() + "\\" + uploadVersionFileReq.getFile().getOriginalFilename();
            uploadVersionFileReq.setFilePath(filePath);
            //操作数据库文件表
            int insertFlag = fileVersionDomainService.insertFileVersion(uploadVersionFileReq);
            //文件上传到本地
            UploadFileUtils uploadFileUtils = new UploadFileUtils();
            uploadFileUtils.uploadFile(uploadVersionFileReq,insertFlag);
            result.setResultObj(insertFlag);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("insertVersionFile:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> updateVersionFile(UploadVersionFileReq uploadVersionFileReq) {
        Result result = new Result<>();
        try {
            //生成文件路径
            String filePath = getFilePath(uploadVersionFileReq);
//            String filePath = "C:\\Users\\LENOVO\\Desktop\\" + uploadVersionFileReq.getSystemType() + "\\" +
//                    uploadVersionFileReq.getVersionNo() + "\\" + uploadVersionFileReq.getFile().getOriginalFilename();
            uploadVersionFileReq.setFilePath(filePath);
            int updateFlag = fileVersionDomainService.updateFileVersion(uploadVersionFileReq);
            UploadFileUtils uploadFileUtils = new UploadFileUtils();
            uploadFileUtils.uploadFile(uploadVersionFileReq,updateFlag);
            result.setResultObj(updateFlag);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("updateVersionFile:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> deleteVersionFile(UploadVersionFileReq uploadVersionFileReq) {
        Result result = new Result<>();
        try {
            int deleteFlag = fileVersionDomainService.deleteFileVersion(uploadVersionFileReq);
            result.setSuccess(true);
            result.setResultObj(deleteFlag);
        }catch (Exception e){
            log.info("deleteVersionFile:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
        }


        return result;
    }

    @Override
    public Result<VersionFileResponse> selectVersionFile(UploadVersionFileReq uploadVersionFileReq) {
        Result result = new Result<>();
        try {
            VersionFileResponse versionFileResponse = fileVersionDomainService.selectVersionFile(uploadVersionFileReq);
            result.setResultObj(versionFileResponse);
            result.setSuccess(true);
        }catch (Exception e){
            log.info("selectVersionFile:{}",e.getMessage());
            result.setSuccess(false);
            result.setErrorCode(e.getMessage());
        }
        return result;
    }

    private String getFilePath(UploadVersionFileReq uploadVersionFileReq){
        return versionPath + uploadVersionFileReq.getSystemType() +
                DataConstants.LINUX_SEPARATOR + uploadVersionFileReq.getVersionNo() +
                DataConstants.LINUX_SEPARATOR + uploadVersionFileReq.getFileName();
    }

}
