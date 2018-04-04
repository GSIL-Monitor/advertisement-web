
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
										<tr>
											<td style="width:20%;">用户ID：</td>
											<td>
												<div>
													<input type="text" name="userId" style="width:60%;"/>
												</div>
											</td>
										</tr>
										<tr>
											<td style="width:20%;">消息标题：</td>
											<td>
												<div>
													<input type="text" name="title" style="width:60%;"/>
												</div>
											</td>
										</tr>
										<tr>
											<td style="width:20%;">消息内容：</td>
											<td>
												<div>
													<input type="text" name="content" style="width:60%;"/>
												</div>
											</td>
										</tr>
										<tr>
											<td style="width:20%;">跳转地址：</td>
											<td>
												<div>
													<input type="text" name="url" style="width:60%;"/>
												</div>
											</td>
										</tr>
										<tr>
											<td style="width:20%;">过期时间：</td>
											<td>
												<div>
													<input type="text" name="expireTime" style="width:60%;"/>
												</div>
											</td>
										</tr>
										<tr>
											<td style="width:20%;">是否简洁信息：</td>
											<td>
												<div>
													<input name="isSimple" type="radio" value="true" checked/>是
													<input name="isSimple" type="radio" value="false" />否
												</div>
											</td>
										</tr>
										<tr>
											<td style="width:20%;">是否离线发送：</td>
											<td>
												<div>
													<input name="isOffline" type="radio" value="true" checked/>是
													<input name="isOffline" type="radio" value="false" />否
												</div>
											</td>
										</tr>
										<tr>
											<td style="width:20%;">发送平台：</td>
											<td>
												<div>
													<input name="client" type="radio" value="0" checked/>全部
													<input name="client" type="radio" value="1" />IOS
													<input name="client" type="radio" value="2" />Android
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