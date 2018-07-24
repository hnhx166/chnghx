package com.chnghx.web.index.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chnghx.core.log.LogUtils;

/**
 * 
*    
* 项目名称：chnghx   
* 类名称：IndexController   
* 类描述：   
* 创建人：guohaixiang  
* 创建时间：2018年2月28日 下午4:33:54   
* 修改人：Administrator   
* 修改时间：2018年2月28日 下午4:33:54   
* 修改备注：   
* @version 1.0
*
 */
@RestController
@RequestMapping("/")
public class IndexController {
	
	private static final Logger logger = LogUtils.getBussinessLogger();

	@RequestMapping("")
    public synchronized ModelAndView index() {
		ModelAndView view = new ModelAndView("index/index");
		logger.info("日志输出。。。。。");
		List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
		for(int i =0 ; i < 10; i++) {
			Map<String, String> temp = new HashMap<String, String>();
			temp.put("k", "value" + (i+1));
			listData.add(temp);
		}
		
		view.addObject("listData", listData);
		
        return view;
    }
	
	
	@RequestMapping("listData")
    public Map<String, List<Map<String, String>>> listData() {
		Map<String, List<Map<String, String>>> res = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> listData = new ArrayList<Map<String, String>>();
		for(int i =0 ; i < 10; i++) {
			Map<String, String> temp = new HashMap<String, String>();
			temp.put("k", "value" + (i+1));
			listData.add(temp);
		}
		res.put("list", listData);
        return res;
    }
}
