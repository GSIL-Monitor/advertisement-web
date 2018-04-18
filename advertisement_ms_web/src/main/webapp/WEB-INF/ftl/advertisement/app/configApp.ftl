<#include "core.ftl" />
<@htmlHead title="产品配置"/>
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
		<h1><#if appId??>${appKey}${functionTitle}配置<#else>通用${functionTitle}配置</#if></h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<form action="${rc.contextPath}/admin/${functionName}/config.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
					<input type="hidden" name="appId" value="${appId!}">
					<div class="span12">
						<div class="widget-box">
							<div class="widget-title">
								<span class="icon"><i class="icon-th"></i>
								</span>
							</div>
							<div class="widget-content nopadding">
								<table class="table table-bordered table-striped" id="">
									<tbody>
										<#list configCategoryList as categoryElement>
										 <tr style="color:#FF6347; font-size:18px;">
											<td>${categoryElement.categoryName}配置</td>
											<td></td>
										 </tr>
											<#list categoryElement.functionList as item>
												<tr>
													<td style="width:20%;">${item.name}：</td>
													<td>
														<div style="width:40%;">
															<#if item.configActionList?? >
																<select name="${item.key}" class="selectpicker form-control">
																	<#list item.configActionList as element>
																		<option value="${element.actionValue}"<#if item.defaultAction==element.actionValue>selected</#if>>${element.description}</option>
																	</#list>
																</select>
															<#else>
																<input type="text" name="${item.key}" style="width:100%;" value="${item.defaultAction}"/>
															</#if>
														</div>
													</td>
												</tr>
											</#list>
											
										</#list>
										<tr>
											<td colspan="4" style="text-align:center">
												<input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult();"></td>
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