package com.zhao.activiti.demo.controller;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhao.activiti.demo.service.impl.WorkflowProcessDefinitionService;

/**
 * 流程管理控制器
 * 
 */
@Controller
@RequestMapping(value = "/workflow")
public class ActivitiController {
	protected WorkflowProcessDefinitionService workflowProcessDefinitionService;

	protected RepositoryService repositoryService;

	protected RuntimeService runtimeService;

	/**
	 * 
	 * @return
	 */
	public String redeployAll(){
		return "";
	}
	
	@Resource
	public void setWorkflowProcessDefinitionService(WorkflowProcessDefinitionService workflowProcessDefinitionService) {
		this.workflowProcessDefinitionService = workflowProcessDefinitionService;
	}

	@Resource
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Resource
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

}
