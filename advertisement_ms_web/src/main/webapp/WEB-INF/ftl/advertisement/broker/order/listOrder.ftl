
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@cssFile file=["page/list-order.css"] />
<@jsFile file=["page/orderDistribution.js"] />
<@topHeaderMenu />
<@sideBar />
<script>
		$(document).ready(function(){
			dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?type=1&userId="+$('#userId').val();
			dataTableConfig.columns = [{
		      		"data": "${functionId}",
		      		"render": function ( data, type, full, meta ) {
		      			return data+'<input type="hidden" id="orderIds" name="orderIds" value="'+data+'"/>'
		      		}
		      	}, {
			    	"data": null,
			    	"render": function ( data, type, full, meta ) {
			    		if (data.informationInsurance != null) {
			    			return data.informationInsurance.name;
			    		}
		        		return null;
			        }
			    }, {
			    	"data": null,
			    	"render": function ( data, type, full, meta ) {
			    		if (data.informationInsurance != null) {
			    			return data.informationInsurance.genderValue;
			    		}
		        		return null;
			        }
			    }, {
			    	"data": null,
			    	"render": function ( data, type, full, meta ) {
			    		if (data.informationInsurance != null) {
		        			return data.informationInsurance.age+'岁';
		        		}
		        		return null;
			        }
			    }, {
			    	"data": null,
			    	"render": function ( data, type, full, meta ) {
			    		if (data.informationInsurance != null) {
		        			return data.informationInsurance.location;
		        		}
		        		return null;
			        }
			   	}, {
			    	"data": null,
			    	"render": function ( data, type, full, meta ) {
			    		if (data.orderProspectus != null) {
		        			return data.orderProspectus.score;
		        		}
		        		return null;
			        }
			    }, {
			    	"data": "orderSource"
			    },{
			    	"data": "createTimeContent"
			    },{
			    	"data": "statusValue"
				}];
			
			var dataTable = $('#dataTable').DataTable(dataTableConfig);
			
			$('#queryButton').on('click', function(){
				var searchContent = getSearchContent();
				var newUrl="${rc.contextPath}/admin/${functionName}/query.do?type=1&"+searchContent.substr(0,searchContent.length-1);
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
					<input hidden id="userId" value="${userId}"/>
					</div>
					<div class="widget-content nopadding">
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
