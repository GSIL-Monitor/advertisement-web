<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
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
			<form action="${rc.contextPath}/admin/${functionName}/insert.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
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
										<td>供应商：</td>
										<td>
											<div style="width:62%;">
											<select name="supplierKey" class="selectpicker form-control" data-live-search="true">
												<#list supplierList as element>
												<option value="${element.key}">${element.name}</option>
												</#list>
											</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>名称：</td>
										<td>
											<input type="text" name="name" style="width:60%;"></td>
									</tr>
									<tr>
										<td>类型：</td>
										<td>
											<div style="width:62%;">
											<select name="type" class="selectpicker form-control">
												<#list typeList as item>
												<option value="${item.key}">${item.value}</option>
												</#list>
											</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>文件：</td>
										<td>
											<input type="file" name="file" style="width:60%;">
										</td>
									</tr>
									<tr>
										<td>数据导入模板：</td>
										<td>
											<div style="width:62%;">
											<select name="importPageId" class="selectpicker form-control">
												<#list importPageList as page>
												<option value="${page.pageId}">${page.name}</option>
												</#list>
											</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>数据导出模板：</td>
										<td>
											<div style="width:62%;">
											<select name="exportPageId" class="selectpicker form-control">
												<#list exportPageList as page>
												<option value="${page.pageId}">${page.name}</option>
												</#list>
											</select>
											</div>
										</td>
									</tr>
									<tr>
										<td>状态：</td>
										<td>
											<div style="width:62%;">
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
											<input type="submit" name="" value="提交" class=" btn btn-success" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult();"></td>
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
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />