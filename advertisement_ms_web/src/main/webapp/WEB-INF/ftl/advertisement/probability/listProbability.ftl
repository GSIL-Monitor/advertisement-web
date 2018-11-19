
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@sideBar />
<script>
	var channelkey = "${channel}";
	if(channelkey == ""){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?planId=${planId}";
	}else{
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?planId=${planId}&channel=${channel}";
	}
	$(document).ready(function(){
		dataTableConfig.columns = [{
			    	"data": "probabilityId"
			    },{
			    	"data": "plan.advertiser.companyName"
			    },{
			    	"data": "plan.order.name"
			    },{
			    	"data": "plan.name"
			    },{
			    	"data": "channel"
			    },{
			    	"data": "createTimeContent"
				},{
			    	"data": null,
		        	"render": function ( data, type, full, meta ) {
			        	if(data.status ==1){
			            	return "<a href='javascript:;'  target='_blank'>已审核</a>";
			        	}else if(data.status ==3){
			        		return "<a href='javascript:;'  target='_blank'>未审核</a>";
			        	}
			        }
				}];
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryButton').on('click', function(){
			var params = "";
			if (isNotEmpty($('#channel').val())) {
				params += "channel=" + encodeURI(encodeURI($('#channel').val())) + "&";
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
	    </div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
        <div class="widget-box">
          	<div class="widget-title"></span>
            	<h5>${functionTitle}列表</h5>
            	<div class="filter-box">
					<!-- <div class="btn-group">
            			<div class="filter-component">
							<h6>渠道key：</h6>
							<input type="text" name="channel" id="channel" placeholder="请输入渠道key" />
						</div>	  
					</div>
					<div class="btn btn-green" id="queryButton">确定</div>
					<div class="btn btn-white" id="queryReset">重置</div>
					  -->
				</div>
          	</div>
			<div class="widget-content nopadding">
				<table class="table table-bordered data-table" id="dataTable">
					<thead>
						<tr>
							<th>ID</th>
							<th>广告主</th>
							<th>订单名称</th>
							<th>计划名称</th>
							<th>投放渠道</th>
							<th>创建时间</th>
							<th>状态</th>
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
