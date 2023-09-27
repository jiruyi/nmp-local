package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.*;
import com.matrictime.network.dao.mapper.extend.NmplLinkExtMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.enums.LinkEnum;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.PortModel;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.LinkDevice;
import com.matrictime.network.modelVo.LocalLinkDisplayVo;
import com.matrictime.network.modelVo.LocalLinkVo;
import com.matrictime.network.request.EditLinkReq;
import com.matrictime.network.request.QueryLinkReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.LinkService;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.util.*;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.KEY_SPLIT;
import static com.matrictime.network.exception.ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG;

@Slf4j
@Service
public class LinkServiceImpl extends SystemBaseService implements LinkService {

    @Resource
    private NmplLinkMapper nmplLinkMapper;

    @Resource
    private NmplBaseStationInfoMapper nmplBaseStationInfoMapper;

    @Resource
    private NmplLinkExtMapper nmplLinkExtMapper;

    @Resource
    private NmplDeviceInfoMapper nmplDeviceInfoMapper;

    @Value("${proxy.context-path}")
    private String proxyPath;

    @Value("${proxy.port}")
    private String proxyPort;

    @Autowired
    private AsyncService asyncService;

    @Override
    public Result<PageInfo<LocalLinkDisplayVo>> queryLink(QueryLinkReq req) {
        Result result;
        try {
            Page page = PageHelper.startPage(req.getPageNo(),req.getPageSize());

            // 根据条件查询链路列表
            List<LocalLinkDisplayVo> displayVos = nmplLinkExtMapper.queryLink(req);
            PageInfo<LocalLinkDisplayVo> pageResult =  new PageInfo<>();

            pageResult.setList(displayVos);
            pageResult.setCount((int) page.getTotal());
            pageResult.setPages(page.getPages());
            result = buildResult(pageResult);
        }catch (Exception e){
            log.error("LinkServiceImpl.queryLink Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    @Transactional
    public Result editLink(EditLinkReq req) {
        Result result;
        try {
            // check param is legal
            checkEditLinkParam(req);
            switch (req.getEditType()){
                // 批量插入
                case DataConstants.EDIT_TYPE_ADD:
                    for (LocalLinkVo vo : req.getLocalLinkVos()){
                        LocalLinkVo tempVo = makeupDefaultValue(vo);
                        NmplLink link = new NmplLink();
                        BeanUtils.copyProperties(tempVo,link);
                        nmplLinkExtMapper.insertSelectiveWithId(link);
                        // 同步代理
                        try {
                            tempVo.setId(link.getId());
                            syncLink(tempVo);
                        }catch (Exception e){
                            log.error("LinkServiceImpl.editLink Exception:{}",e);
                        }
                    }
                    break;

                case DataConstants.EDIT_TYPE_UPD://批量修改
                case DataConstants.EDIT_TYPE_DEL:// 逻辑删除
                    for (LocalLinkVo vo : req.getLocalLinkVos()){
                        // 校验id是否为空
                        if (vo.getId() == null){
                            throw new Exception("LinkVos.id"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmplLink localLink = nmplLinkMapper.selectByPrimaryKey(vo.getId());
                        if (localLink == null){
                            throw new Exception("link"+ ErrorMessageContants.DATA_CANNOT_FIND_INDB);
                        }
                        NmplLink link = new NmplLink();
                        BeanUtils.copyProperties(vo,link);
                        nmplLinkMapper.updateByPrimaryKeySelective(link);
                        // 同步代理
                        try{
                            syncLink(vo);
                        }catch (Exception e){
                            log.error("LinkServiceImpl.editLink Exception:{}",e);
                        }

                    }
                    break;
                default:
                    throw new Exception("EditType"+ PARAM_IS_UNEXPECTED_MSG);
            }
            result = buildResult(null);
        }catch (Exception e){
            log.error("LinkServiceImpl.editLink Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    public LocalLinkVo makeupDefaultValue(LocalLinkVo vo){
        if (vo.getIsOn() == null){// 链路启停默认开启
            vo.setIsOn(true);
        }
        if (vo.getIsExist() == null){// 链路默认存在
            vo.setIsExist(true);
        }
        Date now = new Date();
        if (vo.getCreateTime() == null){// 默认创建时间为当前时间
            vo.setCreateTime(now);
        }
        if (vo.getUpdateTime() == null){// 默认更新时间为当前时间
            vo.setUpdateTime(now);
        }
        return vo;
    }

    /**
     * 同步链路（同步所有节点）
     * @param vo
     * @throws Exception
     */
    private void syncLink(LocalLinkVo vo) throws Exception{
        Set<String> lanIps = new HashSet<>();
        // 查询所有基站代理节点并过滤重复ip
        NmplBaseStationInfoExample baseExample = new NmplBaseStationInfoExample();
        baseExample.createCriteria().andIsExistEqualTo(IS_EXIST);
        List<NmplBaseStationInfo> stationInfos = nmplBaseStationInfoMapper.selectByExample(baseExample);
        if (!CollectionUtils.isEmpty(stationInfos)){
            for (NmplBaseStationInfo info:stationInfos){
                lanIps.add(info.getLanIp());
            }
        }

        // 查询所有设备代理节点并过滤重复ip
        NmplDeviceInfoExample deviceExample = new NmplDeviceInfoExample();
        deviceExample.createCriteria().andIsExistEqualTo(IS_EXIST);
        List<NmplDeviceInfo> deviceInfos = nmplDeviceInfoMapper.selectByExample(deviceExample);
        if (!CollectionUtils.isEmpty(deviceInfos)){
            for (NmplDeviceInfo info:deviceInfos){
                lanIps.add(info.getLanIp());
            }
        }

        // 同步所有代理节点
        if (!CollectionUtils.isEmpty(lanIps)){
            for (String lanIp : lanIps){
                String url = HttpClientUtil.getUrl(lanIp, proxyPort, proxyPath + URL_LINK_RELATION_UPDATE);
                Map<String,String> map = new HashMap<>();
                map.put(KEY_DATA,JSONObject.toJSONString(vo));
                map.put(KEY_URL,url);
                asyncService.syncLink(map);
            }
        }
    }

    private void checkEditLinkParam(EditLinkReq req) throws Exception{
        // 校验操作类型入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new Exception("EditType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        if (CollectionUtils.isEmpty(req.getLocalLinkVos())){
                throw new Exception("LinkVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }

    }
}
