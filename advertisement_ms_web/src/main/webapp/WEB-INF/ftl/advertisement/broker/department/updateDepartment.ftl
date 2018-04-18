
<#include "core.ftl" />
<@htmlHead title="修改${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a>
			<a href="#" class="current">修改${functionTitle}</a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
				<input type="hidden" name="${functionId}" value="${itemEdit.partnerId?c}"/>
				<div class="widget-box">
					<div class="widget-title">
						<h5>修改${functionTitle}</h5>
					</div>
					<table class="table table-bordered table-striped" id="">
						<tbody>
							<tr>
								<td>部门名称：</td>
								<td>
									<input type="text" name="name" style="width:60%;" <#if itemEdit.name??>value="${itemEdit.name}"</#if>></td>
							</tr>
							<tr>
								<td>部门类别：</td>
								<td>
									<div style="width:60%;">
										<select name="departmentType" class="selectpicker form-control">
											<#list typeList as type>
												<option value="${type.key}"<#if itemEdit.departmentType == type.key>selected</#if>>${type.value}</option>
											</#list>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<td>所属机构：</td>
								<td>
									<div style="width:60%;">
										<select name="departmentType" class="selectpicker form-control">
											<#list merchantList as merchant>
												<option value="${merchant.merchantId}"<#if itemEdit.merchantId == merchant.merchantId>selected</#if>>${merchant.name}</option>
											</#list>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<td>成本：</td>
								<td>
									<input type="text" name="description" style="width:60%;"<#if itemEdit.price??>value="${itemEdit.price}"</#if>>
								</td>
							</tr>
							<tr>
								<td>状态：</td>
								<td>
									<div style="width:60%;">
										<select name="status" class="selectpicker form-control">
											<#list statusList as status>
												<option value="${status.key}"<#if itemEdit.status == status.key>selected</#if>>${status.value}</option>
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
			</form>
		</div>
	</div>
</div>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />