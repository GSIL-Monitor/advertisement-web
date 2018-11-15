
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@sideBar />
<script>
	function reload() {
		var newUrl="${rc.contextPath}/admin/${functionName}/query.do?advertiserId=${advertiserId}";
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload();
	}
	function change(id,status){
		var url = "";
		var message = "";
		if(status=="投放中"){
			url = "${rc.contextPath}/admin/${functionName}/pausePlan.do?${functionId}="+id;
			message="您确认要暂停计划吗";
		}else if(status=="未投放"){
			url = "${rc.contextPath}/admin/${functionName}/startPlan.do?${functionId}="+id;
			message="您确认要开启计划吗";
		}
		var r=confirm(message);
		if(r==true){
			$.ajax({
                type: "POST",
                dataType: "json",
                url: url,
                data: "",
                success: function (data) {
                	alert("修改成功");
			    	reload();
				}
        	});
		}
	}
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?orderId=${orderId}";
		dataTableConfig.columns = [ {
		    	"data": null,
		        "render": function ( data, type, full, meta ) {
		        	if(data.status ==1){
		            	return "<a href='javascript:;' onclick=\"change('"+data.planId+"','"+data.statusValue+"')\" class='btn btn-red' target='_blank'>暂停</a>";
		        	}else if(data.status ==2){
		        		return "<a href='javascript:;' onclick=\"change('"+data.planId+"','"+data.statusValue+"')\" class='btn btn-cyan' target='_blank'>开启</a>";
		        	}else {
		        		return "<a href='javascript:;'  class='' target='_blank'></a>";
		        	}
		        }
		    },{
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
			    	"data": "statusValue"
		        },{
			    	"data": "bestBid"
		        },{
			    	"data": "spend"
		        },{
			    	"data": "consumed"
		        },{
			    	"data": "remark"
		        },{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<div class="list-btn"><a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-yellow" target="_blank">修改</a></div>';
			       }
		        },{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<div class="list-btn"><a href="${rc.contextPath}/admin/${functionName}/setMaterialWindow.do?${functionId}='+data+'"  class="btn btn-green" target="_blank">设置创意</a></div>';
			       }
		        },{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<div class="list-btn"><a href="${rc.contextPath}/admin/strategy/strategyWindow.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">定向</a></div>';
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
							<th>开启/暂停</th>
							<th>ID</th>
							<th>订单名称</th>
							<th>计划名称</th>
							<th>广告主</th>
							<th>创建时间</th>
							<th>投放状态</th>
							<th>出价</th>
							<th>预算</th>
							<th>已消耗预算</th>
							<th>备注</th>
							<th>修改</th>
							<th>设置创意</th>
							<th>定向</th>
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
