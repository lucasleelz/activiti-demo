package com.zhao.activiti.demo.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhao.activiti.demo.dao.LeaveDao;
import com.zhao.activiti.demo.domain.Leave;

@Service
@Transactional(readOnly = true)
public class LeaveService {
	private LeaveDao leaveDao;

	@Autowired
	public void setLeaveDao(LeaveDao leaveDao) {
		this.leaveDao = leaveDao;
	}

	public Leave getLeave(Long id) {
		return leaveDao.findOne(id);
	}
	
	@Transactional(readOnly = false)
	public void saveLeave(Leave leave){
		if(leave.getId() == null){
			leave.setApplyTime(new Date());
		}
		leaveDao.save(leave);
	}

}
