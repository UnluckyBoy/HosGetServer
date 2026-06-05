package com.cloudestudio.hosgetserver.service.Manager;

import com.cloudestudio.hosgetserver.model.Common.MatrixPermission;
import com.cloudestudio.hosgetserver.model.paramBody.SavePermissionBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Class ManagerDataService
 * @Author Create By Matrix·张
 * @Date 2026/6/4 下午12:08
 */
public interface ManagerDataService {
    List<MatrixPermission> queryAllPermission();

    boolean delPermissionsByAccount(String account);
    boolean batchUpPerMission(String account, List<String> permissions);
}
