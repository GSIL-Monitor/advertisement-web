
<#include "core.ftl" />
<@htmlHead title="添加${typeContent}${functionTitle}"/>
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
		<h1>添加${typeContent}${functionTitle}</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<form action="${rc.contextPath}/admin/${functionName}/insert.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
					<input type="hidden" name="advertiserId" value="${advertiser.advertiserId}" />
					<input type="hidden" name="type" value="${type}" />
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
											<td style="width:20%;">广告主描述：</td>
											<td>
												${advertiser.description}</td>
										</td>
										<tr>
											<td style="width:20%;">广告描述：</td>
											<td>
												<input type="text" name="description" style="width:60%;"></td>
										</td>
										</tr>
										<tr>
											<td>广告链接：</td>
											<td>
												<input type="text" name="url" style="width:60%;"></td>
										</tr>
										<tr>
											<td style="width:20%;" >广告app链接：</td>
											<td>
												<div style="width:60%;">
												<#if type == 6 >
													<select name="productId" id="productId" class="selectpicker form-control">
														<option selected>默认为空</option>
														<#list products as product>
															<option value="${product.productId}">【${product.productId}】【${product.name}】ruidai://productDetail?productId=${product.productId}</option>
														</#list>
													</select>
												<#else>
													<select name="appUrl" id="appUrl" class="selectpicker form-control">
														<option selected>默认为空</option>
														<#list products as product>
															<option value="ruidai://productDetail?productId=${product.productId}">【${product.productId}】【${product.name}】ruidai://productDetail?productId=${product.productId}</option>
														</#list>
													</select>
												</#if>
												</div>
											</td>
										</tr>
										<#if type == 1 >
											<tr>
												<td>福利主文案：</td>
												<td>
													<input type="text" name="title" style="width:60%;"></td>
											</tr>
											<tr>
												<td>福利副文案：</td>
												<td>
													<input type="text" name="subTitle" style="width:60%;"></td>
											</tr>
											<tr>
												<td>显示类型：</td>
												<td>
													<div style="width:60%;">
														<select name="showType" id="showType" class="selectpicker form-control">
															<#list staticShowTypes as showType>
																<option value="${showType.key}">${showType.value}</option>
															</#list>
														</select>
													</div>
												</td>
											</tr>
											<tr>
												<td>按钮文案：</td>
												<td>
													<input type="text" name="buttonName" style="width:60%;"></td>
											</tr>
											<tr>
												<td>图片：</td>
												<td>
													<input type="file" name="image" style="width:60%;">
												</td>
											</tr>
											<tr id="bigImage">
												<td>大图：</td>
												<td>
													<input type="file" name="bigImage" style="width:60%;">
												</td>
											</tr>
											<tr>
												<td>分类：</td>
												<td>
													<div style="width:60%;">
														<select name="category" class="selectpicker form-control" data-live-search="true">
															<option value="">默认(空)</option>
															<#list categories as category>
																<option value="${category.categoryId}">【${category.categoryId}】${category.name}</option>
															</#list>
														</select>
													</div>
												</td>
											</tr>
										</#if>
										<#if type == 2 >
											<tr>
												<td>轮播图广告图片：</td>
												<td>
													<input type="file" name="image" style="width:60%;">
												</td>
											</tr>
										</#if>
										<#if type == 3 >
											<tr>
												<td>标签文案：</td>
												<td>
													<input type="text" name="title" style="width:60%;" value="${tempAdvertisement.title}"></td>
											</tr>
											<tr>
												<td>标签图片：</td>
												<td>
													<input type="file" name="image" style="width:60%;">
												</td>
											</tr>
										</#if>
										<#if type == 4 >
											<tr>
												<td>弹窗广告图片：</td>
												<td>
													<input type="file" name="image" style="width:60%;">
												</td>
											</tr>
											<tr>
												<td>显示类型：</td>
												<td>
													<div style="width:60%;">
														<select name="showType" class="selectpicker form-control">
															<#list dynamicShowTypes as showType>
																<option value="${showType.key}">${showType.value}</option>
															</#list>
														</select>
													</div>
												</td>
											</tr>
										</#if>
										<#if type == 5 >
											<tr>
												<td>角标广告图片：</td>
												<td>
													<input type="file" name="image" style="width:60%;">
												</td>
											</tr>
											<tr>
												<td>显示类型：</td>
												<td>
													<div style="width:60%;">
														<select name="showType" class="selectpicker form-control">
															<#list dynamicShowTypes as showType>
																<option value="${showType.key}">${showType.value}</option>
															</#list>
														</select>
													</div>
												</td>
											</tr>
										</#if>
										<#if type == 6 >
											<tr>
												<td>按钮文案：</td>
												<td>
													<input type="text" name="buttonName" style="width:60%;"></td>
											</tr>
											<tr>
												<td>分类：</td>
												<td>
													<div style="width:60%;">
														<select name="category" class="selectpicker form-control" data-live-search="true">
															<option value="">默认(空)</option>
															<#list categories as category>
																<option value="${category.categoryId}">【${category.categoryId}】${category.name}</option>
															</#list>
														</select>
													</div>
												</td>
											</tr>
										</#if>
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
												<input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult();">
											</td>
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
<script>
	$(function() {
		if($("#showType").val()==4){
			$("#bigImage").hide();
			$("#bigImage").removeClass("name");
		}
		if($("#showType").val()==5){
			$("#bigImage").show();
			$("#bigImage").attr("name","bigImage");
		}
		$("#showType").change(function(){
			if($("#showType").val()==4){
				$("#bigImage").hide();
				$("#bigImage").removeClass("name");
			}
			if($("#showType").val()==5){
				$("#bigImage").show();
				$("#bigImage").attr("name","bigImage");
			}
		});
	});
</script>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />