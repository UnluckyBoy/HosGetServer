package com.cloudestudio.hosgetserver.configs.Handler;

import com.cloudestudio.hosgetserver.model.Common.WebSocketSimpleInfo;
import com.cloudestudio.hosgetserver.model.Common.WebSocketUserInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Class SessionManager
 * @Author Create By Matrix·张
 * @Date 2026/1/31 下午11:38
 * SessionManager-会话管理类
 */
@Component
public class SessionManager {

    // 存储所有活跃会话
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    // 存储最后活动时间
    private final Map<String, LocalDateTime> lastActiveTimes = new ConcurrentHashMap<>();
    // 存储用户信息
    private final Map<String, WebSocketUserInfo> userInfos = new ConcurrentHashMap<>();

    /**
     * 添加会话
     */
    public void addSession(WebSocketSession session) {
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        lastActiveTimes.put(sessionId, LocalDateTime.now());

        // 创建默认用户信息
        WebSocketUserInfo userInfo = new WebSocketUserInfo();
        userInfo.setSessionId(sessionId);
        userInfo.setConnectTime(LocalDateTime.now());
        userInfos.put(sessionId, userInfo);

        System.out.println("添加会话: " + sessionId + "，当前会话数: " + sessions.size());
    }

    /**
     * 移除会话（完全清理）
     */
    public void removeSession(String sessionId) {
        // 1. 关闭连接
        closeSession(sessionId);

        // 2. 从所有集合中移除
        sessions.remove(sessionId);
        lastActiveTimes.remove(sessionId);
        userInfos.remove(sessionId);

        System.out.println("移除会话: " + sessionId + "，剩余会话数: " + sessions.size());
    }

    /**
     * 关闭会话连接
     */
    private void closeSession(String sessionId) {
        WebSocketSession session = sessions.get(sessionId);
        if (session != null && session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                System.err.println("关闭会话 " + sessionId + " 时出错: " + e.getMessage());
            }
        }
    }

    /**
     * 更新最后活动时间
     */
    public void updateLastActiveTime(String sessionId) {
        lastActiveTimes.put(sessionId, LocalDateTime.now());
    }

    /**
     * 更新用户信息
     */
    public void updateUserInfo(String sessionId, String nickname,String  role, String avatar) {
        WebSocketUserInfo userInfo = userInfos.getOrDefault(sessionId, new WebSocketUserInfo());
        userInfo.setSessionId(sessionId);
        if (nickname != null) {
            userInfo.setNickname(nickname);
        }
        if(role!=null){
            userInfo.setRole(role);
        }
        if (avatar != null) {
            userInfo.setAvatar(avatar);
        }
        userInfos.put(sessionId, userInfo);
    }

    /**
     * 获取会话
     */
    public WebSocketSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }

    /**
     * 检查会话是否存在
     */
    public boolean containsSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    /**
     * 获取所有会话
     */
    public Map<String, WebSocketSession> getAllSessions() {
        return new ConcurrentHashMap<>(sessions);
    }

    /**
     * 获取所有会话ID
     */
    public java.util.Set<String> getAllSessionIds() {
        return sessions.keySet();
    }

    /**
     * 获取会话数量
     */
    public int getSessionCount() {
        return sessions.size();
    }

    /**
     * 获取在线用户列表（转换为 WebSocketSimpleInfo）
     */
    public java.util.List<WebSocketSimpleInfo> getOnlineUsers() {
        java.util.List<WebSocketSimpleInfo> onlineUsers = new java.util.ArrayList<>();

        userInfos.forEach((sessionId, userInfo) -> {
            WebSocketSimpleInfo simpleInfo = new WebSocketSimpleInfo();
            simpleInfo.setSessionId(sessionId);
            simpleInfo.setNickname(userInfo.getNickname() != null ?
                    userInfo.getNickname() :
                    "用户" + sessionId.substring(0, Math.min(sessionId.length(), 6)));
            simpleInfo.setAvatar(userInfo.getAvatar());
            simpleInfo.setOnline(sessions.containsKey(sessionId) && sessions.get(sessionId).isOpen());
            //simpleInfo.setOnline(sessionExists(userInfo.getSessionId()));

            onlineUsers.add(simpleInfo);
        });

        return onlineUsers;
    }

    /**
     * 检查会话是否存在
     * @param sessionId
     * @return
     */
    private boolean sessionExists(String sessionId) {
        return sessions.containsKey(sessionId);
    }



    /**
     * 清理所有无效会话
     */
    public void cleanupInvalidSessions() {
        sessions.forEach((sessionId, session) -> {
            try {
                if (!session.isOpen()) {
                    removeSession(sessionId);
                    System.out.println("清理无效会话: " + sessionId);
                }
            } catch (Exception e) {
                // 如果检查时出错，也清理掉
                removeSession(sessionId);
                System.err.println("检查会话 " + sessionId + " 时出错，已清理: " + e.getMessage());
            }
        });
    }

    /**
     * 清理超时会话
     */
    public void cleanupTimeoutSessions(int timeoutSeconds) {
        LocalDateTime now = LocalDateTime.now();
        lastActiveTimes.forEach((sessionId, lastActiveTime) -> {
            if (lastActiveTime.plusSeconds(timeoutSeconds).isBefore(now)) {
                System.out.println("清理超时会话: " + sessionId +
                        "，最后活动时间: " + lastActiveTime);
                removeSession(sessionId);
            }
        });
    }

    public LocalDateTime getLastActiveTime(String sessionId) {
        return lastActiveTimes.get(sessionId);
    }
}