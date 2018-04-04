<#include "core.ftl" />
<@htmlHead title="${functionTitle}获客流程列表"/>
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/procedure/query.do?channel=${channel.key}";
		dataTableConfig.columns = [{
			"data": "procedureId"
		}, {
			"data": "channel"
		}, {
			"data": "description"
		}, {
			"data": "imageUrl",
			"render": function ( data, type, full, meta ) {
				return '<img src="' + data + '" style="height:3rem;"/>';
			}
		}, {
			"data": null,
			"render": function ( data, type, full, meta ) {
				return '<input type="text" style="width:20px;" id="sort'+data.procedureId+'" value="'+data.sort+'"/><a href="#" onclick="updateSort(\'#sort'+data.procedureId+'\', \''+data.procedureId+'\')"  class="btn btn-success">修改</a>';
			}
		}, {
			"data": "createTimeContent"
		}, {
			"data": "procedureId",
			"render": function ( data, type, full, meta ) {
					return '<a href="${rc.contextPath}/admin/${functionName}/procedure/updateWindow.do?procedureId='+data+'"	class="btn btn-blue" target="_blank">修改</a>';
			}
		}, {
			"data": "procedureId",
			"render": function ( data, type, full, meta ) {
				var deleteUrl = '${rc.contextPath}/admin/${functionName}/procedure/delete.do?procedureId='+data;
					return '<a href="#" class="btn btn-red" onclick="confirmDelete(\''+deleteUrl+'\');">删除</a>';
			}
		}];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryButton').on('click', function(){
			var searchText=$('#search').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/procedure/query.do?channel=${channel.key}&name="+encodeURI(encodeURI(searchText));
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
	});
	function updateSort(textId, procedureId) {
		$.ajax({
			type : 'POST',
			url : '${rc.contextPath}/admin/${functionName}/procedure/update.do',
			data : {
				sort : $(textId).val(),
				procedureId : procedureId
			},
			dataType : 'json',
			success : function(data) {
				
			}
		});
	}
</script>
<div id="content">
	<@headerPart />
	<div id="content-header">
		<div id="breadcrumb"> 
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}获客流程管理</a> 
			<a href="#" class="current">${functionTitle}获客流程列表</a>
			<span class="add">
				<a href="${rc.contextPath}/admin/${functionName}/procedure/insertWindow.do?channel=${channel.key}" target="_blank"><button>+添加${functionTitle}获客流程</button></a>
			</span>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="widget-box">
				<div class="widget-title">
					<h5>${functionTitle}列表</h5>
					<div class="filter-box">
					</div>
				</div>
				<div class="widget-content nopadding">
					<table class="table table-bordered data-table" id="dataTable">
						<thead>
							<tr>
								<th>获客流程ID</th>
								<th>渠道编号</th>
								<th>描述</th>
								<th>流程图</th>
								<th>排序</th>
								<th>创建时间</th>
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




