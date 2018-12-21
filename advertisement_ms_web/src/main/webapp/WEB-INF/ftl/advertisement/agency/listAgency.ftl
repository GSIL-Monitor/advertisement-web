
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
		    	"data": "${functionId}"
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
		    	"data": "status"
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
		
			var isAdvertiser=document.getElementById("isAdvertiser").checked;
			var isPosition=document.getElementById("isPosition").checked;
			var isDate=document.getElementById("isDate").checked;
			
			var params = "isAdvertiser=" + isAdvertiser + "&isPosition=" + isPosition + "&isDate=" + isDate + "&";
			if (isNotEmpty($('#createTimeStart').val())) {
				params += "queryStartTime=" + encodeURI(encodeURI($('#createTimeStart').val())) + "&";
			}
			if (isNotEmpty($('#createTimeEnd').val())) {
				params += "queryEndTime="+encodeURI(encodeURI($('#createTimeEnd').val())) + "&";
			}
			if (isNotEmpty($('#name').val())) {
				params += "companyName=" +encodeURI(encodeURI($('#name').val())) + "&";
			}
			if (isNotEmpty($('#positionName').val())) {
				params += "positionName=" +encodeURI(encodeURI($('#positionName').val())) + "&";
			}
			var newUrl="${rc.contextPath}/admin/${functionName}/queryStatisticFromDB.do?" + params;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
		
		$('#downloadButton').on('click', function(){
			var queryStartTime=$('#createTimeStart').val();
			var queryEndTime=$('#createTimeEnd').val();
		
			var isAdvertiser=document.getElementById("isAdvertiser").checked;
			var isPosition=document.getElementById("isPosition").checked;
			var isDate=document.getElementById("isDate").checked;
			
			var params = "isAdvertiser=" + isAdvertiser + "&isPosition=" + isPosition + "&isDate=" + isDate + "&";
			if (isNotEmpty($('#createTimeStart').val())) {
				params += "queryStartTime=" + encodeURI(encodeURI($('#createTimeStart').val())) + "&";
			}
			if (isNotEmpty($('#createTimeEnd').val())) {
				params += "queryEndTime="+encodeURI(encodeURI($('#createTimeEnd').val())) + "&";
			}
			if (isNotEmpty($('#userId').val())) {
				params += "userId=" +encodeURI(encodeURI($('#userId').val())) + "&";
			}
			if (isNotEmpty($('#positionName').val())) {
				params += "positionName=" +encodeURI(encodeURI($('#positionName').val())) + "&";
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
											<h6>日期：</h6>
											<@timeRangeSearchBar/>
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
											<h6>银行：</h6>
											<input type="text" name="productName" id="productName" placeholder="搜索银行" />
										</div>	 
									</td>
									
									<td>
										<div class="filter-component">
											<h6>类型：</h6>
											<input type="text" name="type" id="type" placeholder="类型" />
										</div>	 
									</td>
								</tr>
								<tr>
									<td>
				            			<div class="btn-group">
				            				  <h6>合并广告主：</h6><input type="checkbox" name="box1" id="isAdvertiser" onClick="countChoices(this)">
				            				  <h6>合并位置：</h6><input type="checkbox" name="box2" id="isPosition" onClick="countChoices(this)">
				            				  <h6>合并日期：</h6><input type="checkbox" name="box3" id="isDate" onClick="countChoices(this)">
										</div>
									</td>
									<td>
									</td>
									<td>
										<div class="btn btn-green" id="queryButton">确定</div>
										<div class="btn btn-white" id="queryReset">重置</div>
										<div class="btn btn-red" id="downloadButton" style = "float:right">下载</div>
									</td>
								</tr>
							</table>
							</div>
						</div>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<th>ID</th>
			                  	<th>上级用户ID</th>
			                  	<th>用户ID</th>
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


