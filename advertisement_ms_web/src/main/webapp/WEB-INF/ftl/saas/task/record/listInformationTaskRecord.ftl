<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@cssFile file=["page/list-order.css"] />
<@sideBar />
<script>
	$(document).ready(function(){
		dataTableConfig.ajax = "${rc.contextPath}/admin/${functionName}/query.do?informationTaskId=${informationTaskId}";
		dataTableConfig.columns = [{
				"data": "${functionId}",
				"render": function ( data, type, full, meta ) {
					return '<div class="list-btn"><input type="checkbox" name="allocateSelect" value="'+data+'" /></div>';
				}
			}, {
				"data": "workOrder.workOrderId"
			}, {
				"data": "workOrder.informationInsurance.name"
			}, {
				"data": "workOrder.informationInsurance.genderValue"
			}, {
				"data": "workOrder.informationInsurance.age"
			}, {
				"data": "workOrder.staff.name"
			}, {
				"data": "workOrder.statusValue"
			}, {
				"data": "workOrder.callTimes"
			}, {
				"data": "statusValue"
			}, {
				"data": "allocateTimeContent"
			}, {
				"data": "createTimeContent"
			}];

		dataTable = $('#dataTable').DataTable(dataTableConfig);
		$('#queryButton').on('click', search);
		$('#selectAll').change(function(){
			$('input[name="allocateSelect"]').each(function(){
				$(this).prop('checked', $('#selectAll').prop('checked'));
			});
		});
	});
	
	var search = function(obj, sort){
		var params = getQueryCondition();
		if (isNotEmpty(sort)) {
			params += "sortCondition=" + sort + "&";
		}
		var newUrl="${rc.contextPath}/admin/${functionName}/query.do?informationTaskId=${informationTaskId}&" + params;
		dataTable.ajax.url(newUrl);
		dataTable.ajax.reload();
	};

	function getQueryCondition() {
		var params = "";
		if (isNotEmpty($('#name').val())) {
			params = "name="+encodeURI(encodeURI($('#name').val())) + "&";
		}
		if (isNotEmpty($('#gender').val())) {
			params += "gender=" + $('#gender').val() + "&";
		}
		if (isNotEmpty($('#allocateStatus').val())) {
			params += "allocateStatus=" + $('#allocateStatus').val() + "&";
		}
		if (isNotEmpty($('#status').val())) {
			params += "status=" + $('#status').val() + "&";
		}
		if (isNotEmpty($('#startAllocateTime').val())) {
			params += "startAllocateTimeValue=" + $('#startAllocateTime').val() + "&";
		}
		if (isNotEmpty($('#endAllocateTime').val())) {
			params += "startAllocateTimeValue=" + $('#endAllocateTime').val() + "&";
		}
		if (isNotEmpty($('#startCallTimes').val())) {
			params += "startCallTimes=" + $('#startCallTimes').val() + "&";
		}
		if (isNotEmpty($('#endCallTimes').val())) {
			params += "endCallTimes=" + $('#endCallTimes').val() + "&";
		}
		return params;
	}

	function allocate() {
		var ids = "";
		$('input[name="allocateSelect"]').each(function(){
			if ($(this).prop('checked')) {
				ids += $(this).attr('value') + ',';
			}
		});
		if (isEmpty(ids)) {
			TipWindow.showSingle('请选择数据后再进行分配！');
		} else {
			preAllocate(${informationTaskId}, null, ids);
		}
	}

	function preRetakeSelect() {
		var ids = "";
		$('input[name="allocateSelect"]').each(function(){
			if ($(this).prop('checked')) {
				ids += $(this).attr('value') + ',';
			}
		});
		if (isEmpty(ids)) {
			TipWindow.showSingle('请选择数据后再进行回收！');
		} else {
			preAllocate(${informationTaskId}, null, ids, 2);
		}
	}

</script>
<div id="content">
	<@headerPart />
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a> 
			<a href="#" class="current">${functionTitle}列表</a>
			<span class="add">
				<a href="javascript:;" onclick="preRetakeSelect()"><button class="btn btn-yellow">回收选中数据</button></a>
				<a href="javascript:;" onclick="preAllocate(${informationTaskId}, null, null, 2)"><button class="btn btn-red">回收全部数据</button></a>
				<a href="javascript:;" onclick="allocate()"><button class="btn btn-blue">分配选中数据</button></a>
				<a href="javascript:;" onclick="preAllocate(${informationTaskId}, getQueryCondition())"><button class="btn btn-green">分配全部数据</button></a>
			</span>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="widget-box">
				<div class="widget-title"></span>
					<h5>${functionTitle}列表</h5>
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
								<td>
									<span class="field-name">分配状态</span>
									<select name="allocateStatus" id="allocateStatus" class="selectpicker form-control">
										<option value="">选择分配状态</option>
										<#list allocateStatusList as status>
										<option value="${status.key}">${status.value}</option>
										</#list>
									</select>
								</td>
								<td>
									<span class="field-name">客户状态</span>
									<select name="status" id="status" class="selectpicker form-control">
										<option value="">选择客户状态</option>
										<#list statusList as status>
										<option value="${status.key}">${status.value}</option>
										</#list>
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<span class="field-name">分配时间</span>
									<@timeRangeSearchBar startId="startAllocateTime" endId="endAllocateTime" hasTime="true"/>
								</td>
								<td colspan="2">
									<span class="field-name">拨打次数</span>
									<input type="text" id="startCallTimes"/><h6>到</h6>
									<input type="text" id="endCallTimes"/>
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
								<th><input type="checkbox" id="selectAll" />全选</th>
								<th>数据编号</th>
								<th>姓名</th>
								<th>性别</th>
								<th>年龄</th>
								<th>当前所属人员</th>
								<th class="sorting" value="w.status">拨打进度</th>
								<th class="sorting" value="call_times">拨打次数</th>
								<th class="sorting" value="t.status">分配状态</th>
								<th class="sorting" value="allocate_time">分配时间</th>
								<th class="sorting" value="w.create_time">数据创建时间</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<@allocateInformationPopup/>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />