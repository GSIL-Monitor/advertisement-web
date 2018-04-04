
<#include "core.ftl" />
<@htmlHead title="${functionTitle}详情"/>
<@sideBar />
<div id="content">
	<@headerPart />
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"> <i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="${rc.contextPath}/admin/${functionName}/list.do" class="current">${functionTitle}详情</a>
		</div>
	</div>
	<div class="container-fluid">
			<div class="row-fluid">
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
										<td>登录用户名：</td>
										<td>${user.username}</td>
									</tr>
									<tr>
										<td>姓名：</td>
										<td>${itemEdit.name}</td>
									</tr>
									<tr>
										<td>性别：</td>
										<td>${itemEdit.genderValue}</td>
									</tr>
									<tr>
										<td>手机号：</td>
										<td>${itemEdit.mobile}</td>
									</tr>
									<tr>
										<td>邮箱：</td>
										<td>${itemEdit.email}</td>
									</tr>
									<#if (subprojectList?? && subprojectList?size>0)>
									<tr>
										<td>所属项目：</td>
										<td>${itemEdit.subproject.name}</td>
									</tr>
									</#if>
									<tr>
										<td>角色：</td>
										<td>${itemEdit.staffRole.name}</td>
									</tr>
									<tr>
										<td>上级领导：</td>
										<td>${parentStaff.name}</td>
									</tr>
									<tr>
										<td>描述：</td>
										<td>${itemEdit.description}</td>
									</tr>
									<tr>
										<td>状态：</td>
										<td>${itemEdit.statusValue}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
	</div>
</div>
<@footPart />
<@htmlFoot />