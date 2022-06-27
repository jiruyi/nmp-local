package com.matrictime.network.controller.aop;

import com.matrictime.network.base.RequestContext;
import com.matrictime.network.base.util.JwtUtils;
import com.matrictime.network.constant.DataConstants;
import com.matrictime.network.dao.mapper.UserMapper;
import com.matrictime.network.dao.model.User;
import com.matrictime.network.dao.model.UserExample;
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
import java.util.List;

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
        //1 获取token
        String token = request.getHeader(DataConstants.REQUESET_HEADER_TOKEN);
        if(StringUtils.isEmpty(token)){
            log.info("请求:{}的token为空,访问被拒绝!",request.getRequestURI());
            response.setStatus(403);
            return false;
        }
        //2 解析token
        if(ObjectUtils.isEmpty(JwtUtils.getClaimByName(token,"userId"))){
            response.setStatus(403);
            return false;
        }
        String userId = JwtUtils.getClaimByName(token,"userId").asString();
        //3 查询用户信息
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUserIdEqualTo(userId).andIsExistEqualTo(DataConstants.IS_EXIST);
        List<User> users = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(users)){
            response.setStatus(403);
            return false;
        }
        //4. 查看用户token是否失效
        Object redisToken  = redisTemplate.opsForValue().get(userId+ DataConstants.USER_LOGIN_JWT_TOKEN);
        if(ObjectUtils.isEmpty(redisToken) || !token.equals(redisToken.toString())){
            response.setStatus(403);
            response.setCharacterEncoding("Utf-8");
            response.getWriter().print("登录已经失效，请重新登录");
            return false;
        }

        RequestContext.setUser(users.get(NumberUtils.INTEGER_ZERO));
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
}
