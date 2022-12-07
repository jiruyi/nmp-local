package com.matrictime.network.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.matrictime.network.api.request.LogoutReq;
import com.matrictime.network.base.UcConstants;
import com.matrictime.network.service.LoginService;
import com.matrictime.network.util.HttpClientUtil;
import com.matrictime.network.util.ParamCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.matrictime.network.base.UcConstants.LOGOUT_MSG;
import static com.matrictime.network.base.UcConstants.REDIS_LOGIN_KEY;
import static com.matrictime.network.constant.DataConstants.KEY_SPLIT_UNDERLINE;

/**
 * websocket的处理类。
 * 作用相当于HTTP请求
 * 中的controller
 */
@Component
@Slf4j
@ServerEndpoint("/webSocket/{account}")
public class WebSocketServer {

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收用户id*/
    private String account = "";

    public static ConcurrentHashMap<String,WebSocketServer> getWebSocketMap() {
        return webSocketMap;
    }

    public static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context){
        applicationContext = context;
    }

    /**
     * 连接建立成
     * 功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("account") String account) {
        try {
            account = URLDecoder.decode(account,"Utf-8");
        } catch (UnsupportedEncodingException e) {
            log.warn("websocket.onOpen Exception:{}",e.getMessage());
        }
        this.session = session;
        this.account = account;

        RedissonClient redisson = applicationContext.getBean(RedissonClient.class);
        RLock rLock = redisson.getLock(REDIS_LOGIN_KEY+account);
        log.info("-----get onOpen lock object-----："+rLock);

        boolean tryLock = false;
        try {
            tryLock = rLock.tryLock(10, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.warn("get onOpen lock exception");
            e.printStackTrace();
        }

        if (!tryLock) {
            log.info("get onOpen lock failed");
        }

        try {
            if(webSocketMap.containsKey(account)){
                WebSocketServer webSocketServer = webSocketMap.get(account);
                webSocketServer.sendMessage(LOGOUT_MSG);
                serverClose(webSocketServer.session);

                LoginService loginService = applicationContext.getBean(LoginService.class);
                LogoutReq req = new LogoutReq();
                req.setLoginAccount(account);
                loginService.syslogoutWithOutToken(req);
                webSocketMap.remove(account);

                //加入set中
                webSocketMap.put(account,this);
                addOnlineCount();
            }else{
                //加入set中
                webSocketMap.put(account,this);
                //在线数加1
                addOnlineCount();
            }
            log.info("连接:"+account+",当前在线用户数为:" + getOnlineCount());
            for (String key : webSocketMap.keySet()) {
                log.info("当前在线用户:"+key);
            }
        }catch (Exception e){
            log.info("onOpen Exception:"+e.getMessage());
            e.printStackTrace();
        }finally {
            if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        }

    }

    /**
     * 连接关闭
     * 调用的方法
     */
    @OnClose
    public void onClose() {
        RedissonClient redisson = applicationContext.getBean(RedissonClient.class);
        RLock rLock = redisson.getLock(REDIS_LOGIN_KEY+account);
        log.info("-----get onClose lock object-----："+rLock);

        boolean tryLock = false;
        try {
            tryLock = rLock.tryLock(10, 10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.warn("get onClose lock exception");
            e.printStackTrace();
        }

        if (!tryLock) {
            log.info("get onClose lock failed");
        }

        try {
            if(webSocketMap.containsKey(account)){
                webSocketMap.remove(account);
                //从set中删除
                subOnlineCount();
                if (!ParamCheckUtil.checkVoStrBlank(account)){
                    try {
                        log.info("websocket系统退出信息:{}",account);
                        LoginService loginService = applicationContext.getBean(LoginService.class);
                        LogoutReq req = new LogoutReq();
                        req.setLoginAccount(account);
                        loginService.syslogoutWithOutToken(req);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            log.info("用户退出:"+account+",当前在线用户数为:" + getOnlineCount());
            for (String key : webSocketMap.keySet()) {
                log.info("当前在线用户:"+key);
            }
        }catch (Exception e){
            log.info("onClose Exception:"+e.getMessage());
            e.printStackTrace();
        }finally {
            if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        }

    }

    /**
     * 收到客户端消
     * 息后调用的方法
     * @param message
     * 客户端发送过来的消息
     **/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:"+account+",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
        if(StringUtils.isNotBlank(message)){
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("fromUserId",this.account);
                String toUserId=jsonObject.getString("toUserId");
                //传送给对应toUserId用户的websocket
                if(StringUtils.isNotBlank(toUserId)&&webSocketMap.containsKey(toUserId)){
                    webSocketMap.get(toUserId).sendMessage(message);
                }else{
                    //否则不在这个服务器上，发送到mysql或者redis
                    log.error("请求的userId:"+toUserId+"不在该服务器上");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {

        log.error("用户错误:"+this.account+",原因:"+error.getMessage());
        error.printStackTrace();
    }


    /**
     * 实现服务
     * 器主动推送
     */
    public void sendMessage(String message) {
        try {
            log.info("websocket推送消息:"+message);
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverClose(){
        if (this.session.isOpen()){
            try {
                CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,"鉴权失败！");
                session.close(closeReason);

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public void serverClose(Session session){
        if (session.isOpen()){
            try {
                CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,"鉴权失败！");
                session.close(closeReason);

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }



    /**
     * 获得此时的
     * 用户数
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 在线用户数加1
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 在线用户数减1
     */
    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
