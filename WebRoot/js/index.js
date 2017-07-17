var index = {};
index.flowdepid = "";//部署id
index.flowdefid = "";//定义id
index.flowintid = "";//实例id
index.taskid = "";//任务id
index.flowimagename = "";//流程图名称
index.flowxmlname = "";//流程图xml名称
index.businesskey = "";//业务id
index.hisstate = "";//历史状态 0完成 1未完成
index.state = "";//状态
window.onload = function() {
};
index.addTab = function(p) {
	var title = $(p).text();
	if ($('#e-tabs').tabs('exists', title)){ 
		$('#e-tabs').tabs('select', title); 
	} else {
		$("#e-tabs").tabs('add', {
			title: title,
		    closable: true
		});
    }
	var tab = $('#e-tabs').tabs('getSelected');
	if (title == "查看流程模板") {
		tab.panel('refresh', path + 'html/flowmodallist.html');
	} else if (title == "新建流程") {
		tab.panel('refresh', path + 'html/selectflowmodal.html');
	} else if (title == "未启动任务") {
		tab.panel('refresh', path + 'html/flownotstart.html');
	} else if (title == "待办任务") {
		tab.panel('refresh', path + 'html/todealtask.html');
	} else if (title == "未完成任务") {
		tab.panel('refresh', path + 'html/notCompleteTask.html');
	} else if (title == "已完成任务") {
		tab.panel('refresh', path + 'html/completeTask.html');
	}
};

index.openProcessModal = function() {
	$("#processWin").dialog("open");
};

index.addProcessModal = function() {
	$.messager.progress();
	$("#processForm").ajaxSubmit({
		dataType:'json',//返回数据类型
		success:function(data) {
        	$.messager.progress("close");
        	common.alert("添加成功!");
        	$("#processWin").dialog("close");
        }
	});
};