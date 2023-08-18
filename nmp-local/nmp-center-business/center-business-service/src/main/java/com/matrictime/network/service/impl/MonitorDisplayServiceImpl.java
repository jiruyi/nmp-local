package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplCompanyInfoMapper;
import com.matrictime.network.dao.mapper.NmplStationSummaryMapper;
import com.matrictime.network.dao.mapper.NmplTerminalUserMapper;
import com.matrictime.network.dao.model.*;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.CompanyInfoVo;
import com.matrictime.network.modelVo.ReportBusinessVo;
import com.matrictime.network.request.*;
import com.matrictime.network.response.QueryCompanyUserResp;
import com.matrictime.network.response.QueryDeviceResp;
import com.matrictime.network.response.queryUserResp;
import com.matrictime.network.service.MonitorDisplayService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.matrictime.network.constant.DataConstants.IS_EXIST;
import static com.matrictime.network.exception.ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG;

@Slf4j
@Service
public class MonitorDisplayServiceImpl extends SystemBaseService implements MonitorDisplayService {

    @Resource
    private NmplStationSummaryMapper stationSummaryMapper;

    @Resource
    private NmplCompanyInfoMapper companyInfoMapper;

    @Resource
    private NmplTerminalUserMapper terminalUserMapper;


    /**
     * 查询小区用户数
     * @param req
     * @return
     */
    @Override
    public Result<QueryCompanyUserResp> queryCompanyUser(QueryCompanyUserReq req) {
        Result result;

        try {
            QueryCompanyUserResp resp = new QueryCompanyUserResp();
            NmplCompanyInfoExample example = new NmplCompanyInfoExample();
            example.createCriteria().andIsExistEqualTo(IS_EXIST);
            List<NmplCompanyInfo> nmplCompanyInfos = companyInfoMapper.selectByExample(example);

            if (!CollectionUtils.isEmpty(nmplCompanyInfos)){
                // 小区列表
                List<CompanyInfoVo> companyInfos = new ArrayList<>();
                // 在线用户数列表
                List<String> onlineUser = new ArrayList<>();
                // 接入用户数列表
                List<String> accessUser = new ArrayList<>();
                for (NmplCompanyInfo companyInfo:nmplCompanyInfos){
                    CompanyInfoVo vo = new CompanyInfoVo();
                    BeanUtils.copyProperties(companyInfo,vo);
                    NmplTerminalUserExample userExample = new NmplTerminalUserExample();
                    userExample.createCriteria().andCompanyNetworkIdEqualTo(companyInfo.getCompanyNetworkId());
                    List<NmplTerminalUser> terminalUsers = terminalUserMapper.selectByExample(userExample);
                    if (!CollectionUtils.isEmpty(terminalUsers)){
                        terminalUsers.get(0).getSumNumber();
                    }

                    companyInfos.add(vo);
                }
                resp.setCompanyInfo(companyInfos);
            }

            result = buildResult(resp);
        }catch (Exception e){
            log.error("MonitorDisplayServiceImpl.queryCompanyUser Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    /**
     * 查询用户数
     * @param req
     * @return
     */
    @Override
    public Result<queryUserResp> queryUser(QueryUserReq req) {
        Result result;

        try {


            result = buildResult(null);
        }catch (Exception e){
            log.error("MonitorDisplayServiceImpl.queryUser Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    /**
     * 查询设备数
     * @param req
     * @return
     */
    @Override
    public Result<QueryDeviceResp> queryDevice(QueryDeviceReq req){
        Result result;

        try {
//            NmplDictionaryExample example = new NmplDictionaryExample();
//            example.createCriteria().andIsExistEqualTo(IS_EXIST);
//            List<NmplDictionary> dictionaries = dictionaryMapper.selectByExample(example);
            result = buildResult(null);
        }catch (Exception e){
            log.error("MonitorDisplayServiceImpl.queryDevice Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

//    private void checkQueryUserParam(QueryUserReq req) throws Exception{
//        // 校验操作类型入参是否合法
//        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
//            throw new Exception("editType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
//        }
//        // 校验操作类型为新增时入参是否合法
//        if (DataConstants.EDIT_TYPE_ADD.equals(req.getEditType()) || DataConstants.EDIT_TYPE_UPD.equals(req.getEditType())){
//            if (CollectionUtils.isEmpty(req.getReportBusinessVos())){
//                throw new Exception("reportBusinessVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
//            }
//        }
//        // 校验操作类型为删除时入参是否合法
//        if (DataConstants.EDIT_TYPE_DEL.equals(req.getEditType()) && CollectionUtils.isEmpty(req.getDelIds())){
//            throw new Exception("delIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
//        }
//    }
}
