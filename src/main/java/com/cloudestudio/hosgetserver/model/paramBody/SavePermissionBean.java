package com.cloudestudio.hosgetserver.model.paramBody;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @Class SavePremissionBean
 * @Author Create By Matrix·张
 * @Date 2026/6/5 上午8:17
 */
@Data
public class SavePermissionBean implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private String account;
    private List<String> permission;
}
