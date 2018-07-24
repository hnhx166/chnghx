package com.chnghx.web.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class KickoutController {

	@RequestMapping("kickout")
	public String out() {
		return "您已经在其他地方登录，请重新登录！";
	}
}
