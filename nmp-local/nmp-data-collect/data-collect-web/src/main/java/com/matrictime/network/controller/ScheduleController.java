package com.matrictime.network.controller;

import com.matrictime.network.schedule.MyTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/7/20 0020 10:22
 * @desc
 */
@Controller
public class ScheduleController {

    @Autowired
    private MyTask myTask;



    @GetMapping("/test")
    public boolean test(@Param("cro") String cro) {
        myTask.updateCron(cro);
        return true;
    }
}
