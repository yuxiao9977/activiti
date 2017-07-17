<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<%-- 全局CSS --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/common.css"/>">
<link rel="Shortcut Icon" type="image/x-icon" href="<c:url value="/images/logo3.png"/>">
<%-- easyui 自定义图标 --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/easy-icon.css"/>">
<%-- jquery 1.11.3 --%>
<script type="text/javascript" src="<c:url value="/jquery/jquery.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/jquery/jquery.form.js"/>"></script>
<%-- easyui1.5 --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/easyui1.5/themes/bootstrap/easyui.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/easyui1.5/themes/icon.css"/>">
<script type="text/javascript" src="<c:url value="/easyui1.5/jquery.easyui.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/easyui1.5/locale/easyui-lang-zh_CN.js"/>"></script>
<%-- 通用JS --%>
<script type="text/javascript" src="<c:url value="/js/common.js"/>"></script>
<%-- 全局 JS --%>
<script type="text/javascript">
var path = "<c:url value='/'/>".split(";")[0];
</script>
<input id="userid" type="hidden" value="<s:property value="#session.uservo.id"/>"/>
<input id="username" type="hidden" value="<s:property value="#session.uservo.username"/>"/>
<div id="flowFormWin" class="easyui-dialog" title="流程表单窗口" style="width:1000px;height:600px;"
data-options="iconCls:'icon-edit',resizable:true,modal:true,closed:true,buttons:[
{text:'确定',iconCls:'icon-ok',handler:function(){saveFlowForm();}},
{text:'关闭',iconCls:'icon-cancel',handler:function(){$('#flowFormWin').dialog('close');}}]">
</div>