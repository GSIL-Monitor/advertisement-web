
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@sideBar />
<script>
	function reload() {
		var newUrl="${rc.contextPath}/admin/${functionName}/query.do?advertiserId=${advertiserId}";
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload();
	}
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?advertiserId=${advertiserId}";
		dataTableConfig.columns = [{
			    	"data": "orderId"
			    },{
			    	"data": "name"
			    },{
			    	"data": "advertiser.companyName"
			    },{
			    	"data": "amount"
			    },{
			    	"data": "statusContent"
			    },{
			    	"data": "createTimeContent"
				},{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<div class="list-btn"><a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-yellow" target="_blank">修改</a></div>';
			       }
		        },{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<div class="list-btn"><a href="${rc.contextPath}/admin/plan/list.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">计划详情</a></div>';
			       }
		        },{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<div class="list-btn"><a href="${rc.contextPath}/admin/plan/insertWindow.do?${functionId}='+data+'"  class="btn btn-green" target="_blank">计划添加</a></div>';
			       }
		}];
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryButton').on('click', function(){
			var params = "";
			if (isNotEmpty($('#advertiserId').val())) {
				params += "advertiserId=" + encodeURI(encodeURI($('#advertiserId').val())) + "&";
			}
			if (isNotEmpty($('#title').val())) {
				params += "name=" + encodeURI(encodeURI($('#title').val())) + "&";
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
	    		<a href="${rc.contextPath}/admin/${functionName}/insertWindow.do?advertiserId=${advertiserId}" target="_blank"><button>+添加${functionTitle}</button></a>
	    	</span>
	    </div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
        <div class="widget-box">
          	<div class="widget-title"></span>
            	<h5>${functionTitle}列表</h5>
            	<div class="filter-box">
					<div class="btn-group">
            			<div class="filter-component">
							<h6>搜索：</h6>
							<input type="text" name="title" id="title" placeholder="请输入订单名称" />
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
							<th>ID</th>
							<th>订单名称</th>
							<th>广告主</th>
							<th>总预算</th>
							<th>状态</th>
							<th>创建时间</th>
							<th>修改</th>
							<th>计划详情</th>
							<th>添加计划</th>
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
