package com.cloudestudio.hosgetserver.service.WebSocket;

import com.cloudestudio.hosgetserver.model.ReportBean.WorkInfoBean;

/**
 * @Class WebSocketMessageService
 * @Author Create By Matrix·张
 * @Date 2026/2/1 上午12:42
 * WebSocket消息服务
 */
public interface WebSocketMessageService {
    void broadcastWorkOrderCreated(WorkInfoBean workInfo);
    void broadcastWorkOrderToAdmin(WorkInfoBean workInfo);
}
