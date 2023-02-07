package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.dao.mapper.NmpHeartReportMapper;
import com.matrictime.network.dao.mapper.NmpOperateServerInfoMapper;
import com.matrictime.network.dao.model.NmpHeartReport;
import com.matrictime.network.dao.model.NmpHeartReportExample;
import com.matrictime.network.dao.model.NmpOperateServerInfo;
import com.matrictime.network.dao.model.NmpOperateServerInfoExample;
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

import static com.matrictime.network.base.constant.DataConstants.OPERATE_STATUS_WAIT;
import static com.matrictime.network.base.constant.DataConstants.OPERATE_TYPE_START_SERVER;
import static com.matrictime.network.base.exception.ErrorMessageContants.PLEASE_WAIT;
import static com.matrictime.network.exception.ErrorMessageContants.CONFIG_IS_NOT_EXIST;
import static com.matrictime.network.exception.ErrorMessageContants.SYSTEM_EXCEPTION;

@Service
@Slf4j
public class ServerServiceImpl extends SystemBaseService implements ServerService {

    @Resource
    private NmpHeartReportMapper nmpHeartReportMapper;

    @Resource
    private NmpOperateServerInfoMapper nmpOperateServerInfoMapper;

    @Override
    public Result<GetServerStatusResp> getStatus() {
        Result result;
        try {

            GetServerStatusResp resp = new GetServerStatusResp();
            NmpHeartReportExample example = new NmpHeartReportExample();
            example.createCriteria().andUpdateTimeGreaterThanOrEqualTo(DateUtils.addSecondsForDate(new Date(),-30));
            List<NmpHeartReport> nmpHeartReports = nmpHeartReportMapper.selectByExample(example);

            // 默认返回离线
            String serverStatus = DataConstants.SERVER_STATUS.get(2);
            if (!CollectionUtils.isEmpty(nmpHeartReports)){
                NmpHeartReport nmpHeartReport = nmpHeartReports.get(0);
                String status = DataConstants.SERVER_STATUS.get(nmpHeartReport.getStatus());
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
            checkStartStatus();
            NmpOperateServerInfoExample example = new NmpOperateServerInfoExample();
            example.createCriteria().andOperateTypeEqualTo(OPERATE_TYPE_START_SERVER);
            NmpOperateServerInfo serverInfo = new NmpOperateServerInfo();
            serverInfo.setOperateStatus(OPERATE_STATUS_WAIT);
            result = buildResult(nmpOperateServerInfoMapper.updateByExampleSelective(serverInfo,example));
        }catch (SystemException e){
            log.error("ServerServiceImpl.start SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.start Exception:{}",e.getMessage());
            result = failResult("");
        }

        return result;
    }

    @Override
    public Result getStartStatus() {
        Result result;
        try {
            checkStartStatus();
            result = buildResult(null);
        }catch (SystemException e){
            log.error("ServerServiceImpl.getStartStatus SystemException:{}",e.getMessage());
            result = failResult(e);
        }catch (Exception e){
            log.error("ServerServiceImpl.getStartStatus Exception:{}",e.getMessage());
            result = failResult("");
        }
        return result;
    }

    private void checkStartStatus() throws Exception {
        NmpOperateServerInfoExample example = new NmpOperateServerInfoExample();
        example.createCriteria().andOperateTypeEqualTo(OPERATE_TYPE_START_SERVER);
        List<NmpOperateServerInfo> nmpOperateServerInfos = nmpOperateServerInfoMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(nmpOperateServerInfos)){
            throw new Exception(CONFIG_IS_NOT_EXIST);
        }else {
            NmpOperateServerInfo serverInfo = nmpOperateServerInfos.get(0);
            if (OPERATE_STATUS_WAIT == serverInfo.getOperateStatus()){
                throw new SystemException(PLEASE_WAIT);
            }
        }
    }

}
