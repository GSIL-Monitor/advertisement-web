<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
<@cssFile file=["page/hscheckdata.css"] />
<@jsFile file=["page/hscheckdata/citydata.js", "page/hscheckdata/hscheckdata.js"] />
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
		<h1>添加${functionTitle}</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<form action="${rc.contextPath}/admin/${functionName}/config.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
					<input type="hidden" name="activityId" value="${activityId}">
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
											<td>性别：</td>
											<td>
												<input type="radio" name="gender" value="">不限
												<input type="radio" name="gender" value="男">男
												<input type="radio" name="gender" value="女">女
											</td>
										</tr>
										<tr>
											<td>年龄：</td>
											<td>
											</td>
										</tr>
										<tr>
											<td>区域：</td>
											<td>
												<div id="cityDuoXuan" style="width:300px;" class="hsCheckData"></div>
											</td>
										</tr>
										<tr>
											<td>平台：</td>
											<td>
												<input type="radio" name="gender" value="不限">
												<input type="radio" name="gender" value="IOS">
												<input type="radio" name="gender" value="Android">
												<input type="radio" name="gender" value="PC">
											</td>
										</tr>	
										<tr>
											<td>联网方式：</td>
											<td>
												<input type="radio" name="gender" value="不限">
												<input type="radio" name="gender" value="WIFI">
												<input type="radio" name="gender" value="4G">
												<input type="radio" name="gender" value="3G">
											</td>
										</tr>	
										<tr>
											<td>运营商：</td>
											<td>
												<input type="radio" name="gender" value="移动">
												<input type="radio" name="gender" value="联通">
												<input type="radio" name="gender" value="电信">
											</td>
										</tr>	
										
										<tr>
											<td colspan="4" style="text-align:center">
												<input type="submit" name="" value="提交" class=" btn btn-success" style="width: 100px;border: 0;" id="allInputBtn" onclick="checkResult();"></td>
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
	$('#cityDuoXuan').hsCheckData({
        isShowCheckBox: true, //默认为false
        minCheck: 0,//默认为0，不限最少选择个数
        maxCheck: 10,//默认为0，不限最多选择个数
        data: cityData
    });
</script>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />