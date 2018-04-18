<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do";
		dataTableConfig.columns = [{
	      		"data": "${functionId}"
	    	}, {
		    	"data": "name"
		    }, {
		    	"data": "departmentTypeContent"
		    }, {
		    	"data": "merchant.name"
		    }, {
		    	"data": "brokerNum"
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<div class="list-btn"><a href="${rc.contextPath}/admin/broker/list.do?${functionId}='+data+'"  class="btn btn-green" target="_blank">查看代理人列表</a></div>';
		        }
		    }, {
		    	"data": "${functionId}",
		        "render": function ( data, type, full, meta ) {
		            return '<div class="list-btn"><a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-yellow" target="_blank">修改</a></div>';
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
			var searchText = $('#filterType').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?type=" + encodeURI(encodeURI(searchText));
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});

		$('#queryReset').on('click', function() {
			$('#defaultFilterText').text($('#defaultFilterVal').val());
			var searchText = $('#defaultFilterVal').attr('index');
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?type=" + encodeURI(encodeURI(searchText));
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
 
		$('#dropdownMenu li a').each(function() {
			$(this).click(function(e) {
				$('#defaultFilterText').text($(this).text());
				$('#filterType').val($(this).attr('index'));
			})
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
					<div class="filter-box">
						<div class="btn-group">
							<select name="filterType" id="filterType" class="selectpicker form-control">
								<option value="">全部</option>
			                    <#list typeMap as type>
									<option value="${type.key}">${type.value}</option>
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
				                <th>部门ID</th>
				                <th>部门名称</th>
				                <th>部门类别</th>
				                <th>所属机构</th>
				                <th>代理人数量</th>
				                <th>代理人列表</th>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />