
<#include "core.ftl" />
<@htmlHead title="修改${functionTitle}"/>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="${rc.contextPath}/admin/${functionName}/list.do" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="#" class="current">修改${itemEdit.typeContent}</a>
		</div>
		<h1>修改${itemEdit.typeContent}${functionTitle}</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<form action="" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
					<input type="hidden" name="${functionId}" value="${itemEdit.planId?c}"/>
					<input type="hidden" name="status" value="${itemEdit.status}"/>
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
										<td>value:</td>
										<td>
											<input type="text" readonly="readonly"  value="${value}"/>
										</td>
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
<script>
	
</script>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />