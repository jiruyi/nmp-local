package com.matrictime.network.service.impl;

import com.matrictime.network.base.SystemBaseService;
import com.matrictime.network.base.SystemException;
import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.base.enums.LoginTypeEnum;
import com.matrictime.network.base.exception.ErrorMessageContants;
import com.matrictime.network.base.util.AesEncryptUtil;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.convert.UserConvert;
import com.matrictime.network.dao.domain.RoleDomainService;
import com.matrictime.network.dao.domain.UserDomainService;
import com.matrictime.network.dao.mapper.NmplRoleMapper;
import com.matrictime.network.dao.model.NmplLoginDetail;
import com.matrictime.network.dao.model.NmplRole;
import com.matrictime.network.dao.model.NmplUser;
import com.matrictime.network.model.Result;
import com.matrictime.network.request.LoginRequest;
import com.matrictime.network.request.UserInfo;
import com.matrictime.network.request.UserRequest;
import com.matrictime.network.response.LoginResponse;
import com.matrictime.network.response.PageInfo;
import com.matrictime.network.response.UserInfoResp;
import com.matrictime.network.service.UserService;
import com.matrictime.network.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2022/2/24 0024 10:22
 * @desc
 */
@Service
@Slf4j
public class UserServiceImpl  extends SystemBaseService implements UserService {

    @Autowired
    private UserDomainService userDomainService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserConvert userConvert;

    @Autowired
    private NmplRoleMapper roleMapper;

    @Override
    public Result<LoginResponse> login(LoginRequest loginRequest) {
        Result<LoginResponse> result = null;
        List<NmplUser> list = new ArrayList<>();
        try {
            list =  userDomainService.getUserByParamter(loginRequest);
            /**1.0用户名密码登录*/
            if (LoginTypeEnum.PASSWORD.getCode().equals(loginRequest.getType())){
                /**2.0 检查账户状态*/
                if(redisTemplate.hasKey(loginRequest.getLoginAccount()+ DataConstants.USER_ACCOUNT_LOCK_PREFIX)){
                    throw new SystemException(ErrorMessageContants.PASSWORD_ERROR_LIMIT_MSG);
                }
                /**3.0密码登录校验*/
                checkPassword(list,loginRequest);
            }
            /**4. 短信验证码登录**/
            if (LoginTypeEnum.MOBILEPHONE.getCode().equals(loginRequest.getType())){
                checkMobileCode(loginRequest,list);
            }
            /**5 生成token放入redis**/


            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(list.get(NumberUtils.INTEGER_ZERO).getNickName(),
                    list.get(NumberUtils.INTEGER_ZERO).getPassword());
            subject.login(usernamePasswordToken);

            String token = buildToken(list.get(NumberUtils.INTEGER_ZERO));
            LoginResponse response = LoginResponse.builder().token(token).build();
            result = buildResult(response);
        }catch (Exception e){
            log.error("用户名{}登录异常：{}",loginRequest.getLoginAccount(),e.getMessage());
            result = failResult(e);
        }
        /**6.0 插入登录明细*/
        insetLoginDetail(loginRequest,list,result);
        return result;


    }

    /**
     * @title insertUser
     * @param [userRequest]
     * @return com.matrictime.network.model.Result<java.lang.Integer>
     * @description  插入用户
     * @author jiruyi
     * @create 2022/2/28 0028 11:08
     */
    @Override
    public Result<Integer> insertUser(UserRequest userRequest) {
        Result<Integer> result = null;
        try {
            //1.根据手机号码查询
            List<NmplUser> listUser = userDomainService.getUserByPhone(userRequest.getPhoneNumber());
            if(!CollectionUtils.isEmpty(listUser)){
                throw new SystemException(ErrorMessageContants.PHONE_EXIST_ERROR_MSG);
            }
            listUser = userDomainService.getUserByLoginAccount(userRequest.getLoginAccount());
            if(!CollectionUtils.isEmpty(listUser)){
                throw new SystemException(ErrorMessageContants.LOGINACCOUNT_EXIST_ERROR_MSG);
            }
            userRequest.setCreateUser(String.valueOf(RequestContext.getUser().getUserId()));
            int count = userDomainService.insertUser(userRequest);
            result = buildResult(count);
        }catch (Exception e){
            log.error("用户{}创建异常：{}",userRequest,e.getMessage());
            result = failResult(e);
        }

        return result;
    }

    /**
     * @title updateUser
     * @param [userRequest]
     * @return com.matrictime.network.model.Result<java.lang.Integer>
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 15:01
     */
    @Override
    public Result<Integer> updateUser(UserRequest userRequest) {
        Result<Integer> result = null;
        try {
            //1.根据手机号码查询
            List<NmplUser> listUser = userDomainService.getUserByPhone(userRequest.getPhoneNumber());
            if(!CollectionUtils.isEmpty(listUser) &&
                    !userRequest.getUserId().equals(listUser.get(0).getUserId().toString())){
                throw new SystemException(ErrorMessageContants.PHONE_EXIST_ERROR_MSG);
            }
            userRequest.setUpdateUser(String.valueOf(RequestContext.getUser().getUserId()));
            int count = userDomainService.updateUser(userRequest);
            result = buildResult(count);
        }catch (Exception e){
            log.error("用户{}创建异常：{}",userRequest,e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    /**
     * @title deleteUser
     * @param [userRequest]
     * @return com.matrictime.network.model.Result<java.lang.Integer>
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 16:01
     */
    @Override
    public Result<Integer> deleteUser(UserRequest userRequest) {
        Result<Integer> result = null;
        try {
            int count = userDomainService.deleteUser(userRequest);
            result = buildResult(count);
        }catch (Exception e){
            log.error("用户{}创建异常：{}",userRequest,e.getMessage());
            result = failResult(e);
        }
        return result;
    }

    /**
     * @title selectUserList
     * @param [userRequest]
     * @return com.matrictime.network.model.Result<java.lang.Integer>
     * @description
     * @author jiruyi
     * @create 2022/2/28 0028 16:46
     */
    @Override
    public Result<PageInfo> selectUserList(UserRequest userRequest) {
        try {
            PageInfo pageInfo = userDomainService.selectUserList(userRequest);
            //参数转换
            List<UserRequest> list =  userConvert.to(pageInfo.getList());
            //角色转换
            if(!CollectionUtils.isEmpty(list)){
                for(UserRequest user: list){
                    String roleId = user.getRoleId();
                    List<String> roleIds = Arrays.asList(roleId.split(","));
                    String roleName = "";
                    for(String  id : roleIds){
                        NmplRole role= roleMapper.selectByPrimaryKey(Long.valueOf(id));
                        roleName+=role.getRoleName()+",";
                    }
                    user.setRoleName(roleName);
                }
            }
            pageInfo.setList(list);
            return buildResult(pageInfo);
        }catch (Exception e){
            log.error("selectUserList exception :{}",e.getMessage());
            return  failResult(e);
        }
    }

    /**
     * @title passwordReset
     * @param [userInfo]
     * @return com.matrictime.network.model.Result<java.lang.Integer>
     * @description 密码重置
     * @author jiruyi
     * @create 2022/3/2 0002 9:32
     */
    @Override
    public Result<Integer> passwordReset(UserInfo userInfo) {
        try {
            //个人信息密码修改 查询老密码
            if("1".equals(userInfo.getType())){
                NmplUser nmplUser = userDomainService.getUserById(Long.valueOf(userInfo.getUserId()));
                if(ObjectUtils.isEmpty(nmplUser)){
                    throw new SystemException(ErrorMessageContants.USER_NO_EXIST_ERROR_MSG);
                }
                if(!userInfo.getOldPassword().equals(nmplUser.getPassword())){
                    throw new SystemException(ErrorMessageContants.OLD_PASSWORD_ERROR_MSG);
                }
            }
            int count = userDomainService.passwordReset(userInfo);
            return  buildResult(count);
        }catch (Exception e){
            log.error("selectUserList exception :{}",e.getMessage());
            return  failResult(e);
        }
    }

    /**
     * @title checkPassword
     * @param [list, loginRequest]
     * @return boolean
     * @description  5分钟连续密码错误三次 账号锁定10分钟
     * @author jiruyi
     * @create 2022/2/24 0024 14:18
     */
    public boolean checkPassword(List<NmplUser> list,LoginRequest loginRequest){
        /**1.0用户名不存在*/
        if(CollectionUtils.isEmpty(list)){
            log.info("登录账号{}不存在",loginRequest.getLoginAccount());
            throw new SystemException(ErrorMessageContants.USERNAME_NO_EXIST_MSG);
        }
        NmplUser networkUser = list.get(NumberUtils.INTEGER_ZERO);
        if (ObjectUtils.isEmpty(networkUser)){
            throw new SystemException(ErrorMessageContants.USERNAME_NO_EXIST_MSG);
        }
        /**2.0 密码比较*/
        if(loginRequest.getPassword().equals(networkUser.getPassword())){
            //删除锁定状态
            if(redisTemplate.hasKey(networkUser.getLoginAccount()+DataConstants.PASSWORD_ERROR_PREFIX)){
                redisTemplate.delete(networkUser.getLoginAccount()+DataConstants.PASSWORD_ERROR_PREFIX);
            }
            return true;
        }
        log.info("用户id{}密码错误",loginRequest.getLoginAccount());
        if(redisTemplate.hasKey(networkUser.getLoginAccount()+DataConstants.PASSWORD_ERROR_PREFIX)){
            /**3.0 错误加1*/
            redisTemplate.opsForValue().increment(networkUser.getLoginAccount()+DataConstants.PASSWORD_ERROR_PREFIX);
            /**3.1 三次锁定*/
            if(DataConstants.PASSWORD_ERROR_NUM <= (Integer) redisTemplate.opsForValue()
                    .get(networkUser.getLoginAccount() + DataConstants.PASSWORD_ERROR_PREFIX)){
                redisTemplate.opsForValue().set(networkUser.getLoginAccount()+DataConstants.USER_ACCOUNT_LOCK_PREFIX,
                        true,10, TimeUnit.MINUTES);
                log.info("用户id{}密码错误三次锁定10分钟",networkUser.getLoginAccount());
                throw new SystemException(ErrorMessageContants.PASSWORD_ERROR_LIMIT_MSG);
            }
        }else{
            /**4.0 初始化参数*/
            redisTemplate.opsForValue().set(networkUser.getLoginAccount()+DataConstants.PASSWORD_ERROR_PREFIX,
                    NumberUtils.INTEGER_ONE,300,TimeUnit.SECONDS);
        }
        throw new SystemException(ErrorMessageContants.USERNAME_OR_PASSWORD_ERROR_MSG);
    }


    /**
     * @title checkMobileCode
     * @param [loginRequest, list]
     * @return boolean
     * @description  手机短信登录
     * @author jiruyi
     * @create 2022/2/24 0024 14:24
     */
    public boolean checkMobileCode(LoginRequest loginRequest, List<NmplUser> list){
        // 检验手机号合法性
        if(CollectionUtils.isEmpty(list)){
            throw new SystemException(ErrorMessageContants.PHONE_NO_EXIST_MSG);
        }
        //检验短信验证码
        Object smsCode = redisTemplate.opsForValue().get(DataConstants.USER_LOGIN_SMS_CODE+loginRequest.getPhone());
        log.info("用户：loginRequest.getPhone()+DataConstants.USER_LOGIN_SMS_CODE的smsCode:{}",smsCode);
        //验证码已过期
        if(ObjectUtils.isEmpty(smsCode)){
            throw new SystemException(ErrorMessageContants.SMS_CODE_NO_EXIST_MSG);
        }
        loginRequest.setSmsCode(AesEncryptUtil.aesDecrypt(loginRequest.getSmsCode()));
        //验证码不正确
        if(!loginRequest.getSmsCode().equals(smsCode.toString())){
            throw new SystemException(ErrorMessageContants.SMS_CODE_ERROR_MSG);
        }
        //删除验证码
        redisTemplate.delete(DataConstants.USER_LOGIN_SMS_CODE+loginRequest.getPhone());
        return true;
    }


    /**
     * @title buildToken
     * @param [user]
     * @return java.lang.String
     * @description
     * @author jiruyi
     * @create 2022/2/24 0024 14:31
     */
    public String buildToken(NmplUser user){
        String token = JwtUtils.createToken(user);
        redisTemplate.opsForValue().set(user.getUserId()+DataConstants.USER_LOGIN_JWT_TOKEN,token,1, TimeUnit.DAYS);
        return token;
    }


    /**
     * @title insetLoginDetail
     * @param [loginRequest, list, result]
     * @return void
     * @description
     * @author jiruyi
     * @create 2022/2/25 0025 9:32
     */
    public void insetLoginDetail(LoginRequest loginRequest, List<NmplUser> list, Result<LoginResponse> result){
        try {
            String ip = ObjectUtils.isEmpty(request.getHeader("X-Real-IP"))
                    ?request.getRemoteAddr():request.getHeader("X-Real-IP");
            NmplLoginDetail loginDetail = NmplLoginDetail.builder()
                    .loginIp(ip)
                    .loginAddr(loginRequest.getLoginAddr())
                    .loginType(Byte.valueOf(loginRequest.getType()))
                    .isSuccess(result.isSuccess())
                    .failCause(result.getErrorMsg())
                    .loginAccount(loginRequest.getLoginAccount()).build();
            //补充字段
            if(!CollectionUtils.isEmpty(list) && !ObjectUtils.isEmpty(list.get(NumberUtils.INTEGER_ZERO))){
                loginDetail.setNickName(list.get(NumberUtils.INTEGER_ZERO).getNickName());
                loginDetail.setLoginAccount(list.get(NumberUtils.INTEGER_ZERO).getLoginAccount());
            }
            if(ObjectUtils.isEmpty(loginDetail.getLoginAccount())){
                loginDetail.setLoginAccount(loginRequest.getPhone());
            }
            int n = userDomainService.insertLoginDetail(loginDetail);
            log.info("insetLoginDetail result num:{}",n);
        }catch (Exception e){
            log.error("insetLoginDetail exception:{}",e.getMessage());
        }

    }


    @Override
    public Result<UserInfoResp> getUserInfo(UserRequest userRequest) {
        try {
            NmplUser user = userDomainService.getUserById(Long.valueOf(userRequest.getUserId()));
            //参数转换
            UserInfoResp userInfoResp = new UserInfoResp();
            BeanUtils.copyProperties(user,userInfoResp);
            NmplRole role = roleMapper.selectByPrimaryKey(Long.valueOf(user.getRoleId()));
            userInfoResp.setRoleName(role.getRoleName());
            return buildResult(userInfoResp);
        }catch (Exception e){
            log.error("selectUserList exception :{}",e.getMessage());
            return  failResult(e);
        }
    }
}
