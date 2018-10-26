<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
<@sideBar />
<div id="content">
	<@headerPart />
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="${rc.contextPath}/admin/${functionName}/list.do" class="current">${functionTitle}管理</a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<form action="${rc.contextPath}/admin/${functionName}/insert.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
				<input type="hidden" name="orderId" value="${orderId}" style="width:60%;">
				<input type="hidden" name="advertiserId" value="${advertiserId}" style="width:60%;">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"><i class="icon-th"></i>
							</span>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered table-striped" id="">
								<tbody>
									<tr>
										<td style="width:20%;">计划名称：</td>
										<td>
											<input type="text" name="name" placeholder="建议订单名称+计划名称+月份+日期" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>开始时间：</td>
										<td>
											<input type="text" name="startTimeValue" id="startTimeValue" style="width:60%;"></td>
									</tr>
									<tr>
										<td>结束时间：</td>
										<td>
											<input type="text" name="endTimeValue" id="endTimeValue" style="width:60%;"></td>
									</tr>
									<tr>
										<td>该计划预算金额（元）：</td>
										<td>
											<input type="text" name="spend" placeholder="最高预算不得超过剩余预算" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>投放总量：</td>
										<td>
											<input type="text" name="count" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>当前最高出价：</td>
										<td>
											<input type="text" name="bestBid" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>结算方式：</td>
										<td>
											<div style="width:60%;">
												<select name="quotaType" class="selectpicker form-control">
													<#list quotaTypeList as type>
														<option value="${type.key}">${type.value}</option>
													</#list>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="4" style="text-align:center">
											<input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult();">
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<script>
	$(function() {
		timer('#startTimeValue');
		$('#startTimeValue').datetimepicker({
			maxDate:0,
			onShow:function( ct ){
				this.setOptions({
					maxDate:$('#endTimeValue').val()?$('#endTimeValue').val():false
				})
			},
			step: 15,
			defaultTime: '08:00',
			format:'Y-m-d H:i'
		});
		timer('#endTimeValue');
		$('#endTimeValue').datetimepicker({
			minDate:0,
			onShow:function( ct ){
				this.setOptions({
					minDate:$('#startTimeValue').val()?$('#startTimeValue').val():false
				})
			},
			step: 15,
			defaultTime: '08:00',
			format:'Y-m-d H:i'
		});
	});
</script>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />