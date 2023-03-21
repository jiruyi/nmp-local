package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.*;
import com.matrictime.network.service.VersionService;
import com.matrictime.network.util.FileHahUtil;
import com.matrictime.network.util.FileUtils;
import com.matrictime.network.util.ParamCheckUtil;
import com.matrictime.network.util.ShellUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.KEY_SLASH;
import static com.matrictime.network.exception.ErrorMessageContants.*;

@Service
@Slf4j
public class VersionServiceImpl extends SystemBaseService implements VersionService {
    @Override
    public Result load(VersionLoadReq request) {
        Result result = new Result<>();
        File dest;
        try{
            checkLoadParam(request);
            MultipartFile file = request.getFile();

            // 判断单个文件大于100M
            long fileSize = file.getSize();
            if (fileSize > DataConstants.FILE_SIZE) {
                throw new SystemException(ErrorMessageContants.FILE_IS_TOO_LARGE);
            }

            dest = new File(request.getUploadPath() + request.getFileName());
            // 如果pathAll路径不存在，则创建相关该路径涉及的文件夹;
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            file.transferTo(dest);

            // 文件md5校验
            String hashCode = FileHahUtil.md5HashCode(new FileInputStream(dest));
            if (!request.getCheckCode().equals(hashCode)){
                if (dest.exists()){
                    dest.delete();
                }
                throw new SystemException(ErrorMessageContants.FILE_IS_DIFF);
            }
        }catch (SystemException e){
            log.warn("VersionServiceImpl.load SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.warn("VersionServiceImpl.load Exception:{}",e);
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<Integer> start(VersionStartReq request) {
        Result result = new Result<>();
        try{
            checkStartParam(request);

            String tarGzFileName = request.getUploadPath()+request.getFileName();
            File tarGzFile = new File(tarGzFileName);
            if (!tarGzFile.exists()){
                throw new SystemException(tarGzFileName+FILE_NOT_EXIST);
            }

            // 解压
            FileUtils.decompressTarGz(tarGzFile,request.getUploadPath());
            String operDir = getOpenDir(request.getUploadPath(), request.getFileName());

            // 安装
            String installFileName = operDir+OPER_INSTALL;
            File installFile = new File(installFileName);
            if (!installFile.exists()){
                throw new SystemException(installFileName+FILE_NOT_EXIST);
            }

            List<String> install = getShellList();
            install.add(installFileName);
            Integer installRes = ShellUtil.runShell(install);
            if (!installRes.equals(NumberUtils.INTEGER_ZERO)){
                throw new SystemException(INSTALL_FAIL);
            }

            // 启动
            String runFileName = operDir+OPER_RUN;
            File runFile = new File(runFileName);
            if (!runFile.exists()){
                throw new SystemException(runFileName+FILE_NOT_EXIST);
            }
            List<String> run = getShellList();
            run.add(runFileName);
            Integer runRes = ShellUtil.runShell(run);
            if (!runRes.equals(NumberUtils.INTEGER_ZERO)){
                throw new SystemException(RUN_FAIL);
            }

        }catch (SystemException e){
            log.warn("VersionServiceImpl.start SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.warn("VersionServiceImpl.start Exception:{}",e);
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<Integer> run(VersionRunReq request) {
        Result result = new Result<>();
        try{
            checkRunParam(request);

            // 启动
            String operDir = getOpenDir(request.getUploadPath(), request.getFileName());
            String runFileName = operDir+OPER_RUN;
            File runFile = new File(runFileName);
            if (!runFile.exists()){
                throw new SystemException(runFileName+FILE_NOT_EXIST);
            }
            List<String> run = getShellList();
            run.add(runFileName);
            Integer runRes = ShellUtil.runShell(run);
            if (!runRes.equals(NumberUtils.INTEGER_ZERO)){
                throw new SystemException(RUN_FAIL);
            }

        }catch (SystemException e){
            log.warn("VersionServiceImpl.run SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.warn("VersionServiceImpl.run Exception:{}",e);
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<Integer> stop(VersionStopReq request) {
        Result result = new Result<>();
        try{
            checkStopParam(request);

            // 停止
            String operDir = getOpenDir(request.getUploadPath(), request.getFileName());
            String stopFileName = operDir+OPER_STOP;
            File stopFile = new File(stopFileName);
            if (!stopFile.exists()){
                throw new SystemException(stopFileName+FILE_NOT_EXIST);
            }
            List<String> stop = getShellList();
            stop.add(stopFileName);
            Integer stopRes = ShellUtil.runShell(stop);
            if (!stopRes.equals(NumberUtils.INTEGER_ZERO)){
                throw new SystemException(STOP_FAIL);
            }

        }catch (SystemException e){
            log.warn("VersionServiceImpl.stop SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.warn("VersionServiceImpl.stop Exception:{}",e);
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<Integer> uninstall(VersionUninstallReq request) {
        Result result = new Result<>();
        try{
            checkUninstallParam(request);

            // 卸载
            String operDir = getOpenDir(request.getUploadPath(), request.getFileName());
            String uninstallFileName = operDir+OPER_UNINSTALL;
            File uninstallFile = new File(uninstallFileName);
            if (!uninstallFile.exists()){
                throw new SystemException(uninstallFileName+FILE_NOT_EXIST);
            }
            List<String> uninstall = getShellList();
            uninstall.add(uninstallFileName);
            Integer stopRes = ShellUtil.runShell(uninstall);


            if (stopRes.equals(NumberUtils.INTEGER_ZERO)){
                List<String> rmrf = new ArrayList<>();
                rmrf.add("rm");
                rmrf.add("-rf");
                rmrf.add(request.getUploadPath());
                Integer rmrfRes = ShellUtil.runShell(rmrf);
                if (!rmrfRes.equals(NumberUtils.INTEGER_ZERO)){
                    throw new SystemException(UNINSTALL_FAIL);
                }
            }else {
                throw new SystemException(UNINSTALL_FAIL);
            }

        }catch (SystemException e){
            log.warn("VersionServiceImpl.uninstall SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.warn("VersionServiceImpl.uninstall Exception:{}",e);
            result = failResult("");
        }
        return result;
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
        if (ParamCheckUtil.checkVoStrBlank(req.getCheckCode())){
            throw new SystemException("checkCode"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkStartParam(VersionStartReq req){
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

    private void checkRunParam(VersionRunReq req){
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

    private void checkStopParam(VersionStopReq req){
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

    private void checkUninstallParam(VersionUninstallReq req){
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

    private String getOpenDir (String uploadPath, String fileName){
        StringBuffer openDir = new StringBuffer(uploadPath);
        if (ParamCheckUtil.checkVoStrBlank(fileName)){
            return openDir.toString();
        }else {
            openDir.append(FilenameUtils.removeExtension(FilenameUtils.removeExtension(fileName)));
            openDir.append(KEY_SLASH);
            return openDir.toString();
        }
    }

    private List<String> getShellList(){
        List<String> list = new ArrayList<>();
        list.add("sh");
        return list;
    }
}
