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
			<a href="#" class="current">${functionTitle}管理</a>
		</div>
		<h1>添加${functionTitle}</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<form action="${rc.contextPath}/admin/${functionName}/insert.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
					<input type="hidden" name="contentType" value="1"/>
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
											<td style="width:20%;">标题：</td>
											<td>
												<input type="text" name="title" style="width:60%;">
											</td>
										</tr>
										
										<tr>
											<td>次标题：</td>
											<td>
												<input type="text" name="description" style="width:60%;">
											</td>
										</tr>
										
										<tr>
											<td>内容：</td>
											<td>
												<textarea name="content" style="width:60%;height:200px;"></textarea>
											</td>
										</tr>
										
										<tr>
											<td>宣传页链接设置（可不填）：</td>
											<td>
												<input type="text" name="linkValue" style="width:60%;">
											</td>
										</tr>
										<tr>
											<td>按钮文字：</td>
											<td>
												<input type="text" name="buttonLabel" style="width:60%;">
											</td>
										</tr>
										<tr>
											<td>按钮链接：</td>
											<td>
												<input type="text" name="buttonLink" style="width:60%;">
											</td>
										</tr>
										<tr>
											<td>按钮样式：</td>
											<td>
												<div style="width:60%;">
													<select name="buttonType" class="selectpicker form-control">
														<#list typeList as status>
															<option value="${status.key}">${status.value}</option>
														</#list>
													</select>
												</div>
											</td>
										</tr>
										<tr>
											<td>微信分享标题：</td>
											<td>
												<input type="text" name="weixinShareTitle" style="width:60%;">
											</td>
										</tr>
										<tr>
											<td>微信分享语：</td>
											<td>
												<input type="text" name="weixinShareDesc" style="width:60%;">
											</td>
										</tr>
										
										<tr>
											<td>图片：</td>
											<td>
												<input type="file" name="image" style="width:60%;">
											</td>
										</tr>
										<tr>
											<td rowspan="2">微信分享图：</td>
											<td>
												<input type="file" name="weixin" style="width:60%;">
											</td>
										</tr>
										<tr>
											<td style="color:red;">1.微信的宽高标准为：400*400、2.上传图片太大会导致系统错误，请注意图片大小、3.请上传标准的图片</td>
										</tr>
										<tr>
											<td>类型：</td>
											<td>
												<div style="width:60%;">
													<select name="category" class="selectpicker form-control">
														<#list categorysList as category>
															<option value="${category.key}">${category.value}</option>
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
															<option value="${status.key}">${status.value}</option>
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