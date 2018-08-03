<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/channel/query.do?activityId=${activityId}";
		dataTableConfig.columns = [{
			"data": "name"
		}, {
			"data": "key"
		}, {
			"data": "typeContent"
		}, {
			"data": "createTimeContent"
		}, {
			"data": "prizeCount"
		}, {
			"data": "statusContent"
		}, {
			"data": "key",
			"render": function ( data, type, full, meta ) {
					return '<a href="${rc.contextPath}/admin/${functionName}/giftList.do?activityId=${activityId}&channel='+data+'"	class="btn btn-cyan" target="_blank">查看</a>';
			}
		}, {
			"data": "${functionId}",
			"render": function ( data, type, full, meta ) {
					return '<a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"	class="btn btn-blue" target="_blank">策略</a>';
			}
		}, {
			"data": "key",
			"render": function ( data, type, full, meta ) {
					return '<a href="${rc.contextPath}/admin/${functionName}/channel/config/insertWindow.do?activityId=${activityId}&channel='+data+'" class="btn btn-blue" target="_blank">配置管理</a>';
			}
		}, {
			"data": "${functionId}",
			"render": function ( data, type, full, meta ) {
					return '<a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"	class="btn btn-blue" target="_blank">编辑</a>';
			}
		}, {
			"data": "${functionId}",
			"render": function ( data, type, full, meta ) {
				var deleteUrl = '${rc.contextPath}/admin/${functionName}/delete.do?${functionId}='+data;
					return '<a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a>';
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
	<@headerPart />
	<div id="content-header">
		<div id="breadcrumb"> 
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a> 
			<a href="#" class="current">${functionTitle}列表</a>
			<a href="#" class="current">${functionTitle}渠道列表</a>
			<span class="add">
	    		<a href="${rc.contextPath}/admin/${functionName}/allocateChannelWindow.do?activityId=${activityId}" target="_blank"><button>+添加${functionTitle}渠道</button></a>
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
							<div class="filter-component">
								<h6>日期：</h6>
								<@timeRangeSearchBar/>
							</div>
							<div class="filter-component">
								<h6>渠道名称：</h6>
								<input type="text" name="search" id="search" placeholder="搜索渠道名称" />
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
								<th>渠道名称</th>
								<th>渠道ID</th>
								<th>渠道类型</th>
								<th>上线时间</th>
								<th>奖品个数 </th>
								<th>状态</th>
								<th>查看奖品</th>
								<th>投放策略</th>
								<th>配置管理</th>
								<th>操作</th>
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




