package com.cloudestudio.hosgetserver.configs.Handler;

import com.cloudestudio.hosgetserver.model.Common.OnlineUsersData;
import com.cloudestudio.hosgetserver.model.Common.WebSocketPrivateMessage;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebSocketResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Class MatrixWebSocketHandler
 * @Author Create By Matrix·张
 * @Date 2026/1/31 下午11:37
 * MatrixWebSocketHandler逻辑处理
 */
@Component
public class MatrixWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final SessionManager sessionManager;

    public MatrixWebSocketHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();

        // 添加会话到管理器
        sessionManager.addSession(session);

        // 发送欢迎消息
        WebSocketResponse welcomeResponse = WebSocketResponse.connectionSuccess(sessionId);
        sendMessage(session, welcomeResponse);

        // 广播新用户上线
        WebSocketResponse joinResponse = WebSocketResponse.userJoin(sessionId);
        broadcastMessage(joinResponse);

        System.out.println(TimeUtil.GetTime(true)+"新连接: " + sessionId + "，当前连接数: " + sessionManager.getSessionCount());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String sessionId = session.getId();

        sessionManager.updateLastActiveTime(sessionId);// 更新最后活动时间

        System.out.println(TimeUtil.GetTime(true)+"收到来自 " + sessionId + " 的消息: " + payload);

        // 处理不同类型的消息
        try {
            // 解析消息
            JsonNode jsonNode = objectMapper.readTree(payload);
            // 获取消息类型
            String msgType = jsonNode.has("type") ? jsonNode.get("type").asText() : "message";
            switch (msgType) {
                case "ping":
                    // 心跳包
                    WebSocketResponse pongResponse = WebSocketResponse.heartbeat();
                    sendMessage(session, pongResponse);
                    break;

                case "message":
                    // 普通消息
                    String content = jsonNode.has("content") ? jsonNode.get("content").asText() : payload;
                    // 创建消息响应
                    WebSocketResponse messageResponse = WebSocketResponse.message(
                            sessionId.substring(0, Math.min(sessionId.length(), 8)),
                            content
                    );
                    // 广播消息
                    broadcastMessage(messageResponse);
                    break;
                case "get_users":
                    // 获取在线用户列表
                    sendOnlineUsers(session);
                    break;
                case "user_info":
                    // 更新用户信息
                    String nickname = jsonNode.has("nickname") ? jsonNode.get("nickname").asText() : null;
                    String account = jsonNode.has("account") ? jsonNode.get("account").asText() : null;
                    String avatar = jsonNode.has("avatar") ? jsonNode.get("avatar").asText() : null;
                    String role = jsonNode.has("role") ? jsonNode.get("role").asText() : null;
                    sessionManager.updateUserInfo(sessionId,nickname,account,role, avatar);
                    WebSocketResponse userInfoResponse = WebSocketResponse.success(
                            "system",
                            "用户信息更新成功"
                    );

                    sendMessage(session, userInfoResponse);
                    break;
                default:
                    // 未知消息类型
                    WebSocketResponse errorResponse = WebSocketResponse.failure("error","不支持的消息类型: " + msgType);
                    sendMessage(session, errorResponse);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
            WebSocketResponse errorResponse = WebSocketResponse.error("消息处理失败: " + e.getMessage());
            sendMessage(session, errorResponse);
        }
    }

    /**
     * 发送消息到指定会话
     */
    private void sendMessage(WebSocketSession session, WebSocketResponse response) throws IOException {
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(response.toJson()));
            } catch (IOException e) {
                System.err.println("发送消息失败: " + session.getId() + " - " + e.getMessage());
                // 发送失败时清理会话
                sessionManager.removeSession(session.getId());
                throw e;
            }
        }
    }

    /**
     * 处理私聊消息
     */
    private void handlePrivateMessage(WebSocketSession session, JsonNode jsonNode) throws IOException {
        String sessionId = session.getId();

        if (!jsonNode.has("to") || !jsonNode.has("content")) {
            WebSocketResponse errorResponse = WebSocketResponse.failure(
                    "error",
                    "私聊消息必须包含 to 和 content 字段"
            );
            sendMessage(session, errorResponse);
            return;
        }

        String toSessionId = jsonNode.get("to").asText();
        String content = jsonNode.get("content").asText();

        WebSocketSession targetSession = sessionManager.getSession(toSessionId);
        if (targetSession == null || !targetSession.isOpen()) {
            WebSocketResponse errorResponse = WebSocketResponse.failure(
                    "error",
                    "目标用户不在线"
            );
            sendMessage(session, errorResponse);
            return;
        }

        // 创建私聊消息
        WebSocketResponse privateResponse = WebSocketResponse.success(
                "private_message",
                "私聊消息",
                new WebSocketPrivateMessage(
                        sessionId.substring(0, Math.min(sessionId.length(), 8)),
                        content,
                        System.currentTimeMillis()
                )
        );

        // 发送给接收方
        sendMessage(targetSession, privateResponse);

        // 发送确认给发送方
        WebSocketResponse confirmResponse = WebSocketResponse.success("system", "私聊消息发送成功"
        );
        sendMessage(session, confirmResponse);
    }

    /**
     * 广播消息给所有连接的客户端
     */
    public void broadcastMessage(WebSocketResponse response) {
        String message = response.toJson();
        sessionManager.getAllSessions().forEach((sessionId, session) -> {
            try {
                if (session.isOpen()) {
                    System.out.println(TimeUtil.GetTime(true)+"发送消息到 " + sessionId + " 成功:" +message);
                    session.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                System.err.println(TimeUtil.GetTime(true)+"发送消息到 " + sessionId + " 失败: " + e.getMessage());
                // 发送失败时清理会话
                sessionManager.removeSession(sessionId);
            }
        });
    }

    public void sendMessageToUsers(WebSocketResponse response) {
        String message = response.toJson();
        sessionManager.getAllSessions().forEach((sessionId, session) -> {
            try {
                if (session.isOpen()) {
                    System.out.println(TimeUtil.GetTime(true)+"发送消息到 " + sessionId + " 成功:" +message);
                    session.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                System.err.println(TimeUtil.GetTime(true)+"发送消息到 " + sessionId + " 失败: " + e.getMessage());
                // 发送失败时清理会话
                sessionManager.removeSession(sessionId);
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        String sessionId = session.getId();
        //sessions.remove(sessionId);
        sessionManager.removeSession(sessionId);

        // 广播用户下线
        WebSocketResponse leaveResponse = WebSocketResponse.userLeave(sessionId);
        broadcastMessage(leaveResponse);
        System.out.println(TimeUtil.GetTime(true)+"连接关闭: " + sessionId + "，状态: " + status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println(TimeUtil.GetTime(true)+"传输错误: " + session.getId() + " - " + exception.getMessage());
        String sessionId = session.getId();
        sessionManager.removeSession(sessionId);
        session.close(CloseStatus.SERVER_ERROR);

        WebSocketResponse errorResponse = WebSocketResponse.error("连接发生错误");
        sendMessage(session, errorResponse);
    }

    /**
     * 发送消息给指定用户
     */
    public static void sendToUser(String sessionId, String message) throws IOException {
//        WebSocketSession session = sessions.get(sessionId);
//        if (session != null && session.isOpen()) {
//            session.sendMessage(new TextMessage(message));
//        }
    }

    /**
     * 心跳检测 - 每30秒检查一次
     */
    @Scheduled(fixedRate = 30000)
    public void heartbeatCheck() {
        LocalDateTime now = LocalDateTime.now();
        sessionManager.getAllSessions().forEach((sessionId, session) -> {
            try {
                LocalDateTime lastActiveTime = sessionManager.getLastActiveTime(sessionId);

                // 如果超过60秒没有活动，发送心跳
                if (lastActiveTime != null && lastActiveTime.plusSeconds(60).isBefore(now)) {
                    if (session.isOpen()) {
                        WebSocketResponse heartbeatResponse = WebSocketResponse.heartbeat();
                        session.sendMessage(new TextMessage(heartbeatResponse.toJson()));
                    }
                }
            } catch (Exception e) {
                System.err.println("发送心跳失败: " + e.getMessage());
            }
        });
    }

    /**
     * 定期清理无效会话（每分钟执行一次）
     */
    @Scheduled(fixedRate = 60000)
    public void cleanupInvalidSessions() {
        System.out.println("开始清理无效会话...");
        sessionManager.cleanupInvalidSessions();
        sessionManager.cleanupTimeoutSessions(120); // 清理超过2分钟无活动的会话
        System.out.println("清理完成，当前会话数: " + sessionManager.getSessionCount());
    }

    /**
     * 发送在线用户列表
     */
    private void sendOnlineUsers(WebSocketSession session) throws IOException {
        OnlineUsersData data = new OnlineUsersData();
        data.setTotal(sessionManager.getSessionCount());
        data.setUsers(sessionManager.getOnlineUsers());

        WebSocketResponse usersResponse = WebSocketResponse.success("users", "在线用户列表", data);
        sendMessage(session, usersResponse);
        System.out.println(TimeUtil.GetTime(true)+"data:"+data+"返回usersResponse:"+usersResponse);
        sendMessage(session, usersResponse);
    }
}
