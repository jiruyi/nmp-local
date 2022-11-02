package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.SnowFlake;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.response.UploadImgResp;
import com.matrictime.network.service.FileService;
import com.matrictime.network.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

import static com.matrictime.network.base.constant.DataConstants.IMG_TYPE_PNG;
import static com.matrictime.network.base.exception.ErrorMessageContants.IMG_IS_NOT_PNG;
import static com.matrictime.network.constant.DataConstants.KEY_POINT;
import static com.matrictime.network.constant.DataConstants.KEY_SLASH;

@Slf4j
@Component
public class FileServiceImpl extends SystemBaseService implements FileService {

    @Override
    public Result<UploadImgResp> uploadImg(MultipartFile file, String imagePath, String imageDir){
        Result<UploadImgResp> result = new Result<>();
        UploadImgResp uploadImgResp = new UploadImgResp();
        File dest;
        try{
            String datePath = DateUtils.formatDateToInteger(new Date());
            String originalFilename = file.getOriginalFilename();
            String fileType = FilenameUtils.getExtension(originalFilename).toLowerCase();
            String fileName = SnowFlake.nextId_String();
            StringBuffer filePath = new StringBuffer();

            // 判断单个文件大于10M
            long fileSize = file.getSize();
            if (fileSize > DataConstants.FILE_SIZE) {
                throw new SystemException(ErrorMessageContants.IMG_TOO_LARGE);
            }


            //判断上传是否是图
            if(isImage(fileType)){
                filePath.append(imageDir).append(datePath).append(KEY_SLASH).append(fileName).append(KEY_POINT).append(fileType);
                dest = new File(imagePath + filePath.toString());
            }else {
                throw new SystemException(IMG_IS_NOT_PNG);
            }
            // 如果pathAll路径不存在，则创建相关该路径涉及的文件夹;
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }

            // 将获取到的附件file,transferTo写入到指定的位置(即:创建dest时，指定的路径)
            file.transferTo(dest);
            uploadImgResp.setFilePath(filePath.toString());
            result.setResultObj(uploadImgResp);
        } catch (SystemException e){
            log.info("FileServiceImpl.uploadImg SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e) {
            log.info("FileServiceImpl.uploadImg Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }


    private boolean isImage(String fileType){
        if(IMG_TYPE_PNG.equals(fileType)){
            return true;
        }
        return false;
    }

}
