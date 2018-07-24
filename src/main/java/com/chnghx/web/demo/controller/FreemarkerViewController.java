package com.chnghx.web.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chnghx.core.cache.CacheService;
import com.chnghx.core.properties.SeurityProperties;
import com.chnghx.dao.mysql.entity.SsoDomain;
import com.chnghx.service.SSODomainService;

/**
 * 
*    
* 项目名称：chnghx   
* 类名称：FreemarkerViewController   
* 类描述：   
* 创建人：guohaixiang  
* 创建时间：2018年2月28日 上午9:59:28   
* 修改人：Administrator   
* 修改时间：2018年2月28日 上午9:59:28   
* 修改备注：   
* @version 1.0
*
 */
@Controller
@RequestMapping("view")
public class FreemarkerViewController {

	@Autowired
    private SSODomainService sSODomainService;
	
	//配置文件
	@Autowired
	private SeurityProperties seurityProperties;
	
	//redis 使用 
	@Autowired
	private CacheService<String, Object> cacheService;
	
	@RequestMapping("free")
    public ModelAndView freemarker(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String sid = session.getId();
		
		ModelAndView view  = new ModelAndView("index");
		long id = 95L;
		SsoDomain domain = sSODomainService.selectByPrimaryKey(id);
		view.addObject("abc", "你好啊，freemarker模板。");
		view.addObject("domain", domain);
		view.addObject("sid", sid);
		
		///////////////读取配置相关///////////////////////
		view.addObject("prop", seurityProperties);
		
		/////////////redis 相关/////////////
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "a0");
		map.put("b", "b0");
		map.put("c", "c0");
		//放入redis
//		cacheService.put("my_abc", map);
//		//取值
//		Object cacheV = cacheService.get("my_abc");
//		view.addObject("cacheV", cacheV);
		
        return view;
    }
}
