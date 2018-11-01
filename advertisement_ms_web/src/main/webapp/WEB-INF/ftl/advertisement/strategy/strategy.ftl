<#include "core.ftl" />
<@htmlHead title="添加${functionTitle}"/>
<@cssFile file=["page/hscheckdata.css"] />
<@jsFile file=["page/hscheckdata/citydata.js", "page/hscheckdata/hscheckdata.js"] />
<style>
	input[type="radio"] {
		display: inline-block;
		vertical-align: baseline;
		margin: 0 0.05rem 0 0.3rem;
	}
</style>
<@headerPart />
<@topHeaderMenu />
<@sideBar />
<div id="content">
	<div id="content-header">
		<div id="breadcrumb">
			<a href="#" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				${functionTitle}管理
			</a>
			<a href="${rc.contextPath}/admin/${functionName}/list.do" class="current">添加${functionTitle}</a>
		</div>
		<h1>添加${functionTitle}</h1>
	</div>
	<div class="container-fluid">
		<hr>
			<div class="row-fluid">
				<form action="${rc.contextPath}/admin/${functionName}/updateProbabilityStrategy.do" method="post" name="form" enctype="multipart/form-data" target="formCommitIframe">
					<input type="hidden" name="planId" value="${planId}">
					<div>
						<div class="widget-box">
							<div class="widget-title">
								<span class="icon"><i class="icon-th"></i>
								</span>
							</div>
							<div class="widget-content nopadding">
								<table class="table table-bordered table-striped" id="">
									<tbody>
										<tr>
											<td style="width:20%;">性别：</td>
											<td>
												<input type="radio" name="genderStrategy" value="" checked>不限
												<input type="radio" name="genderStrategy" value="M">男
												<input type="radio" name="genderStrategy" value="F">女
											</td>
										</tr>
										<tr>
											<td>年龄：</td>
											<td>
												<input type="radio" name="age" value="" checked>不限
												<input type="radio" name="age" value="others">自定义
												<span class="hidden" id="ageRange">
													<input type="number" name="startAge" id="startAge" /><span>-</span>
													<input type="number" name="endAge" id="endAge"/>
												</span>
											</td>
										</tr>
										<tr>
											<td>区域：</td>
											<td>
												<div id="cityDuoXuan" style="width:300px;" class="hsCheckData"></div>
												<input type="hidden" id="citys" name="ipRegionStrategy" value="">
											</td>
										</tr>
										<tr>
											<td>平台：</td>
											<td>
												<input type="radio" name="deviceTypeStrategy" value="" checked>不限
												<input type="radio" name="deviceTypeStrategy" value="IOS">IOS
												<input type="radio" name="deviceTypeStrategy" value="Android">Android
												<input type="radio" name="deviceTypeStrategy" value="PC">PC
											</td>
										</tr>	
										<tr>
											<td>联网方式：</td>
											<td>
												<input type="radio" name="netWorkStrategy" value="不限" checked>不限
												<input type="radio" name="netWorkStrategy" value="WIFI">以太网
												<input type="radio" name="netWorkStrategy" value="WIFI">WIFI
												<input type="radio" name="netWorkStrategy" value="2G">2G
												<input type="radio" name="netWorkStrategy" value="3G">3G
												<input type="radio" name="netWorkStrategy" value="4G">4G
											</td>
										</tr>	
										<tr>
											<td>运营商：</td>
											<td>
												<input type="radio" name="tMobileStrategy" value="" checked>不限
												<input type="radio" name="tMobileStrategy" value="移动">中国移动
												<input type="radio" name="tMobileStrategy" value="联通">中国联通
												<input type="radio" name="tMobileStrategy" value="电信">中国电信
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
<input type="hidden" id="reqData" value="${strategyValue}">
<script>
	$('#cityDuoXuan').hsCheckData({
        isShowCheckBox: true, //默认为false
        minCheck: 0,//默认为0，不限最少选择个数
        maxCheck: 10,//默认为0，不限最多选择个数
        data: cityData
    });
    $('input[name="age"]').on('click', function() {
    	var val = $(this).val();
    	console.log(val)
    	if(val == 'others') {
    		$('#ageRange').removeClass('hidden');
    	} else {
    		$('#ageRange').addClass('hidden');
    	}
    })
</script>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />