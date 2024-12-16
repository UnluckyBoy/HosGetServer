package com.cloudestudio.hosgetserver.webTools;

import lombok.Data;

/**
 * @Class PushResponse
 * @Author Create By Matrix·张
 * @Date 2024/12/14 下午4:10
 * 推送数据返回类
 */
@Data
public class PushResponse {
    private boolean result;
    private String desc;
    private String errorCode;
    private String errorName;
    private String id;

    public PushResponse(boolean result, String desc, String errorCode, String errorName, String id) {
        this.result = result;
        this.desc = desc;
        this.errorCode = errorCode;
        this.errorName = errorName;
        this.id = id;
    }
}
