<#include "core.ftl" />
<@htmlHead title="数据列表"/>
<@cssFile file=["page/list-order.css", "page/detail-work-order.css"] />
<@jsFile file=["page/call/scripts/japp.js", "page/call/jbarextent.js", "page/call/jquery-easyui-1.4/jquery.easyui.min.js", "echarts.min.js"] />
<@sideBar />
<script>
	function goDetail(id) {
		if (!isLoading()) {
			showLoading();
		}
		$.ajax({
			type:"GET",
			dataType:"html",
			url: "/ms/admin/staff/workorder/detail.do?workOrderId=" + id,
			success:function(data){
				$('#pageContainer1').addClass('hide');
				$('#pageContainer2').removeClass('hide');
				$('#pageContainer2').html('');
				$('#pageContainer2').html(data);
				hideLoading();
			},
			error:function() {
				console.log('网络错误')
			}
		});
	}

	function returnToList() {
		$('#pageContainer1').removeClass('hide');
		$('#pageContainer2').addClass('hide');
	}
	$(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do";
		dataTableConfig.columns = [{
		  		"data": "workOrderId"
			}, {
				"data": "taskName"
			}, {
				"data": "informationInsurance.name"
			}, {
				"data": "informationInsurance.genderValue"
			}, {
				"data": "informationInsurance.age"
			}, {
				"data": "informationInsurance.address"
			}, {
				"data": "callRecordCount"	
			}, {
				"data": "lastCallTime"
			}, {
				"data": "nextCallBackTime"
			}, {
				"data": "statusValue"
			}, {
				"data": "createTimeContent"
			}, {
				"data": "${functionId}",
				"render": function ( data, type, full, meta ) {
					return '<div class="list-btn"><a onclick="goDetail(' + data  +')" class="btn btn-green">查看详情</a></div>';
				}
			}];
		
		var dataTable = $('#dataTable').DataTable(dataTableConfig);
		
		$('#queryButton').on('click', function(){
			var params = "";
			if (isNotEmpty($('#informationTaskId').val())) {
				params += "informationTaskId=" + $('#informationTaskId').val() + "&";
			}
			if (isNotEmpty($('#name').val())) {
				params = "name="+encodeURI(encodeURI($('#name').val())) + "&";
			}
			if (isNotEmpty($('#gender').val())) {
				params += "gender=" + $('#gender').val() + "&";
			}
			if (isNotEmpty($('#status').val())) {
				params += "status=" + $('#status').val() + "&";
			}
			if (isNotEmpty($('#createTimeStart').val())) {
				params += "startCreateTimeValue=" + $('#createTimeStart').val() + "&";
			}
			if (isNotEmpty($('#createTimeEnd').val())) {
				params += "endCreateTimeValue=" + $('#createTimeEnd').val() + "&";
			}
			if (isNotEmpty($('#startCallBackTime').val())) {
				params += "startCallBackTimeValue=" + $('#startCallBackTime').val() + "&";
			}
			if (isNotEmpty($('#endCallBackTime').val())) {
				params += "endCallBackTimeValue=" + $('#endCallBackTime').val() + "&";
			}
			var newUrl="${rc.contextPath}/admin/${functionName}/query.do?" + params;
			dataTable.ajax.url(newUrl);
			dataTable.ajax.reload();
		});
	});
</script>
<@callComponent/>
<div id="content">
	<@headerPart />
	<div id="pageContainer1">
		<div id="content-header">
			<div id="breadcrumb"> 
				<a href="#" title="外呼中心" class="tip-bottom"><i class="icon-book"></i>外呼中心</a> 
				<a href="#" class="current">数据列表</a>
			</div>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="widget-box">
			  		<div class="widget-title">
						<h5>外呼数据列表</h5>
						<div class="filter-box">
							<table border="0">
								<tr>
									<th width="20%"></th>
									<th width="20%"></th>
									<th width="20%"></th>
									<th width="20%"></th>
									<th width="20%"></th>
								</tr>
								<tr>
									<td>
										<span class="field-name">批次</span>
										<select name="informationTaskId" id="informationTaskId" class="selectpicker form-control">
											<option value="">选择批次</option>
											<#list taskList as task>
											<option value="${task.informationTaskId}">${task.name}</option>
											</#list>
										</select>
									</td>
									<td>
										<span class="field-name">客户姓名</span>
										<input type="text" name="name" id="name" placeholder="搜索姓名" />
									</td>
									<td>
										<span class="field-name">性别</span>
										<select name="gender" id="gender" class="selectpicker form-control">
											<option value="">选择性别</option>
											<#list genderList as gender>
											<option value="${gender.tagsId}">${gender.name}</option>
											</#list>
										</select>
									</td>
									<td colspan="2">
										<span class="field-name">下发日期</span>
										<@timeRangeSearchBar/>
									</td>
								</tr>
								<tr>
									<td>
										<span class="field-name">结束码</span>
										<select name="status" id="status" class="selectpicker form-control">
											<option value="">选择结束码</option>
											<#list statusList as status>
											<option value="${status.key}">${status.value}</option>
											</#list>
										</select>
									</td>
									<td colspan="2">
										<span class="field-name">回拨时间</span>
										<@timeRangeSearchBar startId="startCallBackTime" endId="endCallBackTime" hasTime="true"/>
									</td>
								</tr>
								<tr>
									<td>
										<div class="btn btn-green" id="queryButton">确定</div>
										<div class="btn btn-white" id="queryReset">重置</div>
									</td>
								</tr>
							</table>
						</div>
				  	</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="dataTable">
							<thead>
								<tr>
									<th>编号</th>
									<th>批次</th>
									<th>姓名</th>
									<th>性别</th>
									<th>年龄</th>
									<th>所在城市</th>
									<th>拨打次数</th>
									<th>最新拨打时间</th>
									<th>下次回拨时间</th>
									<th>结束码</th>
									<th>创建时间</th>
									<th>查看详情</th>
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
	<div id="pageContainer2"></div>
	<@resultTipDialog retUrl="" />
</div>
<@footPart />
<@htmlFoot />