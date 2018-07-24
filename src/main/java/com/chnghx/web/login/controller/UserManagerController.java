package com.chnghx.web.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chnghx.web.login.service.RegService;

@Controller
@RestController
@RequestMapping("user")
public class UserManagerController {

	
	@Autowired
	private RegService regService;
	
	@RequestMapping("list")
	public ModelAndView list() {
		ModelAndView view = new ModelAndView("user/list");
		/*User user = regService.selectByPrimaryKey(1);
		System.out.println(user);*/
		return view;
	}
}
