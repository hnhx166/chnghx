package com.chnghx.web.demo.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

	private static Logger logger = Logger.getLogger(HelloController.class);
	
	/***
	 * 
	 * 
	 * 返回试图页面请求
	 * 
	 * @param name
	 * @param model
	 * @return
	 */
	@RequestMapping("/hello")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        logger.info("hello");
        model.addAttribute("name", name);
        return "hello";
    }
}
