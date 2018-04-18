<#include "core.ftl" />
<@htmlHead title="修改${functionTitle}"/>
<@sideBar />
<div id="content">
	<@headerPart />
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="${rc.contextPath}/admin/${functionName}/list.do" class="current">${functionTitle}管理</a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" target="formCommitIframe">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-th"></i>
							</span>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered table-striped" id="">
								<tbody>
									<tr>
										<td>消息显示范围级别：</td>
										<td>
											<div style="width:62%;">
												<select name="staffRoleId" id="staffRoleId" class="selectpicker form-control">
													<#list staffRoleList as staffRole>
													<option value="${staffRole.staffRoleId?c}" <#if itemEdit.showStaff.staffRoleId == staffRole.staffRoleId>selected</#if>>${staffRole.name}</option>
													</#list>
												</select>
											</div>
										</td>
									</tr>					 
									<tr>
										<td>消息显示范围：<br/>(其下属都可以看到)</td>
										<td>
											<div style="width:62%;">
												<select name="showStaffId" id="showStaffId" class="selectpicker form-control" data-live-search="true">
													<#list staffList as staff>
													<option value="${staff.staffId?c}" <#if itemEdit.showStaffId == staff.staffId>selected</#if>>${staff.name}</option>
													</#list>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>内容：</td>
										<td>
											<textarea name="content" style="width:60%;height: 2.5rem;">${itemEdit.content}</textarea></td>
									</tr>
									
									<tr>
										<td>开始时间：</td>
										<td>
											<input type="text" name="startTimeValue" id="startTimeValue" value="${itemEdit.startTimeContent}" style="width:60%;"></td>
									</tr>
									<tr>
										<td>结束时间：</td>
										<td>
											<input type="text" name="endTimeValue" id="endTimeValue" value="${itemEdit.endTimeContent}" style="width:60%;"></td>
									</tr>
									 <tr>
										<td>优先级：</td>
										<td>
											<input type="text" name="sort" value="${itemEdit.sort}" style="width:60%;"></td>
									</tr>
									<tr>
										<td>状态：</td>
										<td>
											<div style="width:62%;">
											<select name="status" class="selectpicker form-control">
												<#list statusList as status>
												<option value="${status.key}" <#if itemEdit.status == status.key>selected</#if>>${status.value}</option>
												</#list>
											</select>
											</div>
										</td>
									</tr>
									<tr>
										<td colspan="4" style="text-align:center">
											<input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult();"></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<input type="hidden" name="${functionId}" value="${itemEdit.danmakuId?c}"/>
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
		$('#staffRoleId').change(function(){
			$.ajax({
				type: 'POST',
				url: "${rc.contextPath}/admin/staff/searchStaffChildren.do?staffRoleId="+$('#staffRoleId').val(),
				data: {},
				success:function(data,status){
					var newNode = '';
					for(var i = 0;i <data.staffList.length; i++) {
						newNode += '<option value="'+ data.staffList[i].staffId +'">' + data.staffList[i].name + '</option>';
					}
					$('#showStaffId').empty().html(newNode);
					$('#showStaffId').selectpicker('refresh');
				},
				error : function(data) {
					//...
					console.log("query error...");
				}
			});
		});
	});
</script>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />