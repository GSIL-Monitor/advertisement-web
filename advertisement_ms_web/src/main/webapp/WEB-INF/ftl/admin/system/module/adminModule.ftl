<#include "/common/core.ftl" />
<@htmlHead title="模块管理"/>
<@sideBar />
<script>
$(document).ready(function(){
	dataTableConfig.ajax = "${rc.contextPath}/admin/module/queryModules.do";
	dataTableConfig.columns = [
	    {
      		"data": "name"
    	},{
	      	"data": "description"
	    },{
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	return '<div class="list-btn"><a href="${rc.contextPath}/admin/module/updateModuleRightWindow.do?moduleid='+data+'"  class="btn btn-blue">变更模块权限</a></div>';
	        }
	    },{
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	            var deleteUrl = '${rc.contextPath}/admin/module/deleteModule.do?id='+data;
	            return '<div class="list-btn"><a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a></div>';
	        }
	    }];
	
	var dataTable = $('#dataTable').DataTable(dataTableConfig);
	
	$('#queryButton').on('click', function(){
		var module_name=$('#module_name').val();
		var newUrl="${rc.contextPath}/admin/module/queryModules.do?name="+module_name;
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
        <a href="#" class="current">模块管理</a>
        <span class="add">
          <a href="${rc.contextPath}/admin/module/insertModuleWindow.do" target="_blank"><button>+添加模块</button></a>
        </span>
      </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
        <div class="widget-box">
          <div class="widget-title">
            <div class="filter-box">
            </div>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="dataTable">
              <thead>
                <tr>
                  <th>模块名</th>
                  <th>描述</th>
                  <th>模块权限</th>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/module/adminModules.do" />
<@footPart />
<@htmlFoot />