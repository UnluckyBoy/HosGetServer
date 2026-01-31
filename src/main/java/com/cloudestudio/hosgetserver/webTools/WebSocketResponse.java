package com.cloudestudio.hosgetserver.webTools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * @Class WebSocketResponse
 * @Author Create By Matrix·张
 * @Date 2026/1/31 下午11:45
 * WebSocket返回消息类
 */
@Data
public class WebSocketResponse {
    private boolean success;      // 处理状态
    private int code;             // 处理代码
    private String msg;           // 处理描述
    private Object content;       // 处理数据
    private String type;          // 消息类型（system, message, error等）
    private String timestamp;     // 时间戳

    private WebSocketResponse() {}

    // 用于JSON序列化的ObjectMapper
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 创建成功响应
     */
    public static WebSocketResponse success(String type, String message, Object content) {
        WebSocketResponse response = new WebSocketResponse();
        response.setSuccess(true);
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMsg(message);
        response.setContent(content);
        response.setType(type);
        response.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return response;
    }

    /**
     * 创建成功响应（无内容）
     */
    public static WebSocketResponse success(String type, String message) {
        return success(type, message, null);
    }

    /**
     * 创建失败响应
     */
    public static WebSocketResponse failure(String type, String message) {
        WebSocketResponse response = new WebSocketResponse();
        response.setSuccess(false);
        response.setCode(ResponseCode.FAILED.getCode());
        response.setMsg(message);
        response.setContent(null);
        response.setType(type);
        response.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return response;
    }

    /**
     * 创建失败响应（指定错误码）
     */
    public static WebSocketResponse failure(int code, String type, String message) {
        WebSocketResponse response = new WebSocketResponse();
        response.setSuccess(false);
        response.setCode(code);
        response.setMsg(message);
        response.setContent(null);
        response.setType(type);
        response.setTimestamp(String.valueOf(System.currentTimeMillis()));
        return response;
    }

    /**
     * 转换为JSON字符串
     */
    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // 如果JSON转换失败，返回一个简单的错误JSON
            return String.format(
                    "{\"success\":false,\"code\":500,\"msg\":\"JSON转换失败\",\"type\":\"error\",\"timestamp\":%d}",
                    System.currentTimeMillis()
            );
        }
    }

    /**
     * 创建连接成功响应
     */
    public static WebSocketResponse connectionSuccess(String sessionId) {
        return success("system", "连接成功", createConnectionData(sessionId));
    }

    /**
     * 创建用户加入响应
     */
    public static WebSocketResponse userJoin(String sessionId) {
        return success("system", "用户加入聊天室", createUserData(sessionId));
    }

    /**
     * 创建用户离开响应
     */
    public static WebSocketResponse userLeave(String sessionId) {
        return success("system", "用户离开聊天室", createUserData(sessionId));
    }

    /**
     * 创建消息响应
     */
    public static WebSocketResponse message(String from, String content) {
        MessageData data = new MessageData();
        data.setFrom(from);
        data.setContent(content);
        data.setTimestamp(System.currentTimeMillis());
        return success("message", "收到消息", data);
    }

    /**
     * 创建心跳响应
     */
    public static WebSocketResponse heartbeat() {
        return success("heartbeat", "心跳检测", new HeartbeatData());
    }

    /**
     * 创建错误响应
     */
    public static WebSocketResponse error(String message) {
        return failure("error", message);
    }

    /**
     * 创建Token过期响应
     */
    public static WebSocketResponse tokenExpired() {
        return failure(ResponseCode.TOKEN_EXPIRED.getCode(), "system", ResponseCode.TOKEN_EXPIRED.getMsg());
    }

    /**
     * 创建未登录响应
     */
    public static WebSocketResponse notLogin() {
        return failure(ResponseCode.NO_LOGIN.getCode(), "system", ResponseCode.NO_LOGIN.getMsg());
    }

    // 内部数据类
    @Data
    private static class ConnectionData {
        private String sessionId;
        private long timestamp = System.currentTimeMillis();
    }

    @Data
    private static class UserData {
        private String userId;
        private String shortId; // 短ID，用于显示
        private long timestamp = System.currentTimeMillis();
    }

    @Data
    private static class MessageData {
        private String from;
        private String content;
        private long timestamp;
        private String displayName; // 显示名称
    }

    @Data
    private static class HeartbeatData {
        private long timestamp = System.currentTimeMillis();
        private String status = "alive";
    }

    // 辅助方法
    private static Object createConnectionData(String sessionId) {
        ConnectionData data = new ConnectionData();
        data.setSessionId(sessionId);
        return data;
    }

    private static Object createUserData(String sessionId) {
        UserData data = new UserData();
        data.setUserId(sessionId);
        data.setShortId(sessionId.length() > 8 ? sessionId.substring(0, 8) : sessionId);
        return data;
    }
}