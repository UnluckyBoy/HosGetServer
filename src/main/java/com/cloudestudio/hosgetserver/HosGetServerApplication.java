package com.cloudestudio.hosgetserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HosGetServerApplication {
	private static final Logger logger = LogManager.getLogger(HosGetServerApplication.class);
	// 静态初始化块，用于在应用启动时记录日志
	static {
		logger.info("HosGetServer服务启动中...");
	}

	public static void main(String[] args) {
		SpringApplication.run(HosGetServerApplication.class, args);
		logger.info("HosGetServer服务启动成功!");
	}
}
