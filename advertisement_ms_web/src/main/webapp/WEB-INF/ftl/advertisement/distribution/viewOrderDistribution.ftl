
<#include "core.ftl" />
<@htmlHead title="${functionTitle}详情"/>
<@cssFile file=["page/list-order.css"] />
<@topHeaderMenu />
<@sideBar />
<script>
		$(document).ready(function(){
			dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/querybind.do?type=1&orderDistributionId=${itemEdit.orderDistributionId}";
			dataTableConfig.columns = [{
		      		"data": "orderObj.orderId",
		      	}, {
			    	"data": null,
			    	"render": function ( data, type, full, meta ) {
			    		if (data.orderObj.informationInsurance != null) {
			    			return data.orderObj.informationInsurance.name;
			    		}
		        		return null;
			        }
			    }, {
			    	"data": null,
			    	"render": function ( data, type, full, meta ) {
			    		if (data.orderObj.informationInsurance != null) {
			    			return data.orderObj.informationInsurance.genderValue;
			    		}
		        		return null;
			        }
			    }, {
			    	"data": null,
			    	"render": function ( data, type, full, meta ) {
			    		if (data.orderObj.informationInsurance != null) {
		        			return data.orderObj.informationInsurance.age+'岁';
		        		}
		        		return null;
			        }
			    }, {
			    	"data": null,
			    	"render": function ( data, type, full, meta ) {
			    		if (data.orderObj.informationInsurance != null) {
		        			return data.orderObj.informationInsurance.location;
		        		}
		        		return null;
			        }
			   	}, {
			    	"data": null,
			    	"render": function ( data, type, full, meta ) {
			    		if (data.orderObj.orderRecommend != null) {
		        			return data.orderObj.orderRecommend.age.score;
		        		}
		        		return null;
			        }
			    }, {
			    	"data": "orderObj.orderSource"
			    },{
			    	"data": "orderObj.createTimeContent"
			    },{
			    	"data": null,
			    	"render": function( data, type, full,meta){
			    		if (data.orderObj.merchant!=null){
			    			return '合作方：'+data.orderObj.merchant.name;
			    		}else if (data.orderObj.broker!=null){
			    			return '经理人：'+data.orderObj.broker.realName;
			    		}
			    		return null;
			    	}
			    },{
			    	"data": "orderObj.statusValue"
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
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="#" class="current">${functionTitle}详情</a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="widget-box">
				<div class="widget-title">
					<h5>${functionTitle}详情</h5>
				</div>
				<div class="filter-box">
					<table border="0">
						<tbody>
							<tr>
								<td>订单分配ID：</td>
								<td>
									<div>
										${itemEdit.orderDistributionId}
									</div>
								</td>
								<td>操作人：</td>
								<td>
									<div>
										${itemEdit.username}
									</div>
								</td>
							</tr>
							<tr>
								<td>分配条数：</td>
								<td>
									<div>
										${itemEdit.count}
									</div>
								</td>
								<td>订单条件：</td>
								<td>
									<div>
										${itemEdit.orderCondition}
									</div>
								</td>
							</tr>
							<tr>
								<td>申请时间：</td>
								<td>
									<div>
										${itemEdit.createTime}
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<table class="table table-bordered data-table" id="dataTable">
					<thead>
						<tr>
							<th>ID</th>
							<th>客户姓名</th>
							<th>性别</th>
							<th>年龄</th>
							<th>归属地</th>
							<th>订单评分</th>
							<th>来源</th>
							<th>创建时间</th>
							<th>经理人/合作方</th>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />