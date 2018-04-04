<#include "/common/core.ftl" />
<@htmlHead title="分组管理">
	<@jsFile file=["jquery.treetable.js"] />
	<@cssFile file=["jquery.treetable.css"] />
</@htmlHead>
<@sideBar />
<script>
$(document).ready(function(){
	dataTableConfig.ajax = "${rc.contextPath}/admin/group/queryGroups.do";
	dataTableConfig.columns = [{
      		"data": "name"
    	}, {
	    	"data": "description"
	    }, {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	            return '<div class="list-btn"><a href="${rc.contextPath}/admin/group/insertGroupWindow.do?parentid='+data+'"  class="btn btn-green">添加分组</a></div>';
	        }
	    }, {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	return '<div class="list-btn"><a href="${rc.contextPath}/admin/group/updateGroupModules.do?groupid='+data+'"  class="btn btn-blue">编辑分组模块</a></div>';
	        }
	    }, {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	var deleteUrl = '${rc.contextPath}/admin/group/deleteGroup.do?id='+data;
	            return '<div class="list-btn"><a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a></div>';
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
  <@headerPart />
  <div id="content-header">
    <div id="breadcrumb">
        <a href="#" title="系统管理" class="tip-bottom"><i class="icon-book"></i>系统管理</a>
        <a href="#" class="current">分组管理</a>
      </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
        <div class="widget-box">
          <div class="widget-title">
            <div class="filter-box">
              <div class="btn-group">
                <input type="text" name="search" id="search" placeholder="搜索分组" />
              </div>
              <div class="btn btn-green" id="queryButton">确定</div>
              <div class="btn btn-white" id="queryReset">重置</div>
            </div>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="dataTable">
              <thead>
                <tr>
                  <th>组名</th>
                  <th>描述</th>
                  <th>添加分组</th>
                  <th>编辑分组模块</th>
                  <th>删除分组</th>
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




