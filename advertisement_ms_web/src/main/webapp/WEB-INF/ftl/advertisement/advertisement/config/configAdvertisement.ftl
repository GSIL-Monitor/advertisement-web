
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
		<h1>${functionTitle}管理</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<form action="${rc.contextPath}/admin/${functionName}/config.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
					<div class="span12">
						<div class="widget-box">
							<div class="widget-title">
								<span class="icon"><i class="icon-th"></i>
								</span>
							</div>
							<div class="widget-content nopadding">
								<table class="table table-bordered table-striped" id="">
									<tbody>
										<#list configList as config>
											<tr><td style="font-size: x-large;font-weight: bolder;">${config.name}</td><td></td></tr>
											<#list config.functions as function>
												<tr>
													<td style="width:20%;">${function.name}：</td>
													<td>
														<input type="text" name="${function.key}" style="width:40%;" value="<#if function.defaultAction??>${function.defaultAction}</#if>"/>
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
							广告分类提示：<#list categoryLists as category>【${category.categoryId}】${category.name}</#list>
						</div>
					</div>
				</form>
			</div>
	</div>
</div>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />