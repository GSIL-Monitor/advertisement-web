
<#include "core.ftl" />
<@htmlHead title="修改${functionTitle}"/>
<@sideBar />
<div id="content">
	<@headerPart />
	<div id="content-header">
		<div id="breadcrumb">
			<a href="${rc.contextPath}/admin/${functionName}/list.do" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="#" class="current">${functionTitle}修改</a>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<form action="${rc.contextPath}/admin/${functionName}/update.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
				<input type="hidden" name="${functionId}" value="${itemEdit.materialId?c}"/>
				<input type="hidden" name="status" value="${itemEdit.status}"/>
				<div>
					<div class="widget-box">
						<div class="widget-title">
							<h5>${functionTitle}修改</h5>
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered table-striped" id="">
								<tbody>
									<tr>
										<td width="20%">名称：</td>
										<td>
											<input type="text" name="name" style="width:60%;" value="${itemEdit.name}"></td>
										</td>
									</tr>
									<tr>
										<td width="20%">标题：</td>
										<td>
											<input type="text" name="title" style="width:60%;" value="${itemEdit.title}"></td>
										</td>
									</tr>
									<tr>
										<td width="20%">描述：</td>
										<td>
											<input type="text" name="description" style="width:60%;" value="${itemEdit.description}"></td>
										</td>
									</tr>
									<tr>
										<td>图片：</td>
										<td>
											<input type="file" name="image" style="width:60%;">
										</td>
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