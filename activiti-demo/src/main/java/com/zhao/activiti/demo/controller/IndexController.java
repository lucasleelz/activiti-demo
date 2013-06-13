package com.zhao.activiti.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 * 
 */
@Controller
public class IndexController {
	@RequestMapping("/index")
	public String index() {
		return "/main/index";
	}

	@RequestMapping(value = "/welcome")
	public String welcome() {
		return "/main/welcome";
	}
}
