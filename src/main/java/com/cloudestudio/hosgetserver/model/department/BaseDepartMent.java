package com.cloudestudio.hosgetserver.model.department;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class BaseDepartMent
 * @Author Create By Matrix·张
 * @Date 2026/1/27 下午1:50
 * 科室实体基类
 */
@Data
public class BaseDepartMent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String departCode;
    private String departName;
    private String departDesc;
    private String departAddr;
}
