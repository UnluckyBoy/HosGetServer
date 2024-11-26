package com.cloudestudio.hosgetserver.model.mapper;

import com.cloudestudio.hosgetserver.model.PrintStyleBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @Class PrintMapper
 * @Author Create By Matrix·张
 * @Date 2024/11/26 下午3:31
 * 打印
 */
@Service
@Mapper
@Repository
public interface PrintMapper {
    PrintStyleBean query_print_style(String printName);
}
