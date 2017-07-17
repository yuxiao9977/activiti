package com.yx.sm.frame.xtgl.vo;

import java.io.Serializable;

/**
 * 用户实体类
 * @author yx
 */
public class UserVO implements Serializable {
	
	private static final long serialVersionUID = -2930072303705204130L;
	
	private String id;//ID
	private String userid;//用户名
	private String password;//密码
	private String username;//姓名
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
