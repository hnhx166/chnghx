package com.chnghx.core.config;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.Filter;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.chnghx.common.utils.StringUtils;
import com.chnghx.core.shiro.filter.KickoutSessionFilter;
import com.chnghx.core.shiro.filter.LoginFilter;
import com.chnghx.core.shiro.realm.MyShiroRealm;

@Configuration
public class ShiroConfig {
	// 将自己的验证方式加入容器
	@Bean
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		return myShiroRealm;
	}

	// 权限管理，配置主要是Realm的管理认证
	@Bean
	public org.apache.shiro.mgt.SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());

		securityManager.setCacheManager(cacheManager());
		securityManager.setSessionManager(sessionManager());

		return securityManager;
	}

	// Filter工厂，设置对应的过滤条件和跳转条件
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(org.apache.shiro.mgt.SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();

		ClassPathResource cp = new ClassPathResource("shiro_base_auth.properties");
		Properties properties = new Properties();
		try {
			properties.load(cp.getInputStream());
		} catch (IOException e) {
			// LoggerUtils.error(getClass(),"loadfixed_auth_res.properties error!", e);
			System.exit(0);
		}
		Set<Object> set = properties.keySet();
		List<Integer> keys = new LinkedList<Integer>();
		for (Object object : set) {
			if (!StringUtils.isBlank(object)) {
				keys.add(Integer.parseInt(object.toString()));
			}
		}
		Collections.sort(keys);

		for (Integer key : keys) {
			String value = (String) properties.get(key.toString());
			if (value.contains("=")) {
				String varray[] = value.split("=");
				filterChainDefinitionMap.put(varray[0].trim(), varray[1].trim());
				// sb.append(varray[0].trim()).append(" =
				// ").append(varray[1].trim()).append(OTHER).append(CRLF);
			}
		}
		
//		filterChainDefinitionMap.put("/open/**", "anon");
//		filterChainDefinitionMap.put("/*", "login");
		filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");

		// //登出
		// map.put("/logout","logout");
		// //对所有用户认证
		// map.put("/**","authc");
		// 登录
		shiroFilterFactoryBean.setLoginUrl("/open/member/tologin");
		// 首页
		shiroFilterFactoryBean.setSuccessUrl("/");
		// 错误页面，认证不通过跳转
		shiroFilterFactoryBean.setUnauthorizedUrl("/error");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		Map<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("kickout", kickoutSessionFilter());
		filters.put("login", loginFilter());
		shiroFilterFactoryBean.setFilters(filters);
		return shiroFilterFactoryBean;
	}

	/**
	 * cacheManager 缓存 redis实现 使用的是shiro-redis开源插件
	 *
	 * @return
	 */
	public RedisCacheManager cacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		return redisCacheManager;
	}

	/**
	 * 配置shiro redisManager 使用的是shiro-redis开源插件
	 *
	 * @return
	 */
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost("192.168.0.28");
		redisManager.setPort(6379);
		redisManager.setExpire(1800);// 配置缓存过期时间
		redisManager.setTimeout(0);
		// redisManager.setPassword(password);
		return redisManager;
	}

	/**
	 * Session Manager 使用的是shiro-redis开源插件
	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(redisSessionDAO());
		return sessionManager;
	}

	/**
	 * RedisSessionDAO shiro sessionDao层的实现 通过redis 使用的是shiro-redis开源插件
	 */
	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}

	/**
	 * 限制同一账号登录同时登录人数控制
	 *
	 * @return
	 */
	@Bean
	public KickoutSessionFilter kickoutSessionFilter() {
		KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
		kickoutSessionFilter.setCacheManager(cacheManager());
		kickoutSessionFilter.setSessionManager(sessionManager());
		kickoutSessionFilter.setKickoutAfter(false);
		kickoutSessionFilter.setMaxSession(1);
		kickoutSessionFilter.setKickoutUrl("/auth/kickout");//踢出跳转页面
		return kickoutSessionFilter;
	}
	
	@Bean
	public LoginFilter loginFilter() {
		LoginFilter loginFilter = new LoginFilter();
//		loginFilter.setCacheManager(cacheManager());
//		loginFilter.setSessionManager(sessionManager());
//		kickoutSessionControlFilter.setKickoutAfter(false);
//		kickoutSessionControlFilter.setMaxSession(1);
//		kickoutSessionControlFilter.setKickoutUrl("/auth/kickout");//踢出跳转页面
		return loginFilter;
	}

	/***
	 * 授权所用配置
	 *
	 * @return
	 */
	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	// 加入注解的使用，不加入这个注解不生效
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			org.apache.shiro.mgt.SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * Shiro生命周期处理器
	 *
	 */
	@Bean
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
}
