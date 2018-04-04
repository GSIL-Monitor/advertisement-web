<#include "/common/core.ftl" />
<@htmlHead title="菜单管理">
	<@jsFile file=["jquery.treetable.js"] />
	<@cssFile file=["jquery.treetable.css"] />
</@htmlHead>
<@sideBar />
<script>
$(document).ready(function(){
	
	dataTableConfig.ajax = "${rc.contextPath}/admin/menu/queryMenus.do?categoryid=${defaultCategoryId}";
	dataTableConfig.columns = [{
      		"data": "name"
    	}, {
	    	"data": "right_name"
	    }, {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	return '<div class="list-btn"><a href="'+insertMenuWindow(data, 'insertDMenuWindow')+'"  class="btn btn-green">添加目录菜单</a></div>';
	        }
	    }, {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	return '<div class="list-btn"><a href="'+insertMenuWindow(data, 'insertFMenuWindow')+'"  class="btn btn-blue">添加功能菜单</a></div>';
	        }
	    }, {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	var deleteUrl = '${rc.contextPath}/admin/menu/deleteMenu.do?id='+data;
	            return '<div class="list-btn"><a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a></div>';
	        }
	    }];
	    
	dataTableConfig.initComplete = function(settings, json) {
		enableTreetable(json);
  	}
  	
	var dataTable = $('#dataTable').DataTable(dataTableConfig);
	
	$('#queryButton').on('click', function(){
		var categoryId=$('#categoryId').val();
		var newUrl="${rc.contextPath}/admin/menu/queryMenus.do?categoryid="+categoryId;
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload(function(json){
			enableTreetable(json);
		});
	});
	
	function enableTreetable(json){
		for (var i = 0; i < json.data.length; i++){
		  var data = json.data[i];
		  var tr =  $('#dataTable').find("tr").eq(i+1);
		  tr.attr("data-tt-id", data.id);
		  tr.attr("data-tt-parent-id", data.parent_id);
	  	}
	  	$("#dataTable").treetable({ expandable: true }, true);
	  	$("#dataTable").treetable("expandAll");
	}
	
	function jump(href){
		window.location.href=href;
	}
});
function insertMenuWindow(data, menu){
	var categoryId=$('#categoryId').val();
    return '${rc.contextPath}/admin/menu/'+menu+'.do?parentId='+data+'&categoryid='+categoryId;
}
</script>
<div id="content">
  <@headerPart />
  <div id="content-header">
    <div id="breadcrumb">
        <a href="#" title="系统管理" class="tip-bottom"><i class="icon-book"></i>系统管理</a>
        <a href="#" class="current">菜单管理</a>
        <span class="add">
          <a href="javascript:;" onclick="window.location.href=insertMenuWindow('', 'insertDMenuWindow')" target="_blank"><button>+添加目录菜单</button></a>
        </span>
        <span class="add">
          <a href="javascript:;" onclick="window.location.href=insertMenuWindow('', 'insertFMenuWindow')" target="_blank"><button>+添加功能菜单</button></a>
        </span>
      </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
        <div class="widget-box">
          <div class="widget-title">
            <div class="filter-box">
              <div class="btn-group">
                <select name="categoryId" id="categoryId" class="selectpicker form-control">
                  <option value="">全部</option>
                    <#list categorys as category>
                      <option value="${category.id}">${category.name}</option>
                    </#list>
                </select>
              </div>
              <div class="btn btn-green" id="queryButton">确定</div>
              <div class="btn btn-white" id="queryReset">重置</div>
            </div>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="dataTable">
              <thead>
                <tr>
                  <th>菜单名</th>
                  <th>对应权限</th>
                  <th>添加目录菜单</th>
                  <th>添加功能菜单</th>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/menu/adminMenus.do" />
<@footPart />
<@htmlFoot />




