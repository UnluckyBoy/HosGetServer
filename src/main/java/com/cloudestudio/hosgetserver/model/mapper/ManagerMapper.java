package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.Common.MatrixPermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class ManagerMapper
 * @Author Create By Matrix·张
 * @Date 2026/6/4 上午10:58
 * 管理员Mapper
 */
@Service
@Mapper
@Repository
public interface ManagerMapper {
    List<MatrixPermission> queryAllPermission();
}
