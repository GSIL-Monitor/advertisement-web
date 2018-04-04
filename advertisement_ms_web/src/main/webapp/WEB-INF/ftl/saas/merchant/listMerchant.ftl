
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do";
		dataTableConfig.columns = [{
	      		"data": "${functionId}"
	    	}, {
		    	"data": "name"
		    }, {
		    	"data": "fullName"
		    }, {
		    	"data": "shortName"
		    }, {
		    	"data": "englishName"
		    }, {
		    	"data": "telephone"
		    }, {
		    	"data": "statusValue"
		    }, {
		    	"data": "createTimeContent"
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<div class="list-btn"><a href="${rc.contextPath}/admin/${functionName}/view.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">查看详情</a></div>';
		        }
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<div class="list-btn"><a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-blue" target="_blank">修改</a></div>';
		        }
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		        	var deleteUrl = '${rc.contextPath}/admin/${functionName}/delete.do?${functionId}='+data;
		            return '<div class="list-btn"><a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a></div>';
		        }
		    }];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryButton').on('click', function(){
			var searchText=$('#search').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?name="+encodeURI(encodeURI(searchText));
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
	});
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
	      <a href="#" title="系统管理" class="tip-bottom"><i class="icon-book"></i>机构管理</a>
	      <a href="#" class="current">机构列表</a>
	      <span class="add">
	        <a href="${rc.contextPath}/admin/${functionName}//insertWindow.do" target="_blank"><button>+添加机构</button></a>
	      </span>
	    </div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="widget-box">
				<div class="widget-title">
					<div class="filter-box">
						<div class="btn-group">
							<input type="text" name="search" id="search" placeholder="搜索机构" />
						</div>
						<div class="btn btn-green" id="queryButton">确定</div>
						<div class="btn btn-white" id="queryReset">重置</div>
					</div>
				</div>
				<div class="widget-content nopadding">
					<table class="table table-bordered data-table" id="dataTable">
						<thead>
							<tr>
								<th>ID</th>
								<th>名称</th>
								<th>全名</th>
								<th>简称</th>
								<th>英文名</th>
								<th>联系信息</th>
								<th>状态</th>
								<th>创建时间</th>
								<th>详情</th>
								<th>修改</th>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />
