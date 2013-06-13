package com.zhao.activiti.demo.dao;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 检查ProcessEngine是否正常工作
 * 
 * @author 贤者以其昭昭
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans-test.xml")
public class ProcessEngineTest{
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private RuntimeService runtimeService;

	@Resource
	private FormService formService;

	@Resource
	private IdentityService identityService;

	@Resource
	private TaskService taskService;
	@Resource
	private HistoryService historyService;

	@Resource
	private ManagementService managementService;
	
	@Test
	public void testProcessEngine() throws Exception {
		assertNotNull(repositoryService);
		assertNotNull(runtimeService);
		assertNotNull(formService);
		assertNotNull(identityService);
		assertNotNull(taskService);
		assertNotNull(historyService);
		assertNotNull(managementService);
	}
}
