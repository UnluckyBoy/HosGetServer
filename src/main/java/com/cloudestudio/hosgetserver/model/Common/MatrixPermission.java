package com.cloudestudio.hosgetserver.model.Common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Class Permission
 * @Author Create By Matrix·张
 * @Date 2026/6/4 上午11:08
 * 权限实体类
 */
@Data
public class MatrixPermission implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String authority_type;
    private String authority_type_name;
    private String authority_name;
    private String authority_code;
}
