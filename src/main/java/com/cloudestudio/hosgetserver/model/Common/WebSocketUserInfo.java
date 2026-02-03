package com.cloudestudio.hosgetserver.model.Common;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Class WebSocketUserInfo
 * @Author Create By Matrix·张
 * @Date 2026/1/31 下午11:44
 * WebSocket会话用户实体
 */
@Data
public class WebSocketUserInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String sessionId;
    private String mAccount;
    private String nickname;
    private String avatar;
    private String role;
    private LocalDateTime connectTime;

    public WebSocketSimpleInfo toWebSocketSimpleInfo(ConcurrentHashMap<String, WebSocketSession> sessions) {
        WebSocketSimpleInfo info = new WebSocketSimpleInfo();
        info.setSessionId(sessionId);
        info.setMAccount(mAccount);
        info.setNickname(nickname != null ? nickname : "用户" +
                (sessionId.length() > 6 ? sessionId.substring(0, 6) : sessionId));
        info.setAvatar(avatar);
        info.setRole(role);
        info.setOnline(sessions.containsKey(sessionId));
        return info;
    }
}