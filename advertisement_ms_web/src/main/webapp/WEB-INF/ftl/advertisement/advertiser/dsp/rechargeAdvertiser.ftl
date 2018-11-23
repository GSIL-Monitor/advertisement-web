
<#include "core.ftl" />
<@htmlHead title="${functionTitle}列表"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<script>
</script>
<div id="content">
	<div id="content-header">
		<div id="breadcrumb"><a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>${functionTitle}管理</a><a href="#" class="current">${functionTitle}列表</a></div>
		<h1>${functionTitle}列表</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<form action="${rc.contextPath}/admin/${functionName}/recharge.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
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
											<td>广告主id</td>
											<td>
												<input type="text"  readonly="readonly" name="advertiserId" value = ${advertiser.advertiserId} style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td>广告主名称</td>
											<td>
												<input type="text" disabled="disabled" value = ${advertiser.companyName} style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td>广告主用户名</td>
											<td>
												<input type="text" disabled="disabled" value = ${advertiser.bindUserName} style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td>充值金额</td>
											<td>
												<input type="text" name="amount" style="width:60%;"/>
											</td>
										</tr>
										<tr>
											<td colspan="4" style="text-align:center">
												<input type="submit" name="" value="提交" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult(true);"></td>
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

