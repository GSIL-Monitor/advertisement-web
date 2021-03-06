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
										<td style="width:20%;">广告主：</td>
										<td>
										<#if advertiser??>
											${advertiser.companyName}
											<input type="hidden" name="advertiserId" value="${advertiser.advertiserId}" />
										<#else>
											<div style="width:60%;">
												<select name="advertiserId" id="advertiserId" class="selectpicker form-control">
													<#list advertiserList as advertiser>
														<option value="${advertiser.advertiserId}">${advertiser.companyName}</option>
													</#list>
												</select>
											</div>
										</#if>
										</td>
									</td>
									<tr>
										<td>广告标题：</td>
										<td>
											<input type="text" name="title" style="width:60%;"></td>
										</td>
									</tr>
									<tr>
										<td>广告描述：</td>
										<td>
											<input type="text" name="description" style="width:60%;"></td>
										</td>
									</tr>
									<tr>
										<td>广告链接：</td>
										<td>
											<input type="text" name="link" style="width:60%;"></td>
									</tr>
									<tr>
										<td>图片：</td>
										<td>
											<input type="file" name="image" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>位置：</td>
										<td>
											<div style="width:60%;">
												<select name="positionId" class="selectpicker form-control">
													<#list positionList as position>
														<option value="${position.positionId}">${position.name}</option>
													</#list>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>概率：</td>
										<td>
											<input type="text" name="probability" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>排序：</td>
										<td>
											<input type="text" name="sort" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>类型：</td>
										<td>
											<div style="width:60%;">
												<select name="type" class="selectpicker form-control">
													<#list typeList as type>
														<option value="${type.key}">${type.value}</option>
													</#list>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>数量：</td>
										<td>
											<input type="text" name="count" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>收費方式：</td>
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
										<td>单价：</td>
										<td>
											<input type="text" name="unitPrice" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>状态：</td>
										<td>
											<div style="width:60%;">
												<select name="status" class="selectpicker form-control">
													<#list statusList as status>
														<option value="${status.key}">${status.value}</option>
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