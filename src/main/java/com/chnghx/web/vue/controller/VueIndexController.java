package com.chnghx.web.vue.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("vue")
public class VueIndexController {

	@RequestMapping("index")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("vue/index");
		
		
		
		
		
		
		return view;
	}
}
