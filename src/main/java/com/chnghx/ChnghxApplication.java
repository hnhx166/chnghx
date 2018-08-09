package com.chnghx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2 //表示开启Swagger
//@EnableRedisHttpSession//开启spring session支持
@EnableHystrixDashboard//开启hystrixDashboard
@EnableZuulProxy//开启zuul的功能
@EnableDiscoveryClient
@EnableHystrix //注解开启Hystrix
@EnableFeignClients//@EnableFeignClients注解开启Feign的功能
@EnableEurekaClient
@RestController
@SpringBootApplication
public class ChnghxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChnghxApplication.class, args);
	}
}
