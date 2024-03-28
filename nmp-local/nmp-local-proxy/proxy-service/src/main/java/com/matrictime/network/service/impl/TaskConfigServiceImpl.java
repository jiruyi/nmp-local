package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.dao.mapper.NmplTaskConfigMapper;
import com.matrictime.network.dao.model.NmplBaseStationInfoExample;
import com.matrictime.network.dao.model.NmplLocalBaseStationInfo;
import com.matrictime.network.dao.model.NmplLocalBaseStationInfoExample;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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



    }
}
