package com.yx.sm.frame.xtgl.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipInputStream;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yx.sm.frame.base.service.ActivitiService;
import com.yx.sm.frame.util.DateUtil;
import com.yx.sm.frame.xtgl.dao.XtDao;
import com.yx.sm.frame.xtgl.vo.CommentsVO;
import com.yx.sm.frame.xtgl.vo.LeaveVO;
import com.yx.sm.frame.xtgl.vo.UserVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 系统管理Service
 * @author yx
 */
@Service
public class XtService {
	
	private Logger logger = Logger.getLogger(XtService.class);

	@Autowired
	private XtDao xtDao;
	
	@Autowired
	private ActivitiService activitiService;
	
	public UserVO chkLogin(UserVO user) {
		return xtDao.chkLogin(user);
	}
	
	public void addProcessModal(File processFile, String processName) {
		try {
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(processFile));
			activitiService.getRepositoryService().createDeployment().name(processName)
				.addZipInputStream(zipInputStream).deploy();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Object> getFlowModalByPage(int start, int limit) {
		List<ProcessDefinition> list = activitiService.getRepositoryService()
			.createProcessDefinitionQuery().orderByProcessDefinitionKey().asc()
			.orderByProcessDefinitionVersion().asc().listPage(start, limit);
		long count = activitiService.getRepositoryService().createProcessDefinitionQuery().count();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray ja = new JSONArray();
		for (ProcessDefinition p : list) {
			JSONObject jo = new JSONObject();
			jo.put("id", p.getId());
			jo.put("name", p.getName());
			jo.put("key", p.getKey());
			Deployment deploy = activitiService.getRepositoryService()
				.createDeploymentQuery().deploymentId(p.getDeploymentId()).singleResult();
			jo.put("deployid", p.getDeploymentId());
			jo.put("deployname", deploy.getName());
			jo.put("version", p.getVersion());
			jo.put("time", DateUtil.date2String(deploy.getDeploymentTime()));
			jo.put("desc", p.getDescription());
			ja.add(jo);
		}
		map.put("rows", ja);
		map.put("total", count);
		return map;
	}
	
	public Map<String, Object> selectFlowModalByPage(int start, int limit) {
		List<ProcessDefinition> list = activitiService.getRepositoryService()
			.createProcessDefinitionQuery().orderByProcessDefinitionVersion()
			.asc().listPage(start, limit);
		Map<String, ProcessDefinition> m = new HashMap<String, ProcessDefinition>();
		for (ProcessDefinition pd : list) {
			m.put(pd.getKey(), pd);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray ja = new JSONArray();
		for (ProcessDefinition p : m.values()) {
			JSONObject jo = new JSONObject();
			jo.put("id", p.getId());
			jo.put("name", p.getName());
			jo.put("key", p.getKey());
			Deployment deploy = activitiService.getRepositoryService()
				.createDeploymentQuery().deploymentId(p.getDeploymentId()).singleResult();
			jo.put("deployid", p.getDeploymentId());
			jo.put("deployname", deploy.getName());
			jo.put("version", p.getVersion());
			jo.put("time", DateUtil.date2String(deploy.getDeploymentTime()));
			jo.put("desc", p.getDescription().split(",")[0]);
			ja.add(jo);
		}
		map.put("rows", ja);
		map.put("total", ja.size());
		return map;
	}
	
	public void delFlowModal(String deployId) {
		activitiService.getRepositoryService().deleteDeployment(deployId, true);
	}
	
	/**
	 * 保存申请单,未启动
	 * @param leavevo
	 */
	public void saveLeave(LeaveVO leavevo) {
		leavevo.setId(UUID.randomUUID().toString());
		xtDao.saveLeave(leavevo);
	}
	
	/**
	 * 保存申请单,跳过第一环节
	 * @param leavevo
	 */
	public void updateLeaveToNext(LeaveVO leavevo, String taskId) {
		activitiService.getTaskService().complete(taskId);
		xtDao.updateLeave(leavevo);
	}
	
	/**
	 * 保存申请单
	 * @param leavevo
	 */
	public void updateLeaveById(LeaveVO leavevo) {
		xtDao.updateLeave(leavevo);
	}
	
	public Map<String, Object> notStartLeave(Map<String, Object> map) {
		List<LeaveVO> list = xtDao.notStartLeave(map);
		for (LeaveVO leave : list) {
			ProcessDefinition p = activitiService.getRepositoryService()
				.createProcessDefinitionQuery().processDefinitionId(leave.getFlowdefid()).singleResult();
			leave.setFlowname(p.getName());
			leave.setFormkey(p.getDescription().split(",")[0]);
		}
		int count = xtDao.notStartLeaveTotal();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("rows", list);
		m.put("total", count);
		return m;
	}
	
	/**
	 * 启动流程,并跳过第一环节
	 * @param leavevo
	 */
	public void updLeave(LeaveVO leavevo) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("loginUserId", leavevo.getUserid());
		ProcessInstance p = activitiService.getRuntimeService()
			.startProcessInstanceById(leavevo.getFlowdefid(), leavevo.getId(), variables);
		leavevo.setFlowintid(p.getId());
		xtDao.updLeave(leavevo);
		Task t = activitiService.getTaskService().createTaskQuery()
			.processInstanceId(p.getId()).taskDefinitionKey(p.getActivityId()).singleResult();
		activitiService.getTaskService().complete(t.getId());
	}
	
	/**
	 * 待办任务
	 * @param start
	 * @param limit
	 * @return
	 */
	public Map<String, Object> toDealTask(int start, int limit, String userid) {
		List<Task> list = activitiService.getTaskService().createTaskQuery().taskAssignee(userid)
						.listPage(start, limit);
		long count = activitiService.getTaskService().createTaskQuery().taskAssignee(userid).count();
		JSONArray ja = new JSONArray();
		for (Task t : list) {
			JSONObject jo = new JSONObject();
			jo.put("id", t.getId());
			jo.put("name", t.getName());
			jo.put("assignee", getUserById(t.getAssignee()).getUsername());
			ProcessDefinition def = activitiService.getRepositoryService()
				.createProcessDefinitionQuery().processDefinitionId(t.getProcessDefinitionId()).singleResult();
			jo.put("flowname", def.getName());
			jo.put("flowdefid", def.getId());
			jo.put("flowintid", t.getProcessInstanceId());
			HistoricTaskInstance ht = activitiService.getHistoryService().createHistoricTaskInstanceQuery()
				.processInstanceId(t.getProcessInstanceId()).orderByTaskId().asc().list().get(0);
			ProcessInstance inst = activitiService.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(t.getProcessInstanceId()).singleResult();
			jo.put("businesskey", inst.getBusinessKey());
			jo.put("applyusername", getUserById(ht.getAssignee()).getUsername());
			jo.put("starttime", DateUtil.date2String(ht.getStartTime()));
			jo.put("formkey", t.getFormKey());
			Deployment dep = activitiService.getRepositoryService().createDeploymentQuery()
				.deploymentId(def.getDeploymentId()).singleResult();
			jo.put("flowdepid", dep.getId());
			jo.put("imagename", def.getDiagramResourceName());
			jo.put("xmlname", def.getResourceName());
			ja.add(jo);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", ja);
		map.put("total", count);
		return map;
	}
	
	public UserVO getUserById(String uid) {
		UserVO u = new UserVO();
		u.setId(uid);
		return xtDao.getUserById(u);
	}
	
	public LeaveVO getLeaveById(LeaveVO leavevo) {
		LeaveVO leave = xtDao.getLeaveById(leavevo);
		leave.setUsername(getUserById(leave.getUserid()).getUsername());
		return leave;
	}
	
	/**
	 * 获取图片
	 * @param deploymentId
	 * @param imageName
	 * @return
	 */
	public InputStream findImageInputStream(String deploymentId, String imageName) {
		return activitiService.getRepositoryService().getResourceAsStream(deploymentId, imageName);
	}
	
	/**
	 * 画红线
	 * @param taskId
	 * @return
	 */
	public JSONObject findCoordingByTask(String taskId) {
		JSONObject jo = new JSONObject();
		Task task = activitiService.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		String processDefinitionId = task.getProcessDefinitionId();
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) 
			activitiService.getRepositoryService().getProcessDefinition(processDefinitionId);
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance pi = activitiService.getRuntimeService().createProcessInstanceQuery()
			.processInstanceId(processInstanceId).singleResult();
		String activityId = pi.getActivityId();
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		jo.put("x", activityImpl.getX());
		jo.put("y", activityImpl.getY());
		jo.put("w", activityImpl.getWidth());
		jo.put("h", activityImpl.getHeight());
		return jo;
	}
	
	/**
	 * 开发经理审批
	 * @param vo
	 * @param taskId
	 * @param days
	 */
	public void toNext(CommentsVO vo, String taskId, int days) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("_outcome", vo.getState());
		variables.put("_applydays", days);
		activitiService.getTaskService().complete(taskId, variables);
		xtDao.saveComment(vo);
	}
	
	/**
	 * 人事经理审批
	 * @param taskId
	 */
	public void hrToNext(String taskId) {
		activitiService.getTaskService().complete(taskId);
	}
	
	public List<CommentsVO> getCommentsByBId(CommentsVO vo) {
		List<CommentsVO> list = xtDao.getCommentsByBId(vo);
		for (CommentsVO cvo : list) {
			cvo.setUsername(getUserById(cvo.getUserid()).getUsername());
		}
		return list;
	}
	
	/**
	 * 未完成任务
	 * @param start
	 * @param limit
	 * @return
	 */
	public JSONArray notCompleteTask(LeaveVO leavevo) {
		List<LeaveVO> leaveList = xtDao.getLeaveByUserId(leavevo);
		List<Task> taskList = new ArrayList<Task>();
		for (LeaveVO leave : leaveList) {
			List<Execution> exeList = activitiService.getRuntimeService().createExecutionQuery()
				.processInstanceBusinessKey(leave.getId()).orderByProcessInstanceId().desc().list();
			for (Execution exe : exeList) {
				Task t = activitiService.getTaskService().createTaskQuery()
				.executionId(exe.getId()).taskDefinitionKey(exe.getActivityId()).singleResult();
				taskList.add(t);
			}
		}
		JSONArray ja = new JSONArray();
		for (Task t : taskList) {
			JSONObject jo = new JSONObject();
			jo.put("id", t.getId());
			jo.put("name", t.getName());
			jo.put("assignee", getUserById(t.getAssignee()).getUsername());
			ProcessDefinition def = activitiService.getRepositoryService()
				.createProcessDefinitionQuery().processDefinitionId(t.getProcessDefinitionId()).singleResult();
			jo.put("flowname", def.getName());
			jo.put("flowdefid", def.getId());
			jo.put("flowintid", t.getProcessInstanceId());
			HistoricTaskInstance ht = activitiService.getHistoryService().createHistoricTaskInstanceQuery()
				.processInstanceId(t.getProcessInstanceId()).orderByTaskId().asc().list().get(0);
			ProcessInstance inst = activitiService.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(t.getProcessInstanceId()).singleResult();
			jo.put("businesskey", inst.getBusinessKey());
			jo.put("applyusername", getUserById(ht.getAssignee()).getUsername());
			jo.put("starttime", DateUtil.date2String(ht.getStartTime()));
			Deployment dep = activitiService.getRepositoryService().createDeploymentQuery()
				.deploymentId(def.getDeploymentId()).singleResult();
			jo.put("flowdepid", dep.getId());
			jo.put("imagename", def.getDiagramResourceName());
			jo.put("formkey", def.getDescription().split(",")[1]);
			jo.put("state", "1");
			ja.add(jo);
		}
		return ja;
	}
	
	/**
	 * 已完成任务
	 * @param start
	 * @param limit
	 * @return
	 */
	public JSONArray completeTask(LeaveVO leavevo) {
		List<LeaveVO> leaveList = xtDao.getLeaveByUserId(leavevo);
		List<HistoricProcessInstance> hispList = new ArrayList<HistoricProcessInstance>();
		for (LeaveVO leave : leaveList) {
			HistoricProcessInstance hisp = activitiService.getHistoryService()
				.createHistoricProcessInstanceQuery()
				.processInstanceId(leave.getFlowintid()).singleResult();
			if (hisp != null && hisp.getEndTime() != null) {
				hispList.add(hisp);
			}
		}
		JSONArray ja = new JSONArray();
		for (HistoricProcessInstance hp : hispList) {
			JSONObject jo = new JSONObject();
			ProcessDefinition def = activitiService.getRepositoryService()
				.createProcessDefinitionQuery().processDefinitionId(hp.getProcessDefinitionId()).singleResult();
			jo.put("flowname", def.getName());
			HistoricTaskInstance ht = activitiService.getHistoryService().createHistoricTaskInstanceQuery()
				.processInstanceId(hp.getId()).orderByTaskId().asc().list().get(0);
			jo.put("businesskey", hp.getBusinessKey());
			jo.put("applyusername", getUserById(ht.getAssignee()).getUsername());
			jo.put("starttime", DateUtil.date2String(ht.getStartTime()));
			jo.put("endtime", DateUtil.date2String(ht.getEndTime()));
			Deployment dep = activitiService.getRepositoryService().createDeploymentQuery()
				.deploymentId(def.getDeploymentId()).singleResult();
			jo.put("flowdepid", dep.getId());
			jo.put("imagename", def.getDiagramResourceName());
			jo.put("formkey", def.getDescription().split(",")[1]);
			jo.put("state", "0");
			jo.put("flowstate", "归档");
			ja.add(jo);
		}
		return ja;
	}
	
	public void delLeaveById(LeaveVO vo) {
		xtDao.delLeaveById(vo);
	}
	
}
