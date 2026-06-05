package com.cloudestudio.hosgetserver.service.HosCommon;

import com.cloudestudio.hosgetserver.model.Common.MatrixPermission;
import com.cloudestudio.hosgetserver.model.Common.UPBean;
import com.cloudestudio.hosgetserver.model.paramBody.SavePermissionBean;
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
@Service("ManagerService")
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

    /**
     * 修改权限
     * @param account
     * @param permissions
     * @return
     */
    @Override
    public WebResponse batchUpPerMission(String account, List<String> permissions) {
        // 先删除所有权限
        boolean delResult=managerDataService.delPermissionsByAccount(account);
        if(delResult){
            boolean result=managerDataService.batchUpPerMission(account,permissions);
            if(result){
                System.out.println(TimeUtil.GetTime(true)+"更新权限成功->删除更新");
                return WebResponse.success();
            }
            System.err.println(TimeUtil.GetTime(true)+"更新权限失败->更新失败");
            return WebResponse.failure();
        }
        System.err.println(TimeUtil.GetTime(true)+"更新权限失败->删除失败");
        return WebResponse.failure();
    }
}
