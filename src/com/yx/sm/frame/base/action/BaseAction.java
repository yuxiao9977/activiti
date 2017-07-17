package com.yx.sm.frame.base.action;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("global")
@Result(name="ajax", type="json", params={"root", "objectData"})
public class BaseAction extends ActionSupport implements ServletRequestAware, 
	ServletResponseAware, ServletContextAware, SessionAware {
	
	private static final long serialVersionUID = -7845430543745340993L;
	
	protected Object objectData;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletContext context;
	protected Map<String, Object> sessionMap;
	
	protected int rows;//每页行数
	protected int page;//页码
	
	@Override
	public void setSession(Map<String, Object> map) {
		this.sessionMap = map;
	}
	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public Object getObjectData() {
		return objectData;
	}
	public int getStartRow() {
		return (this.page - 1) * this.rows;
	}
}
