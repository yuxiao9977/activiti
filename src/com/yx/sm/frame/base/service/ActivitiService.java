package com.yx.sm.frame.base.service;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivitiService {
	
	@Autowired
	private RepositoryService repositoryService;//资源服务
	@Autowired
	private RuntimeService runtimeService;//运行服务
	@Autowired
	private TaskService taskService;//任务服务
	@Autowired
	private HistoryService historyService;//历史服务
	@Autowired
	private FormService formService;//表单服务
	
	public RepositoryService getRepositoryService() {
		return repositoryService;
	}
	public RuntimeService getRuntimeService() {
		return runtimeService;
	}
	public TaskService getTaskService() {
		return taskService;
	}
	public HistoryService getHistoryService() {
		return historyService;
	}
	public FormService getFormService() {
		return formService;
	}
	
}
