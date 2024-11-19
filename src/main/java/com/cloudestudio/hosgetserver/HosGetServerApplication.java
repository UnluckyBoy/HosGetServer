package com.cloudestudio.hosgetserver;

//import com.cloudestudio.hosgetserver.configs.datasource.DataSourceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@SpringBootApplication
public class HosGetServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HosGetServerApplication.class, args);
	}

}
