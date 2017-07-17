package com.yx.sm.frame.xtgl.vo;

import java.io.Serializable;

public class LeaveVO implements Serializable {

	private static final long serialVersionUID = -2068461228274501840L;
	
	private String id;
	private String userid;//请假人id
	private String flowdefid;//流程定义id
	private String flowintid;//流程实例id
	private int days;//请假天数
	private String type;//类型
	private String content;//内容
	private String applytime;//申请时间
	private String createtime;//创建时间
	private String submittime;//提交时间
	
	private String flowname;//扩展字段 流程名称
	private String username;//扩展字段 用户名称
	private String formkey;//扩展字段 formkey
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getApplytime() {
		return applytime;
	}
	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getSubmittime() {
		return submittime;
	}
	public void setSubmittime(String submittime) {
		this.submittime = submittime;
	}
	public String getFlowintid() {
		return flowintid;
	}
	public void setFlowintid(String flowintid) {
		this.flowintid = flowintid;
	}
	public String getFlowdefid() {
		return flowdefid;
	}
	public void setFlowdefid(String flowdefid) {
		this.flowdefid = flowdefid;
	}
	public String getFlowname() {
		return flowname;
	}
	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFormkey() {
		return formkey;
	}
	public void setFormkey(String formkey) {
		this.formkey = formkey;
	}
	
}
