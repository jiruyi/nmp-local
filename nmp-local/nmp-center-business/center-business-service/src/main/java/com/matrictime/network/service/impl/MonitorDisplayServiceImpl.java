//package com.matrictime.network.service.impl;
//
//import com.matrictime.network.base.SystemBaseService;
//import com.matrictime.network.constant.DataConstants;
//import com.matrictime.network.dao.model.NmplDictionary;
//import com.matrictime.network.dao.model.NmplDictionaryExample;
//import com.matrictime.network.dao.model.NmplReportBusiness;
//import com.matrictime.network.dao.model.NmplReportBusinessExample;
//import com.matrictime.network.exception.ErrorMessageContants;
//import com.matrictime.network.model.Result;
//import com.matrictime.network.modelVo.ReportBusinessVo;
//import com.matrictime.network.request.*;
//import com.matrictime.network.response.QueryCompanyUserResp;
//import com.matrictime.network.response.QueryDeviceResp;
//import com.matrictime.network.response.queryUserResp;
//import com.matrictime.network.service.MonitorDisplayService;
//import com.matrictime.network.util.ParamCheckUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.interceptor.TransactionAspectSupport;
//import org.springframework.util.CollectionUtils;
//import java.util.List;
//
//import static com.matrictime.network.constant.DataConstants.IS_EXIST;
//import static com.matrictime.network.exception.ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG;
//
//@Slf4j
//@Service
//public class MonitorDisplayServiceImpl extends SystemBaseService implements MonitorDisplayService {
//
//
//    /**
//     * 查询小区用户数
//     * @param req
//     * @return
//     */
//    @Override
//    public Result<QueryCompanyUserResp> queryCompanyUser(QueryCompanyUserReq req) {
//        Result result;
//
//        try {
//            NmplReportBusinessExample example = new NmplReportBusinessExample();
//            example.createCriteria().andIsExistEqualTo(IS_EXIST);
//            List<NmplReportBusiness> reportBusinesses = reportBusinessMapper.selectByExample(example);
//            result = buildResult(reportBusinesses);
//        }catch (Exception e){
//            log.error("SystemConfigServiceImpl.queryConfigByPages Exception:{}",e.getMessage());
//            result = failResult("");
//        }
//        return result;
//    }
//
//    /**
//     * 查询用户数
//     * @param req
//     * @return
//     */
//    @Override
//    public Result<queryUserResp> queryUser(QueryUserReq req) {
//        Result result;
//
//        try {
//            // check param is legal
//            checkEditConfigParam(req);
//            switch (req.getEditType()){
//                // 批量插入（暂未使用）
//                case DataConstants.EDIT_TYPE_ADD:
//                    for (ReportBusinessVo vo : req.getReportBusinessVos()){
//                        NmplReportBusiness reportBusiness = new NmplReportBusiness();
//                        BeanUtils.copyProperties(vo,reportBusiness);
//                        reportBusinessMapper.insertSelective(reportBusiness);
//                    }
//                    break;
//                //批量修改
//                case DataConstants.EDIT_TYPE_UPD:
//                    for (ReportBusinessVo vo : req.getReportBusinessVos()){
//                        // 校验id是否为空
//                        if (vo.getId() == null){
//                            throw new Exception("ReportBusinessVos.id"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
//                        }
//                        NmplReportBusiness reportBusiness = new NmplReportBusiness();
//                        reportBusiness.setId(vo.getId());
//                        reportBusiness.setIsReport(vo.getIsReport());
//                        reportBusinessMapper.updateByPrimaryKeySelective(reportBusiness);
//                    }
//                    break;
//                // 批量删除
//                case DataConstants.EDIT_TYPE_DEL:
//                    for (Long id : req.getDelIds()){
//                        reportBusinessMapper.deleteByPrimaryKey(id);
//                    }
//                    break;
//                default:
//                    throw new Exception("EditType"+ PARAM_IS_UNEXPECTED_MSG);
//            }
//
//            result = buildResult(null);
//        }catch (Exception e){
//            log.error("SystemConfigServiceImpl.editBasicConfig Exception:{}",e.getMessage());
//            result = failResult("");
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
//
//        return result;
//    }
//
//    /**
//     * 查询设备数
//     * @param req
//     * @return
//     */
//    @Override
//    public Result<QueryDeviceResp> queryDevice(QueryDeviceReq req){
//        Result result;
//
//        try {
//            NmplDictionaryExample example = new NmplDictionaryExample();
//            example.createCriteria().andIsExistEqualTo(IS_EXIST);
//            List<NmplDictionary> dictionaries = dictionaryMapper.selectByExample(example);
//            result = buildResult(dictionaries);
//        }catch (Exception e){
//            log.error("SystemConfigServiceImpl.queryDictionary Exception:{}",e.getMessage());
//            result = failResult("");
//        }
//        return result;
//    }
//
//    private void checkEditConfigParam(EditBasicConfigReq req) throws Exception{
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
//}
