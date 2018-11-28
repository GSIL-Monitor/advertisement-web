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
						<div class="widget-content nopadding">
							<table class="table table-bordered table-striped" id="">
								<tbody>
									<tr>
										<td>订单名称：</td>
										<td>${itemEdit.plan.order.name}</td>
									</tr>
									<tr>
										<td>广告主名称：</td>
										<td>${itemEdit.plan.advertiser.companyName}</td>
									</tr>
									<tr>
										<td>计划名称：</td>
										<td>${itemEdit.plan.name}</td>
									</tr>
									<tr>
										<td>投放渠道：</td>
										<td>${itemEdit.channel}</td>
									</tr>
									
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
	</div>
	<form action="${rc.contextPath}/admin/${functionName}/review.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
				<input type="hidden" name="probabilityId" value="${itemEdit.probabilityId}" style="width:60%;">
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
										<td style="width:20%;">状态：</td>
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
											<input type="submit" name="" value="确认" class=" btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult(true);">
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