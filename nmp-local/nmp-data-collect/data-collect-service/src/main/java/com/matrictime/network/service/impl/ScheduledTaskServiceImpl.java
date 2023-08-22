package com.matrictime.network.service.impl;

import com.matrictime.network.dao.domain.*;
import com.matrictime.network.facade.DataFacade;
import com.matrictime.network.model.Result;
import com.matrictime.network.modelVo.*;
import com.matrictime.network.response.CompanyHeartbeatResponse;
import com.matrictime.network.response.CompanyInfoResponse;
import com.matrictime.network.response.DataCollectResponse;
import com.matrictime.network.response.TerminalUserResponse;
import com.matrictime.network.service.ScheduledTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by wangqiang
 * @date 2023/8/21.
 */
@Service
@Slf4j
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

    @Resource
    private DataCollectDomainService dataCollectDomainService;

    @Resource
    private StationSummaryDomainService summaryDomainService;

    @Resource
    private TerminalUserDomainService terminalUserDomainService;

    @Resource
    private CompanyInfoDomainService companyInfoDomainService;

    @Resource
    private CompanyHeartbeatDomainService companyHeartbeatDomainService;

    @Resource
    private DataFacade dataFacade;

    @Override
    public void insertDataCollect(String url) {

        List<DataCollectVo> dataCollectVos = dataCollectDomainService.selectDataCollect();
        DataCollectResponse dataCollectResponse = new DataCollectResponse();
        dataCollectResponse.setList(dataCollectVos);
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            result = dataFacade.insertDataCollect(dataCollectResponse);
            log.info("insertDataCollect push result:{}",result);
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("insertDataCollect push Exception:{}",e.getMessage());
        }

    }

    @Override
    public void selectCompanyHeartbeat(String url) {

        List<CompanyHeartbeatVo> list = companyHeartbeatDomainService.selectCompanyHeartbeat();
        CompanyHeartbeatResponse companyHeartbeatResponse = new CompanyHeartbeatResponse();
        companyHeartbeatResponse.setList(list);
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            result = dataFacade.insertCompanyHeartbeat(companyHeartbeatResponse);
            log.info("selectCompanyHeartbeat push result:{}",result);
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("selectCompanyHeartbeat push Exception:{}",e.getMessage());
        }

    }

    @Override
    public void receiveCompany(String url) {
        List<CompanyInfoVo> companyInfoVos = null;
        try {
            companyInfoVos = companyInfoDomainService.selectCompanyInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        CompanyInfoResponse companyInfoResponse = new CompanyInfoResponse();
        companyInfoResponse.setList(companyInfoVos);
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            result = dataFacade.receiveCompany(companyInfoResponse);
            log.info("receiveCompany push result:{}",result);
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("receiveCompany push Exception:{}",e.getMessage());
        }
    }


    @Override
    public void receiveTerminalUser(String url) {

        List<TerminalUserVo> terminalUserVoList = terminalUserDomainService.selectTerminalUser();
        TerminalUserResponse terminalUserResponse = new TerminalUserResponse();
        terminalUserResponse.setList(terminalUserVoList);
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            result = dataFacade.receiveTerminalUser(terminalUserResponse);
            log.info("receiveTerminalUser push result:{}",result);
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("receiveTerminalUser push Exception:{}",e.getMessage());
        }
    }

    @Override
    public void selectSystemHeart(String url) {
        StationSummaryVo stationSummaryVo = summaryDomainService.selectSystemHeart();
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            result = dataFacade.receiveStationSummary(stationSummaryVo);
            log.info("selectSystemHeart push result:{}",result);
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("selectSystemHeart push Exception:{}",e.getMessage());
        }
    }

    @Override
    public void selectStation(String url) {
        StationSummaryVo stationSummaryVo = summaryDomainService.selectStation();
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            result = dataFacade.receiveStationSummary(stationSummaryVo);
            log.info("selectStation push result:{}",result);
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("selectStation push Exception:{}",e.getMessage());
        }
    }

    @Override
    public void selectDevice(String url) {
        StationSummaryVo stationSummaryVo = summaryDomainService.selectDevice();
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            result = dataFacade.receiveStationSummary(stationSummaryVo);
            log.info("selectDevice push result:{}",result);
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("selectDevice push Exception:{}",e.getMessage());
        }
    }

    @Override
    public void selectBorderStation(String url) {
        StationSummaryVo stationSummaryVo = summaryDomainService.selectBorderStation();
        Boolean flag = false;
        Result result = null;
        String data = "";
        String msg =null;
        try {
            result = dataFacade.receiveStationSummary(stationSummaryVo);
            log.info("selectBorderStation push result:{}",result);
        }catch (Exception e){
            flag = true;
            msg = e.getMessage();
            log.info("selectBorderStation push Exception:{}",e.getMessage());
        }
    }
}
