package com.cloudestudio.hosgetserver.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;

/**
 * @Class CorsConfig
 * @Author Create By Matrix·张
 * @Date 2024/11/13 下午9:01
 * 配置类
 */
@Configuration
public class CorsConfig extends WebMvcConfigurationSupport {
    @Value("${back-resource.dir}")
    private String backResourceDir;

    /**
     * 解决跨域问题配置类Cors
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有路径的跨域请求
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
        //.allowCredentials(true) // 是否允许发送Cookie信息
        //.maxAge(3600); // 预检请求的缓存时间(秒)
        super.addCorsMappings(registry);
    }

    /**
     * 用于读取图片
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println(backResourceDir);
        //其中getImage表示图片资源访问的前缀。"file:E:/MatrixProject/BackResource/"是服务器文件真实的存储路径
        /**
         * "classpath:/static/","classpath:/templates/"
         */
        if (backResourceDir != null && !backResourceDir.isEmpty()) {
            backResourceDir = backResourceDir.endsWith(File.separator) ? backResourceDir : backResourceDir + File.separator;
            registry.addResourceHandler("/files/**").addResourceLocations("file:" + backResourceDir);
        }
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/","classpath:/templates/");
    }
}
