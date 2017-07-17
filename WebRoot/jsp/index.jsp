<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="../includePage.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/index.css"/>">
<title>流程管理平台</title>
</head>
<body>
<div class="easyui-layout" class="layout">
	<div data-options="region:'north'" class="headDiv">
		<table class="headTab">
			<tr>
				<td>欢迎访问流程管理平台</td>
				<td valign="bottom" class="loginInfo">
					<s:property value="#session.uservo.username"/> |
					<a class="a1" href="<c:url value="/login.jsp"/>">退出</a>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'west',title:'控制面板',split:true" class="westDiv">
		<div class="easyui-accordion" data-options="fit:true">
		    <div title="管理员工作台">
		        <ul class="easyui-datalist" data-options="fit:true">
				    <li><div class="menu-sub" onclick="index.openProcessModal()">新建流程模板</div></li>
				    <li><div class="menu-sub" onclick="index.addTab(this)">查看流程模板</div></li>
				</ul>
		    </div>
		    <div title="个人工作台" data-options="selected:true">
		        <ul class="easyui-datalist" data-options="fit:true">
				    <li><div class="menu-sub" onclick="index.addTab(this)">新建流程</div></li>
				    <li><div class="menu-sub" onclick="index.addTab(this)">待办任务</div></li>
				    <li><div class="menu-sub" onclick="index.addTab(this)">未启动任务</div></li>
				    <li><div class="menu-sub" onclick="index.addTab(this)">未完成任务</div></li>
				    <li><div class="menu-sub" onclick="index.addTab(this)">已完成任务</div></li>
				</ul>
		    </div>
		</div>
	</div>
	<div data-options="region:'center'">
		<div id="e-tabs" class="easyui-tabs" data-options="fit:true">
		    <div title="首页"></div>
		</div>
	</div>
	<div data-options="region:'south',split:true" class="tailDiv">
	Copyright &copy; 2017 佳仕都科技有限公司 All Rights Reserved.
	</div>
</div>
<div id="processWin" class="easyui-dialog" title="创建流程模板" style="width:400px;height:200px;"
        data-options="iconCls:'icon-add',resizable:true,modal:true,closed:true,
        buttons:[{text:'保存',iconCls:'icon-save',handler:function(){index.addProcessModal();}},
        {text:'关闭',iconCls:'icon-cancel',handler:function(){$('#processWin').dialog('close');}}]">
    <form enctype="multipart/form-data" action="<c:url value="/xtgl/addProcessModal.action"/>" method="post" id="processForm">
    	<div class="fromDiv">
	        <label for="name">流程名称</label>
	        <input class="easyui-textbox" name="processName" data-options="required:true" />
	    </div>
	    <div class="fromDiv">
	        <label for="email">流程文件</label>
	        <input class="easyui-filebox" name="processFile" data-options="required:true,buttonText:'浏览'" />
	    </div>
    </form>
</div>
<script type="text/javascript" src="<c:url value="/js/index.js"/>"></script>
</body>
</html>