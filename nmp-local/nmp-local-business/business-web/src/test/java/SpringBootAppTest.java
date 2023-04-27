import com.matrictime.network.NetworkManagerApplication;
import com.matrictime.network.base.util.DateUtils;
import com.matrictime.network.dao.domain.AlarmDataDomainService;
import com.matrictime.network.model.AlarmInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @title
 * @param
 * @return
 * @description
 * @author jiruyi
 * @create 2021/9/14 0014 15:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetworkManagerApplication.class)
@Slf4j
public class SpringBootAppTest {

    @Autowired
    private AlarmDataDomainService alarmDataDomainService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
        List<AlarmInfo> alarmInfoList = new ArrayList<>();
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.24").alarmUploadTime(new Date()).alarmContentType("1").alarmSourceType("01").alarmLevel("1").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.24").alarmUploadTime(new Date()).alarmContentType("2").alarmSourceType("01").alarmLevel("1").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.24").alarmUploadTime(new Date()).alarmContentType("3").alarmSourceType("01").alarmLevel("1").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.24").alarmUploadTime(new Date()).alarmContentType("4").alarmSourceType("11").alarmLevel("1").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.25").alarmUploadTime(new Date()).alarmContentType("1").alarmSourceType("01").alarmLevel("1").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.25").alarmUploadTime(new Date()).alarmContentType("2").alarmSourceType("02").alarmLevel("1").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.25").alarmUploadTime(new Date()).alarmContentType("3").alarmSourceType("01").alarmLevel("1").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.25").alarmUploadTime(new Date()).alarmContentType("4").alarmSourceType("01").alarmLevel("1").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.25").alarmUploadTime(DateUtils.addDayForNow(1)).alarmSourceType("01").alarmLevel("1").alarmContentType("4").build());
        alarmDataDomainService.acceptAlarmData(alarmInfoList);
    }

    @Test
    public void testRedis_1(){
        log.info(redisTemplate.hasKey("shiro_redis_session:efd54a66-f371-41dc-b63a-4242df4893dd").toString());
        redisTemplate.opsForValue().set("shiro_ redis_session:efd54a66-f371-41dc-b63a-4242df4893d1".toString(),"1");
    }


}
