package com.zhao.activiti.demo.util;

import javax.servlet.http.HttpSession;

import org.activiti.engine.identity.User;

public class UserUtil {
	public static final String USER = "user";

	/**
	 * 设置用户到session
	 * 
	 * @param httpSession
	 * @param user
	 */
	public static void saveUsertoSession(HttpSession session, User user) {
		session.setAttribute(USER, user);
	}

	/**
	 * 从Session中获取当前用户信息
	 * 
	 * @param httpSession
	 * @return
	 */
	public static User getUserFromSession(HttpSession session) {
		User user = (User) session.getAttribute(USER);
		return user == null ? null : user;
	}

	/**
	 * 从当前Session中移除当前用户
	 * 
	 * @param session
	 */
	public static void removeUserFromSession(HttpSession session) {
		session.removeAttribute(USER);
	}
}
