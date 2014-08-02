package com.zhao.activiti.demo.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhao.activiti.demo.util.UserUtil;

/**
 * 登录退出
 * 
 * 
 */

@Controller
@RequestMapping("/user")
public class UserController {
	private IdentityService identityService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginUser() {
		return "user/login";
	}

	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String loginUser(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
		boolean checkPassword = identityService.checkPassword(username, password);
		if (checkPassword) {
			User user = identityService.createUserQuery()//
					.userId(username)//
					.singleResult();
			UserUtil.saveUsertoSession(session, user);

			List<Group> groupList = identityService.createGroupQuery()//
					.groupMember(username)//
					.list();
			session.setAttribute("groups", groupList);
			String[] groupNames = new String[groupList.size()];
			for (int i = 0; i < groupNames.length; i++) {
				groupNames[i] = groupList.get(i).getName();
			}
			session.setAttribute("groupNames", Arrays.toString(groupNames));
			return "redirect:/index";
		} else {
			return "redirect:/user/login?error=true";
		}
	}

	/**
	 * 退出
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		UserUtil.removeUserFromSession(session);
		return "/logout";
	}

	@Autowired
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
}
