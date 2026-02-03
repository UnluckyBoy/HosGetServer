package com.cloudestudio.hosgetserver.configs.Handler;

import com.cloudestudio.hosgetserver.model.Common.WebSocketSimpleInfo;
import com.cloudestudio.hosgetserver.model.Common.WebSocketUserInfo;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
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
        // 关闭连接
        closeSession(sessionId);

        // 从所有集合中移除
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
    public void updateUserInfo(String sessionId,String nickname,String account,String role,String avatar) {
        // 先检查是否有相同昵称但不同会话的情况
//        if (nickname != null) {
//            // 查找是否有其他会话使用相同昵称
//            for (Map.Entry<String, WebSocketUserInfo> entry : userInfos.entrySet()) {
//                String existingSessionId = entry.getKey();
//                WebSocketUserInfo existingUserInfo = entry.getValue();
//                // 如果是相同会话，跳过
//                if (existingSessionId.equals(sessionId)) {
//                    continue;
//                }
//                // 昵称相同但sessionId不同
//                if (existingUserInfo.getNickname() != null && existingUserInfo.getNickname().equals(nickname)) {
//                    System.out.println("发现重复昵称: " + nickname +
//                            "账户: " + account +
//                            "，原会话: " + existingSessionId +
//                            "，新会话: " + sessionId +
//                            "，关闭原会话");
//                    closeSession(existingSessionId);// 关闭前一个会话
//                    sessions.remove(existingSessionId);// 移除前一个会话的信息
//                    lastActiveTimes.remove(existingSessionId);
//                    userInfos.remove(existingSessionId);
//                    // 发送被挤下线的消息（可选）
//                    // sendKickOffMessage(existingSessionId, nickname);
//                    break;
//                }
//            }
//        }
        if (account != null && !account.trim().isEmpty()) {
            // 查找是否有其他会话使用相同账户
            for (Map.Entry<String, WebSocketUserInfo> entry : userInfos.entrySet()) {
                String existingSessionId = entry.getKey();
                WebSocketUserInfo existingUserInfo = entry.getValue();

                // 如果是相同会话，跳过
                if (existingSessionId.equals(sessionId)) {
                    continue;
                }

                // 如果账户相同但sessionId不同
                if (existingUserInfo.getMAccount() != null && existingUserInfo.getMAccount().equals(account)) {
                    System.out.println(TimeUtil.GetTime(true) +"发现重复账户登录: " + account +
                            "，原会话: " + existingSessionId +
                            "，新会话: " + sessionId +
                            "，关闭原会话");
                    // 关闭前一个会话
                    closeSession(existingSessionId);
                    // 移除前一个会话的信息
                    sessions.remove(existingSessionId);
                    lastActiveTimes.remove(existingSessionId);
                    userInfos.remove(existingSessionId);

                    // 发送被挤下线的消息
                    // sendKickOffMessage(existingSessionId, account);
                    break;
                }
            }
        }


        WebSocketUserInfo userInfo = userInfos.getOrDefault(sessionId, new WebSocketUserInfo());
        userInfo.setSessionId(sessionId);
        if (nickname != null) {
            userInfo.setNickname(nickname);
        }
        if (account != null) {
            userInfo.setMAccount(account);  // 设置账户
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