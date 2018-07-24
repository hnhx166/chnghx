package com.chnghx.core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 *
*    
* 项目名称：chnghx   
* 类名称：DruidConfig   
* 类描述：   
* 创建人：guohaixiang  
* 创建时间：2018年5月16日 下午5:03:40   
* 修改人：Administrator   
* 修改时间：2018年5月16日 下午5:03:40   
* 修改备注：   
* @version 1.0
*
 */
@Configuration
public class DruidConfig {

	/**
	 * 配置用户名/密码
	 * @return
	 */
	@Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername","admin");
        servletRegistrationBean.addInitParameter("loginPassword","123456");
        return servletRegistrationBean;
    }
	
	/**
	 * 配置拦截路径
	 * @return
	 */
	@Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");//拦截路径
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");//不拦截的访问后缀和URI
        return filterRegistrationBean;
    }
}
