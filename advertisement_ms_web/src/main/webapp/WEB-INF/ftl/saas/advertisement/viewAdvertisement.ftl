
<#include "core.ftl" />
<@htmlHead title="${itemEdit.typeContent}${functionTitle}详情"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="#" class="current">${itemEdit.typeContent}${functionTitle}详情</a>
		</div>
	</div>
	<div class="container-fluid">
		<hr>
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
										<td>广告主描述：</td>
										<td>${itemEdit.advertiser.description}</td>
									</tr>
									<tr>
										<td>广告描述：</td>
										<td>${itemEdit.title}</td>
									</tr>
									<tr>
										<td>广告链接：</td>
										<td>${itemEdit.url}</td>
									</tr>
									<tr>
										<td>广告链接：</td>
										<td>${itemEdit.appUrl}</td>
									</tr>
									<#if itemEdit.type ==1>
										<tr>
											<td>福利主文案：</td>
											<td>${itemEdit.title}</td>
										</tr>
										<tr>
											<td>福利副文案：</td>
											<td>
												<#if (itemEdit.subTitle)??>
													${itemEdit.subTitle}
												</#if>
											</td>
										</tr>
										<tr>
											<td>按钮文案：</td>
											<td>
												<#if (itemEdit.buttonName)??>
													${itemEdit.buttonName}
												</#if>
											</td>
										</tr>
										<tr>
											<td>图片：</td>
											<td>
												<#if (itemEdit.imageUrl)??>
													<image src="${itemEdit.imageUrl}"/>
												</#if>
											</td>
										</tr>
										<tr>
											<td>分类：</td>
											<td>
												<#if (itemEdit.categoryContent)??>
													${itemEdit.categoryContent}
												</#if>
											</td>
										</tr>
									</#if>
									<#if itemEdit.type == 2 >
										<tr>
											<td>图片：</td>
											<td>
												<#if (itemEdit.imageUrl)??>
													<image src="${itemEdit.imageUrl}"/>
												</#if>
											</td>
										</tr>
									</#if>
									<#if itemEdit.type ==3>
										<tr>
											<td>标签文案：</td>
											<td>${itemEdit.title}</td>
										</tr>
										<tr>
											<td>标签图片：</td>
											<td>
												<#if itemEdit.imageUrl??>
													<image src="${itemEdit.imageUrl}">
													</image>
												</#if>
											</td>
										</tr>
									</#if>
									<tr>
										<td>创建时间：</td>
										<td>${itemEdit.createTimeContent}</td>
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