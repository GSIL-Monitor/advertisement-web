<#include "/common/core.ftl" />
<@htmlHead title="分组管理">
	<@jsFile file=["jquery.treetable.js"] />
	<@cssFile file=["jquery.treetable.css"] />
</@htmlHead>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
$(document).ready(function(){
	dataTableConfig.ajax = "${rc.contextPath}/admin/user/queryMenusByUsername.do?username=${userName}&categoryid=${defaultCategoryId}";
	dataTableConfig.columns = [{
      		"data": "name"
    	}, {
	    	"data": "description"
	    }, {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	            return '<a href="${rc.contextPath}/admin/group/insertGroupWindow.do?parentid='+data+'"  class="btn btn-blue">添加分组</a>';
	        }
	    }, {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	return '<a href="${rc.contextPath}/admin/group/updateGroupModules.do?groupid='+data+'"  class="btn btn-green">编辑分组模块</a>';
	        }
	    }, {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	var deleteUrl = '${rc.contextPath}/admin/group/deleteGroup.do?id='+data;
	            return '<a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a>';
	        }
	    }];
	    
	dataTableConfig.initComplete = function(settings, json) {
	  for (var i = 0; i < json.data.length; i++){
		  var data = json.data[i];
		  var tr =  $('#dataTable').find("tr").eq(i+1);
		  tr.attr("data-tt-id", data.id);
		  tr.attr("data-tt-parent-id", data.parent_id);
	  }
	  $("#dataTable").treetable({ expandable: true });
  	}
  	
	var dataTable = $('#dataTable').DataTable(dataTableConfig);
	
	$('#queryButton').on('click', function(){
		var searchText=$('#search').val();
		var newUrl="${rc.contextPath}/admin/activity/query.do?title="+searchText;
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload();
	});
});
</script>
<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="#" title="系统管理" class="tip-bottom"><i class="icon-book"></i>系统管理</a> <a href="#" class="current">分组管理</a> </div>
    <h1>分组列表</h1>
  </div>
  <div class="container-fluid">
    <hr>
    <div class="row-fluid">
    <span style="float:right;margin:3px 8px 10px 0"><a href="${rc.contextPath}/admin/group/insertGroupWindow.do"><button class="btn btn-yellow">添加分组</button></a></span>
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>数据表格</h5>
            <div style="float:right;margin:3px 8px 10px 0">
	            
				<input type="text" name="search" id="search"/>
				<input type="button" class="btn btn-green" value="查询" style="float:right;margin:0px 0px 10px 3px" id="queryButton"/>
			</div>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="dataTable">
              <thead>
                <tr>
                  <th>名称</th>
                  <th>权限</th>
                  <th>所属栏目</th>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/group/adminGroups.do" />
<@footPart />
<@htmlFoot />




