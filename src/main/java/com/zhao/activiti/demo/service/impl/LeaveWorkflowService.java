package com.zhao.activiti.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhao.activiti.demo.domain.Leave;

@Service
@Transactional
public class LeaveWorkflowService {
	
	private LeaveService leaveService;
	private RuntimeService runtimeService;
	private TaskService taskService;
	private HistoryService historyService;
	private RepositoryService repositoryService;
	private IdentityService identityService;

	/**
	 * 启动流程
	 * 
	 * @param leave
	 * @param variable
	 * @return
	 */
	public ProcessInstance startWorkflow(Leave leave, Map<String, Object> variables) {
		leaveService.saveLeave(leave);
		String businessKey = leave.getId().toString();
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(leave.getUserId());
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", businessKey, variables);
		String processInstanceId = processInstance.getId();
		leave.setProcessInstanceId(processInstanceId);
		return processInstance;
	}

	/**
	 * 查询待办任务
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Leave> findToDoTasks(String userId) {
		List<Leave> results = new ArrayList<Leave>();
		List<Task> tasks = new ArrayList<Task>();

		// 根据当前人的ID查询待办任务
		List<Task> todoTasks = taskService.createTaskQuery()//
				.processDefinitionKey("leave")//
				.taskAssignee(userId)//
				.orderByTaskPriority().desc()//
				.orderByTaskCreateTime().desc()//
				.list();
		// 根据当前人未签收的任务
		List<Task> unsignedTasks = taskService.createTaskQuery()//
				.processDefinitionKey("leave")//
				.taskCandidateUser(userId)//
				.orderByTaskPriority().desc()//
				.orderByTaskCreateTime().desc()//
				.list();

		// 合并
		tasks.addAll(todoTasks);
		tasks.addAll(unsignedTasks);

		// 根据流程的业务ID查询实体并关联
		for (Task task : tasks) {
			String prcessInstanceId = task.getProcessInstanceId();
			ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()//
					.processInstanceId(prcessInstanceId)//
					.singleResult();
			String businessKey = processInstance.getBusinessKey();
			Leave leave = leaveService.getLeave(new Long(businessKey));
			leave.setTask(task);
			leave.setProcessInstance(processInstance);
			leave.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
			results.add(leave);
		}
		return results;
	}

	/**
	 * 读取运行中的流程
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Leave> findRunningProcessInstances() {
		List<Leave> results = new ArrayList<Leave>();
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()//
				.processDefinitionKey("leave")//
				.list();
		// 关联业务实体
		for (ProcessInstance processInstance : processInstances) {
			String businessKey = processInstance.getBusinessKey();
			Leave leave = leaveService.getLeave(Long.parseLong(businessKey));
			leave.setProcessInstance(processInstance);
			leave.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
			results.add(leave);
			// 设置当前的任务信息
			List<Task> tasks = taskService.createTaskQuery()//
					.processInstanceId(processInstance.getId())//
					.orderByTaskCreateTime().desc()//
					.listPage(0, 1);
			leave.setTask(tasks.get(0));
		}
		return results;
	}

	/**
	 * 查询已经完成的流程
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Leave> findFinishProcessInstances() {
		List<Leave> results = new ArrayList<Leave>();
		List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()//
				.processDefinitionKey("leave")//
				.list();
		for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
			String businessKey = historicProcessInstance.getBusinessKey();
			Leave leave = leaveService.getLeave(new Long(businessKey));
			leave.setProcessDefinition(getProcessDefinition(historicProcessInstance.getProcessDefinitionId()));
			leave.setHistoricProcessInstance(historicProcessInstance);
			results.add(leave);
		}
		return results;
	}

	/**
	 * 查询流程定义对象
	 * 
	 * @param processDefinitionId
	 *            流程定义ID
	 * @return
	 */
	protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
		return repositoryService.createProcessDefinitionQuery()//
				.processDefinitionId(processDefinitionId)//
				.singleResult();

	}

	// ****************悲伤的分割线***************//
	@Autowired
	public void setLeaveService(LeaveService leaveService) {
		this.leaveService = leaveService;
	}

	@Autowired
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	@Autowired
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	@Autowired
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	@Autowired
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Autowired
	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}
}
