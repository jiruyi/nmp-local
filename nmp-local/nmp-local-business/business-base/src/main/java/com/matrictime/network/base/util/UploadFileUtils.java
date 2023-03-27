package com.matrictime.network.base.util;

import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.UploadVersionFileReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author by wangqiang
 * @date 2023/3/15.
 */

@Slf4j
public class UploadFileUtils {

    public Result<Integer> uploadFile(UploadVersionFileReq uploadVersionFileReq, Integer fileIsExit) throws Exception{
        Result result = new Result<>();
        String filePath = uploadVersionFileReq.getFilePath() + uploadVersionFileReq.getFileName();
        File dest = new File(filePath);
        String substring = uploadVersionFileReq.getFileSize().
                substring(0, (uploadVersionFileReq.getFileSize().length() - 2));
        int len = uploadVersionFileReq.getFile().getOriginalFilename().length();
        String fileType = uploadVersionFileReq.getFile().getOriginalFilename().substring((len-6),len);
        if(!("tar.gz".equals(fileType))){
            throw new SystemException("文件后缀名不匹配");
        }
        // 判断单个文件大于100M
        if(Float.parseFloat(substring) > DataConstants.FILE_SIZE){
            throw new SystemException("文件太大");
        }
        //更新文件操作
        if(fileIsExit == DataConstants.FILE_UPDATE_SUCCESS){
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
                uploadVersionFileReq.getFile().transferTo(dest);
            }else {
                dest.delete();
                File newFile = new File(filePath);
                uploadVersionFileReq.getFile().transferTo(newFile);
            }
            result.setResultObj(NumberUtils.INTEGER_ONE);
            result.setSuccess(true);
            return result;
        }
        //新增文件操作
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        // 将获取到的附件file,transferTo写入到指定的位置(即:创建dest时，指定的路径)
        uploadVersionFileReq.getFile().transferTo(dest);
        result.setSuccess(true);
        result.setResultObj(NumberUtils.INTEGER_ONE);
        return result;
    }
}
