package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.domain.TaskConfigDomainService;
import com.matrictime.network.dao.model.NmplConfig;
import com.matrictime.network.dao.model.NmplTaskConfig;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.TaskConfigVo;
import com.matrictime.network.modelVo.TaskVo;
import com.matrictime.network.request.EditTaskReq;
import com.matrictime.network.request.QueryTaskReq;
import com.matrictime.network.response.EditConfigResp;
import com.matrictime.network.response.EditTaskResp;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.service.TaskConfigService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.matrictime.network.exception.ErrorMessageContants.PARAM_IS_UNEXPECTED_MSG;

@Slf4j
@Service
public class TaskConfigServiceImpl extends SystemBaseService implements TaskConfigService {

    @Autowired
    private TaskConfigDomainService taskConfigDomainService;

    @Autowired
    private AsyncService asyncService;


    @Override
    public Result<PageInfo> queryTaskByPages(QueryTaskReq req) {
        Result<PageInfo> result;
        try{
            PageInfo<NmplTaskConfig> taskConfigPageInfo = taskConfigDomainService.queryByPages(req);
            result = buildResult(taskConfigPageInfo);
        }catch (SystemException e){
            log.error("TaskConfigServiceImpl.queryTaskByPages SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("TaskConfigServiceImpl.queryTaskByPages Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    @Override
    @Transactional
    public Result<EditTaskResp> edit(EditTaskReq req) {
        Result result;

        try {
            EditConfigResp resp = null;
            // check param is legal
            checkEditConfigParam(req);
            switch (req.getEditType()){
                // 批量插入（暂未使用）
                case DataConstants.EDIT_TYPE_ADD:
                    break;
                //批量修改
                case DataConstants.EDIT_TYPE_UPD:
                    List<TaskConfigVo> vos = new ArrayList<>();
                    List<TaskVo> taskVos = new ArrayList<>();
                    for (TaskConfigVo vo : req.getTaskConfigVos()){
                        // 校验id是否为空
                        if (vo.getId() == null){
                            throw new Exception("TaskConfigVos.id"+ ErrorMessageContants.PARAM_IS_NULL_MSG);
                        }
                        vos.add(vo);
                    }
                    Integer upd = taskConfigDomainService.update(vos);

                    break;
                // 批量删除（暂未使用）
                case DataConstants.EDIT_TYPE_DEL:
                    break;
                default:
                    throw new Exception("EditType"+ PARAM_IS_UNEXPECTED_MSG);
            }
            result = buildResult(resp);
        }catch (Exception e){
            log.error("TaskConfigServiceImpl.edit Exception:{}",e.getMessage());
            result = failResult("");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return result;
    }


    private void checkEditConfigParam(EditTaskReq req) throws Exception{
        // 校验操作类型入参是否合法
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new Exception("editType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
        // 校验操作类型为新增时入参是否合法
        if (DataConstants.EDIT_TYPE_ADD.equals(req.getEditType()) || DataConstants.EDIT_TYPE_UPD.equals(req.getEditType())){
            if (CollectionUtils.isEmpty(req.getTaskConfigVos())){
                throw new Exception("TaskConfigVos"+ErrorMessageContants.PARAM_IS_NULL_MSG);
            }
        }
    }
}
