
<#include "core.ftl" />.

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
			<a href="${rc.contextPath}/admin/${functionName}/list.do" class="current">${functionTitle}管理</a><a href="#" class="current">新建广告主</a>
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
										<tr>
											<td>广告主</td>
											<td>
												<input type="text" name="companyName" style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td>广告主描述</td>
											<td>
												<input type="text" name="description" style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td>账户名</td>
											<td>
												<input type="text" name="username" style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td>密码</td>
											<td>
												<input type="password" name="password" style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td>营业执照照片</td>
											<td>
												<input type="text" name="picture" style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td>公司营业执照注册号</td>
											<td>
												<input type="text" name="busineeNumber" style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td>公司地址</td>
											<td>
												<input type="text" name="address" style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td>联系人姓名</td>
											<td>
												<input type="text" name="name" style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td>联系人电话</td>
											<td>
												<input type="text" name="phone" style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td>联系人微信</td>
											<td>
												<input type="text" name="wechat" style="width:60%;"/>
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