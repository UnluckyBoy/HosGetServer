package com.cloudestudio.hosgetserver.service.WebSocket.Impl;

import com.cloudestudio.hosgetserver.configs.Handler.MatrixWebSocketHandler;
import com.cloudestudio.hosgetserver.configs.Handler.SessionManager;
import com.cloudestudio.hosgetserver.model.Common.WorkOrderNotification;
import com.cloudestudio.hosgetserver.model.ReportBean.WorkInfoBean;
import com.cloudestudio.hosgetserver.service.WebSocket.WebSocketMessageService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebSocketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Class WebSocketMessageServiceImpl
 * @Author Create By Matrix·张
 * @Date 2026/2/1 上午12:42
 * WebSocket消息服务实现
 */
@Service("WebSocketMessageService")
public class WebSocketMessageServiceImpl implements WebSocketMessageService {
    private final SessionManager sessionManager;
    private final MatrixWebSocketHandler webSocketHandler;

    public WebSocketMessageServiceImpl(SessionManager sessionManager,
                                   MatrixWebSocketHandler webSocketHandler) {
        this.sessionManager = sessionManager;
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void broadcastWorkOrderCreated(WorkInfoBean workInfo) {
        try {
            // 构建通知消息
            WorkOrderNotification notification = new WorkOrderNotification();
            notification.setType("notification");
            notification.setWorkOrderId(workInfo.getWorkID());
            notification.setWorkOrderTitle(workInfo.getWorkTitle());
            notification.setWorkOrderContent(workInfo.getWorkContent());
            notification.setCreator(workInfo.getCreator());
            notification.setCreateTime(workInfo.getCreateDate());
            notification.setPriority(workInfo.getPriority());
            notification.setStatus(workInfo.getWorkStatus());
            notification.setTimestamp(System.currentTimeMillis());

            // 创建 WebSocket 响应
            WebSocketResponse response = WebSocketResponse.success(
                    "notification",
                    "新工单创建通知",
                    notification
            );

            // 广播消息
            webSocketHandler.broadcastMessage(response);

        } catch (Exception e) {
            System.out.println(TimeUtil.GetTime(true)+" ---发送工单创建通知失败:"+e);
        }
    }
}
