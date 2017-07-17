<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%session.removeAttribute("uservo");session.invalidate();%>
<!DOCTYPE HTML>
<html>
  <head>
  	<%@ include file="includePage.jsp"%>
  	<link rel="stylesheet" type="text/css" href="<c:url value="/css/login.css"/>">
    <title>登录</title>
  </head>
  <body>
  	<div class="frameDiv">
		<div data-options="region:'north'" class="headDiv">
			流程管理平台 — 登录
		</div>
		<div data-options="region:'center'" class="bodyDiv">
			<form action="<c:url value="/xtgl/chkLogin.action"/>" method="post" id="ff">
				<div class="tdiv">
			    	<table cellpadding="5" align="center">
			    		<tr>
			    			<td><input id="yhm" name="user.userid" class="easyui-textbox loginInput" data-options="required:true,iconCls:'icon-man',prompt:'用户名'"></input></td>
			    		</tr>
			    		<tr>
			    			<td><input id="mm" name="user.password" class="easyui-passwordbox loginInput" data-options="required:true,prompt:'密码'"></input></td>
			    		</tr>
			    		<tr>
			    			<td class="error"><s:property value="objectData.msg"/></td>
			    		</tr>
			    		<tr>
			    			<td class="btnDiv">
			    				<a href="javascript:void(0)" class="easyui-linkbutton btn" data-options="iconCls:'icon-ok'" onclick="submitForm()">登录</a>
		    					<a href="javascript:void(0)" class="easyui-linkbutton btn" data-options="iconCls:'icon-clear'" onclick="clearForm()">清空</a>
			    			</td>
			    		</tr>
			    	</table>
		    	</div>
		    </form>
		</div>
	</div>
	<script type="text/javascript">
		function clearForm() {
			$("#yhm").textbox("clear");
			$("#mm").passwordbox("clear");
		}
		function submitForm() {
			var yhm = $("#yhm").textbox("getValue");
			var mm = $("#mm").passwordbox("getValue");
			if (yhm != "" && mm != "") {
				$("#ff").submit();
			}
		}
	</script>
  </body>
</html>