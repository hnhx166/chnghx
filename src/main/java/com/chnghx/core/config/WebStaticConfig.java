package com.chnghx.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * 静态资源配置
*    
* 项目名称：chnghx   
* 类名称：WebMvcConfig   
* 类描述：   
* 创建人：guohaixiang  
* 创建时间：2018年2月27日 下午7:50:23   
* 修改人：Administrator   
* 修改时间：2018年2月27日 下午7:50:23   
* 修改备注：   
* @version 1.0
*
 */
@Configuration
public class WebStaticConfig extends WebMvcConfigurerAdapter{

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
