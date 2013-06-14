package com.zhao.activiti.demo.service.impl;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class WorkflowProcessDefinitionService {

	@Autowired
	protected RuntimeService runtimeService;

	@Autowired
	protected RepositoryService repositoryService;

	@Autowired
	protected HistoryService historyService;

	/**
	 * 根据流程实例ID查询流程定义
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return 流程定义对象
	 */
	public ProcessDefinition findProcessDefinitionByProcessInstanceId(String processInstanceId) {
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()//
				.processInstanceId(processInstanceId)//
				.singleResult();
		String processDefinitionId = historicProcessInstance.getProcessDefinitionId();
		ProcessDefinition processDefinition = findProcessDefinition(processDefinitionId);
		return processDefinition;
	}

	/**
	 * 根据流程定义ID查询流程定义对象
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @return 流程定义对象
	 */
	public ProcessDefinition findProcessDefinition(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()//
				.processDefinitionId(processDefinitionId)//
				.singleResult();
		return processDefinition;
	}

	/**
	 * 部署类路径下所有的流程定义
	 */
	public void deployAllFromClasspath() {
		this.deployFromClasspath();
		// this.deploySingleProcessForClassPath();
	}

	public void deployFromClasspath(String... processKey) {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		String[] processKeys = { "leave", "leave-dynamic-from" };
		for (String loopProcessKey : processKeys) {
			/** 需要过滤指定流程 */
			if (ArrayUtils.isNotEmpty(processKey)) {
				if (ArrayUtils.contains(processKey, loopProcessKey)) {
					deploySingleProcess(resourceLoader, loopProcessKey);
				} else {
					// TODO 抛出异常 日志信息等...
				}
			} else {
				deploySingleProcess(resourceLoader, loopProcessKey);
			}
		}
	}

	/**
	 * 部署单个流程定义
	 * 
	 * @param resourceLoader
	 * @param loopProcessKey
	 */
	private void deploySingleProcess(ResourceLoader resourceLoader, String processKey) {
		try {
			String classpathResourceUrl = "classpath:/deployment/" + processKey + ".bar"; // 部署的时候有乱码
			// String classpathResourceUrl = "classpath:/deployments/" + processKey + ".zip";
			Resource resource = resourceLoader.getResource(classpathResourceUrl);
			InputStream inputStream = resource.getInputStream();
			if (inputStream != null) {
				ZipInputStream zipInputStream = new ZipInputStream(inputStream);
				repositoryService.createDeployment()//
						.addZipInputStream(zipInputStream)//
						.deploy();
			} else {
				// TODO 抛出异常
			}
		} catch (Exception e) {
			throw new RuntimeException("读取类路径下的资源文件失败");
		}
	}

	private void deploySingleProcessForClassPath() {
		repositoryService.createDeployment()//
				.addClasspathResource("diagrams/leave/leave.bpmn")//
				.addClasspathResource("diagrams/leave/leave.png")//
				.addClasspathResource("diagrams/leave-dynamic-from/leave-dynamic-from.png")//
				.addClasspathResource("diagrams/leave-dynamic-from/leave-dynamic-from.png")//
				.deploy();//
	}

	/**
	 * 重新部署某个流程定义
	 * 
	 * @param processKey
	 *            流程定义KEY
	 */
	public void redeploy(String... processKey) {
		this.deployFromClasspath(processKey);
	}

}
