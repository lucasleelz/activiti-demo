package com.zhao.activiti.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zhao.activiti.demo.domain.Leave;
import com.zhao.activiti.demo.service.impl.LeaveService;
import com.zhao.activiti.demo.service.impl.LeaveWorkflowService;
import com.zhao.activiti.demo.util.UserUtil;

/**
 * 请假流程控制器
 * 
 */
@Controller
@RequestMapping(value = "/leave")
public class LeaveController {

	@Autowired
	private LeaveService leaveService;
	@Autowired
	private LeaveWorkflowService leaveWorkflowService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;

	@RequestMapping(value = "/apply", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("leave", new Leave());
		return "leave/apply";
	}

	/**
	 * 启动请假流程
	 * 
	 * @param leave
	 * @param redirectAttributes
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public String startWorkflow(Leave leave, HttpSession session) {
		User user = UserUtil.getUserFromSession(session);
		leave.setUserId(user.getId());
		Map<String, Object> variables = new HashMap<String, Object>();
		leaveWorkflowService.startWorkflow(leave, variables);
		return "redirect:/leave/apply";
	}

	/**
	 * 任务列表
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/list/task")
	public ModelAndView taskList(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("/leave/taskList");
		String userId = UserUtil.getUserFromSession(session).getId();
		List<Leave> leaves = leaveWorkflowService.findToDoTasks(userId);
		return modelAndView.addObject("leaves", leaves);
	}

	/**
	 * 运行中的流程实例
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list/running")
	public ModelAndView runningList() {
		ModelAndView modelAndView = new ModelAndView("/leave/running");
		List<Leave> leaves = leaveWorkflowService.findRunningProcessInstances();
		return modelAndView.addObject("leaves", leaves);
	}

	/**
	 * 完成的流程实例
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list/finished")
	public ModelAndView finishedList() {
		ModelAndView modelAndView = new ModelAndView("/leave/finished");
		List<Leave> leaves = leaveWorkflowService.findFinishProcessInstances();
		return modelAndView.addObject("leaves", leaves);
	}

	/**
	 * 签收任务
	 * 
	 * @param taskId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/task/claim/{id}")
	public String claim(@PathVariable("id") String taskId, HttpSession session) {
		String userId = UserUtil.getUserFromSession(session).getId();
		taskService.claim(taskId, userId);
		return "redirect:/leave/list/task";
	}

	/**
	 * 读取详细请假信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/detail/{id}")
	@ResponseBody
	public Leave getLeave(@PathVariable("id") Long id) {
		return leaveService.getLeave(id);
	}
}
