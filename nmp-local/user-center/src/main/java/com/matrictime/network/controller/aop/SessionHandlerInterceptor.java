package com.matrictime.network.controller.aop;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.matrictime.network.base.RequestContext;
import com.matrictime.network.base.util.JwtUtils;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.UserMapper;
import com.matrictime.network.dao.model.User;
import com.matrictime.network.dao.model.UserExample;
import com.matrictime.network.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

import static com.matrictime.network.constant.DataConstants.*;
import static com.matrictime.network.exception.ErrorMessageContants.TOKEN_ILLEGAL_MSG;
import static com.matrictime.network.exception.ErrorMessageContants.TOKEN_INVALID_MSG;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project microservicecloud-jzsg
 * @date 2021/9/7 0007 11:31
 * @desc
 */
@Slf4j
@Component
public class SessionHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired(required = false)
    private UserMapper userMapper ;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestContext.setRequestStartTimestamp();
        RequestContext.setRequest(request);
        if (StringUtils.isEmpty(request.getHeader(REQUESET_HEADER_DESTINATION))){
            //1 获取token
            String token = request.getHeader(REQUESET_HEADER_TOKEN);
            String dest = request.getHeader(REQUESET_HEADER_DEST);
            if(StringUtils.isEmpty(token) || StringUtils.isEmpty(dest)){
                log.info("请求:{}的token:{}/dest:{}为空,访问被拒绝!",request.getRequestURI(),token,dest);
                response.setStatus(SC_OK);
                Result result = new Result<Object>(false,null,String.valueOf(SC_FORBIDDEN),TOKEN_ILLEGAL_MSG);
                responseJson(response,result);
                return false;
            }
            //2 解析token
            if(ObjectUtils.isEmpty(JwtUtils.getClaimByName(token,"userId"))){
                response.setStatus(SC_OK);
                Result result = new Result<Object>(false,null,String.valueOf(SC_FORBIDDEN),TOKEN_ILLEGAL_MSG);
                responseJson(response,result);
                return false;
            }
            String userId = JwtUtils.getClaimByName(token,"userId").asString();
            //3 查询用户信息
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUserIdEqualTo(userId).andIsExistEqualTo(DataConstants.IS_EXIST);
            List<User> users = userMapper.selectByExample(userExample);
            if (CollectionUtils.isEmpty(users)){
                response.setStatus(SC_OK);
                Result result = new Result<Object>(false,null,String.valueOf(SC_FORBIDDEN),TOKEN_ILLEGAL_MSG);
                responseJson(response,result);
                return false;
            }
            //4. 查看用户token是否失效
            StringBuffer sb = new StringBuffer(SYSTEM_UC);
            sb.append(USER_LOGIN_JWT_TOKEN).append(KEY_SPLIT_UNDERLINE).append(userId).append(KEY_SPLIT_UNDERLINE).append(dest);
            Object redisToken  = redisTemplate.opsForValue().get(sb.toString());
            if(ObjectUtils.isEmpty(redisToken) || !token.equals(redisToken.toString())){
                response.setStatus(SC_OK);
                Result result = new Result<Object>(false,null,String.valueOf(SC_FORBIDDEN),TOKEN_INVALID_MSG);
                responseJson(response,result);
                return false;
            }

            RequestContext.setUser(users.get(NumberUtils.INTEGER_ZERO));
        }
        return true;
    }

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        RequestContext.clean();
    }

    /**
     * 返回JSON数据
     * @param response
     * @param obj
     * @throws Exception
     */
    public static void responseJson(HttpServletResponse response, Object obj) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat));
        writer.close();
        response.flushBuffer();
    }
}
