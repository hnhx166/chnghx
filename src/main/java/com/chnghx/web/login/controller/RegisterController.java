package com.chnghx.web.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.chnghx.dao.mysql.entity.User;
import com.chnghx.web.login.service.LoginService;
import com.chnghx.web.login.service.RegService;

@RestController
@RequestMapping("reg")
public class RegisterController {
	
	@Autowired
	private RegService regService;
	
	@Autowired
	private LoginService LoginService;
	
	@RequestMapping("pc_reg")
	public ModelAndView pc_reg() {
		ModelAndView view = new ModelAndView("user/reg/pc_reg");
		return view;
	}
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	@RequestMapping("doReg")
	public Map<String, Object> doreg(User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		User checkUser = regService.selectByLoginName(user.getLoginName());
		if(null  != checkUser) {
			result.put("message", "该用户已注册");
			return result;
		}
		int res = regService.insert(user);
		if(res > 0) {
			result.put("message", "注册成功");
		}else {
			result.put("message", "注册失败");
		}
		return result;
	}
	
	/**
	 * 验证账号
	 * @param loginName
	 * @return
	 */
	@RequestMapping("validateLoginName")
	public Map<String, Object> validateLoginName(String loginName){
		Map<String, Object> result = new HashMap<String, Object>();
		boolean valid = true;
		User user = regService.selectByLoginName(loginName);
		if(null  != user) {
			valid = false; 
		}
		
		
		result.put("valid", valid);
		return result;
	}

}
