
<#include "core.ftl" />
<@htmlHead title="修改${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="${rc.contextPath}/admin/${functionName}/list.do" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="#" class="current">修改${itemEdit.typeContent}</a>
		</div>
		<h1>修改${itemEdit.typeContent}${functionTitle}</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
					<input type="hidden" name="${functionId}" value="${itemEdit.planId?c}"/>
					<input type="hidden" name="status" value="${itemEdit.status}"/>
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
										<td>计划名称：</td>
										<td>
											<input type="text" name="name" style="width:60%;" value="${itemEdit.name}"></td>
										</td>
									</tr>
									<tr>
										<td>广告链接：</td>
										<td>
											<input type="text" name="link" style="width:60%;" value="${itemEdit.link}"></td>
										</td>
									</tr>
									<tr>
										<td>预算：</td>
										<td>
											<input type="text" name="spend" style="width:60%;" value="${itemEdit.spend?c}"></td>
										</td>
									</tr>
									<tr>
										<td>日限额：</td>
										<td>
											<input type="text" name="dayLimit" style="width:60%;" value="${itemEdit.dayLimit?c}"></td>
										</td>
									</tr>
									<tr>
										<td>出价：</td>
										<td>
											<input type="text" name="bestBid" style="width:60%;" value="${itemEdit.bestBid?c}"></td>
										</td>
									</tr>
									<tr>
										<td>开始时间：</td>
										<td>
											<input type="text" name="startTimeValue" id="startTimeValue" style="width:60%;" value="${itemEdit.startTime}"></td>
									</tr>
									<tr>
										<td>结束时间：</td>
										<td>
											<input type="text" name="endTimeValue" id="endTimeValue" style="width:60%;" value="${itemEdit.endTime}"></td>
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