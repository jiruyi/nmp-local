package com.matrictime.network.service.impl;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.dao.mapper.NmplConfigMapper;
import com.matrictime.network.dao.mapper.extend.NmplConfigExtMapper;
import com.matrictime.network.dao.model.NmplConfig;
import com.matrictime.network.dao.model.NmplConfigExample;
import com.matrictime.network.exception.ErrorCode;
import com.matrictime.network.exception.ErrorMessageContants;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.EditConfigReq;
import com.matrictime.network.request.QueryConfigByPagesReq;
import com.matrictime.network.response.EditConfigResp;
import com.matrictime.network.response.QueryConfigByPagesResp;
import com.matrictime.network.service.ConfigService;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ConfigServiceImpl extends SystemBaseService implements ConfigService {

    @Autowired(required = false)
    private NmplConfigExtMapper nmplConfigExtMapper;

    @Autowired(required = false)
    private NmplConfigMapper nmplConfigMapper;

    @Override
    public Result<QueryConfigByPagesResp> queryByPages(QueryConfigByPagesReq req) {
        Result result;
        try {
            PageInfo<NmplConfig> pageInfo = PageHelper.startPage(req.getPageNo(), req.getPageSize()).doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    NmplConfig nmplConfig = new NmplConfig();
                    if (StringUtils.isNotBlank(req.getConfigName())){
                        StringBuffer sb = new StringBuffer("%").append(req.getConfigName()).append("%");
                        nmplConfig.setConfigName(sb.toString());
                    }
                    nmplConfigExtMapper.selectByExample(nmplConfig);
                }
            });

            QueryConfigByPagesResp resp = new QueryConfigByPagesResp();
            resp.setPageInfo(pageInfo);
            result = buildResult(resp);
        }catch (Exception e){
            log.error(e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    @Override
    public Result<EditConfigResp> editConfig(EditConfigReq req) {
        Result result;
        try {
            // check param is legal
            checkEditConfigParam(req);


            EditConfigResp resp = new EditConfigResp();
            result = buildResult(resp);
        }catch (SystemException e){
            log.error(e.getMessage());
            result = failResult(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error(e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    private void checkEditConfigParam(EditConfigReq req) {
        if (ParamCheckUtil.checkVoStrBlank(req.getEditType())){
            throw new SystemException(ErrorCode.PARAM_IS_NULL, "EditType"+ErrorMessageContants.PARAM_IS_NULL_MSG);
        }
    }
}
