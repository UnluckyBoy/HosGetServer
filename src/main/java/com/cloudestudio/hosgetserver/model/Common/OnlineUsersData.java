package com.cloudestudio.hosgetserver.model.Common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Class OnlineUsersData
 * @Author Create By Matrix·张
 * @Date 2026/1/31 下午11:42
 * 在线用户数实体类
 */
@Data
public class OnlineUsersData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int total;
    private java.util.List<WebSocketSimpleInfo> users;
}
