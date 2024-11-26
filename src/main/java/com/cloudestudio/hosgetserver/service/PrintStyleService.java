package com.cloudestudio.hosgetserver.service;

import com.cloudestudio.hosgetserver.model.PrintStyleBean;

/**
 * @Class PrintStyleService
 * @Author Create By Matrix·张
 * @Date 2024/11/26 下午3:31
 * 打印格式接口
 */
public interface PrintStyleService {
    PrintStyleBean query_print_style(String printName);
}
