package com.zhao.activiti.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zhao.activiti.demo.domain.Leave;

/**
 * 请假实体接口
 * 
 * 
 */
@Repository("leaveDao")
public interface LeaveDao extends CrudRepository<Leave, Long> {

}
