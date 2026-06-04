package com.cloudestudio.hosgetserver.service.Manager;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.Common.MatrixPermission;
import com.cloudestudio.hosgetserver.model.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class ManagerServiceImpl
 * @Author Create By Matrix·张
 * @Date 2026/6/4 下午12:12
 */
@Service("ManagerDataService")
public class ManagerDataServiceImpl implements ManagerDataService {
    @Autowired
    ManagerMapper managerMapper;

    /**
     * 查询所偶遇权限
     * @return
     */
    @DS("mysql")
    @Override
    public List<MatrixPermission> queryAllPermission() {
        return managerMapper.queryAllPermission();
    }
}
