<#include "/common/core.ftl" />
<@htmlHead title="栏目管理"/>
<@sideBar />
<script>
$(document).ready(function(){
	dataTableConfig.ajax = "${rc.contextPath}/admin/menucategory/queryMenuCategorys.do";
	dataTableConfig.columns = [{
      		"data": "name"
    	}, {
	    	"data": "description"
	    }, {
	    	"data": "id",
	        "render": function ( data, type, full, meta ) {
	        	var deleteUrl = '${rc.contextPath}/admin/menucategory/deleteMenuCategory.do?id='+data;
	            return '<div class="list-btn"><a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a></div>';
	        }
	    }];
	
	var dataTable = $('#dataTable').DataTable(dataTableConfig);
	
	$('#queryButton').on('click', function(){
		var searchText=$('#search').val();
		var newUrl="${rc.contextPath}/admin/menucategory/queryMenuCategorys.do?name="+encodeURI(encodeURI(searchText));
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
        <a href="#" class="current">栏目管理</a>
        <span class="add">
          <a href="${rc.contextPath}/admin/menucategory/insertMenuCategoryWindow.do" target="_blank"><button>+添加栏目</button></a>
        </span>
      </div>
  </div>
  <div class="container-fluid">
    <div class="row-fluid">
        <div class="widget-box">
          <div class="widget-title">
            <div class="filter-box">
              <div class="btn-group">
                <input type="text" name="search" id="search" placeholder="搜索栏目" />
              </div>
              <div class="btn btn-green" id="queryButton">确定</div>
              <div class="btn btn-white" id="queryReset">重置</div>
            </div>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table" id="dataTable">
              <thead>
                <tr>
                  <th>名称</th>
                  <th>描述</th>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/menucategory/adminMenuCategorys.do" />
<@footPart />
<@htmlFoot />




