package com.chnghx.core.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件上传配置
*    
* 项目名称：chnghx   
* 类名称：FileUploadConfig   
* 类描述：   
* 创建人：guohaixiang  
* 创建时间：2018年5月16日 下午5:03:23   
* 修改人：Administrator   
* 修改时间：2018年5月16日 下午5:03:23   
* 修改备注：   
* @version 1.0
*
 */
@Configuration
public class FileUploadConfig {

	/**
	 * 文件上传配置
	 * 
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// 单个文件最大
		factory.setMaxFileSize("10240KB"); // KB,MB
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("102400KB");
		return factory.createMultipartConfig();
	}
}
