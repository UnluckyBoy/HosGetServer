package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.UserInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Class UserMapper
 * @Author Create By Matrix·张
 * @Date 2024/11/18 下午10:36
 * 用户Mapper
 * Mysql库
 */
@Service
@Mapper
@Repository
public interface UserMapper {
    UserInfoBean loginQuery(Map<String, Object> map);
}
