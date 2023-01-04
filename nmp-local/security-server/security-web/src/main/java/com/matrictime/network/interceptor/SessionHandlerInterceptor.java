package com.matrictime.network.interceptor;


import com.matrictime.network.base.constant.DataConstants;
import com.matrictime.network.context.RequestContext;
import com.matrictime.network.dao.mapper.NmpUserMapper;
import com.matrictime.network.dao.model.NmpUser;
import com.matrictime.network.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class SessionHandlerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private NmpUserMapper nmpUserMapper;

    @Autowired
    private RedisTemplate redisTemplate;


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
        NmpUser user = nmpUserMapper.selectByPrimaryKey(Long.valueOf(userId));
        if (ObjectUtils.isEmpty(user)){
            response.setStatus(403);
            return false;
        }
        //4. 查看用户token是否失效
        Object redisToken  = redisTemplate.opsForValue().get(DataConstants.SECURITY_SERVER +userId+DataConstants.USER_LOGIN_JWT_TOKEN);
        if(ObjectUtils.isEmpty(redisToken) || !token.equals(redisToken.toString())){
            response.setStatus(403);
            response.setCharacterEncoding("Utf-8");
            response.getWriter().print("登录已经失效，请重新登录");
            return false;
        }
        RequestContext.setUser(user);
        return true;
    }

    /**
     * @title afterCompletion
     * @param
     * @return void
     * @description
     * @author jiruyi
     * @create 2021/9/7 0007 14:46
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        RequestContext.clean();
    }
}
