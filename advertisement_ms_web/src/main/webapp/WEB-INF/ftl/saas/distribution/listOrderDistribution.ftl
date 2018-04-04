
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@cssFile file=["page/list-order.css"] />
<@topHeaderMenu />
<@sideBar />
<script>
		$(document).ready(function(){
			dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?type=1";
			dataTableConfig.columns = [{
		      		"data": "${functionId}",
		    	}, {
			    	"data": "username"
			    }, {
			    	"data": "orderCondition",
			    },{
			    	"data": "count",
			    },{
			    	"data": "createTimeContent"
			    },{
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<a href="${rc.contextPath}/admin/${functionName}/view.do?${functionId}='+data+'"  class="btn btn-cyan" target="_blank">查看详情</a>';
			        }
			    },{
			    	"data": null,
			        "render": function ( data, type, full, meta ) {
			        	if(data.status == 0){
			        		var passUrl = "${rc.contextPath}/admin/${functionName}/pass.do?${functionId}="+data.orderDistributionId
			        		var refuseUrl = "${rc.contextPath}/admin/${functionName}/refuse.do?${functionId}="+data.orderDistributionId
			            	return '<div class="list-btn"><a href="#" onclick="confirmOperation(\''+passUrl+'\')" class="btn btn-green">通过</a>  '
			            		  +'<a href="#" onclick="confirmOperation(\''+refuseUrl+'\')" class="btn btn-red">驳回</a></div>';
			            } else{
			            	return data.statusValue;
			            }
			        }
			    }];
			
			var dataTable = $('#dataTable').DataTable(dataTableConfig);
			
			$('#queryButton').on('click', function(){
				var searchContent = getSearchContent();
				var newUrl="${rc.contextPath}/admin/${functionName}/query.do?type=1&"+searchContent.substr(0,searchContent.length-1);
				dataTable.ajax.url(newUrl);
				dataTable.ajax.reload();
			});
			
			$('#queryReset').on('click', function() {
				$("input").val("");
				$("select").each(function() {
					$(this).find("option:selected").attr("selected",false);
				});
				var newUrl="${rc.contextPath}/admin/${functionName}/query.do?type=1";
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
				<div class="widget-title">
					<div class="widget-title"></span>
					<h5>${functionTitle}列表</h5>
					</div>
					<div class="filter-box">
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<tr>
									<th>分配申请ID</th>
									<th>操作人</th>
									<th>订单条件</th>
									<th>条数</th>
									<th>申请时间</th>
									<th>操作</th>
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
</div>
<script>
	$(function() {
		timer('#createTimeEnd');
		jQuery('#createTimeEnd').datetimepicker({
		  maxDate:0,
		  format:'Y-m-d',
		  onShow:function( ct ){
		   	this.setOptions({
		    maxDate:jQuery('#createTimeStart').val()?jQuery('#createTimeStart').val():false
		  	})
		  },
		  timepicker:false
		});
		timer('#createTimeStart');
		jQuery('#createTimeStart').datetimepicker({
		  maxDate:0,
		  format:'Y-m-d',
		  onShow:function( ct ){
		   	this.setOptions({
		    maxDate:jQuery('#createTimeEnd').val()?jQuery('#createTimeEnd').val():false
		  	})
		  },
		  timepicker:false
		});
	});
</script>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />
