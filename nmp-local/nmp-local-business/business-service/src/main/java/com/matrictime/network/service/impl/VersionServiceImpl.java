package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplVersionFileVo;
import com.matrictime.network.modelVo.NmplVersionVo;
import com.matrictime.network.modelVo.StationInfoVo;
import com.matrictime.network.request.*;
import com.matrictime.network.response.*;
import com.matrictime.network.service.UploadFileService;
import com.matrictime.network.service.VersionService;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static com.matrictime.network.constant.DataConstants.*;

@Slf4j
@Service
public class VersionServiceImpl extends SystemBaseService implements VersionService {

    @Value("${upload.module.versionFile}")
    private String moduleName;

    @Autowired(required = false)
    private NmplVersionMapper nmplVersionMapper;

    @Autowired(required = false)
    private NmplVersionFileMapper nmplVersionFileMapper;

    @Autowired(required = false)
    private NmplFileDeviceRelMapper nmplFileDeviceRelMapper;

    @Autowired(required = false)
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Autowired(required = false)
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Autowired
    private UploadFileService uploadFileService;

    @Override
    public Result<EditVersionResp> editVersion(EditVersionReq req) {
        Result result;
        try {
            EditVersionResp resp = null;
            // check param is legal
            checkEditVersionParam(req);
            switch (req.getEditType()){
                case DataConstants.EDIT_TYPE_ADD:
                    for (NmplVersionVo vo : req.getNmplVersionVos()){
                        NmplVersion version = new NmplVersion();
                        BeanUtils.copyProperties(vo,version);
                        nmplVersionMapper.insertSelective(version);
                    }
                    break;
                case DataConstants.EDIT_TYPE_UPD:
                    for (NmplVersionVo vo : req.getNmplVersionVos()){
                        if (vo.getId() == null){
                            throw new SystemException(ErrorCode.PARAM_IS_NULL, "nmplVersionVo.id"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmplVersion version = new NmplVersion();
                        BeanUtils.copyProperties(vo,version);
                        nmplVersionMapper.updateByPrimaryKeySelective(version);
                    }
                    break;
                case DataConstants.EDIT_TYPE_DEL:
                    for (Long id : req.getDelIds()){
                        nmplVersionMapper.deleteByPrimaryKey(id);
                    }
                    break;
                default:
                    throw new SystemException(ErrorCode.PARAM_EXCEPTION, "editType"+ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG);
            }

            result = buildResult(resp);
        }catch (SystemException e){
            log.error("VersionServiceImpl.editVersion SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("VersionServiceImpl.editVersion Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result<UploadVersionFileResp> uploadVersionFile(UploadVersionFileReq req) {
        Result result;
        try {
            UploadVersionFileResp resp = null;
            // check param is legal
            checkUploadVersionFileParam(req);

            // 上传文件
            UploadSingleFileReq fileReq = new UploadSingleFileReq();

            //获取文件名
            String originalFilename = req.getFile().getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(DataConstants.KEY_POINT));
            String fileName = originalFilename.substring(0,originalFilename.lastIndexOf(DataConstants.KEY_POINT));

            FileIsExistReq existReq = new FileIsExistReq();
            existReq.setModuleName(moduleName);
            existReq.setUploadPath(req.getSystemId()+KEY_SLASH+req.getVersionId()+KEY_SLASH);
            int i = 1;
            do {
                existReq.setFileName(originalFilename);
                Result<FileIsExistResp> isExist = uploadFileService.fileIsExist(existReq);
                if (isExist.isSuccess()){
                    if (isExist.getResultObj().getIsExist()){
                        StringBuffer sb = new StringBuffer(fileName);
                        sb.append(LEFT_BRACKET);
                        sb.append(i++);
                        sb.append(RIGHT_BRACKET);
                        sb.append(suffix);
                        originalFilename = sb.toString();
                    }else {
                        break;
                    }
                }else {
                    throw new SystemException(ErrorMessageContants.GET_FILE_ISEXIST_FAIL_MSG+existReq.toString());
                }
            }while (i<50);


            fileReq.setFile(req.getFile());
            fileReq.setModuleName(moduleName);
            fileReq.setFileName(originalFilename);
            fileReq.setUploadPath(req.getSystemId()+KEY_SLASH+req.getVersionId()+KEY_SLASH);

            Result<UploadSingleFileResp> respResult = uploadFileService.uploadSingleFile(fileReq);

            if (respResult.isSuccess()){
                NmplVersionFile versionFile = new NmplVersionFile();
                versionFile.setVersionId(req.getVersionId());
                versionFile.setFilePath(respResult.getResultObj().getFilePath());
                versionFile.setFileName(respResult.getResultObj().getFileName());
                nmplVersionFileMapper.insertSelective(versionFile);
            }


            result = buildResult(resp);
        }catch (SystemException e){
            log.error("VersionServiceImpl.uploadVersionFile SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("VersionServiceImpl.uploadVersionFile Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result<DeleteVersionFileResp> deleteVersionFile(DeleteVersionFileReq req) {
        Result result;
        try {
            DeleteVersionFileResp resp = null;
            // check param is legal
            checkDeleteVersionFileParam(req);
            NmplVersionFile versionFile = new NmplVersionFile();
            versionFile.setId(req.getVersionFileId());
            versionFile.setIsDelete(IS_NOT_EXIST);
            nmplVersionFileMapper.updateByPrimaryKeySelective(versionFile);


            result = buildResult(resp);
        }catch (SystemException e){
            log.error("VersionServiceImpl.deleteVersionFile SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("VersionServiceImpl.deleteVersionFile Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result<QueryVersionFileResp> queryVersionFile(QueryVersionFileReq req) {
        Result result;

        try {
            QueryVersionFileResp resp = null;
            // check param is legal
            checkQueryVersionFileParam(req);

            NmplVersionExample example = new NmplVersionExample();
            example.createCriteria().andSystemIdEqualTo(req.getSystemId()).andIsDeleteEqualTo(true);
            List<NmplVersion> versions = nmplVersionMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(versions)){
                List<NmplVersionVo> vvos = new ArrayList<>(versions.size());

                for(NmplVersion version : versions){
                    NmplVersionVo vo = new NmplVersionVo();
                    BeanUtils.copyProperties(version,vo);

                    NmplVersionFileExample versionFileExample = new NmplVersionFileExample();
                    versionFileExample.createCriteria().andVersionIdEqualTo(version.getId()).andIsDeleteEqualTo(true);
                    List<NmplVersionFile> versionFiles = nmplVersionFileMapper.selectByExample(versionFileExample);
                    if (!CollectionUtils.isEmpty(versionFiles)){
                        List<NmplVersionFileVo> vfos = new ArrayList<>(versionFiles.size());

                        for (NmplVersionFile versionFile : versionFiles){
                            NmplVersionFileVo versionFileVo = new NmplVersionFileVo();
                            BeanUtils.copyProperties(versionFile,versionFileVo);
                            vfos.add(versionFileVo);
                        }
                        vo.setNmplVersionFileVos(vfos);
                    }
                    vvos.add(vo);
                }
                resp = new QueryVersionFileResp();
                resp.setVersionVos(vvos);
            }


            result = buildResult(resp);
        }catch (SystemException e){
            log.error("VersionServiceImpl.queryVersionFile SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("VersionServiceImpl.queryVersionFile Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result<QueryVersionFileDetailResp> queryVersionFileDetail(QueryVersionFileDetailReq req) {
        Result result;
        try {
            QueryVersionFileDetailResp resp = new QueryVersionFileDetailResp();
            // check param is legal
            checkQueryVersionFileDetailParam(req);
            Long id = req.getVersionFileId();
            NmplVersionFile versionFile = nmplVersionFileMapper.selectByPrimaryKey(id);
            Long versionId = versionFile.getVersionId();
            NmplVersion version = nmplVersionMapper.selectByPrimaryKey(versionId);
            List<StationInfoVo> stationInfoVos = new ArrayList<>();

            // 查询已推送列表
            NmplFileDeviceRelExample relExample = new NmplFileDeviceRelExample();
            relExample.createCriteria().andFileIdEqualTo(versionId).andIsDeleteEqualTo(IS_EXIST);
            List<NmplFileDeviceRel> nmplFileDeviceRels = nmplFileDeviceRelMapper.selectByExample(relExample);
            Map<String,Object> map = new HashMap<>();
            if (!CollectionUtils.isEmpty(nmplFileDeviceRels)){
                for (NmplFileDeviceRel rel : nmplFileDeviceRels){
                    map.put(rel.getDeviceId(),rel);
                }
            }

            // 获取站点信息列表
            switch (version.getSystemId()){
                case com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_0:
                    NmplBaseStationInfoExample example = new NmplBaseStationInfoExample();
                    example.createCriteria().andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(IS_EXIST);
                    List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(nmplBaseStationInfos)){
                        for (NmplBaseStationInfo info : nmplBaseStationInfos) {
                            StationInfoVo vo = new StationInfoVo();
                            vo.setId(info.getId());
                            vo.setDeviceId(info.getStationId());
                            vo.setPublic_network_ip(info.getPublicNetworkIp());
                            if (map.containsKey(info.getStationId())){
                                vo.setIsSelected(true);
                            }
                            stationInfoVos.add(vo);
                        }
                    }
                    break;
                case com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_1:
                case com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_2:
                    NmplDeviceInfoExample example1 = new NmplDeviceInfoExample();
                    example1.createCriteria().andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(IS_EXIST);
                    List<NmplDeviceInfo> nmplDeviceInfos = nmplDeviceInfoMapper.selectByExample(example1);
                    if (!CollectionUtils.isEmpty(nmplDeviceInfos)){
                        for (NmplDeviceInfo info : nmplDeviceInfos) {
                            StationInfoVo vo = new StationInfoVo();
                            vo.setId(info.getId());
                            vo.setDeviceId(info.getDeviceId());
                            vo.setPublic_network_ip(info.getPublicNetworkIp());
                            if (map.containsKey(info.getDeviceId())){
                                vo.setIsSelected(true);
                            }
                            stationInfoVos.add(vo);
                        }
                    }
                    break;
                default:
                    break;

            }
            resp.setStationInfoVos(stationInfoVos);
            resp.setSystemId(version.getSystemId());
            resp.setVersionNo(version.getVersionNo());
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("VersionServiceImpl.queryVersionFileDetail SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("VersionServiceImpl.queryVersionFileDetail Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    @Transactional
    public Result<PushVersionFileResp> pushVersionFile(PushVersionFileReq req) {
        Result result;
        try {
            PushVersionFileResp resp = null;
            // check param is legal
            checkPushVersionFileParam(req);
            for (String deviceId : req.getDeviceIds()){
                NmplFileDeviceRel nmplFileDeviceRel = new NmplFileDeviceRel();
                nmplFileDeviceRel.setFileId(req.getFileId());
                nmplFileDeviceRel.setDeviceId(deviceId);
                nmplFileDeviceRelMapper.insertSelective(nmplFileDeviceRel);

                NmplVersionFile versionFile = new NmplVersionFile();
                versionFile.setId(req.getFileId());
                versionFile.setIsPush(com.matrictime.network.base.constant.DataConstants.VERSION_FILE_IS_PUSHED);
                nmplVersionFileMapper.updateByPrimaryKeySelective(versionFile);
            }

            result = buildResult(resp);
        }catch (SystemException e){
            log.error("VersionServiceImpl.pushVersionFile SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("VersionServiceImpl.pushVersionFile Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result<StartVersionFileResp> startVersionFile(StartVersionFileReq req) {
        Result result;

        try {
            StartVersionFileResp resp = null;
            // check param is legal
            checkStartVersionFileParam(req);
            Long fileId = req.getVersionFileId();
            NmplFileDeviceRelExample rexample = new NmplFileDeviceRelExample();
            rexample.createCriteria().andFileIdEqualTo(fileId).andIsDeleteEqualTo(IS_EXIST);
            List<NmplFileDeviceRel> rels = nmplFileDeviceRelMapper.selectByExample(rexample);

            if (!CollectionUtils.isEmpty(rels)){
                NmplVersionFile versionFile = nmplVersionFileMapper.selectByPrimaryKey(fileId);
                if (versionFile!=null){
                    Long versionId = versionFile.getVersionId();
                    NmplVersion version = nmplVersionMapper.selectByPrimaryKey(versionId);
                    if (version!=null){
                        List<String> successIds = new ArrayList<>();
                        List<String> failIds = new ArrayList<>();
                        switch (version.getSystemId()){
                            case com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_0:
                                for (NmplFileDeviceRel rel : rels){
                                    String deviceId = rel.getDeviceId();
                                    NmplBaseStationInfoExample bexample = new NmplBaseStationInfoExample();
                                    bexample.createCriteria().andStationIdEqualTo(deviceId).andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(IS_EXIST);
                                    List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(bexample);
                                    if (!CollectionUtils.isEmpty(stationInfos)){
                                        for (NmplBaseStationInfo info : stationInfos){
                                            StringBuffer sb = new StringBuffer();
                                            sb.append(info.getLanIp());
                                            sb.append(DataConstants.KEY_SPLIT);
                                            sb.append(info.getLanPort());
                                            try {
//                                                String httpResp = HttpClientUtil.postForm(sb.toString(), versionFile.getFilePath() + versionFile.getFileName());
                                                String httpResp = "{\"isSuccess\":true}";
                                                JSONObject jsonObject = JSONObject.parseObject(httpResp);
                                                Object success = jsonObject.get("isSuccess");
                                                if (success != null && success instanceof Boolean){
                                                    if ((Boolean)success){
                                                        successIds.add(info.getStationId());
                                                    }else {
                                                        failIds.add(info.getStationId());
                                                    }
                                                }
                                                log.info("VersionServiceImpl.startVersionFile http httpResp:{},deviceId:{}",httpResp,info.getStationId());
                                            }catch (Exception e){
                                                failIds.add(info.getStationId());
                                                log.warn("VersionServiceImpl.startVersionFile http Exception:{},deviceId:{}",e.getMessage(),info.getStationId());
                                            }
                                        }
                                    }
                                }

                                NmplVersionFile versionFile1 = new NmplVersionFile();
                                versionFile1.setId(fileId);
                                versionFile1.setIsStarted(com.matrictime.network.base.constant.DataConstants.VERSION_FILE_IS_STARTED);
                                nmplVersionFileMapper.updateByPrimaryKeySelective(versionFile1);
                                break;
                            case com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_1:
                            case com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_2:
                                for (NmplFileDeviceRel rel : rels){
                                    String deviceId = rel.getDeviceId();
                                    NmplDeviceInfoExample dexample = new NmplDeviceInfoExample();
                                    dexample.createCriteria().andDeviceIdEqualTo(deviceId).andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(IS_EXIST);
                                    List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(dexample);
                                    if (!CollectionUtils.isEmpty(deviceInfos)){
                                        for (NmplDeviceInfo info : deviceInfos){
                                            StringBuffer sb = new StringBuffer();
                                            sb.append(info.getLanIp());
                                            sb.append(DataConstants.KEY_SPLIT);
                                            sb.append(info.getLanPort());
                                            try {
//                                                String httpResp = HttpClientUtil.postForm(sb.toString(), versionFile.getFilePath() + versionFile.getFileName());
                                                String httpResp = "{\"isSuccess\":true}";
                                                JSONObject jsonObject = JSONObject.parseObject(httpResp);
                                                if (jsonObject != null){
                                                    Object success = jsonObject.get("isSuccess");
                                                    if (success != null && success instanceof Boolean){
                                                        if ((Boolean)success){
                                                            successIds.add(info.getDeviceId());
                                                        }else {
                                                            failIds.add(info.getDeviceId());
                                                        }
                                                    }
                                                }
                                                log.info("VersionServiceImpl.startVersionFile http httpResp:{},deviceId:{}",httpResp,info.getDeviceId());
                                            }catch (Exception e){
                                                failIds.add(info.getDeviceId());
                                                log.warn("VersionServiceImpl.startVersionFile http Exception:{},deviceId:{}",e.getMessage(),info.getDeviceId());
                                            }
                                        }
                                    }
                                }
                                NmplVersionFile versionFile2 = new NmplVersionFile();
                                versionFile2.setId(fileId);
                                versionFile2.setIsStarted(com.matrictime.network.base.constant.DataConstants.VERSION_FILE_IS_STARTED);
                                nmplVersionFileMapper.updateByPrimaryKeySelective(versionFile2);
                                break;
                            default:
                                break;
                        }
                        resp = new StartVersionFileResp();
                        resp.setFailIds(failIds);
                        resp.setSuccessIds(successIds);
                    }
                }
            }
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("VersionServiceImpl.startVersionFile SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("VersionServiceImpl.startVersionFile Exception:{}",e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    private void checkEditVersionParam(EditVersionReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "editType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (DataConstants.EDIT_TYPE_ADD.equals(req.getEditType()) || DataConstants.EDIT_TYPE_UPD.equals(req.getEditType())){
            if (CollectionUtils.isEmpty(req.getNmplVersionVos())){
                throw new SystemException(ErrorCode.PARAM_IS_NULL, "nmplVersionVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
        if (DataConstants.EDIT_TYPE_DEL.equals(req.getEditType()) && CollectionUtils.isEmpty(req.getDelIds())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "delIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkUploadVersionFileParam(UploadVersionFileReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getSystemId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "systemId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getFile() == null || req.getFile().isEmpty()){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "file"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (req.getVersionId() == null){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "versionId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkDeleteVersionFileParam(DeleteVersionFileReq req){
        if (req.getVersionFileId() == null){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "versionFileId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkQueryVersionFileParam(QueryVersionFileReq req){
        if (ParamCheckUtil.checkVoStrBlank(req.getSystemId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "systemId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkQueryVersionFileDetailParam(QueryVersionFileDetailReq req){
        if (req.getVersionFileId() == null){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "versionFileId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkStartVersionFileParam(StartVersionFileReq req){
        if (req.getVersionFileId() == null){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "versionFileId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }

    private void checkPushVersionFileParam(PushVersionFileReq req){
        if (req.getFileId() == null){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "fileId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
//        if (ParamCheckUtil.checkVoStrBlank(req.getSystemId())){
//            throw new SystemException(ErrorCode.PARAM_IS_NULL, "systemId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
//        }
        if (CollectionUtils.isEmpty(req.getDeviceIds())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }
}
