//package com.matrictime.network.service.impl;
//
//import com.matrictime.network.base.SystemBaseService;
//import com.matrictime.network.base.constant.DataConstants;
//import com.matrictime.network.base.exception.ErrorMessageContants;
//import com.matrictime.network.context.RequestContext;
//import com.matrictime.network.dao.mapper.NmpUserMapper;
//import com.matrictime.network.dao.model.NmpUser;
//import com.matrictime.network.dao.model.NmpUserExample;
//import com.matrictime.network.exception.SystemException;
//import com.matrictime.network.model.Result;
//
//import com.matrictime.network.req.UserReq;
//import com.matrictime.network.resp.LoginResp;
//import com.matrictime.network.service.UserService;
//import com.matrictime.network.util.JwtUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.math.NumberUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import static com.matrictime.network.base.exception.ErrorMessageContants.PARAM_IS_NULL_MSG;
//
//@Service
//@Slf4j
//public class UserServiceImpl extends SystemBaseService implements UserService {
//
//    @Resource
//    private NmpUserMapper nmpUserMapper;
//    @Resource
//    private RedisTemplate redisTemplate;
//
//    @Value("${token.timeOut}")
//    private Integer timeOut;
//
//    @Override
//    public Result login(UserReq req) {
//        Result result;
//        try {
//            // 1、校验参数
//            if(req.getLoginAccount().isEmpty()||req.getPassword().isEmpty()){
//                throw new SystemException(PARAM_IS_NULL_MSG);
//            }
//
//            // 2、校验密码是否一致 密码输入失败三次锁定账户
//            NmpUserExample userExample = new NmpUserExample();
//            userExample.createCriteria().andLoginAccountEqualTo(req.getLoginAccount()).andIsExistEqualTo(true);
//            List<NmpUser> list = nmpUserMapper.selectByExample(userExample);
//            checkPassword(list,req);
//
//            // 3、校验通过生成token
//            String token = buildToken(list.get(NumberUtils.INTEGER_ZERO));
//
//            LoginResp response = LoginResp.builder().token(token).build();
//            result = buildResult(response);
//
//        }catch (SystemException e){
//            log.info("用户登录异常：",e.getMessage());
//            result = failResult(e);
//        }catch (Exception e){
//            log.error("用户登录异常：",e.getMessage());
//            result = failResult("");
//        }
//        return result;
//    }
//
//    @Override
//    public Result loginOut() {
//        redisTemplate.delete(DataConstants.SECURITY_SERVER + RequestContext.getUser().getUserId()+DataConstants.USER_LOGIN_JWT_TOKEN);
//        log.info("用户:{}退出系统", RequestContext.getUser().getUserId());
//        return new Result(true, ErrorMessageContants.LOGOUT_SUCCESS_MSG);
//    }
//
//
//
//    private void checkPassword(List<NmpUser> list, UserReq req){
//        /**1.0用户名不存在*/
//        if(CollectionUtils.isEmpty(list)){
//            log.info("登录账号{}不存在",req.getLoginAccount());
//            throw new SystemException(ErrorMessageContants.USERNAME_NO_EXIST_MSG);
//        }
//        NmpUser user = list.get(NumberUtils.INTEGER_ZERO);
//        /**2.0 密码比较*/
//        if(req.getPassword().equals(user.getPassword())){
//            //删除锁定状态
//            if(redisTemplate.hasKey(DataConstants.SECURITY_SERVER + user.getLoginAccount()+ DataConstants.PASSWORD_ERROR_PREFIX)){
//                redisTemplate.delete(DataConstants.SECURITY_SERVER + user.getLoginAccount()+DataConstants.PASSWORD_ERROR_PREFIX);
//            }
//        }else {
//            log.info("用户id{}密码错误",req.getLoginAccount());
//            if(redisTemplate.hasKey(DataConstants.SECURITY_SERVER + user.getLoginAccount()+ DataConstants.PASSWORD_ERROR_PREFIX)){
//                /**3.0 错误加1*/
//                redisTemplate.opsForValue().increment(DataConstants.SECURITY_SERVER + user.getLoginAccount()+ DataConstants.PASSWORD_ERROR_PREFIX);
//                /**3.1 三次锁定*/
//                if(DataConstants.PASSWORD_ERROR_NUM <= (Integer) redisTemplate.opsForValue()
//                        .get(DataConstants.SECURITY_SERVER + user.getLoginAccount()+ DataConstants.PASSWORD_ERROR_PREFIX)){
//                    redisTemplate.opsForValue().set(DataConstants.SECURITY_SERVER + user.getLoginAccount()+DataConstants.USER_ACCOUNT_LOCK_PREFIX,
//                            true,10, TimeUnit.MINUTES);
//                    log.info("用户id{}密码错误三次锁定10分钟",user.getLoginAccount());
//                    throw new SystemException(ErrorMessageContants.PASSWORD_ERROR_LIMIT_MSG);
//                }
//            }else{
//                /**4.0 初始化参数*/
//                redisTemplate.opsForValue().set(DataConstants.SECURITY_SERVER+ user.getLoginAccount()+DataConstants.PASSWORD_ERROR_PREFIX,
//                        NumberUtils.INTEGER_ONE,300,TimeUnit.SECONDS);
//            }
//            throw new SystemException(ErrorMessageContants.USERNAME_OR_PASSWORD_ERROR_MSG);
//        }
//    }
//
//    private String buildToken(NmpUser user){
//        String token = JwtUtils.createToken(user);
//        redisTemplate.opsForValue().set(DataConstants.SECURITY_SERVER +user.getUserId()+DataConstants.USER_LOGIN_JWT_TOKEN,token,timeOut, TimeUnit.HOURS);
//        return token;
//    }
//}
