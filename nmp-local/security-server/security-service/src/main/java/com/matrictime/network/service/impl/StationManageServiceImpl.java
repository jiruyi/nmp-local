package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.dao.mapper.NmpsStationManageMapper;
import com.matrictime.network.dao.mapper.extend.NmpsStationManageExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CaManageVo;
import com.matrictime.network.modelVo.DnsManageVo;
import com.matrictime.network.modelVo.PageInfo;
import com.matrictime.network.modelVo.StationManageVo;
import com.matrictime.network.req.StationManageRequest;
import com.matrictime.network.resp.DnsManageResp;
import com.matrictime.network.resp.StationManageResp;
import com.matrictime.network.service.StationManageService;
import com.matrictime.network.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.base.constant.DataConstants.STATION_MANAGE_DELETE_URL;

/**
 * @author by wangqiang
 * @date 2023/11/3.
 */
@Slf4j
@Service
public class StationManageServiceImpl implements StationManageService {

    @Resource
    private NmpsStationManageMapper stationManageMapper;

    @Resource
    private NmpsStationManageExtMapper stationManageExtMapper;

    @Value("${security-proxy.context-path}")
    private String securityProxyPath;

    @Value("${security-proxy.port}")
    private String securityProxyPort;

    @Override
    public Result<Integer> insertStationManage(StationManageRequest stationManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            //唯一性校验
            NmpsStationManageExample stationManageExample = new NmpsStationManageExample();
            NmpsStationManageExample.Criteria criteria = stationManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(stationManageRequest.getNetworkId());
            List<NmpsStationManage> nmpsStationManages = stationManageMapper.selectByExample(stationManageExample);
            if(!CollectionUtils.isEmpty(nmpsStationManages)){
                return new Result<>(false,"入网id不唯一");
            }
            NmpsStationManage nmpsStationManage = new NmpsStationManage();
            BeanUtils.copyProperties(stationManageRequest,nmpsStationManage);
            int i = stationManageMapper.insertSelective(nmpsStationManage);
            //代理推送
            if(i == 1){
                syncProxy(stationManageRequest,"insert");
            }
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("insertStationManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> deleteStationManage(StationManageRequest stationManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            NmpsStationManage nmpsStationManage = new NmpsStationManage();
            BeanUtils.copyProperties(stationManageRequest,nmpsStationManage);
            //构建条件
            NmpsStationManageExample stationManageExample = new NmpsStationManageExample();
            NmpsStationManageExample.Criteria criteria = stationManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(stationManageRequest.getNetworkId());
            nmpsStationManage.setIsExist(false);
            int i = stationManageMapper.updateByExampleSelective(nmpsStationManage, stationManageExample);
            //代理推送
            if(i == 1){
                syncProxy(stationManageRequest,"delete");
            }
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("deleteStationManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<PageInfo<StationManageVo>> selectStationManage(StationManageRequest stationManageRequest) {
        Result<PageInfo<StationManageVo>> result = new Result<>();
        PageInfo<StationManageVo> pageInfo = new PageInfo<>();
        try {
            //分页查询数据
            Page page = PageHelper.startPage(stationManageRequest.getPageNo(),stationManageRequest.getPageSize());
            List<StationManageVo> stationManageVos = stationManageExtMapper.selectStationManage(stationManageRequest);
            pageInfo.setList(stationManageVos);
            pageInfo.setCount((int) page.getTotal());
            pageInfo.setPages(page.getPages());
            result.setSuccess(true);
            result.setResultObj(pageInfo);
        }catch (Exception e){
            result.setErrorMsg(e.getMessage());
            result.setSuccess(false);
            log.error("selectStationManage: {}",e.getMessage());
        }
        return result;
    }

    @Override
    public Result<Integer> updateStationManage(StationManageRequest stationManageRequest) {
        Result<Integer> result = new Result<>();
        try {
            //唯一性校验
            NmpsStationManageExample stationManageExample = new NmpsStationManageExample();
            NmpsStationManageExample.Criteria criteria = stationManageExample.createCriteria();
            criteria.andNetworkIdEqualTo(stationManageRequest.getNetworkId());
            criteria.andIdNotEqualTo(stationManageRequest.getId());
            List<NmpsStationManage> nmpsStationManages = stationManageMapper.selectByExample(stationManageExample);
            if(!CollectionUtils.isEmpty(nmpsStationManages)){
                return new Result<>(false,"入网id不唯一");
            }
            //构建更新条件
            NmpsStationManageExample manageExample = new NmpsStationManageExample();
            NmpsStationManageExample.Criteria manageExampleCriteria = manageExample.createCriteria();
            manageExampleCriteria.andNetworkIdEqualTo(stationManageRequest.getNetworkId());
            NmpsStationManage nmpsStationManage = new NmpsStationManage();
            BeanUtils.copyProperties(stationManageRequest,nmpsStationManage);
            int i = stationManageMapper.updateByExampleSelective(nmpsStationManage, manageExample);
            //代理推送
            if(i == 1){
                syncProxy(stationManageRequest,"insert");
            }
            result.setSuccess(true);
            result.setResultObj(i);
        }catch (Exception e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            log.error("updateStationManage: {}",e.getMessage());
        }
        return result;
    }

    /**
     * 代理端推送
     * @param stationManageRequest
     * @param flag
     */
    private void syncProxy(StationManageRequest stationManageRequest, String flag){
        try {
            String urlString = "";
            StationManageVo vo = new StationManageVo();
            BeanUtils.copyProperties(stationManageRequest,vo);
            JSONObject jsonParam = new JSONObject();
            jsonParam.put(JSON_KEY_EDITTYPE,flag);
            jsonParam.put("StationManageVo",vo);
            if(flag.equals("insert")){
                urlString = STATION_MANAGE_INSERT_URL;
            }else {
                urlString = STATION_MANAGE_DELETE_URL;
            }
            String url = HttpClientUtil.getUrl(vo.getComIp(), securityProxyPort, securityProxyPath + urlString);
            HttpClientUtil.post(url,jsonParam.toJSONString());
        }catch (Exception e){
            log.warn("StationManageServiceImpl.syncProxy Exception:{}",e);
        }
    }
}
