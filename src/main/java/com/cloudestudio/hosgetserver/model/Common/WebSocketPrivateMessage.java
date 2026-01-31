package com.cloudestudio.hosgetserver.model.Common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class WebSocketPrivateMessage
 * @Author Create By Matrix·张
 * @Date 2026/1/31 下午11:46
 * WebSocketPrivateMessage私聊消息实体
 */
@Data
public class WebSocketPrivateMessage implements Serializable {
    private String from;
    private String content;
    private long timestamp;

    public WebSocketPrivateMessage(String from, String content, long timestamp) {
        this.from = from;
        this.content = content;
        this.timestamp = timestamp;
    }
}
