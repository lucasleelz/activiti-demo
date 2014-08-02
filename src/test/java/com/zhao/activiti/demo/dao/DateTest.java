package com.zhao.activiti.demo.dao;

import java.util.Date;

import org.junit.Test;

public class DateTest {
	@Test
	public void testDate() throws Exception {
		//2013-6-13 17:17:40
		//2013-06-13+07%3A25
		System.out.println(new Date().toLocaleString());
	}
}
