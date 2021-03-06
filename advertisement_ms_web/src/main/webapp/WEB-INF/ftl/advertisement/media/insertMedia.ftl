
<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="${rc.contextPath}/admin/${functionName}/list.do" class="current">${functionTitle}管理</a>
		</div>
		<h1>添加${functionTitle}</h1>
	</div>
	<div class="container-fluid">
		<hr>
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
										<input type="hidden" name="allocateType" value="0"></td>
										<tr>
											<td style="width:20%;">key：</td>
											<td>
												<input type="text" name="key" style="width:60%;"></td>
										</tr>
										<tr>
											<td>名称(必须填写)：</td>
											<td>
												<input type="text" name="name" style="width:60%;"></td>
										</tr>
										<tr>
											<td>描述：</td>
											<td>
												<input type="text" name="description" style="width:60%;"></td>
										</tr>
										<tr>
											<td>单价(填写数字)：</td>
											<td>
												<input type="text" name="unitPrice" style="width:60%;"></td>
										</tr>
										
										<tr>
											<td>宽：</td>
											<td>
												<input type="text" name="width" style="width:60%;"></td>
										</tr>
										<tr>
											<td>高：</td>
											<td>
												<input type="text" name="height" style="width:60%;"></td>
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
												<input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult(true);"></td>
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