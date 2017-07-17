package com.yx.sm.frame.xtgl.action;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.yx.sm.frame.base.action.BaseAction;
import com.yx.sm.frame.xtgl.service.XtService;
import com.yx.sm.frame.xtgl.vo.CommentsVO;
import com.yx.sm.frame.xtgl.vo.LeaveVO;
import com.yx.sm.frame.xtgl.vo.UserVO;

import net.sf.json.JSONObject;

/**
 * 系统管理
 * @author yuxiao
 */
@Namespace("/xtgl")
@Result(name="toIndex", location="/jsp/index.jsp")
public class XtAction extends BaseAction {
	
	private static final long serialVersionUID = 5714364771227197377L;

	private Logger logger = Logger.getLogger(XtAction.class);
	
	@Autowired
	private XtService xtService;
	
	private UserVO user;
	
	private File processFile;
	
	private LeaveVO leavevo;
	
	private CommentsVO commentsvo;
	
	/**
	 * 用户名密码查询用户
	 * @return
	 */
	@Action("chkLogin")
	public String chkLogin() {
		UserVO uservo = xtService.chkLogin(user);
		if (uservo == null) {
			JSONObject jo = new JSONObject();
			jo.put("msg", "用户名或密码不正确");
			objectData = jo;
			return "login";
		}
		sessionMap.put("uservo", uservo);
		return "toIndex";
	}
	
	/**
	 * 添加流程模板
	 * @return
	 */
	@Action("addProcessModal")
	public String addProcessModal() {
		xtService.addProcessModal(processFile, request.getParameter("processName"));
		return "ajax";
	}
	
	/**
	 * 查询模板
	 * @return
	 */
	@Action("getFlowModalByPage")
	public String getFlowModalByPage() {
		objectData = xtService.getFlowModalByPage(getStartRow(), rows);
		return "ajax";
	}
	
	/**
	 * 查询可用模板
	 * @return
	 */
	@Action("selectFlowModalByPage")
	public String selectFlowModalByPage() {
		objectData = xtService.selectFlowModalByPage(getStartRow(), rows);
		return "ajax";
	}
	
	/**
	 * 删除流程模板
	 * @return
	 */
	@Action("delFlowModal")
	public String delFlowModal() {
		xtService.delFlowModal(request.getParameter("deployId"));
		objectData = "删除成功";
		return "ajax";
	}
	
	/**
	 * 保存请假单
	 * @return
	 */
	@Action("saveLeave")
	public String saveLeave() {
		xtService.saveLeave(leavevo);
		objectData = "保存成功";
		return "ajax";
	}
	
	@Action("notStartLeave")
	public String notStartLeave() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startRow", getStartRow());
		map.put("rows", rows);
		objectData = xtService.notStartLeave(map);
		return "ajax";
	}
	
	@Action("startFlow")
	public String startFlow() {
		xtService.updLeave(leavevo);
		objectData = "启动成功";
		return "ajax";
	}
	
	@Action("toDealTask")
	public String toDealTask() {
		UserVO u = (UserVO) sessionMap.get("uservo");
		objectData = xtService.toDealTask(getStartRow(), rows, u.getId());
		return "ajax";
	}
	
	@Action("getLeaveById")
	public String getLeaveById() {
		objectData = xtService.getLeaveById(leavevo);
		return "ajax";
	}
	
	@Action("viewImage")
	public String viewImage() throws Exception {
		String deploymentId = request.getParameter("depId");
		String imageName = request.getParameter("imageName");
		InputStream in = xtService.findImageInputStream(deploymentId, imageName);
		OutputStream out = response.getOutputStream();
		for (int b = -1; (b = in.read()) != -1;) {
			out.write(b);
		}
		out.close();
		in.close();
		return null;
	}
	
	@Action("findCoordingByTask")
	public String findCoordingByTask() {
		objectData = xtService.findCoordingByTask(request.getParameter("taskid"));
		return "ajax";
	}
	
	@Action("toNext")
	public String toNext() {
		commentsvo.setId(UUID.randomUUID().toString());
		xtService.toNext(commentsvo, request.getParameter("taskid"), 
				Integer.parseInt(request.getParameter("days")));
		objectData = "提交成功";
		return "ajax";
	}
	
	@Action("updateLeaveToNext")
	public String updateLeaveToNext() {
		xtService.updateLeaveToNext(leavevo, request.getParameter("taskid"));
		objectData = "提交成功";
		return "ajax";
	}
	
	@Action("updateLeaveById")
	public String updateLeaveById() {
		xtService.updateLeaveById(leavevo);
		objectData = "保存成功";
		return "ajax";
	}
	
	@Action("hrToNext")
	public String hrToNext() {
		xtService.hrToNext(request.getParameter("taskid"));
		objectData = "提交成功";
		return "ajax";
	}
	
	@Action("getCommentsByBId")
	public String getCommentsByBId() {
		objectData = xtService.getCommentsByBId(commentsvo);
		return "ajax";
	}
	
	@Action("notCompleteTask")
	public String notCompleteTask() {
		objectData = xtService.notCompleteTask(leavevo);
		return "ajax";
	}
	
	@Action("completeTask")
	public String completeTask() {
		objectData = xtService.completeTask(leavevo);
		return "ajax";
	}
	
	@Action("delLeaveById")
	public String delLeaveById() {
		xtService.delLeaveById(leavevo);
		return "ajax";
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public File getProcessFile() {
		return processFile;
	}

	public void setProcessFile(File processFile) {
		this.processFile = processFile;
	}

	public LeaveVO getLeavevo() {
		return leavevo;
	}

	public void setLeavevo(LeaveVO leavevo) {
		this.leavevo = leavevo;
	}

	public CommentsVO getCommentsvo() {
		return commentsvo;
	}

	public void setCommentsvo(CommentsVO commentsvo) {
		this.commentsvo = commentsvo;
	}
	
}
