package com.cloudestudio.hosgetserver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class AddrInfo
 * @Author Create By Matrix·张
 * @Date 2025/1/1 下午10:13
 * 区域代码类
 */
@Data
public class AddrInfo implements Serializable {
    private String addr_code;
    private String addr_name;
}
