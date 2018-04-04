<#include "....//../common/core.ftl" />
<@htmlHead title="参与问卷调查，即送100元滴滴红包" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["wap/activity/pingan3in1.js"] />
	<@cssFile file=["common/activity/shanyin/pinganwq.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<div class="header">
		<img src="${cdnUrl}/img/common/logo/${merchant.englishName}-logo.png?${cdnFileVersion}">
	</div>
	<div class="banner">
		<img src="${cdnUrl}/img/wap/activity/pingansy/wq/banner.png">
	</div>
	<#-- 填写赠险表单信息区域 -->
	<div class="form-area">
		<div class="form-title-area">
			<span class="form-title-content">
				<p>参与问卷调查，即送最高<span class="money">100元</span>滴滴红包</p>
			</span>
			<i class="form-title-left-icon"></i>
			<i class="form-title-right-icon"></i>
		</div>
		<#-- 字段 -->
		<@surveyForm />
		<input type="hidden" name="gorderId" id="gorderId" value="${gorderId?c}" />
		<#-- 提交按钮 -->
		<div class="submit-button-area">
			<div class="submit-button-shadow">
				<div class="submit-button" id="surveyButton">
					立即领取滴滴红包
				</div>
			</div>
		</div>
	</div>
	<#-- 尾部 -->
	<@footer activityPath="/pingan/sywq"/>
</div>
<@htmlFoot />