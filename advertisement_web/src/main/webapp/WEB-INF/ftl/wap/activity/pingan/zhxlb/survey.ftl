<#include "../../../common/core.ftl" />
<@htmlHead title="参与问卷调查，即送100元滴滴红包" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["common/activity/pingan/zhx.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader insurance="${merchant.englishName}" description=""/>
	<#-- 填写赠险表单信息区域 -->
	<div class="form-area">
		<div class="form-title-area">
			<@surveyPageTitle/>
			<i class="form-title-left-icon"></i>
			<i class="form-title-right-icon"></i>
		</div>
		<#-- 字段 -->
		<@surveyFormByMerchant/>
		<input type="hidden" name="gorderId" id="gorderId" value="${gorderId?c}" />
		<#-- 提交按钮 -->
		<@surveySubmitButton/>
	</div>
	<#-- 尾部 -->
	<@footer activityPath="/pingan/zhx"/>
</div>
<@htmlFoot />