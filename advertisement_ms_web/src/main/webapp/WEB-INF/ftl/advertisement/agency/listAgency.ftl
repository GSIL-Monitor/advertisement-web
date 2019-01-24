<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.iDisplayLength = 1000;
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do";
		dataTableConfig.columns = [
			{
		    	"data": "indirectUserId"
		    }, {
		    	"data": "inviteUserId"
		    }, {
		    	"data": "userId"
		    }, {
		    	"data": "name"
		    }, {
		    	"data": "mobile"
		    }, {
		    	"data": "productName"
		    }, {
		    	"data": "brokerage"
		    }, {
		    	"data": "statusValue"
		    }, {
		    	"data": "type"
		    }, {
		    	"data": "updateTimeValue"
		    }];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#date').change(function(){
			reload();
		});
		$('#pv').change(function(){
			reload();
		});
		
		$('#queryButton').on('click', function(){
			var queryStartTime=$('#createTimeStart').val();
			var queryEndTime=$('#createTimeEnd').val();

			
			var params ="";
			if (isNotEmpty($('#userId').val())) {
				params += "userId=" +encodeURI(encodeURI($('#userId').val())) + "&";
			}
			if (isNotEmpty($('#inviteUserId').val())) {
				params += "inviteUserId=" +encodeURI(encodeURI($('#inviteUserId').val())) + "&";
			}
			if (isNotEmpty($('#productName').val())) {
				params += "productName=" +encodeURI(encodeURI($('#productName').val())) + "&";
			}
			if (isNotEmpty($('#name').val())) {
				params += "name=" +encodeURI(encodeURI($('#name').val())) + "&";
			}
			if (isNotEmpty($('#mobile').val())) {
				params += "mobile=" +encodeURI(encodeURI($('#mobile').val()));
			}
			if (isNotEmpty($('#type').val())) {
				params += "type=" +encodeURI(encodeURI($('#type').val())) + "&";
			}
			if (isNotEmpty($('#status').val())) {
				params += "status=" +encodeURI(encodeURI($('#status').val())) + "&";
			}
			var newUrl="${rc.contextPath}/admin/${functionName}/queryAgencyDB.do?" + params;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
		
		$('#downloadButton').on('click', function(){
			var queryStartTime=$('#createTimeStart').val();
			var queryEndTime=$('#createTimeEnd').val();
		
			
			var params = "";
			
			if (isNotEmpty($('#userId').val())) {
				params += "userId=" +encodeURI(encodeURI($('#userId').val())) + "&";
			}
			if (isNotEmpty($('#inviteUserId').val())) {
				params += "inviteUserId=" +encodeURI(encodeURI($('#inviteUserId').val())) + "&";
			}
			if (isNotEmpty($('#productName').val())) {
				params += "productName=" +encodeURI(encodeURI($('#productName').val())) + "&";
			}
			if (isNotEmpty($('#mobile').val())) {
				params += "mobile=" +encodeURI(encodeURI($('#mobile').val())) + "&";
			}
			if (isNotEmpty($('#name').val())) {
				params += "name=" +encodeURI(encodeURI($('#name').val())) + "&";
			}
			if (isNotEmpty($('#status').val())) {
				params += "status=" +encodeURI(encodeURI($('#status').val())) + "&";
			}
			if (isNotEmpty($('#type').val())) {
				params += "type=" +encodeURI(encodeURI($('#type').val())) + "&";
			}
			
			var url="${rc.contextPath}/admin/${functionName}/download.do?" + params;
			 $.ajax({
                type: "POST",
                dataType: "json",
                url: url,
                data: "",
                success: function (data) {
                	var newUrl = data.path;
                	if (isNotEmpty(newUrl)){
                    	window.location.href = newUrl;
                	} else {
                		alert("无数据！");
                	}
                }
            });
		});
		
		function reload() {
			var date=$('#date').val();
			var pv=$('#pv').val();
			var newUrl="${rc.contextPath}/admin/${functionName}/queryAdvertisement.do?advertisementId=${advertisementId?default(1)}&statisticsDate="+encodeURI(encodeURI(date))+"&isPv="+pv;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		}
	});
	var checkNum = 0;
	function countChoices(obj) {
		var max = 2;
		obj.checked?checkNum++:checkNum--;
   		if(checkNum>max){
   			alert("对不起，你只能选择" + max + "个选项!");
   			checkNum--;
   			obj.checked=false;
		}
	}
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb"><a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a><a href="#" class="current">${functionTitle}列表</a></div>
		<h1>每日广告统计列表</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<div class="widget-box">
					<div class="widget-title"><span class="icon"><i class="icon-th"></i></span>
						<h5>数据列表</h5>
						<div class="filter-box">
							<table border="0">
								<tr>
									<th></th>
									<th></th>
									<th></th>
								</tr>
								<tr>
									<td>
										<div class="filter-component">
											<h6>邀请用户ID：</h6>
											<input type="text" name="inviteUserId" id="inviteUserId" placeholder="搜索邀请ID" />
										</div>	 
									</td>
									<td>
										<div class="filter-component">
											<h6>用户：</h6>
											<input type="text" name="userId" id="userId" placeholder="搜索ID" />
										</div>	 
									</td>
									<td>
										<div class="filter-component">
											<h6>产品：</h6>
											<input type="text" name="productName" id="productName" placeholder="搜索办理产品" />
										</div>	 
									</td>
									<td>
										<div class="filter-component">
											<h6>姓名：</h6>
											<input type="text" name="name" id="name" placeholder="搜索办卡姓名" />
										</div>	 
									</td>
									<td>
										<div class="filter-component">
											<h6>手机号：</h6>
											<input type="text" name="mobile" id="mobile" placeholder="搜索办卡手机号" />
										</div>	 
									</td>
									<td>
										<div class="filter-component">
											<h6>类型：</h6>
											<select name="type" id="type" class="selectpicker form-control">
											<option value="">--请选择--</option>
						                        <#list typeList as type>
						                        	<option value="${type.key}">${type.value}</option>
						                        </#list>
					                      </select>
                      					 </div>	
										
									</td>
									<td>
									    
										 <div style="width:60%;">
										 <h6>审核状态：</h6>
					                      <select name="status" id="status" class="selectpicker form-control">
					                      <option value="">--请选择--</option>
						                        <#list statusList as status>
						                        	<option value="${status.key}">${status.value}</option>
						                        </#list>
					                      </select>
                      					 </div>	 
									</td>
								</tr>
								<tr>
									<td>
										<div class="btn btn-green" id="queryButton">确定</div>
										<div class="btn btn-white" id="queryReset">重置</div>
										<td>
										<div class="btn btn-red" id="downloadButton" style = "float:right">下载</div>
										</td>
										
									</td>
								</tr>
							</table>
							</div>
						</div>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<th>间接上级用户ID</th>
			                  	<th>直接上级用户ID</th>
			                  	<th>办卡用户ID</th>
			                  	<th>办卡姓名</th>
			                  	<th>办卡手机号</th>
			                  	<th>办卡银行</th>
			                  	<th>佣金</th>
			                  	<th>状态</th>
			                  	<th>类型</th>
			                  	<th>更新时间</th>	
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


