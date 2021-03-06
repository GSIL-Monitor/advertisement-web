
<#include "core.ftl" />
<@htmlHead title="修改${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="#" class="current">添加${functionTitle}</a>
		</div>
		<h1>修改${functionTitle}</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
					<input type="hidden" name="${functionId}" value="${itemEdit.channelId?c}"/>
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
											<td style="width:20%;">key：</td>
											<td>
												<input type="text" name="key" style="width:60%;" value="${itemEdit.key}"></td>
										</tr>
										<tr>
											<td>名称（必须填写）：</td>
											<td>
												<input type="text" name="name" style="width:60%;" value="${itemEdit.name}"></td>
										</tr>
										<!--<tr>
											<td>类型：</td>
											<td>
												<div style="width:60%;">
													<select name="type" class="selectpicker form-control">
														<#list typeList as type>
															<option value="${type.tagsId}"<#if itemEdit.type == type.value>selected</#if>>${type.name}</option>
														</#list>
													</select>
												</div>
											</td>
										</tr>
										<tr>
											<td>描述：</td>
											<td>
												<input type="text" name="description" style="width:60%;" value="${itemEdit.description}"></td>
										</tr>  -->
										<tr>
											<td>bonus(填写数字)：</td>
											<td>
												<input type="text" name="bonus" style="width:60%;" value="${itemEdit.bonus?c}"></td>
										</tr>
										<tr>
											<td>单价(填写数字)：</td>
											<td>
												<input type="text" name="unitPrice" style="width:60%;" value="${itemEdit.unitPrice?c}"></td>
										</tr>
										<!--<tr>
											<td>显示字段：</td>
											<td>
												<input type="text" name="showFields" style="width:60%;" value="${itemEdit.showFields}"></td>
										</tr>
										<tr>
											<td>渠道回调：</td>
											<td>
												<input type="text" name="notifyHandler" style="width:60%;" value="${itemEdit.notifyHandler}"></td>
										</tr>-->
										<tr>
											<td>密钥：</td>
											<td>
												<input type="text" name="encryptKey" style="width:60%;" value="${itemEdit.encryptKey}"></td>
										</tr>
										<tr>
											<td>反射接口：</td>
											<td>
												<input type="text" name="deliverOrderUrl" style="width:60%;" value="${itemEdit.deliverOrderUrl}"></td>
										</tr>
										<!--<tr>
											<td>来源：</td>
											<td>
												<input type="text" name="mark" style="width:60%;" value="${itemEdit.mark}"></td>
										</tr>
										<tr>
											<td>渠道编码：</td>
											<td>
												<input type="text" name="deliverMediaCode" style="width:60%;" value="${itemEdit.deliverMediaCode}"></td>
										</tr>-->
										<tr>
											<td>app类型：</td>
											<td>
												<div style="width:60%;">
													<select name="appId" class="selectpicker form-control">
														<#list appList as param>
															<option value="${param.key}"<#if itemEdit.appId == param.key>selected</#if>>${param.value}</option>
														</#list>
													</select>
												</div>
											</td>
										</tr>
										<tr>
											<td>ios下载链接地址：</td>
											<td>
												<input type="text" name="iosDownloadUrl" style="width:60%;"></td>
										</tr>
										<tr>
											<td>android下载链链接地址：</td>
											<td>
												<input type="text" name="androidDownloadUrl" style="width:60%;"></td>
										</tr>
										<tr>
											<td>android安装包：</td>
											<td>
												<input type="file" name="androidFile" style="width:60%;"></td>
										</tr>
										<!--
										<tr>
											<td>添加logo：</td>
											<td>
												<input type="file" name="image" style="width:60%;"></td>
										</tr>
										<tr>
											<td>承保接口类型：</td>
											<td>
												<div style="width:60%;">
													<select name="deliverType" class="selectpicker form-control">
														<option value="" selected>默认(空)</option>
														<#list deliverTypeList as param>
															<option value="${param.key}"<#if itemEdit.deliverType == param.key>selected</#if>>${param.value}</option>
														</#list>
													</select>
												</div>
											</td>
										</tr>-->
										<tr>
											<td>统计展示类型：</td>
											<td>
												<div style="width:60%;">
													<select name="showType" class="selectpicker form-control">
														<option value="">默认(空)</option>
														<#list showTypeList as param>
															<option value="${param.key}"<#if itemEdit.showType == param.key>selected</#if>>${param.value}</option>
														</#list>
													</select>
												</div>
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
						</div>
					</div>
				</form>
			</div>
	</div>
</div>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />