package com.chnghx.web.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chnghx.dao.mysql.entity.SsoDomain;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/users")
public class SwaggerController {

	 	@ApiOperation(value="Get all users",notes="requires noting")
	    @RequestMapping(method=RequestMethod.GET)
	    public List<SsoDomain> getUsers(){
	        List<SsoDomain> list=new ArrayList<SsoDomain>();
	 
	        SsoDomain domain=new SsoDomain();
	        domain.setMainDomain("domain1");
	        list.add(domain);
	 
	        SsoDomain domain2=new SsoDomain();
	        domain2.setMainDomain("domain2");
	        list.add(domain2);
	        return list;
	    }
	 
	    @ApiOperation(value="Get user with id",notes="requires the id of user")
	    @RequestMapping(value="/{name}",method=RequestMethod.GET)
	    public SsoDomain getUserById(@PathVariable String name){
	    	SsoDomain domain=new SsoDomain();
	    	domain.setMainDomain("hello world");
	        return domain;
	    }
}
