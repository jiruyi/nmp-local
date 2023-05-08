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
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(new Date()).alarmContentType("1").alarmSourceType("00").alarmLevel("1").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(new Date()).alarmContentType("2").alarmSourceType("00").alarmLevel("2").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(new Date()).alarmContentType("3").alarmSourceType("00").alarmLevel("3").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(new Date()).alarmContentType("4").alarmSourceType("00").alarmLevel("4").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(new Date()).alarmContentType("1").alarmSourceType("00").alarmLevel("1").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(new Date()).alarmContentType("1").alarmSourceType("00").alarmLevel("2").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(new Date()).alarmContentType("2").alarmSourceType("00").alarmLevel("3").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(new Date()).alarmContentType("3").alarmSourceType("00").alarmLevel("4").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(DateUtils.addDayForNow(-1)).alarmSourceType("00").alarmLevel("2").alarmContentType("1").alarmContent("cpu过高").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(DateUtils.addDayForNow(-2)).alarmSourceType("00").alarmLevel("1").alarmContentType("2").alarmContent("内存不足").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.20").alarmUploadTime(DateUtils.addDayForNow(-1)).alarmSourceType("00").alarmLevel("2").alarmContentType("3").alarmContent("磁盘满载").build());
        alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.20").alarmUploadTime(DateUtils.addDayForNow(-2)).alarmSourceType("00").alarmLevel("1").alarmContentType("4").alarmContent("流量过高").build());
        alarmDataDomainService.acceptAlarmData(alarmInfoList);
    }

    @Test
    public void testRedis_1(){
        log.info(redisTemplate.hasKey("shiro_redis_session:efd54a66-f371-41dc-b63a-4242df4893dd").toString());
        redisTemplate.opsForValue().set("shiro_ redis_session:efd54a66-f371-41dc-b63a-4242df4893d1".toString(),"1");
    }


}
