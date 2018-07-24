package com.chnghx.web.demo.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chnghx.dao.mysql.entity.SsoDomain;
import com.chnghx.service.SSODomainService;


@Controller
@RestController //用此注解返回json格式数据，不需要@ResponseBody注解
@ResponseBody
public class UserController {
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
    private SSODomainService sSODomainService;
	
	
	/**
	 * 
	 * 返回json格式数据
	 * 
	 * @return
	 */
	@RequestMapping("/getDomainById")
    public SsoDomain getDomainById() {
		long id = 95L;
		SsoDomain domain = sSODomainService.selectByPrimaryKey(id);
        return domain;
    }
	

}
