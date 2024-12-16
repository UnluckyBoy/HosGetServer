package com.cloudestudio.hosgetserver.service;

import com.cloudestudio.hosgetserver.model.UserInfoBean;

import java.util.Map;

/**
 * @Class UserLoginService
 * @Author Create By Matrix·张
 * @Date 2024/12/14 下午3:11
 * 用户登录接口
 */
public interface UserLoginService {
    UserInfoBean loginQuery(Map<String, Object> map);
}
