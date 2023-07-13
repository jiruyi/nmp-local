package com.matrictime.network.service;

import java.util.Date;

public interface UpdateInfoService {

    /**
     * 通知信息更新表
     * @param deviceType 设备类型（01:接入基站 02:边界基站 11:密钥中心 20:数据采集）
     * @param tableName
     * @param operationType 操作类型：新增:1 修改:2 删除:3
     * @param createUser
     * @param createTime
     * @return
     */
    int updateInfo(String deviceType,String tableName, String operationType, String createUser, Date createTime);
}
