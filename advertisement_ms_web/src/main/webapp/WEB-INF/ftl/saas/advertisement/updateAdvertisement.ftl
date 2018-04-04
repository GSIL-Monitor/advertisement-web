
<#include "core.ftl" />
<@htmlHead title="修改${itemEdit.typeContent}${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="#" class="current">修改${itemEdit.typeContent}${functionTitle}</a>
		</div>
		<h1>修改${itemEdit.typeContent}${functionTitle}</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
					<input type="hidden" name="${functionId}" value="${itemEdit.advertisementId?c}"/>
					<input type="hidden" name="type" value="${itemEdit.type}" />
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
												${itemEdit.advertiser.description!}</td>
										</td>
										<tr>
											<td style="width:20%;">广告描述：</td>
											<td>
												<input type="text" name="description" style="width:60%;" value="${itemEdit.description!}"></td>
										</td>
										</tr>
										<tr>
											<td>广告链接：</td>
											<td>
												<input type="text" name="url" style="width:60%;" value="${itemEdit.url!}"></td>
										</tr>
										<tr>
											<td>广告app链接：</td>
											<td>
												<div style="width:60%;">
													<select name="appUrl" id="appUrl" class="selectpicker form-control">
														<option>默认为空</option>
														<#list products as product>
															<#if (itemEdit.appUrl)?? && itemEdit.appUrl=="ruidai://productDetail?productId=${product.productId}">
																<option value="ruidai://productDetail?productId=${product.productId}" selected>【${product.productId}】【${product.name}】ruidai://productDetail?productId=${product.productId}</option>
															<#else>
																<option value="ruidai://productDetail?productId=${product.productId}">【${product.productId}】【${product.name}】ruidai://productDetail?productId=${product.productId}</option>
															</#if>
														</#list>
													</select>
												</div>
											</td>
										</tr>
										<#if itemEdit.type == 1 >
											<tr>
												<td>福利主文案：</td>
												<td>
													<input type="text" name="title" style="width:60%;" value="${itemEdit.title}"></td>
											</tr>
											<tr>
												<td>福利副文案：</td>
												<td>
													<input type="text" name="subTitle" style="width:60%;" value="${itemEdit.subTitle}"></td>
											</tr>
											<tr>
												<td>按钮文案：</td>
												<td>
													<input type="text" name="buttonName" style="width:60%;" value="${itemEdit.buttonName}"></td>
											</tr>
											<tr>
												<td>图片：</td>
												<td>
													<#if (itemEdit.imageUrl)??>
														<image src="${itemEdit.imageUrl}"/>
													</#if>
													<input type="file" name="image" style="width:60%;">
												</td>
											</tr>
											<tr>
												<td>分类：</td>
												<td>
													<div style="width:60%;">
														<select name="category" class="selectpicker form-control" data-live-search="true">
															<option value="">默认(空)</option>
															<#list categories as category>
																<#if (itemEdit.category)?? && itemEdit.category==category.categoryId>
																	<option value="${category.categoryId}" selected>【${category.categoryId}】${category.name}</option>
																<#else>
																	<option value="${category.categoryId}">【${category.categoryId}】${category.name}</option>
																</#if>
															</#list>
														</select>
													</div>
												</td>
											</tr>
										</#if>
										<#if itemEdit.type == 2 >
											<tr>
												<td>轮播图图片：</td>
												<td>
													<#if itemEdit.imageUrl??>
														<image src="${itemEdit.imageUrl}">
														</image>
													</#if>
													<input type="file" name="image" style="width:60%;"></td>
											</tr>
										</#if>
										<#if itemEdit.type == 3 >
											<tr>
												<td>标签文案：</td>
												<td>
													<input type="text" name="title" style="width:60%;" value="${itemEdit.title}"></td>
											</tr>
											<tr>
												<td>标签图片：</td>
												<td>
													<#if itemEdit.imageUrl??>
														<image src="${itemEdit.imageUrl}">
														</image>
													</#if>
													<input type="file" name="image" style="width:60%;"></td>
											</tr>
										</#if>
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