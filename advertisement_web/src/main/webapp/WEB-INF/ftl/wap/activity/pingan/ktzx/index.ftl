<#include "../../../common/core.ftl" />
<#if type?? && type=="zijia">
	<#assign typeTitle="免费领5万平安自驾险"/>
<#elseif type?? && type=="hangyan">
	<#assign typeTitle="免费领200元航班延误险"/>
<#else>
	<#assign typeTitle="免费领100万平安出行险"/>
</#if>

<@htmlHead title="${typeTitle}" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["common/activity/pingan/ktzx.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader/>
	<div class="banner">
		<img src="${cdnUrl}/img/wap/activity/pingan/ktzx/banner-${type}.png">
	</div>

	<div class="wrapper">
		<#if change?? && change=="true">
		<div class="switch-button">
			<#if type="hangyan">
			<img src="${cdnUrl}/img/wap/activity/pingan/ktzx/hangyan-disable.png"/>
			<a href="/m/activity/pingan/ktcx.html?change=true&channel=${registerFrom!""}">
				<img class="right-icon" src="${cdnUrl}/img/wap/activity/pingan/ktzx/chuxing-enable.png"/>
			</a>
			<#else>
			<a href="/m/activity/pingan/kthy.html?change=true&channel=${registerFrom!""}">
				<img src="${cdnUrl}/img/wap/activity/pingan/ktzx/hangyan-enable.png"/>
			</a>
			<img class="right-icon" src="${cdnUrl}/img/wap/activity/pingan/ktzx/chuxing-disable.png"/>
			</#if>
		</div>
		</#if>
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<input type="hidden" id="type" value="${type}"/>
			<#if surveyPositionConfig?? && surveyPositionConfig=="index">
			<div class="form-tag-area">
				<span class="form-tag-content">${typeTitle}</span>
				<i class="form-tag-left"></i>
				<i class="form-tag-right"></i>
			</div>
			<@surveyForm />

			<div class="form-title">
				填写信息、保单生效
			</div>
			</#if>
			<#-- 字段 -->
			<@commonZengXianForm/>

			<input type="hidden" id="smsTokenId" value="${smsId}" />
			<#-- 条款 -->
			<@pinganProtocol/>


			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button big-button" id="submitButton">
					
				</div>
			</div>

			<div class="warn-tips">
				<#if channel?? && (channel == "pawifihy")>
				*平安WiFi用户专享额外奖励30金币
				<#else>
				*郑重承诺：我们将严格遵守信息保密制度
				</#if>
			</div>

			<div class="warm-tips">
				<span>平安人寿后续将致电以确认免费保险生效事宜</span>
			</div>
		</div>
		<@randomSmsToken/>
		<#-- 保险详情描述 -->
		<div class="insurance-description">
			<div class="insurance-table">
				<img src="${cdnUrl}/img/wap/activity/pingan/ktzx/table-${type}.png">
			</div>
			<@insuranceProvider/>
			<@activityRule/>
		</div>
		<#-- 尾部 -->
		<@footer activityPath="/pingan/ktzx"/>
	</div>
</div>
<#-- 邮箱弹窗 -->
<@emailPopWindow />
<#-- 身份证弹窗 -->
<@identityCardPopWindow />
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">平安出行险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与保费测算领取${surveyPromotionTextConfig}" buttonFunction=""/>

<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@htmlFoot/>