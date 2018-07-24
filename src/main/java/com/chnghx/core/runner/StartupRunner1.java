package com.chnghx.core.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/***
 * 
 * @author Administrator
 * 
 * Spring Boot应用程序在启动后，会遍历CommandLineRunner接口的实例并运行它们的run方法。也可以利用@Order注解（或者实现Order接口）来规定所有CommandLineRunner实例的运行顺序。
 * 
 * 
 *
 */

@Component
@Order(value=1)
public class StartupRunner1 implements CommandLineRunner {

	public void run(String... arg0) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作111111111<<<<<<<<<<<<<");
	}

}
