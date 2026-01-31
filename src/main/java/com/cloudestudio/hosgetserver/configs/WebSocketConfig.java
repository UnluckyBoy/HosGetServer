package com.cloudestudio.hosgetserver.configs;

import com.cloudestudio.hosgetserver.configs.Handler.MatrixWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * @Class WebSocketConfig
 * @Author Create By Matrix·张
 * @Date 2026/1/31 下午11:40
 * WebSocketConfig配置类
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final MatrixWebSocketHandler matrixWebSocketHandler;
    // 构造函数注入
    public WebSocketConfig(MatrixWebSocketHandler matrixWebSocketHandler) {
        this.matrixWebSocketHandler = matrixWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(matrixWebSocketHandler, "/ws")
                .setAllowedOrigins("*"); // 生产环境应该配置具体的域名
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        container.setMaxSessionIdleTimeout(600000L); // 10分钟
        return container;
    }
}
