
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
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?orderId=${orderId}";
		dataTableConfig.columns = [{
			    	"data": "probabilityId"
			    },{
			    	"data": "order.name"
			    },{
			    	"data": "name"
			    },{
			    	"data": "advertiser.companyName"
			    },{
			    	"data": "排期"
			    },{
			    	"data": "createTimeContent"
				},{
			    	"data": "statusValue"
		        },{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">预览</a>';
			       }
		        },{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">修改</a>';
			       }
		        },{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">设置创意</a>';
			       }
		        },{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<a href="${rc.contextPath}/admin/strategy/strategyWindow.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">添加策略</a>';
			       }
		        }];
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryButton').on('click', function(){
			var params = "";
			if (isNotEmpty($('#advertiserId').val())) {
				params += "advertiserId=" + encodeURI(encodeURI($('#advertiserId').val())) + "&";
			}
			if (isNotEmpty($('#title').val())) {
				params += "title=" + encodeURI(encodeURI($('#title').val())) + "&";
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
	    		<a href="${rc.contextPath}/admin/${functionName}/insertAdvertisementWindow.do?advertiserId=${advertiserId}" target="_blank"><button>+添加${functionTitle}</button></a>
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
							<h6>广告名称：</h6>
							<input type="text" name="title" id="title" placeholder="输入广告名称" />
						</div>	  
					</div>
					<div class="btn-group">
            			<div style="width:60%;">
							<h6>广告主名称：</h6>
							<select name="advertiserId" id="advertiserId" class="selectpicker form-control">
								<#list advertiserList as advertiser>
									<option value="${advertiser.advertiserId}">${advertiser.companyName}</option>
								</#list>
							</select>
						</div>
					</div>
					<div class="btn-group">
            			<div style="width:60%;">
							<h6>广告类型：</h6>
							<select name="advertiserId" id="advertiserId" class="selectpicker form-control">
								<#list typeList as type>
									<option value="${type.key}">${type.value}</option>
								</#list>
							</select>
						</div>
					</div>
					<div class="btn-group">
            			<div style="width:60%;">
							<h6>广告状态：</h6>
							<select name="advertiserId" id="advertiserId" class="selectpicker form-control">
								<#list statusList as status>
									<option value="${status.key}">${status.value}</option>
								</#list>
							</select>
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
							<th>排期</th>
							<th>创建时间</th>
							<th>投放状态</th>
							<th>查看详情</th>
							<th>修改</th>
							<th>设置创意</th>
							<th>添加策略</th>
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
