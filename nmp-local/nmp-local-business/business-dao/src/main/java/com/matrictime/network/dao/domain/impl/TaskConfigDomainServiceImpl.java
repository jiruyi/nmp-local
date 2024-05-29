package com.matrictime.network.dao.domain.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.matrictime.network.dao.domain.TaskConfigDomainService;
import com.matrictime.network.dao.mapper.NmplTaskConfigMapper;
import com.matrictime.network.dao.mapper.extend.NmplTaskConfigExtMapper;
import com.matrictime.network.dao.model.NmplConfig;
import com.matrictime.network.dao.model.NmplTaskConfig;
import com.matrictime.network.dao.model.NmplTaskConfigExample;
import com.matrictime.network.modelVo.TaskConfigVo;
import com.matrictime.network.request.QueryTaskReq;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.matrictime.network.constant.DataConstants.KEY_PERCENT;
import static com.matrictime.network.constant.DataConstants.UPDTIME_DESC;

@Service
@Slf4j
public class TaskConfigDomainServiceImpl implements TaskConfigDomainService {

    @Resource
    private NmplTaskConfigMapper taskConfigMapper;

    @Resource
    private NmplTaskConfigExtMapper taskConfigExtMapper;

    @Override
    public PageInfo<NmplTaskConfig> queryByPages(QueryTaskReq req) {
        Page page = PageHelper.startPage(req.getPageNo(),req.getPageSize());
        NmplTaskConfigExample example = new NmplTaskConfigExample();
        NmplTaskConfigExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(UPDTIME_DESC);
        if (!ParamCheckUtil.checkVoStrBlank(req.getTaskName())){
            criteria.andTaskNameLike(KEY_PERCENT+req.getTaskName()+KEY_PERCENT);
        }
        if (!ParamCheckUtil.checkVoStrBlank(req.getDeviceType())){
            criteria.andDeviceTypeEqualTo(req.getDeviceType());
        }
        if (!ParamCheckUtil.checkVoStrBlank(req.getTaskType())){
            criteria.andTaskTypeEqualTo(req.getTaskType());
        }
        List<NmplTaskConfig> nmplTaskConfigs = taskConfigMapper.selectByExample(example);
        // 列表分页
        PageInfo<NmplTaskConfig> pageResult =  new PageInfo<>((int)page.getTotal(), page.getPages(), nmplTaskConfigs);
        return pageResult;
    }

    @Override
    @Transactional
    public Integer update(List<TaskConfigVo> vos) {
        int i = taskConfigExtMapper.batchUpdate(vos);
        return i;
    }
}
