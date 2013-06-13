package com.zhao.activiti.demo.controller;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhao.activiti.demo.service.impl.WorkflowProcessDefinitionService;
import com.zhao.activiti.demo.service.impl.WorkflowTraceService;

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

	protected WorkflowTraceService traceService;
	
	/**
	 * 
	 * @return
	 */
	public String redeployAll(){
		return "";
	}
	
	
	
	
	
	
	
	
	
	@Autowired
	public void setWorkflowProcessDefinitionService(WorkflowProcessDefinitionService workflowProcessDefinitionService) {
		this.workflowProcessDefinitionService = workflowProcessDefinitionService;
	}

	@Autowired
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Autowired
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@Autowired
	public void setTraceService(WorkflowTraceService traceService) {
		this.traceService = traceService;
	}
}
