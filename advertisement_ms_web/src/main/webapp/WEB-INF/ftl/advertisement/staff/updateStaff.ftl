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
			<a href="${rc.contextPath}/admin/${functionName}/list.do" class="current">修改${functionTitle}</a>
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
										<td style="width:30%;">用户名：</td>
										<td>
											<input type="text" name="username" style="width:60%;" value="${user.username}"></td>
									</tr>
									<tr>
										<td>密码：</td>
										<td>
											<input type="text" id="passwordStr" name="password" style="width:60%;"><a href="javascript:;" onclick="createPassword()" class="btn btn-green" style="margin-left: .1rem;">生成随机密码</a></td>
									</tr>
									<tr>
										<td>姓名：</td>
										<td>
											<input type="text" name="name" style="width:60%;" value="${itemEdit.name}"></td>
									</tr>
									<tr>
										<td>性别：</td>
										<td>
											<div style="width:62%;">
												<select name="gender" class="selectpicker form-control">
												<#list genderList as tags>
													<option value="${tags.tagsId?c}" <#if itemEdit.gender == tags.tagsId>selected</#if>>${tags.name}</option>
												</#list>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>手机号：</td>
										<td>
											<input type="text" name="mobile" style="width:60%;" value="${itemEdit.mobile}"></td>
									</tr>
									<tr>
										<td>邮箱：</td>
										<td>
											<input type="text" name="email" style="width:60%;" value="${itemEdit.email}"></td>
									</tr>
									<#if (subprojectList?? && subprojectList?size>0)>
									<tr>
										<td>所属项目：</td>
										<td>
											<div style="width:62%;">
												<select name="subprojectId" id="subprojectId" class="selectpicker form-control">
												<#list subprojectList as subproject>
													<option value="${subproject.subprojectId?c}" <#if itemEdit.subprojectId == subproject.subprojectId>selected</#if>>${subproject.name}</option>
												</#list>
												</select>
											</div>
										</td>
									</tr>
									</#if>
									<tr>
										<td>角色权限：</td>
										<td>
											<div style="width:62%;">
												<select name="staffRoleId" id="staffRoleId" class="selectpicker form-control">
													<option value="">请选择角色权限</option>
													<#list staffRoleList as staffRole>
													<option value="${staffRole.staffRoleId?c}" <#if itemEdit.staffRoleId == staffRole.staffRoleId>selected</#if>>${staffRole.name}</option>
													</#list>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>上级领导：</td>
										<td>
											<div style="width:62%;">
												<select name="parentStaffId" id="parentStaffId" class="selectpicker form-control" data-live-search="true">
													<#list staffList as staff>
													<option value="${staff.staffId?c}" <#if itemEdit.staffId == staff.staffId>selected</#if>>${staff.name}</option>
													</#list>
												</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>描述（选填）：</td>
										<td>
											<input type="text" name="description" style="width:60%;" value="${itemEdit.description}"></td>
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
				<input type="hidden" name="staffId" value="${itemEdit.staffId?c}">
			</form>
		</div>
	</div>
</div>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<script>
$('#staffRoleId').change(function(){
	$.ajax({
		type: 'POST',
		url: "${rc.contextPath}/admin/${functionName}/searchStaffByRoleId.do?staffRoleId="+$('#staffRoleId').val(),
		data: {},
		success:function(data,status){
			var staffRoleList = data.selfStaffList;
			var newNode = '';
			for(var i = 0;i <staffRoleList.length; i++) {
				newNode += '<option value="'+ staffRoleList[i].staffId +'">' + staffRoleList[i].name + '</option>';
			}
			$('#parentStaffId').empty().html(newNode);
			$('#parentStaffId').selectpicker('refresh');
		},
		error : function(data) {
			//...
			console.log("query error...");
		}
	});
});
function createPassword() {
	var seed = new Array('A','B','C','D','E','F','G','H','I','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z',
									'a','b','c','d','e','f','g','h','i','j','k','m','n','p','Q','r','s','t','u','v','w','x','y','z',
									'2','3','4','5','6','7','8','9');//数组
			seedLength = seed.length;//数组长度
			var randomPassword = '';
			for (i = 0; i < 8; i++) {
				j = Math.floor(Math.random()*seedLength);
			randomPassword += seed[j];
		}
		$("#passwordStr").attr("value",randomPassword);
}
</script>
<@htmlFoot />