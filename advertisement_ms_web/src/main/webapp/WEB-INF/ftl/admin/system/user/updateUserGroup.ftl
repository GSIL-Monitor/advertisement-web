<#include "/common/core.ftl" />
<@htmlHead title="编辑分组模块"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
$(document).ready(function(){
	dataTableConfig.ajax = "${rc.contextPath}/admin/user/queryGroups.do";
	dataTableConfig.columns = [
	    {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	return '<input type="checkbox" name="unselectRight" value="'+data+'" />';
	        }
	    },{
      		"data": "name"
    	},{
	      	"data": "description"
	    }];
	
	$('#unselectTable').DataTable(dataTableConfig);
	
	dataTableConfig = initConfig();
	dataTableConfig.ajax = "${rc.contextPath}/admin/user/queryGroupsByUsername.do?flag=in&username=${username}";
	dataTableConfig.columns = [
	    {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	return '<input type="checkbox" name="selectRight" value="'+data+'" />';
	        }
	    },{
      		"data": "name"
    	},{
	      	"data": "description"
	    }];
	$('#selectTable').DataTable(dataTableConfig);
	
});

function addSelectRights() {
	var rightIds = "";
	$("input[name='unselectRight']").each(function(checkbox){
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
	$("input[name='selectRight']").each(function(){
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
  <div id="content-header">
    <div id="breadcrumb">
        <a href="#" title="系统管理" class="tip-bottom"><i class="icon-book"></i>系统管理</a>
        <a href="${rc.contextPath}/admin/user/adminUsers.do" title="用户管理" class="tip-bottom"><i class="icon-book"></i>用户管理</a>
        <a href="#" class="current">用户分组管理</a>
      </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
    	<span style="float:right;margin:3px 8px 10px 0"><a href="javascript:;" class="btn btn-green" onclick="addSelectRights()">添加</a></span>
        <div class="widget-box">
          <div class="widget-title">
            <h5>未选权限</h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="unselectTable">
              <thead>
                <tr>
                  <th></th>
                  <th>名称</th>
                  <th>描述</th>
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
            <h5>已选权限</h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="selectTable">
              <thead>
                <tr>
                  <th></th>
                  <th>模块名称</th>
                  <th>描述</th>
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