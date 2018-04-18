<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@cssFile file=["page/list-order.css"] />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do";
		dataTableConfig.columns = [{
		  		"data": "informationTaskId"
			}, {
				"data": "name"
			}, {
				"data": "supplier.name"
			}, {
				"data": "count"
			}, {
				"data": null,
				"render": function(data, type, full, meta) {
					return data.importProgress + "/" + data.count;
				}
			}, {
				"data": null,
				"render": function(data, type, full, meta) {
					return data.allocateProgress + "/" + data.count;
				}
			}, {
				"data": null,
				"render": function(data, type, full, meta) {
					return data.callProgress + "/" + data.count;
				}
			}, {
				"data": "typeValue"
			}, {
				"data": "statusValue"
			}, {
				"data": "createTimeContent"
			}, {
				"data": "${functionId}",
				"render": function ( data, type, full, meta ) {
					return '<div class="list-btn"><a onclick="preAllocate('+data+')"  class="btn btn-green" target="_blank">分配</a></div>';
				}
			<#if realtimeAllocate>
			}, {
				"data": "${functionId}",
				"render": function ( data, type, full, meta ) {
					return '<div class="list-btn"><a onclick="preAllocate('+data+', null, null ,3)"  class="btn btn-blue" target="_blank">制定实时策略</a></div>';
				}
			</#if>
			}, {
				"data": "${functionId}",
				"render": function ( data, type, full, meta ) {
					return '<div class="list-btn"><a href="${rc.contextPath}/admin/task/record/list.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">查看数据</a></div>';
				}
			}, {
				"data": "${functionId}",
				"render": function ( data, type, full, meta ) {
					return '<div class="list-btn"><a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-yellow" target="_blank">编辑</a></div>';
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
			var type = $('#type').val();
			var status = $('#status').val();
			var createTimeStart = $('#createTimeStart').val();
			var createTimeEnd = $('#createTimeEnd').val();
			var params = "";
			if (isNotEmpty(searchText)) {
				params = "name="+encodeURI(encodeURI(searchText)) + "&";
			}
			if (isNotEmpty(type)) {
				params += "type=" + type + "&";
			}
			if (isNotEmpty(status)) {
				params += "status=" + status + "&";
			}
			if (isNotEmpty(createTimeStart)) {
				params += "createTimeStart=" + createTimeStart + "&";
			}
			if (isNotEmpty(createTimeEnd)) {
				params += "createTimeEnd=" + createTimeEnd + "&";
			}
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?" + params;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
		
	});
</script>
<div id="content">
	<@headerPart />
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a> 
			<a href="#" class="current">${functionTitle}列表</a>
			<span class="add">
				<a href="${rc.contextPath}/admin/${functionName}/insertWindow.do" target="_blank"><button>+添加${functionTitle}</button></a>
			</span>
		</div>
	</div>
  	<div class="container-fluid">
		<div class="row-fluid">
			<div class="widget-box">
			  	<div class="widget-title">
					<h5>${functionTitle}列表</h5>
					<div class="filter-box">
						<div class="btn-group">
							<select name="type" id="type" class="selectpicker form-control">
								<option value="">选择类型</option>
								<#list typeList as type>
								<option value="${type.key}">${type.value}</option>
								</#list>
							</select>
							<select name="type" id="status" class="selectpicker form-control">
								<option value="">选择状态</option>
								<#list statusList as status>
								<option value="${status.key}">${status.value}</option>
								</#list>
							</select>
							<div class="filter-component">
								<h6>日期：</h6>
								<@timeRangeSearchBar/>
							</div>
							<div class="filter-component">
								<h6>批次名称：</h6>
								<input type="text" name="search" id="search" placeholder="搜索批次" />
							</div>
						</div>
						<div class="btn btn-green" id="queryButton">确定</div>
						<div class="btn btn-white" id="queryReset">重置</div>
					</div>
			  	</div>
				<div class="widget-content nopadding">
					<table class="table table-bordered data-table" id="dataTable">
					  	<thead>
							<tr>
							  	<th>任务编号</th>
							  	<th>批次名称</th>
							  	<th>供应商</th>
							  	<th>数据量</th>
							  	<th>导入进度</th>
							  	<th>分配进度</th>
							  	<th>拨打进度</th>
							  	<th>类型</th>
							  	<th>状态</th>
							  	<th>创建时间</th>
							  	<th>分配</th>
							  	<#if realtimeAllocate>
							  	<th>实时分配策略</th>
							  	</#if>
							  	<th>查看数据</th>
							  	<th>修改</th>
							  	<th>删除</th>
							</tr>
					  	</thead>
					  	<tbody></tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<@allocateInformationPopup/>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />