package com.chnghx.web.page.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chnghx.core.page.Pagination;
import com.chnghx.dao.mysql.entity.User;
import com.chnghx.web.login.service.RegService;
import com.github.pagehelper.PageHelper;

@RestController
public class PageController {

	
	@Autowired
	private RegService regService;
	
	@RequestMapping("getUsers")
	public Pagination<User> doreg(Integer page,Integer pageSize) {
		
		User user = new User();
		//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
		PageHelper.startPage(page, pageSize);
		List<User> result = regService.getUsers(user);
		
		Pagination<User> pageData = new Pagination<User>(page, pageSize);
		pageData.setItems(result);
		return pageData;
	}
}
