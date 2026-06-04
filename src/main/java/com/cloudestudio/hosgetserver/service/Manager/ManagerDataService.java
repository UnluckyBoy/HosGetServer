package com.cloudestudio.hosgetserver.service.Manager;

import com.cloudestudio.hosgetserver.model.Common.MatrixPermission;

import java.util.List;

/**
 * @Class ManagerDataService
 * @Author Create By Matrix·张
 * @Date 2026/6/4 下午12:08
 */
public interface ManagerDataService {
    List<MatrixPermission> queryAllPermission();
}
