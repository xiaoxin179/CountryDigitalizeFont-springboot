package com.xiaoxin.Country.service.impl;

import cn.hutool.core.lang.Dict;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoxin.Country.controller.domain.ImMessageDTO;
import com.xiaoxin.Country.entity.User;
import com.xiaoxin.Country.exception.ServiceException;
import com.xiaoxin.Country.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaoxin
 */
@ServerEndpoint(value = "/imserver/{userid}")
@Component
public class WebSocketServer {
    @Resource
    IUserService userService;
    private static IUserService staticUserService;
//    在程序启动的时候加载到内存中，就是在程序初始化的时候，就把他赋值成一个静态的，在遇到多线程的时候就不需要在bean中去拿
    @PostConstruct
    public void setStaticUser(){
        staticUserService = userService;
    }
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userid") String userid) {
        sessionMap.put(userid, session);
        log.info("有新用户加入，userid={}, 当前在线人数为：{}", userid, sessionMap.size());
        Dict dict = Dict.create().set("nums", sessionMap.size());
        sendAllMessage(JSONUtil.toJsonStr(dict));  // 后台发送消息给所有的客户端
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("userid") String userid) {
        sessionMap.remove(userid);
        log.info("有一连接关闭，userid={}的用户session, 当前在线人数为：{}", userid, sessionMap.size());
        Dict dict = Dict.create().set("nums", sessionMap.size());
        sendAllMessage(JSONUtil.toJsonStr(dict));  // 后台发送消息给所有的客户端
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userid") String userid) throws JsonProcessingException {
        log.info("服务端收到用户userid={}的消息:{}", userid, message);
//        处理数据库
//    添加创建时间
        if (staticUserService == null) {
            return;
        }
//        在这里直接使用userService的话会出现空指针的问题，因为消息通道都是多线程的，在多线程中是没有办法直接从bean来获取到userservice的
        User user = staticUserService.getOne(new QueryWrapper<User>().eq("userid", userid));
        if (user == null) {
            log.error("获取用户信息失败 userid={}", userid);
            return;
        }
        ImMessageDTO messageDTO = ImMessageDTO.builder().userid(userid).username(user.getName()).avatar(user.getAvatar()).sign(user.getSign()).createTime(new Date()).text(message).build();
        String jsonStr = JSONUtil.toJsonStr(messageDTO);//处理之后的消息体
        this.sendAllMessage(jsonStr);
        log.info("发送消息：{}", jsonStr);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 服务端发送消息给除了自己的其他客户端
     */
    private void sendMessage(Session fromSession, String message) {
        sessionMap.values().forEach(session -> {
            if (fromSession != session) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    log.error("服务端发送消息给客户端异常", e);
                }
            }
        });
    }

    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
}

