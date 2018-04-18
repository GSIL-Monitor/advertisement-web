
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
					<input type="hidden" name="${functionId}" value="${itemEdit.pushId?c}"/>
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
											<td>推送软件</td>
											<td>
												<div>
													<input name="type" type="radio" value="1"<#if itemEdit.type == 1>checked</#if>/>兴贷
													<input name="type" type="radio" value="2"<#if itemEdit.type == 2>checked</#if>/>瑞贷
												</div>
											</td>
										</tr>
										<tr>
											<td>用户ID：</td>
											<td>
												<div>
													<input type="text" name="userId" style="width:60%;" value="${itemEdit.userId??}"/>
												</div>
											</td>
										</tr>
										<tr>
											<td>消息标题：</td>
											<td>
												<div>
													<input type="text" name="title" style="width:60%;" value="${itemEdit.title}"/>
												</div>
											</td>
										</tr>
										<tr>
											<td>消息内容：</td>
											<td>
												<div>
													<input type="text" name="content" style="width:60%;" value="${itemEdit.content}"/>
												</div>
											</td>
										</tr>
										<tr>
											<td>跳转地址：</td>
											<td>
												<div style="width:60%;" >
													<select name="url" class="selectpicker form-control" data-live-search="true">
														<option value>应用首页</option>
														<#list products as product>
															<option value="ruidai://productDetail?productId=${product.productId}" <#if itemEdit.url?contains("${product.productId}") == true>selected</#if>>【${product.productId}】${product.name}</option>
														</#list>
													</select>
												</div>
											</td>
										</tr>
										<tr>
											<td>过期时间：</td>
											<td>
												<div>
													<input type="text" name="expireTime" style="width:60%;" value="${itemEdit.expireTime?c}"/>
												</div>
											</td>
										</tr>
										<tr>
											<td>是否简洁信息：</td>
											<td>
												<div>
													<input name="isSimple" type="radio" value="true"<#if itemEdit.isSimple == true>checked</#if>/>是
													<input name="isSimple" type="radio" value="false"<#if itemEdit.isSimple == false>checked</#if>/>否
												</div>
											</td>
										</tr>
										<tr>
											<td>是否离线发送：</td>
											<td>
												<div>
													<input name="isOffline" type="radio" value="true"<#if itemEdit.isOffline == true>checked</#if>/>是
													<input name="isOffline" type="radio" value="false"<#if itemEdit.isOffline == false>checked</#if>/>否
												</div>
											</td>
										</tr>
										<tr>
											<td>发送平台：</td>
											<td>
												<div>
													<input name="client" type="radio" value="0"<#if itemEdit.client == 0>checked</#if>/>全部
													<input name="client" type="radio" value="1"<#if itemEdit.client == 1>checked</#if>/>IOS
													<input name="client" type="radio" value="2"<#if itemEdit.client == 2>checked</#if>/>Android
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