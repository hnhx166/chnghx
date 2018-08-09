package com.chnghx.web.swagger.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class Swagger22Controller {

	@ApiOperation(value="测试", notes="helloworld测试")
	@ApiImplicitParam(name="id", value="用户ID", required=false, dataType="String", paramType="path")
	@RequestMapping("testSwagger/{id}")
	public String testSwagger(@PathVariable String id) {
		return "hello swagger";
	}
}
