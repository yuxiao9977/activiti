package com.yx.sm.frame.xtgl.vo;

import java.io.Serializable;

public class CommentsVO implements Serializable {

	private static final long serialVersionUID = -640099721050496314L;
	
	private String id;
	private String businessid;//业务id
	private String content;//内容
	private String userid;//评论人
	private int state;//状态 0通过 1不通过
	private String createtime;//创建时间
	
	private String username;//扩展字段 评论人名称
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBusinessid() {
		return businessid;
	}
	public void setBusinessid(String businessid) {
		this.businessid = businessid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
