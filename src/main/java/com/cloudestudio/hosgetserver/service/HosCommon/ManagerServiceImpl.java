package com.cloudestudio.hosgetserver.service.HosCommon;

import com.cloudestudio.hosgetserver.model.Common.MatrixPermission;
import com.cloudestudio.hosgetserver.model.Common.UPBean;
import com.cloudestudio.hosgetserver.service.Manager.ManagerDataService;
import com.cloudestudio.hosgetserver.service.UserLoginService;
import com.cloudestudio.hosgetserver.webTools.TimeUtil;
import com.cloudestudio.hosgetserver.webTools.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class UserCommonServiceImpl
 * @Author Create By Matrix·张
 * @Date 2026/6/3 下午4:47
 * 用户信息公共类实现
 */
@Service("UserCommonService")
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    UserLoginService userLoginService;

    @Autowired
    ManagerDataService managerDataService;

    /**
     * 查询所有用户及权限
     * @return
     */
    @Override
    public WebResponse queryUPermission() {
        List<UPBean> resultList=userLoginService.queryUPermission();
        System.out.println(TimeUtil.GetTime(true)+"查询用户信息-权限: " + resultList);
        return WebResponse.success(resultList);
    }

    /**
     * 查询所有权限
     * @return
     */
    @Override
    public WebResponse queryAllPermission() {
        List<MatrixPermission> resultList=managerDataService.queryAllPermission();
        System.out.println(TimeUtil.GetTime(true)+"查询所有权限: " + resultList);
        return WebResponse.success(resultList);
    }
}
