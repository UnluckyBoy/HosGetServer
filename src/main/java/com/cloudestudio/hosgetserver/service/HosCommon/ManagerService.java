package com.cloudestudio.hosgetserver.service.HosCommon;

import com.cloudestudio.hosgetserver.webTools.WebResponse;

/**
 * @Class UserCommonService
 * @Author Create By Matrix·张
 * @Date 2026/6/3 下午4:46
 * 用户雍熙公共服务类
 */
public interface ManagerService {
    WebResponse queryUPermission();
    WebResponse queryAllPermission();
}
