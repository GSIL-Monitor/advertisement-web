<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@sideBar />
<script>
$(document).ready(function(){
	dataTableConfig.ajax = "${rc.contextPath}/admin/plan/queryCreative.do?planId=${planId}&isSelect=false";
	dataTableConfig.columns = [
	    {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	return '<input type="checkbox" name="unselectCreative" value="'+data+'" />';
	        }
	    },{
      		"data": "name"
      	},{
      		"data": "title"
    	},{
	      	"data": "description"
	    },{
	      	"data": "imageUrl",
	      	"render": function ( data, type, full, meta ) {
	        	return '<img src="'+data+'" style="max-height: 100px;"/>';
	        }
	    },{
	      	"data": "createTimeValue"
	    }];
	
	$('#unselectTable').DataTable(dataTableConfig);
	
	dataTableConfig = initConfig();
	dataTableConfig.ajax = "${rc.contextPath}/admin/plan/queryCreative.do?planId=${planId}&isSelect=true";
	dataTableConfig.columns = [
	    {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	return '<input type="checkbox" name="selectCreative" value="'+data+'" />';
	        }
	    },{
      		"data": "name"
      	},{
      		"data": "title"
    	},{
	      	"data": "description"
	    },{
	      	"data": "imageUrl",
	      	"render": function ( data, type, full, meta ) {
	        	return '<img src="'+data+'" />';
	        }
	    },{
	      	"data": "createTimeValue"
	    }];
	$('#selectTable').DataTable(dataTableConfig);
	
});

function addSelectRights() {
	var rightIds = "";
	$("input[name='unselectCreative']").each(function(checkbox){
		var checked = $(this).is(':checked');
		if (checked) {
			if (rightIds != "") {
	        	rightIds = rightIds + "," + $(this).val();
	        } else {
	        	rightIds = $(this).val();
	        }
        }
	})
	sendAjax('${rc.contextPath}/admin/user/insertUserGroups.do?username=${username}&groupIds=' + rightIds);
}

function deleteSelectRights() {
	var rightIds = "";
	$("input[name='selectCreative']").each(function(){
		var checked = $(this).is(':checked');
		if (checked) {
			if (rightIds != "") {
	        	rightIds = rightIds + "," + $(this).val();
	        } else {
	        	rightIds = $(this).val();
	        }
		}
	})
	sendAjax('${rc.contextPath}/admin/user/deleteUserGroups.do?username=${username}&groupIds=' + rightIds);
}
</script>
<div id="content">
	<@headerPart />
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a> 
			<a href="#" class="current">${functionTitle}列表</a>
      </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
    	<span style="float:right;margin:3px 8px 10px 0"><a href="javascript:;" class="btn btn-green" onclick="addSelectRights()">添加</a></span>
        <div class="widget-box">
          <div class="widget-title">
            <h5>未选创意</h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="unselectTable">
              <thead>
                <tr>
                  <th></th>
                  <th>名称</th>
                  <th>标题</th>
                  <th>描述</th>
                  <th>图片</th>
                  <th>创建日期</th>
                </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
          </div>
        </div>
        <span style="float:right;margin:3px 8px 10px 0"><a href="javascript:;"  class="btn btn-red" onclick="deleteSelectRights()">删除</a></span>
        <div class="widget-box">
          <div class="widget-title">
            <h5>已选创意</h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="selectTable">
              <thead>
                <tr>
                  <th></th>
                  <th>名称</th>
                  <th>标题</th>
                  <th>描述</th>
                  <th>图片</th>
                  <th>创建日期</th>
                </tr>
              </thead>
              <tbody>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
<@resultTipDialog retUrl="${rc.contextPath}/admin/user/updateUserGroupsWindow.do?username=${username}" />
<@footPart />
<@htmlFoot />