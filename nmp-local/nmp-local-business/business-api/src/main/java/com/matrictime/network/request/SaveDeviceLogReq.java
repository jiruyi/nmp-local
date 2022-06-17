package com.matrictime.network.request;

import com.matrictime.network.model.DeviceLog;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SaveDeviceLogReq implements Serializable {
    private static final long serialVersionUID = 3899953904094038498L;

    private List<DeviceLog> deviceLogs;
}
