package com.cloudestudio.hosgetserver.service.Impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.UserInfoBean;
import com.cloudestudio.hosgetserver.model.mapper.UserMapper;
import com.cloudestudio.hosgetserver.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Class UserLoginImpl
 * @Author Create By Matrix·张
 * @Date 2024/12/14 下午3:12
 * 用户登录实现类
 */
@Service("UserLoginService")
public class UserLoginImpl implements UserLoginService {
    @Autowired
    UserMapper userMapper;

    @DS("mysql")
    @Override
    public UserInfoBean loginQuery(Map<String, Object> map) {
        return userMapper.loginQuery(map);
    }
}
