<#include "../../../common/core.ftl" />
<@htmlHead title="免费领10万阳光出行险" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "wap/activity/yangguang.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["common/activity/yangguang/ktcx${template!''}.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeaderYangGuang/>
	<#if templete??>
	<div class="banner">
		<img src="${cdnUrl}/img/wap/activity/yangguang/ktcx/banner${template!''}.png">
	</div>
	<#else>
	<@commonBanner path="yangguang/ktcx"/>
	</#if>
	<div class="wrapper">
		<#if !template??>
		<div class="car">
			<img src="${cdnUrl}/img/wap/activity/yangguang/ktcx/car.png">
		</div>
		</#if>
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<#if surveyPositionConfig?? && surveyPositionConfig=="index">
				<div class="form-title">
					<img src="${cdnUrl}/img/wap/activity/yangguang/ktcx/form-survey-title.png"/>
				</div>
				<@surveyFormYangGuang />
			</#if>
			<div class="form-title form-info-title">
				<img src="${cdnUrl}/img/wap/activity/yangguang/ktcx/form-info-title.png"/>
			</div>
			<#-- 字段 -->
			<@commonZengXianForm/>

			<input type="hidden" id="smsTokenId" value="${smsId}" />
			<#-- 条款 -->
			<@yangguangProtocol/>


			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button big-button" id="submitButton">
					免费领取
				</div>
			</div>

			<@warmTipYangGuang/>
		</div>
		<@randomSmsToken/>
		<#-- 保险详情描述 -->
		<div class="insurance-description">
			<div class="insurance-table-title">
				<img src="${cdnUrl}/img/wap/activity/yangguang/ktcx/table-title.png">
			</div>
			<div class="insurance-table">
				<img src="${cdnUrl}/img/wap/activity/yangguang/ktcx/table-content.png">
			</div>
			<div class="insurance-provider">
				<#-- 以上保险由<span class="money">远山保险和阳光人寿</span>联合提供 -->
			</div>
		</div>

		<#-- <@activityRuleYangGuang/> -->
		<#-- 尾部 -->
		<@footer activityPath="/yg/ktcx" chineseName="阳光人寿保险股份有限公司" englishName="Sunshine Insurance Group Inc."/>
	</div>
</div>
<#-- 邮箱弹窗 -->
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>信息已成功提交，<span class="money">阳光客服</span>后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与保费测算即送100元滴滴红包" buttonFunction=""/>
<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@htmlFoot/>