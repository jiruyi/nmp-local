package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.domain.OutlinePcDomainService;
import com.matrictime.network.dao.mapper.NmplBaseStationInfoMapper;
import com.matrictime.network.dao.mapper.NmplOutlinePcInfoMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.NmplOutlinePcInfoVo;
import com.matrictime.network.request.OutlinePcReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.OutlinePcService;
import com.matrictime.network.util.CommonCheckUtil;
import com.matrictime.network.util.CsvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class OutlinePcServiceImpl extends SystemBaseService implements OutlinePcService {
    @Resource
    OutlinePcDomainService outlinePcDomainService;

    @Resource
    NmplOutlinePcInfoMapper nmplOutlinePcInfoMapper;

    @Resource
    private AsyncService asyncService;

    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Value("${proxy.port}")
    private String port;

    @Value("${proxy.context-path}")
    private String contextPath;

    @Override
    public Result save(OutlinePcReq outlinePcReq) {
        Result<Integer> result;
        try {
            if(!parmLenthCheck(outlinePcReq)){
                throw new SystemException(ErrorMessageContants.PARAM_LENTH_ERROR_MSG);
            }
            NmplUser nmplUser = RequestContext.getUser();
            outlinePcReq.setCreateUser(nmplUser.getNickName());
            Integer num = outlinePcDomainService.save(outlinePcReq);
            if(num.equals(DataConstants.INSERT_OR_UPDATE_SUCCESS)){
                result =  buildResult(num);
                NmplOutlinePcInfoExample nmplOutlinePcInfoExample = new NmplOutlinePcInfoExample();
                nmplOutlinePcInfoExample.createCriteria().andStationNetworkIdEqualTo(outlinePcReq.getStationNetworkId());
                List<NmplOutlinePcInfo> nmplOutlinePcInfoList = nmplOutlinePcInfoMapper.selectByExample(nmplOutlinePcInfoExample);
                if(!CollectionUtils.isEmpty(nmplOutlinePcInfoList)){
                    String data = JSONObject.toJSONString(nmplOutlinePcInfoList.get(0));
                    pushToProxy(DataConstants.URL_OUTLINEPC_INSERTORUPDATE,data);
                }
            }else {
                result = failResult("");
            }
        }catch (SystemException e){
            log.info("创建异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("创建异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result modify(OutlinePcReq outlinePcReq) {
        Result<Integer> result;
        try {
            if(!parmLenthCheck(outlinePcReq)){
                throw new SystemException(ErrorMessageContants.PARAM_LENTH_ERROR_MSG);
            }
            NmplUser nmplUser = RequestContext.getUser();
            outlinePcReq.setUpdateUser(nmplUser.getNickName());
            Integer num = outlinePcDomainService.modify(outlinePcReq);
            if(num.equals(DataConstants.INSERT_OR_UPDATE_SUCCESS)){
                result =  buildResult(num);
                NmplOutlinePcInfoExample nmplOutlinePcInfoExample = new NmplOutlinePcInfoExample();
                nmplOutlinePcInfoExample.createCriteria().andStationNetworkIdEqualTo(outlinePcReq.getStationNetworkId());
                List<NmplOutlinePcInfo> nmplOutlinePcInfoList = nmplOutlinePcInfoMapper.selectByExample(nmplOutlinePcInfoExample);
                if(!CollectionUtils.isEmpty(nmplOutlinePcInfoList)){
                    String data = JSONObject.toJSONString(nmplOutlinePcInfoList.get(0));
                    pushToProxy(DataConstants.URL_OUTLINEPC_INSERTORUPDATE,data);
                }
            }else {
                result = failResult("");
            }

        }catch (SystemException e){
            log.info("修改异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("修改异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result delete(OutlinePcReq outlinePcReq) {
        Result<Integer> result;
        try {
            NmplUser nmplUser = RequestContext.getUser();
            outlinePcReq.setUpdateUser(nmplUser.getNickName());
            Integer num = outlinePcDomainService.delete(outlinePcReq);
            if(num.equals(DataConstants.INSERT_OR_UPDATE_SUCCESS)){
                result =  buildResult(num);
                NmplOutlinePcInfoExample nmplOutlinePcInfoExample = new NmplOutlinePcInfoExample();
                nmplOutlinePcInfoExample.createCriteria().andStationNetworkIdEqualTo(outlinePcReq.getStationNetworkId());
                List<NmplOutlinePcInfo> nmplOutlinePcInfoList = nmplOutlinePcInfoMapper.selectByExample(nmplOutlinePcInfoExample);
                if(!CollectionUtils.isEmpty(nmplOutlinePcInfoList)){
                    String data = JSONObject.toJSONString(nmplOutlinePcInfoList.get(0));
                    pushToProxy(DataConstants.URL_OUTLINEPC_INSERTORUPDATE,data);
                }
            }else {
                result = failResult("");
            }

        }catch (SystemException e){
            log.info("删除异常",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.info("删除异常",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result<PageInfo> queryByConditon(OutlinePcReq outlinePcReq) {
        Result<PageInfo> result = null;
        try {
            //多条件查询
            PageInfo<NmplOutlinePcInfoVo> pageResult =  new PageInfo<>();
            pageResult = outlinePcDomainService.query(outlinePcReq);
            result = buildResult(pageResult);
        }catch (SystemException e){
            log.error("查询异常：",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("查询异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    public Result upload(MultipartFile file) {
        Result result;
        try {
            File tmp = new File(System.getProperty("user.dir")+File.separator+file.getName()+".csv");
            log.info(System.getProperty("user.dir")+File.separator+file.getName());
            file.transferTo(tmp);
            List<NmplOutlinePcInfo> nmplOutlinePcInfoList = CsvUtils.readCsvToPc(tmp);
            tmp.delete();
            Integer num = outlinePcDomainService.batchInsert(nmplOutlinePcInfoList);
            if(num>DataConstants.INSERT_OR_UPDATE_SUCCESS){
                result =  buildResult(num);
                if(!CollectionUtils.isEmpty(nmplOutlinePcInfoList)){
                    Map<String,List<NmplOutlinePcInfo>> map = new HashMap<>();
                    map.put("list",nmplOutlinePcInfoList);
                    String data = JSONObject.toJSONString(map);
                    pushToProxy(DataConstants.URL_OUTLINEPC_BATCHINSERT,data);
                }
            }else {
                result = buildResult(null);
            }
        }catch (SystemException e){
            log.error("查询异常：",e.getMessage());
            result = failResult(e);
        } catch (Exception e) {
            log.error("查询异常：",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    private boolean parmLenthCheck(OutlinePcReq outlinePcReq){
        boolean flag = true;
        if(outlinePcReq.getStationNetworkId()!=null){
            flag = CommonCheckUtil.checkStringLength(outlinePcReq.getStationNetworkId(), null, 32);
        }
        if(outlinePcReq.getDeviceName()!=null){
            flag = CommonCheckUtil.checkStringLength(outlinePcReq.getDeviceName(), null, 16);
        }
        if(outlinePcReq.getRemark()!=null){
            flag = CommonCheckUtil.checkStringLength(outlinePcReq.getRemark(), null, 256);
        }
        return flag;
    }

    /**
     * 将一体机信息推送到代理
     * @param suffix
     * @param data
     * @throws Exception
     */
    public void pushToProxy(String suffix,String data)throws Exception {
        Map<String, String> deviceMap = getAllUrl();
        Set<String> set = deviceMap.keySet();
        for (String lanIp : set) {
            Map<String, String> map = new HashMap<>();
            map.put(DataConstants.KEY_DEVICE_ID, deviceMap.get(lanIp));
            map.put(DataConstants.KEY_DATA, data);
            String url = "http://" + lanIp + ":" + port + contextPath + suffix;
            map.put(DataConstants.KEY_URL, url);
            asyncService.httpPush(map);
        }
    }

    /**
     * 目前逻辑一体机推送到所有基站
     * @return
     */
    private Map<String,String> getAllUrl(){
        Map<String,String> map = new HashMap<>();
        List<NmplBaseStationInfo> nmplBaseStationInfos = nmplBaseStationInfoMapper.selectByExample(null);
        for (NmplBaseStationInfo nmplBaseStationInfo : nmplBaseStationInfos) {
            map.put(nmplBaseStationInfo.getLanIp(),nmplBaseStationInfo.getStationId());
        }
        return map;
    }

}
