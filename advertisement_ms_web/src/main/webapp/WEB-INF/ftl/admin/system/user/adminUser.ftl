<#include "/common/core.ftl" />
<@htmlHead title="用户管理"/>
<@topHeaderMenu />
<@sideBar />
<script>
$(document).ready(function(){
	dataTableConfig.ajax = "${rc.contextPath}/admin/user/queryUsers.do";
	dataTableConfig.columns = [{
      		"data": "username"
    	}, {
	    	"data": "name"
    	}, {
	    	"data": "email"
    	}, {
	    	"data": "department"
    	}, {
	    	"data": "pwd_keep_time"
    	},{
	    	"data": "username",
	        "render": function ( data, type, full, meta ) {
	        	return '<div class="list-btn"><a href="${rc.contextPath}/admin/user/updateUserWindow.do?username='+data+'"  class="btn btn-green" target="_blank">修改资料</a></div>';
	        }
    	},{
	    	"data": "username",
	        "render": function ( data, type, full, meta ) {
	        	return '<div class="list-btn"><a href="${rc.contextPath}/admin/user/updatePasswordWindow.do?username='+data+'"  class="btn btn-cyan" target="_blank">修改密码</a></div>';
	        }
    	},{
	    	"data": "username",
	        "render": function ( data, type, full, meta ) {
	        	return '<div class="list-btn"><a href="${rc.contextPath}/admin/user/updateUserGroupsWindow.do?username='+data+'"  class="btn btn-blue" target="_blank">分组管理</a></div>';
	        }
	    }, {
	    	"data": "username",
	        "render": function ( data, type, full, meta ) {
	        	var deleteUrl = '${rc.contextPath}/admin/user/deleteUser.do?username='+data;
	            return '<div class="list-btn"><a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a></div>';
	        }
	    }];
	
	var dataTable = $('#dataTable').DataTable(dataTableConfig);
	
	$('#queryButton').on('click', function(){
		var searchText=$('#search').val();
		var newUrl="${rc.contextPath}/admin/user/queryUsers.do?name="+encodeURI(encodeURI(searchText));
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload();
	});
});
</script>
<div id="content">
  <@headerPart />
  <div id="content-header">
    <div id="breadcrumb">
        <a href="#" title="系统管理" class="tip-bottom"><i class="icon-book"></i>系统管理</a>
        <a href="#" class="current">用户管理</a>
        <span class="add">
          <a href="${rc.contextPath}/admin/user/insertUserWindow.do" target="_blank"><button>+添加用户</button></a>
        </span>
      </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
        <div class="widget-box">
          <div class="widget-title">
            <div class="filter-box">
              <div class="btn-group">
                <input type="text" name="search" id="search" placeholder="搜索用户" />
              </div>
              <div class="btn btn-green" id="queryButton">确定</div>
              <div class="btn btn-white" id="queryReset">重置</div>
            </div>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="dataTable">
              <thead>
                <tr>
                  <th>用户名</th>
                  <th>姓名</th>
                  <th>邮箱</th>
                  <th>所在部门</th>
                  <th>密码有效时间</th>
                  <th>修改资料</th>
                  <th>修改密码</th>
                  <th>分组管理</th>
                  <th>删除</th>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/user/adminUsers.do" />
<@footPart />
<@htmlFoot />




