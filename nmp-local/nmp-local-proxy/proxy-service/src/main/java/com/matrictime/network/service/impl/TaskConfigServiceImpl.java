package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.mapper.NmplTaskConfigMapper;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import com.matrictime.network.dao.model.NmplLocalBaseStationInfo;
import com.matrictime.network.dao.model.NmplLocalBaseStationInfoExample;
import com.matrictime.network.enums.DeviceTypeEnum;
import com.matrictime.network.modelVo.BaseStationInfoVo;
import com.matrictime.network.modelVo.CenterBaseStationInfoVo;
import com.matrictime.network.modelVo.CenterTaskConfigVo;
import com.matrictime.network.service.TaskConfigService;
import com.matrictime.network.service.UpdateInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.matrictime.network.base.constant.DataConstants.*;
import static com.matrictime.network.constant.DataConstants.*;

@Service
@Slf4j
public class TaskConfigServiceImpl extends SystemBaseService implements TaskConfigService {

    @Autowired
    private UpdateInfoService updateInfoService;

    @Resource
    private NmplTaskConfigMapper taskConfigMapper;

    @Override
    public void initInfo(List<CenterTaskConfigVo> centerTaskConfigVos) {


        Date createTime = new Date();
        Set<String> types = new HashSet<>();
        for (int i=0;i<centerTaskConfigVos.size();i++){
            CenterTaskConfigVo vo = centerTaskConfigVos.get(i);
            if (DeviceTypeEnum.STATION_INSIDE.getCode().equals(vo.getDeviceType())){
                types.add(DeviceTypeEnum.STATION_INSIDE.getCode());
            }
        }

        int updateTable = 0;

        for (String type : types){
            // 插入通知表更新
            updateTable = updateInfoService.updateInfo(type,NMPL_BASE_STATION_INFO,EDIT_TYPE_ADD,SYSTEM_NM,createTime);
        }

    }
}
