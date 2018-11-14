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
			<a href="${rc.contextPath}/admin/plan/list.do" title="${functionTitle}管理" class="tip-bottom"><i class="icon-book"></i>
				计划管理
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
												<input type="radio" name="genderStrategy" value="no" checked>不限
												<input type="radio" name="genderStrategy" value="M">男
												<input type="radio" name="genderStrategy" value="F">女
											</td>
										</tr>
										<tr>
											<td>年龄：</td>
											<td>
												<input type="radio" name="age" value="no" checked>不限
												<input type="radio" name="age" value="others">自定义
												<span class="hidden" id="ageRange">
													<input type="number" name="startAge" id="startAge" /><span>-</span>
													<input type="number" name="endAge" id="endAge"/>
													<input type="hidden" id="ageRangeinput" name = "ageStrategy" value="${strategyValue.ageStrategy}">
												</span>
											</td>
										</tr>
										<tr>
											<td>区域：</td>
											<td>
												<div id="cityDuoXuan" data-id="${strategyValue.ipRegionStrategy}" style="width:300px;" class="hsCheckData"></div>
												<input type="hidden" id="citys" name="ipRegionStrategy" value="">
											</td>
										</tr>
										<tr>
											<td>平台：</td>
											<td>
												<input type="radio" name="deviceTypeStrategy" value="no" checked>不限
												<input type="radio" name="deviceTypeStrategy" value="iOS">iOS
												<input type="radio" name="deviceTypeStrategy" value="Android">Android
												<input type="radio" name="deviceTypeStrategy" value="PC">PC
											</td>
										</tr>	
										<tr>
											<td>联网方式：</td>
											<td>
												<input type="radio" name="netWorkStrategy" value="no" checked>不限
												<input type="radio" name="netWorkStrategy" value="0">以太网
												<input type="radio" name="netWorkStrategy" value="1">wifi
												<input type="radio" name="netWorkStrategy" value="2">蜂窝网络
												<input type="radio" name="netWorkStrategy" value="3">WIFI
												<input type="radio" name="netWorkStrategy" value="4">2G
												<input type="radio" name="netWorkStrategy" value="5">3G
												<input type="radio" name="netWorkStrategy" value="6">4G
											</td>
										</tr>	
										<tr>
											<td>运营商：</td>
											<td>
												<input type="radio" name="carrierStrategy" value="no" checked>不限
												<input type="radio" name="carrierStrategy" value="中国移动">中国移动
												<input type="radio" name="carrierStrategy" value="中国联通">中国联通
												<input type="radio" name="carrierStrategy" value="中国电信">中国电信
											</td>
										</tr>	
										
										<tr>
											<td colspan="4" style="text-align:center">
												<input type="submit" name="" value="提交" class="btn btn-green" style="width: 100px;border: 0;" id="allInputBtn" onclick="return checkResult();"></td>
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
<div class="modal hide fade" id="myModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h3>提示</h3>
  </div>
  <div class="modal-body">
    <p>操作成功！</p>
  </div>
  <div class="modal-footer">
    <a href="#" data-dismiss="modal" class="btn">关闭</a>
    <a href="#" data-dismiss="modal" class="btn btn-primary">确定</a>
  </div>
</div>
<input type="hidden" id="reqData" value="${strategyValue}">
<input type="hidden" id="genderStrategy" value="${strategyValue.genderStrategy}">
<input type="hidden" id="deviceTypeStrategy" value="${strategyValue.deviceTypeStrategy}">
<input type="hidden" id="netWorkStrategy" value="${strategyValue.netWorkStrategy}">
<input type="hidden" id="carrierStrategy" value="${strategyValue.carrierStrategy}">
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
    function initForm() {
    	if($('#ageRangeinput').val()) {
    		if($('#ageRangeinput').val()=="no"){
    			$(":radio[name='age'][value='no']").prop("checked", "checked");
    		}else{
	    		$('#ageRange').removeClass('hidden');
	    		$('#startAge').val($('#ageRangeinput').val().split('-')[0]);
	    		$('#endAge').val($('#ageRangeinput').val().split('-')[1]);
	    		$(":radio[name='age'][value='others']").prop("checked", "checked");
    		}
    	}
    	$(":radio[name='genderStrategy'][value='" + $('#genderStrategy').val() + "']").prop("checked", "checked");
    	$(":radio[name='deviceTypeStrategy'][value='" + $('#deviceTypeStrategy').val() + "']").prop("checked", "checked");
    	$(":radio[name='netWorkStrategy'][value='" + $('#netWorkStrategy').val() + "']").prop("checked", "checked");
    	$(":radio[name='carrierStrategy'][value='" + $('#carrierStrategy').val() + "']").prop("checked", "checked");
    	$('#citys').val($('#cityDuoXuan').attr('data-id'));
    }
    initForm();
    function checkResult() {
    	if($('input[name="age"]:checked').val() == 'no') {
    		$('#ageRangeinput').val('no');
    		return true;
    	}
    	if($('#startAge').val()=="" || $('#endAge').val()==""){
    		$('#ageRangeinput').val("no");
    		return true;
    	}
    	var ageRange = $('#startAge').val() + '-' + $('#endAge').val();
    	$('#ageRangeinput').val(ageRange);
    	$('#myModal').modal('toggle')；
    	return true;
    }
</script>
<@resultTipDialog retUrl="${rc.contextPath}/admin/${functionName}/list.do" />
<@footPart />
<@htmlFoot />