<#include "core.ftl" />
<@htmlHead title="${functionTitle}详情"/>
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
										<td>素材名称：</td>
										<td>${itemEdit.name}</td>
									</tr>
									<tr>
										<td>图片：</td>
										<td><img src="${itemEdit.imageUrl}" style="max-height: 100px;"/></td>
									</tr>
									<tr>
										<td>尺寸：</td>
										<td>${itemEdit.sizeContent}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
	</div>
	<form action="${rc.contextPath}/admin/${functionName}/review.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
				<input type="hidden" name="materialId" value="${itemEdit.materialId}" style="width:60%;">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-content nopadding">
							<table class="table table-bordered table-striped" id="">
								<tbody>
									<tr>
										<td style="width:20%;">选择状态：</td>
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
<@footPart />
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@htmlFoot />