package com.cloudestudio.hosgetserver.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Class UserInfoBean
 * @Author Create By Matrix·张
 * @Date 2024/11/18 下午10:42
 * 用户信息Bean
 */
@Data
public class UserInfoBean implements Serializable {
    private String uAccount;
    private String uName;
    private String uTel;
    private String organization_name;
    private String headerImageUrl;
    private String authority_key;
}
