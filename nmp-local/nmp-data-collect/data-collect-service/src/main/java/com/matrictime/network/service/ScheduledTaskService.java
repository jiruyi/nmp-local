package com.matrictime.network.service;

import com.matrictime.network.model.Result;
import com.matrictime.network.response.CompanyHeartbeatResponse;
import com.matrictime.network.response.DataCollectResponse;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author by wangqiang
 * @date 2023/8/21.
 */
public interface ScheduledTaskService {

    void insertDataCollect(String url);

    void selectCompanyHeartbeat(String url);

    void receiveCompany(String url);

    void receiveTerminalUser(String url);

    void selectSystemHeart(String url);

    void selectStation(String url);

    void selectDevice(String url);

    void selectBorderStation(String url);


}
