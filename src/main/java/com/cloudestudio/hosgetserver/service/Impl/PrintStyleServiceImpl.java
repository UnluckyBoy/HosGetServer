package com.cloudestudio.hosgetserver.service.Impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cloudestudio.hosgetserver.model.PrintStyleBean;
import com.cloudestudio.hosgetserver.model.mapper.PrintMapper;
import com.cloudestudio.hosgetserver.service.PrintStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Class PrintStyleServiceImpl
 * @Author Create By Matrix·张
 * @Date 2024/11/26 下午3:35
 * 打印格式服务实现
 */
@Service("PrintStyleService")
public class PrintStyleServiceImpl implements PrintStyleService {
    @Autowired
    PrintMapper printMapper;

    @DS("mysql")
    @Override
    public PrintStyleBean query_print_style(String printName) {
        return printMapper.query_print_style(printName);
    }
}
