import com.matrictime.network.NetworkManagerApplication;
import com.matrictime.network.dao.domain.AlarmDataDomainService;
import com.matrictime.network.dao.mapper.extend.NmplAlarmInfoExtMapper;
import com.matrictime.network.model.AlarmInfo;
import com.matrictime.network.service.impl.AsyncService;
import com.matrictime.network.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @param
 * @author jiruyi
 * @title
 * @return
 * @description
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
    @Autowired
    private Executor taskExecutor;

    @Autowired
    private ApplicationContext ac;

    @Autowired
    private AsyncService asyncService;
    @Autowired
    private NmplAlarmInfoExtMapper alarmInfoExtMapper;

    @Test
    public void testRedis() {
        for (int j = 0; j < 500; j++) {
            List<AlarmInfo> alarmInfoList = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8342391251767685120").alarmSourceIp("192.168.72.103").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("01").alarmLevel("1").alarmContent("checksunm失败").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8342391251767685120").alarmSourceIp("192.168.72.103").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("01").alarmLevel("2").alarmContent("系统塌了").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8342391251767685120").alarmSourceIp("192.168.72.103").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("01").alarmLevel("3").alarmContent("系统塌了").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8343028466324107264").alarmSourceIp("192.168.72.20").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("01").alarmLevel("1").alarmContent("数据库断开").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8343031749797244928").alarmSourceIp("192.168.72.24").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("02").alarmLevel("2").alarmContent("线程中断").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8343031749797244928").alarmSourceIp("192.168.72.24").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("02").alarmLevel("3").alarmContent("系统异常").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8343031749797244928").alarmSourceIp("192.168.72.24").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("02").alarmLevel("1").alarmContent("系统塌了").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8343028679881289728").alarmSourceIp("192.168.72.23").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("11").alarmLevel("1").alarmContent("密钥告警").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8343028679881289728").alarmSourceIp("192.168.72.23").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("11").alarmLevel("2").alarmContent("系统塌了").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8343028679881289728").alarmSourceIp("192.168.72.23").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("11").alarmLevel("3").alarmContent("日志过大").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8343028679881289728").alarmSourceIp("192.168.72.23").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmContentType("5").alarmSourceType("11").alarmLevel("3").alarmContent("系统塌了").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8343028466324107264").alarmSourceIp("192.168.72.20").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmSourceType("01").alarmLevel("2").alarmContentType("5").alarmContent("系统崩溃").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceId("8343028466324107264").alarmSourceIp("192.168.72.20").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmSourceType("01").alarmLevel("1").alarmContentType("5").alarmContent("缓存失效").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmSourceType("00").alarmLevel("2").alarmContentType("1").alarmContent("cpu过高").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.21").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmSourceType("00").alarmLevel("1").alarmContentType("2").alarmContent("内存不足").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.20").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmSourceType("00").alarmLevel("2").alarmContentType("3").alarmContent("磁盘满载").build());
                alarmInfoList.add(AlarmInfo.builder().alarmSourceIp("192.168.72.20").alarmUploadTime(DateUtils.addDayForNow(-i)).alarmSourceType("00").alarmLevel("1").alarmContentType("4").alarmContent("流量过高").build());
            }
            alarmInfoExtMapper.batchInsert(alarmInfoList);
        }
    }

    @Test
    public void testRedis_1() {
        log.info(redisTemplate.hasKey("shiro_redis_session:efd54a66-f371-41dc-b63a-4242df4893dd").toString());
        redisTemplate.opsForValue().set("shiro_ redis_session:efd54a66-f371-41dc-b63a-4242df4893d1".toString(), "1");
    }


}
