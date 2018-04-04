
<#include "core.ftl" />
<@htmlHead title="修改${functionTitle}"/>
<@sideBar />
<div id="content">
	<@headerPart />
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="#" class="current">${functionTitle}修改</a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
				<input type="hidden" name="${functionId}" value="${itemEdit.orderId?c}"/>
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<h5>${functionTitle}修改</h5>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered table-striped" id="">
								<tbody>
									<tr>
										<td width="20%">订单ID：</td>
										<td width="80%">${itemEdit.orderId?c}</td>
									</tr>
									<tr>
										<td width="20%">客户姓名：</td>
										<td width="80%">
											<input type="text" name="name" style="width:60%;"<#if itemEdit.informationInsurance.name??>value="${itemEdit.informationInsurance.name}"</#if>></td>
									</tr>
									<tr>
										<td width="20%">客户性别：</td>
										<td width="80%">
											<@radioButton name="gender" labels="男,女" values="1,2" defaultValue="${itemEdit.informationInsurance.gender?c}"/>
									</tr>
									<tr>
										<td width="20%">客户年龄：</td>
										<td width="80%">
									</tr>
									<tr>
										<td width="20%">客户归属地：</td>
										<td width="80%">
											<input type="text" name="location" style="width:60%;"<#if itemEdit.informationInsurance.location??>value="${itemEdit.informationInsurance.location}"</#if>></td>
									</tr>
									<tr>
										<td colspan="4" style="text-align:center">
											<input type="submit" name="" value="提交" class="btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult();"></td>
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