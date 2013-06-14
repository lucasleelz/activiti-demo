package com.zhao.activiti.demo.controller;

import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
	 * 部署全部流程
	 * 
	 * @return
	 */

	@RequestMapping(value = "/redeploy/all")
	public String redeployAll() {
		workflowProcessDefinitionService.deployAllFromClasspath();
		return "redirect:/workflow/processList";
	}

	@RequestMapping(value = "/processList")
	public ModelAndView processList() {
		ModelAndView modelAndView = new ModelAndView("workflow/processList");
		List<ProcessDefinition> processDefinitions = repositoryService//
				.createProcessDefinitionQuery()//
				.list();
		return modelAndView.addObject("processDefinitions", processDefinitions);
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

}
