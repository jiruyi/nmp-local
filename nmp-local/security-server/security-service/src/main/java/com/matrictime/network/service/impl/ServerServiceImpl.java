package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmpHeartReportMapper;
import com.matrictime.network.dao.model.NmpHeartReport;
import com.matrictime.network.dao.model.NmpHeartReportExample;
import com.matrictime.network.exception.SystemException;
import com.matrictime.network.model.Result;
import com.matrictime.network.resp.GetServerStatusResp;
import com.matrictime.network.service.ServerService;
import com.matrictime.network.util.DateUtils;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ServerServiceImpl extends SystemBaseService implements ServerService {

    @Resource
    private NmpHeartReportMapper nmpHeartReportMapper;

    @Override
    public Result<GetServerStatusResp> getStatus() {
        Result result;
        try {

            GetServerStatusResp resp = new GetServerStatusResp();
            NmpHeartReportExample example = new NmpHeartReportExample();
            example.createCriteria().andUpdateTimeGreaterThanOrEqualTo(DateUtils.addSecondsForDate(new Date(),-30));
            List<NmpHeartReport> nmpHeartReports = nmpHeartReportMapper.selectByExample(example);

            // 默认返回离线
            String serverStatus = DataConstants.serverStatus.get(2);
            if (!CollectionUtils.isEmpty(nmpHeartReports)){
                NmpHeartReport nmpHeartReport = nmpHeartReports.get(0);
                String status = DataConstants.serverStatus.get(nmpHeartReport.getStatus());
                if (!ParamCheckUtil.checkVoStrBlank(status)){
                    serverStatus = status;
                }
            }

            resp.setStatus(serverStatus);
            result = buildResult(resp);
        }catch (SystemException e){
            log.error("ServerServiceImpl.getServerStatus SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.getServerStatus Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    @Override
    public Result start() {
        Result result;
        try {
            result = buildResult(null);
        }catch (SystemException e){
            log.error("ServerServiceImpl.start SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.start Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }
}
