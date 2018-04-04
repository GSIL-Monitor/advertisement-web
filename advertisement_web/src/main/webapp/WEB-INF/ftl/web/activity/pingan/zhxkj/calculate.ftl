<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/pingan/xfdc-web.css"] />
</@htmlHead>
<@emailPopWindow title="我们会将保费试算结果发送到您的邮箱"/>
<div class="container calculate-background">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader/>
	<#-- Banner信息 -->
	<div class="banner">
		<img src="${cdnUrl}/img/web/activity/pingan/xfdc/calculate-banner.png?${cdnFileVersion}">
	</div>

	<div class="calculate-container clearfix">
		<#-- 已有多少人领取 -->
		<div class="form-title-area">
			<span class="form-title-content">
				<span class="form-title-prefix">&nbsp;</span><@promotionTitleContent type="保费试算"/>
			</span>
			<i class="form-title-left-icon"></i>
			<i class="form-title-right-icon"></i>
		</div>
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area calculate-form">
			<@pinganHongYunCalculateForm activityPath="/pingan/zhxkj"/>
		</div>
		<div class="form-right-icon">
			<img src="${cdnUrl}/img/web/activity/pingan/xfdc/form-right-icon.png">
			<div class="form-right-title">《鸿运随行返还型意外保障》</div>
		</div>
	</div>
	<#-- 测保说明 -->
	<@calculateDescription/>
	<#-- 尾部 -->
	<@footer activityPath="/pingan/zhxkj"/>
</div>
<@htmlFoot />