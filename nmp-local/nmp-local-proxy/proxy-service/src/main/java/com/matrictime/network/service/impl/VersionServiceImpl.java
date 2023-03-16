package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.service.VersionService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@Slf4j
public class VersionServiceImpl extends SystemBaseService implements VersionService {
    @Override
    public Result<Integer> load(VersionLoadReq request) {
        Result<Integer> result = new Result<>();
        File dest;
        try{
            checkLoadParam(request);
            MultipartFile file = request.getFile();

            // 判断单个文件大于100M
            long fileSize = file.getSize();
            if (fileSize > DataConstants.FILE_SIZE) {
                throw new SystemException(ErrorMessageContants.FILE_IS_TOO_LARGE);
            }

            // 文件md5校验


            dest = new File(request.getUploadPath() + request.getFileName());
            // 如果pathAll路径不存在，则创建相关该路径涉及的文件夹;
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);
        }catch (SystemException e){
            result = failResult(e);
        }catch (Exception e){
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<Integer> start(VersionStartReq request) {
        return null;
    }

    @Override
    public Result<Integer> run(VersionRunReq request) {
        return null;
    }

    @Override
    public Result<Integer> stop(VersionStopReq request) {
        return null;
    }

    @Override
    public Result<Integer> uninstall(VersionUninstallReq request) {
        return null;
    }

    private void checkLoadParam(VersionLoadReq req){
        if(null == req){
            throw new SystemException("req"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getFileName())){
            throw new SystemException("fileName"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (ParamCheckUtil.checkVoStrBlank(req.getUploadPath())){
            throw new SystemException("uploadPath"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }
}
