package com.cloudestudio.hosgetserver.model.Common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Class WebSocketSimpleInfo
 * @Author Create By Matrix·张
 * @Date 2026/1/31 下午11:43
 * 会话用户简单信息实体
 */
@Data
public class WebSocketSimpleInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String sessionId;
    private String mAccount;
    private String nickname;
    private String avatar;
    private String role;
    private boolean online;
}
