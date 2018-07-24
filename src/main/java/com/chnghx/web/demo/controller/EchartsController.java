package com.chnghx.web.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("echart")
public class EchartsController {

	/**
	 * 维他康智海报六芒星
	 * @return
	 */
	@RequestMapping("hexagram")
	public ModelAndView hexagram() {
		ModelAndView view = new ModelAndView("demo/echarts/star");
		
		String[] tz = {"阴虚", "阳虚", "邪毒", "湿滞", "血瘀", "气郁", "气紊"};
		
		List<Map<String, Object>> indicator = new ArrayList<Map<String, Object>>();
		for(int i =0; i < tz.length -1 ; i ++) {
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("text", tz[i]);
			temp.put("max", 100);
			indicator.add(temp);
		}
		
		//用户值
		int[] userData = {60, 20, 85, 40, 10, 90,58};
		
		//标准值
		int[] standardData = {30, 30, 30, 30, 40, 40,60};
		
		view.addObject("indicator", indicator);
		view.addObject("userData", userData);
		view.addObject("standardData", standardData);
		view.addObject("abc", "你好");
		
		return view;
	}
	
}
