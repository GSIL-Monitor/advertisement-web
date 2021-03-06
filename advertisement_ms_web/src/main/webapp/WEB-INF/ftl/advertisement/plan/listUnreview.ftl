
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
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/reviewQuery.do?orderId=${orderId}";
		dataTableConfig.columns = [{
			    	"data": "planId"
			    },{
			    	"data": "order.name"
			    },{
			    	"data": "name"
			    },{
			    	"data": "advertiser.companyName"
			    },{
			    	"data": "createTimeContent"
				},{
			    	"data": "dayConsumed"
				},{
			    	"data": null,
		        	"render": function ( data, type, full, meta ) {
			        	if(data.status ==1){
			            	return "<a href='javascript:;'  target='_blank'>已审核</a>";
			        	}else if(data.status ==2){
			        		return "<a href='javascript:;'  target='_blank'>已审核</a>";
			        	}else if(data.status ==6){
			        		return "<a href='javascript:;'  class='' target='_blank'>驳回</a>";
			        	}else if(data.status ==3){
			        		return "<a href='javascript:;'  class='' target='_blank'>未审核</a>";
			        	}
			        }
		        },{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<div class="list-btn"><a href="${rc.contextPath}/admin/${functionName}/reviewDetails.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">审核</a></div>';
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
			var newUrl="${rc.contextPath}/admin/${functionName}/reviewQuery.do?" + params;
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
							<h6>计划名称：</h6>
							<input type="text" name="title" id="title" placeholder="请输入计划名称" />
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
							<th>计划名称</th>
							<th>广告主</th>
							<th>创建时间</th>
							<th>当日消耗</th>
							<th>状态</th>
							<th>审核</th>
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
