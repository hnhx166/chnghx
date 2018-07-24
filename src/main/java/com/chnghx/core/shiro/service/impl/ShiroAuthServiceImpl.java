package com.chnghx.core.shiro.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.core.io.ClassPathResource;

import com.chnghx.common.utils.LoggerUtils;
import com.chnghx.common.utils.StringUtils;
import com.chnghx.core.shiro.service.ShiroAuthService;


public class ShiroAuthServiceImpl implements ShiroAuthService {
	

	// 注意/r/n前不能有空格
	private static final String CRLF = "\r\n";
//	private static final String OTHER = VinuxService.getProperty("shiro.filter");
	@Resource
	private ShiroFilterFactoryBean shiroFilterFactoryBean;


	@Override
	public String loadFilterChainDefinitions() {
		
		StringBuffer sb = new StringBuffer();
			sb.append(getFixedAuthRule());//固定权限，采用读取配置文件
			sb.append(getDynaAuthRule());//获取角色动态权限
			sb.append(getRestfulAuthRule());//其他自定义
		return sb.toString();
	}

	// 生成restful风格功能权限规则
	private String getRestfulAuthRule() {
	
		return "";
	}

	// 根据角色，得到动态权限规则
	private String getDynaAuthRule() {
	  
   
	   return "";
	}
	
	/**
	 * 从配额文件获取固定权限验证规则串
	 * @author zhou-baicheng
	 */
	private String getFixedAuthRule(){
		StringBuffer sb = new StringBuffer("");
		ClassPathResource cp = new ClassPathResource("shiro_base_auth.properties");
		Properties properties = new Properties();  
		try { 
			properties.load(cp.getInputStream());
		} catch (IOException e) {
			LoggerUtils.error(getClass(),"loadfixed_auth_res.properties error!", e);
			System.exit(0);
		}
		Set<Object> set = properties.keySet();
		List<Integer> keys = new LinkedList<Integer>();
		for (Object object : set) {
			if(!StringUtils.isBlank(object)){
				keys.add(Integer.parseInt(object.toString()));
			}
		}
		Collections.sort(keys);
		
		for (Integer key : keys) {
			String value = (String) properties.get(key.toString());
			if(value.contains("=")){
				String varray [] = value.split("=");
				sb.append(varray[0].trim()).append(" = ").append(varray[1].trim())/*.append(OTHER)*/.append(CRLF);
			}
		}
		return sb.toString();
	}

	@Override
	// 此方法加同步锁
	public synchronized void reCreateFilterChains() {
		AbstractShiroFilter shiroFilter = null;
		try {
			shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
		} catch (Exception e) {
			LoggerUtils.error(getClass(),"getShiroFilter from shiroFilterFactoryBean error!", e);
			throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
		}

		PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
				.getFilterChainResolver();
		DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
				.getFilterChainManager();

		// 清空老的权限控制
		manager.getFilterChains().clear();

		shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
		shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
		// 重新构建生成
		Map<String, String> chains = shiroFilterFactoryBean
				.getFilterChainDefinitionMap();
		for (Map.Entry<String, String> entry : chains.entrySet()) {
			String url = entry.getKey();
			String chainDefinition = entry.getValue().trim().replace(" ", "");
			manager.createChain(url, chainDefinition);
		}

	}
	public void setShiroFilterFactoryBean(
			ShiroFilterFactoryBean shiroFilterFactoryBean) {
		this.shiroFilterFactoryBean = shiroFilterFactoryBean;
	}

}
