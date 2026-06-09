package com.cloudestudio.hosgetserver.model.paramBody;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Class UInfoRequrstParam
 * @Author Create By Matrix·张
 * @Date 2026/6/9 上午10:51
 */
@Data
public class UInfoRequestBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String account;
    private String password;
}
