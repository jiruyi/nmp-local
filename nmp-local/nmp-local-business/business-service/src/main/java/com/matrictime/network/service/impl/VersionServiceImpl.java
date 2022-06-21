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
import com.matrictime.network.util.DateUtils;
import com.matrictime.network.util.FileUtils;
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
import java.util.concurrent.Future;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.base.constant.DataConstants.KEY_FAIL_IDS;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.exception.ErrorMessageContants.FILE_IS_TOO_BIG_MSG;

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

    @Autowired(required = false)
    private NmplConfigurationMapper nmplConfigurationMapper;

    @Autowired
    private UploadFileService uploadFileService;

    @Autowired
    private AsyncService asyncService;

    @Value("${upload.versionFile.maxSize}")
    private Integer fileMaxSize;

    @Value("${upload.versionFile.maxSize.type}")
    private String fileMaxSizeType;

    @Value("${thread.maxPoolSize}")
    private Integer maxPoolSize;

    // TODO: 2022/4/1 上线前需要确定等待时间
    @Value("${version.start.file.time.out}")
    private int versionStartFileTimeOut;

    @Value("${device.path.startfile}")
    private String startPath;

    @Value("${device.path.pushfile}")
    private String pushPath;

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
            QueryVersionFileResp resp = new QueryVersionFileResp();;
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
                    }else {
                        vo.setNmplVersionFileVos(new ArrayList<>(1));
                    }
                    vvos.add(vo);
                }
                resp.setVersionVos(vvos);
            }else {
                resp.setVersionVos(new ArrayList<>(1));
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
    public Result<QueryVersionResp> queryVersion() {
        Result result;

        try {
            QueryVersionResp resp = new QueryVersionResp();;
            // check param is legal
//            checkQueryVersionParam(req);

            NmplVersionExample example = new NmplVersionExample();
            example.createCriteria().andIsDeleteEqualTo(true);
            List<NmplVersion> versions = nmplVersionMapper.selectByExample(example);
            Map<String,List<NmplVersionVo>> versionMap = new HashMap<>(4);
            versionMap.put(com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_0,new ArrayList<>(1));
            versionMap.put(com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_1,new ArrayList<>(1));
            versionMap.put(com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_2,new ArrayList<>(1));
            versionMap.put(com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_3,new ArrayList<>(1));

            if (!CollectionUtils.isEmpty(versions)){
                for(NmplVersion version : versions){
                    NmplVersionVo vo = new NmplVersionVo();
                    BeanUtils.copyProperties(version,vo);
                    String systemId = vo.getSystemId();
                    if (versionMap.containsKey(systemId)){
                        List<NmplVersionVo> tempList = versionMap.get(systemId);
                        tempList.add(vo);
                        versionMap.put(systemId,tempList);
                    }
                }
            }
            resp.setVersionMap(versionMap);
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("VersionServiceImpl.queryVersion SystemException:{}",e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("VersionServiceImpl.queryVersion Exception:{}",e.getMessage());
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
                case com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_3:
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
            PushVersionFileResp resp = new PushVersionFileResp();
            // check param is legal
            checkPushVersionFileParam(req);

            List<String> successIds = new ArrayList<>();
            List<String> failIds = new ArrayList<>();
            List<Map<String,String>> httpList = new ArrayList<>();

            NmplVersionFile file = nmplVersionFileMapper.selectByPrimaryKey(req.getFileId());
            if (file == null){
                throw new SystemException(req.getFileId() + DataConstants.KEY_SPLIT + ErrorMessageContants.FILE_NOT_EXIST);
            }

            for (String deviceId : req.getDeviceIds()){
                Map<String,String> map = new HashMap<>();
                map.put(KEY_DEVICE_ID,deviceId);
                map.put(KEY_FILE_ID,String.valueOf(file.getId()));
                map.put(KEY_FILE_PATH,file.getFilePath());
                map.put(KEY_FILE_NAME,file.getFileName());

                NmplConfigurationExample configurationExample = new NmplConfigurationExample();
                switch (req.getSystemId()){
                    case com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_0:
                        NmplBaseStationInfoExample bexample = new NmplBaseStationInfoExample();
                        bexample.createCriteria().andStationIdEqualTo(deviceId).andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(IS_EXIST);
                        List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(bexample);
                        if (!CollectionUtils.isEmpty(stationInfos)){
                            NmplBaseStationInfo info = stationInfos.get(0);
                            configurationExample.createCriteria().andTypeEqualTo(CONFIGURATION_DEVICE_TYPE_1).andDeviceTypeEqualTo(CONFIGURATION_TYPE_PUSHFILE);
                            List<NmplConfiguration> configurations = nmplConfigurationMapper.selectByExample(configurationExample);
                            if (!CollectionUtils.isEmpty(configurations)){
                                map.put(KEY_URL,HttpClientUtil.getUrl(info.getLanIp(),configurations.get(0).getRealPort(),configurations.get(0).getUrl()));
                            }

                            httpList.add(map);
                        }
                        break;
                    case com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_1:
                    case com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_2:
                    case com.matrictime.network.base.constant.DataConstants.SYSTEM_ID_3:
                        NmplDeviceInfoExample dexample = new NmplDeviceInfoExample();
                        dexample.createCriteria().andDeviceIdEqualTo(deviceId).andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(IS_EXIST);
                        List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(dexample);
                        if (!CollectionUtils.isEmpty(deviceInfos)){
                            NmplDeviceInfo info = deviceInfos.get(0);
                            switch (req.getSystemId()){
                                case SYSTEM_ID_1:
                                    configurationExample.createCriteria().andTypeEqualTo(CONFIGURATION_DEVICE_TYPE_2).andDeviceTypeEqualTo(CONFIGURATION_TYPE_PUSHFILE);
                                    break;
                                case SYSTEM_ID_2:
                                    configurationExample.createCriteria().andTypeEqualTo(CONFIGURATION_DEVICE_TYPE_3).andDeviceTypeEqualTo(CONFIGURATION_TYPE_PUSHFILE);
                                    break;
                                case SYSTEM_ID_3:
                                    configurationExample.createCriteria().andTypeEqualTo(CONFIGURATION_DEVICE_TYPE_4).andDeviceTypeEqualTo(CONFIGURATION_TYPE_PUSHFILE);
                                    break;
                            }
                            List<NmplConfiguration> configurations = nmplConfigurationMapper.selectByExample(configurationExample);
                            if (!CollectionUtils.isEmpty(configurations)){
                                map.put(KEY_URL,HttpClientUtil.getUrl(info.getLanIp(),configurations.get(0).getRealPort(),configurations.get(0).getUrl()));
                            }
                            httpList.add(map);
                        }
                        break;
                    default:
                        break;
                }
            }

            if (!CollectionUtils.isEmpty(httpList)){
                List<Future> futures = new ArrayList<>();
                if (httpList.size()>maxPoolSize){
                    List<List<Map<String, String>>> splitList = splitList(httpList);
                    for (List<Map<String, String>> tmpList : splitList){
                        Future<Map<String, List<String>>> mapFuture = asyncService.httpPushFile(tmpList);
                        futures.add(mapFuture);
                    }
                }else {
                    Future<Map<String, List<String>>> mapFuture = asyncService.httpPushFile(httpList);
                    futures.add(mapFuture);
                }

                Date timeout = DateUtils.addMinuteForDate(new Date(), versionStartFileTimeOut);
                while (true) {
                    if (!CollectionUtils.isEmpty(futures)) {
                        boolean isAllDone = true;
                        for (Future future : futures) {
                            if (null == future || !future.isDone()) {
                                isAllDone = false;
                            }else {
                                try {
                                    Map<String, List<String>> msg =  (Map<String, List<String>>) future.get();
                                    if (!CollectionUtils.isEmpty(msg)) {
                                        if (!CollectionUtils.isEmpty(msg.get(KEY_SUCCESS_IDS))){
                                            successIds.addAll(msg.get(KEY_SUCCESS_IDS));
                                        }
                                        if (!CollectionUtils.isEmpty(msg.get(KEY_FAIL_IDS))){
                                            failIds.addAll(msg.get(KEY_FAIL_IDS));
                                        }
                                    }
                                } catch (Exception e) {
                                    log.info("版本文件启动线程池处理单个批次配置出错！error:{}",e.getMessage());
                                }
                            }
                        }
                        if (isAllDone || new Date().after(timeout)) {
                            break;
                        }
                    }else {
                        break;
                    }
                }
                if (successIds.size()>0){
                    NmplVersionFile versionFile = new NmplVersionFile();
                    versionFile.setId(req.getFileId());
                    versionFile.setIsPush(com.matrictime.network.base.constant.DataConstants.VERSION_FILE_IS_PUSHED);
                    nmplVersionFileMapper.updateByPrimaryKeySelective(versionFile);
                }
            }

            resp.setFailIds(failIds);
            resp.setSuccessIds(successIds);
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
                        List<Map<String,String>> httpList = new ArrayList<>();
                        NmplConfigurationExample configurationExample = new NmplConfigurationExample();
                        switch (version.getSystemId()){
                            case SYSTEM_ID_0:
                                configurationExample.createCriteria().andTypeEqualTo(CONFIGURATION_DEVICE_TYPE_1).andDeviceTypeEqualTo(CONFIGURATION_TYPE_STARTFILE);
                                List<NmplConfiguration> stationiConfigurations = nmplConfigurationMapper.selectByExample(configurationExample);
                                for (NmplFileDeviceRel rel : rels){
                                    String deviceId = rel.getDeviceId();
                                    NmplBaseStationInfoExample bexample = new NmplBaseStationInfoExample();
                                    bexample.createCriteria().andStationIdEqualTo(deviceId).andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(IS_EXIST);
                                    List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(bexample);
                                    Map<String,String> map = new HashMap<>();
                                    map.put(KEY_DEVICE_ID,deviceId);
                                    map.put(KEY_FILE_ID,String.valueOf(fileId));
                                    if (!CollectionUtils.isEmpty(stationInfos)){
                                        NmplBaseStationInfo info = stationInfos.get(0);
                                        if (!CollectionUtils.isEmpty(stationiConfigurations)) {
                                            map.put(KEY_URL,HttpClientUtil.getUrl(info.getLanIp(),stationiConfigurations.get(0).getRealPort(),stationiConfigurations.get(0).getUrl()));
                                        }
                                    }
                                    httpList.add(map);
                                }
                                break;
                            case SYSTEM_ID_1:
                            case SYSTEM_ID_2:
                            case SYSTEM_ID_3:
                                switch (version.getSystemId()) {
                                    case SYSTEM_ID_1:
                                        configurationExample.createCriteria().andTypeEqualTo(CONFIGURATION_DEVICE_TYPE_2).andDeviceTypeEqualTo(CONFIGURATION_TYPE_STARTFILE);
                                        break;
                                    case SYSTEM_ID_2:
                                        configurationExample.createCriteria().andTypeEqualTo(CONFIGURATION_DEVICE_TYPE_3).andDeviceTypeEqualTo(CONFIGURATION_TYPE_STARTFILE);
                                        break;
                                    case SYSTEM_ID_3:
                                        configurationExample.createCriteria().andTypeEqualTo(CONFIGURATION_DEVICE_TYPE_4).andDeviceTypeEqualTo(CONFIGURATION_TYPE_STARTFILE);
                                        break;
                                }
                                List<NmplConfiguration> deviceConfigurations = nmplConfigurationMapper.selectByExample(configurationExample);
                                for (NmplFileDeviceRel rel : rels){
                                    String deviceId = rel.getDeviceId();
                                    NmplDeviceInfoExample dexample = new NmplDeviceInfoExample();
                                    dexample.createCriteria().andDeviceIdEqualTo(deviceId).andStationStatusEqualTo(com.matrictime.network.base.constant.DataConstants.STATION_STATUS_ACTIVE).andIsExistEqualTo(IS_EXIST);
                                    List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(dexample);
                                    Map<String,String> map = new HashMap<>();
                                    map.put(KEY_DEVICE_ID,deviceId);
                                    map.put(KEY_FILE_ID,String.valueOf(fileId));
                                    if (!CollectionUtils.isEmpty(deviceInfos)){
                                        NmplDeviceInfo info = deviceInfos.get(0);
                                        if (!CollectionUtils.isEmpty(deviceConfigurations)){
                                            map.put(KEY_URL,HttpClientUtil.getUrl(info.getLanIp(),deviceConfigurations.get(0).getRealPort(),deviceConfigurations.get(0).getUrl()));
                                        }
                                    }
                                    httpList.add(map);
                                }
                                break;
                            default:
                                break;
                        }

                        if (!CollectionUtils.isEmpty(httpList)){
                            List<Future> futures = new ArrayList<>();
                            if (httpList.size()>maxPoolSize){
                                List<List<Map<String, String>>> splitList = splitList(httpList);
                                for (List<Map<String, String>> tmpList : splitList){
                                    Future<Map<String, List<String>>> mapFuture = asyncService.httpStartFile(tmpList);
                                    futures.add(mapFuture);
                                }
                            }else {
                                Future<Map<String, List<String>>> mapFuture = asyncService.httpStartFile(httpList);
                                futures.add(mapFuture);
                            }

                            Date timeout = DateUtils.addMinuteForDate(new Date(), versionStartFileTimeOut);
                            while (true) {
                                if (!CollectionUtils.isEmpty(futures)) {
                                    boolean isAllDone = true;
                                    for (Future future : futures) {
                                        if (null == future || !future.isDone()) {
                                            isAllDone = false;
                                        }else {
                                            try {
                                                Map<String, List<String>> msg =  (Map<String, List<String>>) future.get();
                                                if (!CollectionUtils.isEmpty(msg)) {
                                                    if (!CollectionUtils.isEmpty(msg.get(KEY_SUCCESS_IDS))){
                                                        successIds.addAll(msg.get(KEY_SUCCESS_IDS));
                                                    }
                                                    if (!CollectionUtils.isEmpty(msg.get(KEY_FAIL_IDS))){
                                                        failIds.addAll(msg.get(KEY_FAIL_IDS));
                                                    }
                                                }
                                            } catch (Exception e) {
                                                log.info("版本文件启动线程池处理单个批次配置出错！error:{}",e.getMessage());
                                            }
                                        }
                                    }
                                    if (isAllDone || new Date().after(timeout)) {
                                        break;
                                    }
                                }else {
                                    break;
                                }
                            }
                            if (successIds.size()>0){
                                NmplVersionFile updateFile = new NmplVersionFile();
                                updateFile.setId(fileId);
                                updateFile.setIsStarted(com.matrictime.network.base.constant.DataConstants.VERSION_FILE_IS_STARTED);
                                nmplVersionFileMapper.updateByPrimaryKeySelective(updateFile);
                            }
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

    private List<List<Map<String,String>>> splitList(List<Map<String,String>> list){
        int length = list.size();
        /**
         * num 可以分成的组数
         **/
        int num = ( length + maxPoolSize - 1 )/maxPoolSize ;
        //用于存放最后结果
        List<List<Map<String,String>>> resultList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            // 开始位置
            int fromIndex = i * maxPoolSize;
            // 结束位置
            int toIndex = (i+1) * maxPoolSize < length ? ( i+1 ) * maxPoolSize : length ;
            resultList.add(list.subList(fromIndex,toIndex)) ;
        }
        return resultList;
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
        if (!FileUtils.checkFileSize(req.getFile().getSize(),fileMaxSize,fileMaxSizeType)){
            throw new SystemException(FILE_IS_TOO_BIG_MSG+fileMaxSize+fileMaxSizeType);
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

    private void checkQueryVersionParam(QueryVersionReq req){
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
        if (ParamCheckUtil.checkVoStrBlank(req.getSystemId())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "systemId"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (CollectionUtils.isEmpty(req.getDeviceIds())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "deviceIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }
}
