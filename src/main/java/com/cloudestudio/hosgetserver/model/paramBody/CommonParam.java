package com.cloudestudio.hosgetserver.model.paramBody;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class CommonParam
 * @Author Create By Matrix·张
 * @Date 2026/1/25 下午1:08
 * 公共参数请求体
 */
@Data
public class CommonParam implements Serializable {
    private static final long serialVersionUID = 1L;
    private String orderId;
}
