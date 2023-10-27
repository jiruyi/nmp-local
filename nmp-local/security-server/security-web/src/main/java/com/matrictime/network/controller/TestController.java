//package com.matrictime.network.controller;
//
//import com.matrictime.network.base.util.DateUtils;
//import com.matrictime.network.controller.aop.MonitorRequest;
//import com.matrictime.network.dao.mapper.NmpKeyInfoMapper;
//import com.matrictime.network.dao.mapper.extend.KeyInfoMapper;
//import com.matrictime.network.dao.model.NmpKeyInfo;
//import com.matrictime.network.exception.ErrorMessageContants;
//import com.matrictime.network.model.Result;
//import com.matrictime.network.request.UpdEncryptConfReq;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Random;
//
//import static com.matrictime.network.base.constant.DataConstants.*;
//
//@RequestMapping(value = "/test")
//@RestController
//@Slf4j
//public class TestController {
//
//    @Resource
//    private NmpKeyInfoMapper nmpKeyInfoMapper;
//
//    @MonitorRequest
//    @RequestMapping(value = "/addKeyInfo",method = RequestMethod.POST)
//    public Result addKeyInfo(){
//        try {
//            Result result = new Result<>();
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
//                    0, 0, 0);
//            for (int i=0;i<1440;i++){
//                Random random = new Random();
//                calendar.add(Calendar.MINUTE,1);
//                NmpKeyInfo keyInfo = new NmpKeyInfo();
//                keyInfo.setDataValue(Long.valueOf(random.nextInt(10)));
//                keyInfo.setDataType(USED_DOWN_DATA_VALUE);
//                keyInfo.setCreateTime(calendar.getTime());
//                keyInfo.setUpdateTime(calendar.getTime());
//                nmpKeyInfoMapper.insertSelective(keyInfo);
//                NmpKeyInfo keyInfo1 = new NmpKeyInfo();
//                keyInfo1.setDataValue(Long.valueOf(random.nextInt(10)));
//                keyInfo1.setDataType(USED_UP_DATA_VALUE);
//                keyInfo1.setCreateTime(calendar.getTime());
//                keyInfo1.setUpdateTime(calendar.getTime());
//                nmpKeyInfoMapper.insertSelective(keyInfo1);
//                NmpKeyInfo keyInfo2 = new NmpKeyInfo();
//                keyInfo2.setDataValue(Long.valueOf(random.nextInt(10)));
//                keyInfo2.setDataType(LAST_DOWN_DATA_VALUE);
//                keyInfo2.setCreateTime(calendar.getTime());
//                keyInfo2.setUpdateTime(calendar.getTime());
//                nmpKeyInfoMapper.insertSelective(keyInfo2);
//                NmpKeyInfo keyInfo3 = new NmpKeyInfo();
//                keyInfo3.setDataValue(Long.valueOf(random.nextInt(10)));
//                keyInfo3.setDataType(LAST_UP_DATA_VALUE);
//                keyInfo3.setCreateTime(calendar.getTime());
//                keyInfo3.setUpdateTime(calendar.getTime());
//                nmpKeyInfoMapper.insertSelective(keyInfo3);
//            }
//            return result;
//        }catch (Exception e){
//            log.error("EncryptController.updEncryptConf exception:{}",e.getMessage());
//            return new Result(false, ErrorMessageContants.SYSTEM_ERROR_MSG);
//        }
//    }
//
//    public static void main(String[] args) {
////        Calendar calendar = Calendar.getInstance();
////        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
////                0, 0, 0);
////        for (int i=0;i<100;i++){
////            Random random = new Random();
////            calendar.add(Calendar.MINUTE,1);
////            System.out.println("随机数:" + Long.valueOf(random.nextInt(1000000000)));
////            System.out.println("时间" + DateUtils.formatDateToString(calendar.getTime()));
////        }
//    }
//}
