package com.zhao.activiti.helloworld;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans-test.xml")
public class ActivitiHelloWorldTest {
	
	@Resource
	private RepositoryService repositoryService;
	
	private Deployment deployment;
	
	/**
	 * 部署方式。
	 * @throws Exception
	 */
	@Test
	public void testDeployProcess() throws Exception {
		
		ProcessEngine processEngine;
		
//		//模型
//		repositoryService.createDeployment().addBpmnModel(resourceName, bpmnModel);
//		
//		//类路径
//		repositoryService.createDeployment().addClasspathResource(resource);
//		
//		//流
//		repositoryService.createDeployment().addInputStream(resourceName, inputStream);
//		
//		//字符串 就是XML 源文件~以字符串的方式部署
//		repositoryService.createDeployment().addString(resourceName, text);
//		
//		//压缩流
//		repositoryService.createDeployment().addZipInputStream(zipInputStream);
		
	}
	
	@Test
	public void testDeploymentByClassPathResource() throws Exception {
		String resource = "HelloWorld.bpmn";
		deployment = repositoryService//
				.createDeployment()//
				.addClasspathResource(resource)//
				.deploy();
		
	}
	
	@Before
	public void setUp(){
		
	}
	
	@After
	public void tearDown(){
		repositoryService.deleteDeployment(deployment.getId());
	}
}











