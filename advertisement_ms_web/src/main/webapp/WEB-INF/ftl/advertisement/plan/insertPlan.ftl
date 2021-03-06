<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
<@sideBar />
<div id="content">
	<@headerPart />
	<div id="content-header">
		<div id="breadcrumb">
			<a href="${rc.contextPath}/admin/${functionName}/list.do" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="#" class="current">新建${functionTitle}</a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<form action="${rc.contextPath}/admin/${functionName}/insert.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
				<input type="hidden" name="orderId" value="${orderId}">
				<input type="hidden" name="advertiserId" value="${advertiserId}">
				<div>
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
										<td style="width:20%;">投放链接：</td>
										<td>
											<input type="text" name="link" placeholder="广告跳转链接" style="width:60%;">
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
										<td>每日开启时间：</td>
										<td>
											<input type="hidden" id="dayStartTimeInput" value="${itemEdit.dayStartTime}">
											<div style="width:60%;">
												<select data-live-search="true" value="${itemEdit.dayStartTime}" name="dayStartTime" class="selectpicker form-control" id="dayStartTime">
													<option value="">请选择</option>
													<option value="00:00">00:00</option>
													<option value="01:00">01:00</option>
													<option value="02:00">02:00</option>
													<option value="03:00">03:00</option>
													<option value="04:00">04:00</option>
													<option value="05:00">05:00</option>
													<option value="06:00">06:00</option>
													<option value="07:00">07:00</option>
													<option value="08:00">08:00</option>
													<option value="09:00">09:00</option>
													<option value="10:00">10:00</option>
													<option value="11:00">11:00</option>
													<option value="12:00">12:00</option>
													<option value="13:00">13:00</option>
													<option value="14:00">14:00</option>
													<option value="15:00">15:00</option>
													<option value="16:00">16:00</option>
													<option value="17:00">17:00</option>
													<option value="18:00">18:00</option>
													<option value="19:00">19:00</option>
													<option value="20:00">20:00</option>
													<option value="21:00">21:00</option>
													<option value="22:00">22:00</option>
													<option value="23:00">23:00</option>
													<option value="24:00">24:00</option>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>每日结束时间：</td>
										<td>
											<input type="hidden" id="dayEndTimeInput" value="${itemEdit.dayEndTime}">
											<div style="width:60%;">
												<select data-live-search="true" name="dayEndTime" class="selectpicker form-control" id="dayEndTime">
													<option value="">请选择</option>
													<option value="00:00">00:00</option>
													<option value="01:00">01:00</option>
													<option value="02:00">02:00</option>
													<option value="03:00">03:00</option>
													<option value="04:00">04:00</option>
													<option value="05:00">05:00</option>
													<option value="06:00">06:00</option>
													<option value="07:00">07:00</option>
													<option value="08:00">08:00</option>
													<option value="09:00">09:00</option>
													<option value="10:00">10:00</option>
													<option value="11:00">11:00</option>
													<option value="12:00">12:00</option>
													<option value="13:00">13:00</option>
													<option value="14:00">14:00</option>
													<option value="15:00">15:00</option>
													<option value="16:00">16:00</option>
													<option value="17:00">17:00</option>
													<option value="18:00">18:00</option>
													<option value="19:00">19:00</option>
													<option value="20:00">20:00</option>
													<option value="21:00">21:00</option>
													<option value="22:00">22:00</option>
													<option value="23:00">23:00</option>
													<option value="24:00">24:00</option>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>该计划预算金额（元）：</td>
										<td>
											<input type="text" name="spend" placeholder="最高预算不得超过剩余预算" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>日限额（元）：</td>
										<td>
											<input type="text" name="dayLimit" placeholder="请输入日限金额" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>出价：</td>
										<td>
											<input type="text" name="bestBid" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>允许投放的媒体分类：</td>
										<td>
											<div style="width:60%;">
												<select multiple data-live-search="true" class="selectpicker form-control" id="allowChannelType">
													<#list channelTypeList as type>
														<option value="${type.value}">${type.name}</option>
													</#list>
												</select>
												<input type="hidden" name="allowChannelCategory" id="allowChannelTypeVal">
											</div>
										</td>
									</tr>
									<tr>
										<td>禁止投放的媒体分类：</td>
										<td>
											<div style="width:60%;">
												<select multiple data-live-search="true" class="selectpicker form-control" id="forbidChannelType">
													<#list channelTypeList as type>
														<option value="${type.value}">${type.name}</option>
													</#list>
												</select>
												<input type="hidden" name="forbidChannelCategory" id="forbidChannelTypeVal">
											</div>
										</td>
									</tr>
									<tr>
										<td>结算方式：</td>
										<td>
											<div style="width:60%;">
												<select name="chargeType" class="selectpicker form-control">
													<#list quotaTypeList as type>
														<option value="${type.key}">${type.value}</option>
													</#list>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="4" style="text-align:center">
											<input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult(true);">
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
		function initForm() {
			$('#dayStartTime').selectpicker('val', $('#dayStartTimeInput').val());
			$('#dayEndTime').selectpicker('val', $('#dayEndTimeInput').val());
		}
		initForm();
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
		timer('#dayStartTimeValue');
		$('#dayStartTimeValue').datetimepicker({
			minDate:0,
			onShow:function( ct ){
				this.setOptions({
					minDate:$('#dayStartTimeValue').val()?$('#dayStartTimeValue').val():false
				})
			},
			step: 15,
			defaultTime: '08:00',
			format:'Y-m-d H:i'
		});
		timer('#dayEndTimeValue');
		$('#dayEndTimeValue').datetimepicker({
			minDate:0,
			onShow:function( ct ){
				this.setOptions({
					minDate:$('#dayEndTimeValue').val()?$('#dayEndTimeValue').val():false
				})
			},
			step: 15,
			defaultTime: '08:00',
			format:'Y-m-d H:i'
		});
		$('#allowChannelType').on('changed.bs.select',function(e) {
			var allowChannelType = $('#allowChannelType').selectpicker('val');
  			allowChannelType = allowChannelType.join(',');
  			$('#allowChannelTypeVal').attr('value', allowChannelType);
		})
		$('#forbidChannelType').on('changed.bs.select',function(e) {
			var forbidChannelType = $('#forbidChannelType').selectpicker('val');
  			forbidChannelType = forbidChannelType.join(',');
  			$('#forbidChannelTypeVal').attr('value', forbidChannelType);
		})
	});
</script>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />