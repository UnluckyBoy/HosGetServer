package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.Common.MatrixPermission;
import com.cloudestudio.hosgetserver.model.paramBody.SavePermissionBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    boolean batchUpPerMission(@Param("account") String account,@Param("permissions") List<String> permissions);
    boolean delPermissionsByAccount(String account);
}
