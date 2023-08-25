package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmplDictionaryMapper;
import com.matrictime.network.dao.mapper.NmplReportBusinessMapper;
import com.matrictime.network.dao.mapper.extend.NmplDictionaryExtMapper;
import com.matrictime.network.dao.model.NmplDictionary;
import com.matrictime.network.dao.model.NmplDictionaryExample;
import com.matrictime.network.dao.model.NmplReportBusiness;
import com.matrictime.network.dao.model.NmplReportBusinessExample;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.ReportBusinessVo;
import com.matrictime.network.request.EditBasicConfigReq;
import com.matrictime.network.request.QueryDictionaryReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.SystemConfigService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

import static com.matrictime.network.constant.DataConstants.IS_EXIST;
import static com.matrictime.network.constant.DataConstants.KEY_PERCENT;
import static com.matrictime.network.exception.ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG;

@Slf4j
@Service
public class SystemConfigServiceImpl extends SystemBaseService implements SystemConfigService {

    @Resource
    NmplReportBusinessMapper reportBusinessMapper;

    @Resource
    NmplDictionaryMapper dictionaryMapper;

    @Resource
    NmplDictionaryExtMapper dictionaryExtMapper;

    /**
     * 基础配置查询
     * @return
     */
    @Override
    public Result<PageInfo> queryBasicConfig() {
        Result<PageInfo> result;

        try {
            PageInfo pageInfo = new PageInfo<>();
            NmplReportBusinessExample example = new NmplReportBusinessExample();
            example.createCriteria().andIsExistEqualTo(IS_EXIST);
            List<NmplReportBusiness> reportBusinesses = reportBusinessMapper.selectByExample(example);
            pageInfo.setList(reportBusinesses);
            result = buildResult(pageInfo);
        }catch (Exception e){
            log.error("SystemConfigServiceImpl.queryConfigByPages Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    @Transactional
    public Result editBasicConfig(EditBasicConfigReq req) {
        Result result;

        try {
            // check param is legal
            checkEditConfigParam(req);
            switch (req.getEditType()){
                // 批量插入（暂未使用）
                case DataConstants.EDIT_TYPE_ADD:
                    for (ReportBusinessVo vo : req.getReportBusinessVos()){
                        NmplReportBusiness reportBusiness = new NmplReportBusiness();
                        BeanUtils.copyProperties(vo,reportBusiness);
                        reportBusinessMapper.insertSelective(reportBusiness);
                    }
                    break;
                //批量修改
                case DataConstants.EDIT_TYPE_UPD:
                    for (ReportBusinessVo vo : req.getReportBusinessVos()){
                        // 校验id是否为空
                        if (vo.getId() == null){
                            throw new Exception("ReportBusinessVos.id"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        NmplReportBusiness reportBusiness = new NmplReportBusiness();
                        reportBusiness.setId(vo.getId());
                        reportBusiness.setIsReport(vo.getIsReport());
                        reportBusinessMapper.updateByPrimaryKeySelective(reportBusiness);
                    }
                    break;
                // 批量删除
                case DataConstants.EDIT_TYPE_DEL:
                    for (Long id : req.getDelIds()){
                        reportBusinessMapper.deleteByPrimaryKey(id);
                    }
                    break;
                default:
                    throw new Exception("EditType"+ PARAM_IS_UNEXPECTED_MSG);
            }

            result = buildResult(null);
        }catch (Exception e){
            log.error("SystemConfigServiceImpl.editBasicConfig Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }

    /**
     * 字典表查询
     * @param req
     * @return
     */
    @Override
    public Result<PageInfo> queryDictionary(QueryDictionaryReq req) {
        Result<PageInfo> result;

        try {
            PageInfo pageInfo = new PageInfo<>();
            NmplDictionaryExample example = new NmplDictionaryExample();
            NmplDictionaryExample.Criteria criteria = example.createCriteria();
            criteria.andIsExistEqualTo(IS_EXIST);
            String keyWords = req.getKeyWords();
            if (!ParamCheckUtil.checkVoStrBlank(keyWords)){
                criteria.andIdCodeLike(KEY_PERCENT+keyWords+KEY_PERCENT);
                NmplDictionaryExample.Criteria criteria2 = example.createCriteria();
                criteria2.andIdNameLike(KEY_PERCENT+keyWords+KEY_PERCENT).andIsExistEqualTo(IS_EXIST);
                example.or(criteria2);
            }
            List<NmplDictionary> dictionaries = dictionaryMapper.selectByExample(example);
            pageInfo.setList(dictionaries);
            result = buildResult(pageInfo);
        }catch (Exception e){
            log.error("SystemConfigServiceImpl.queryDictionary Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    @Transactional
    public Result uploadDictionary(MultipartFile file) {
        Result result;
        try {
            File tmp = new File(System.getProperty("user.dir")+File.separator+file.getName()+".csv");
            log.info(System.getProperty("user.dir")+File.separator+file.getName());
            file.transferTo(tmp);
            List<NmplDictionary> dictionaries = CsvServiceImpl.readCsvToPc(tmp);
            tmp.delete();
            Integer num = dictionaryExtMapper.batchInsert(dictionaries);
            result = buildResult(num);
        }catch (Exception e) {
            log.error("SystemConfigServiceImpl.uploadDictionary Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    private void checkEditConfigParam(EditBasicConfigReq req) throws Exception{
        // 校验操作类型入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new Exception("editType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 校验操作类型为新增时入参是否合法
        if (DataConstants.EDIT_TYPE_ADD.equals(req.getEditType()) || DataConstants.EDIT_TYPE_UPD.equals(req.getEditType())){
            if (CollectionUtils.isEmpty(req.getReportBusinessVos())){
                throw new Exception("reportBusinessVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
        // 校验操作类型为删除时入参是否合法
        if (DataConstants.EDIT_TYPE_DEL.equals(req.getEditType()) && CollectionUtils.isEmpty(req.getDelIds())){
            throw new Exception("delIds"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }
}
