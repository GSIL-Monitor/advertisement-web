
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@cssFile file=["page/list-order.css"] />
<@jsFile file=["page/orderDistribution.js"] />
<@topHeaderMenu />
<@sideBar />
<script>
		$(document).ready(function(){
			dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?type=1";
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
			    	"data": "relativeOrder"
			    },{
			    	"data": "statusValue"
				},{
			    	"data": null,
			        "render": function ( data, type, full, meta ) {
			        	if(data.status == 0){
			        	 	return '<div class="list-btn"><button type="button"  class="btn btn-blue" onclick="getBrokerList('+data.orderId+')" >分配给经纪人</button></div>';
			        	} else if(data.broker != null){
			        		return '经纪人：'+data.broker.realName;
			        	} else {
			        		return null;
			        	}
			        }
			    <#if hasMerchant?? && hasMerchant>
			    },{
			    	"data": null,
			        "render": function ( data, type, full, meta ) {
			        	if(data.status == 0){
			        	 	return '<div class="list-btn"><button type="button"  class="btn btn-green" data-toggle="modal" data-target="#modal-form-m"  onclick="getMerchantList('+data.orderId+')" >分配给合作方</button></div>';
			        	} else if(data.merchant != null){
			        		return '合作方：'+data.merchant.name;
			        	} else {
			        		return null;
			        	}
			        }
			    </#if>
			    }, {
			    	"data": "${functionId}",
			        "render": function ( data, type, full, meta ) {
			            return '<div class="list-btn"><a href="${rc.contextPath}/admin/${functionName}/updateWindow.do?${functionId}='+data+'"  class="btn btn-yellow" target="_blank">修改</a></div>';
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
			<span class="add">
				<button type="button" class="btn btn-white" onclick="getBrokerList()" >+全选批量分配${functionTitle}<span style="color: #ee5f5b;font-weight:bold;">给代理人</span></button>
				<#if hasMerchant?? && hasMerchant>
				<button type="button" class="btn btn-white" onclick="getMerchantList()" >+全选批量分配${functionTitle}<span style="color: #ee5f5b;font-weight:bold;">给合作方</span></button>
				</#if>
			</span>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="widget-box">
				<div class="widget-title">
					<div class="widget-title"></span>
					<h5>${functionTitle}列表</h5>
					</div>
					<@orderSearchForm/>
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
									<th>关联订单</th>
									<th>状态</th>
									<th>分配给代理人</th>
									<#if hasMerchant?? && hasMerchant>
									<th>分配给合作方</th>
									</#if>
									<th>修改</th>
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
	<div class="common-popup-tip hide" id="distributeBrokerPopup">
		<div class="close-icon" id="closeBrokerTipButton"><img src="${cdnUrl}/img/close-white.png" alt=""></div>
		<div class="tip-title" id="gridSystemModalLabel">分配<span id="userNum"></span></div>
		<input type="hidden" id="orderIdInput" value=""/>
		<input type="hidden" id="userIdInput" value=""/>
		<input type="hidden" id="userNumInput" value=""/>
		<div class="tip-body">
			<div class="staff-area" id="brokerTable">
				<table>
					<thead>
						<tr>
							<th>经济人ID</th>
							<th>经纪人姓名</th>
							<th>分配产品</th>
							<th>分配</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div>
			<div class="submit-error" id="submitErrorContent1"></div>
			<div class="submit-btn btn-green" id="quantityBrokerDistribute" onclick="getQuantityDistribution()">分配</div>
		</div>
	</div>
	<div class="common-popup-tip hide" id="distributeMerchantPopup">
		<div class="close-icon" id="closeMerchantTipButton"><img src="${cdnUrl}/img/close-white.png" alt=""></div>
		<div class="tip-title">分配<span id="userNumMerchant"></span></div>
		<input type="hidden" id="orderIdMerchantInput" value=""/>
		<input type="hidden" id="merchantIdInput" value=""/>
		<input type="hidden" id="userNumMerchantInput" value=""/>
		<div class="tip-body">
			<div class="staff-area" id="merchantTable">
				<table>
					<thead>
						<tr>
							<th>合作方ID</th>
							<th>合作方名称</th>
							<th>分配</th>
						</tr>
					</thead>
					<tbody>
						
					</tbody>
				</table>
			</div>
			<div class="submit-error" id="submitErrorContent2"></div>
			<div class="submit-btn btn-green" id="quantityMerchantDistribute" onclick="getMerchantQuantityDistribution()">分配</div>
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
