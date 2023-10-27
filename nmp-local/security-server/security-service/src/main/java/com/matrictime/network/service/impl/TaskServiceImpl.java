package com.matrictime.network.service.impl;

import com.matrictime.network.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Date;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {


    @Override
    public int delKeyInfoData(Date now, Integer saveDays) {
        int i = 0;
        return i;
    }
}
