package com.cloudestudio.hosgetserver.model.Common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Class UPBean
 * @Author Create By Matrix·张
 * @Date 2026/6/3 下午2:18
 * 用户权限类
 */
@Data
public class UPBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String uAccount;
    private String uName;
    private String department_name;
    private String permissions;
}
