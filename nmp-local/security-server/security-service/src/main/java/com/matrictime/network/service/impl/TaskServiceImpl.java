package com.matrictime.network.service.impl;

import com.matrictime.network.dao.mapper.NmpKeyInfoMapper;
import com.matrictime.network.dao.model.NmpKeyInfoExample;
import com.matrictime.network.service.TaskService;
import com.matrictime.network.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Resource
    private NmpKeyInfoMapper nmpKeyInfoMapper;

    @Override
    public int delKeyInfoData(Date now, Integer saveDays) {
        NmpKeyInfoExample example = new NmpKeyInfoExample();
        example.createCriteria().andUpdateTimeLessThan(DateUtils.addDayForDate(now,-saveDays));
        int i = nmpKeyInfoMapper.deleteByExample(example);
        return i;
    }
}
