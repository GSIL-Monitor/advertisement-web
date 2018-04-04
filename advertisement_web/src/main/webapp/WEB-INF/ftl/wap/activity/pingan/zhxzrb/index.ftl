<#include "../../../common/core.ftl" />
<@htmlHead title="免费领万元账户安全保障送1万元理财金" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["common/activity/pingan/zhxlb.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<div class="container">
	<@commonHeader/>
	<@commonBanner path="pingan/zhxzrb"/>

	<div class="wrapper">
		<#-- 已有多少人领取 -->
		<@commonAppliedCount/>
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<#if surveyPositionConfig?? && surveyPositionConfig=="index">
			<div class="form-title">
				参与调查，免费领<span class="money">1万元</span>账户安全险
			</div>
			<@surveyForm/>
			<div class="form-title" style="margin-top:2rem;">
				填写信息，保单生效
			</div>
			</#if>
			<#-- 字段 -->
			<@commonZengXianForm/>

			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button" id="submitButton">
					立即领取<#-- <span style="font-size:2.2rem;">10000元</span>理财金 -->
				</div>
			</div>
			<input type="hidden" id="smsTokenId" value="${smsId}" />
		</div>
		<@randomSmsToken/>
		<#-- 保险详情描述 -->
		<div class="insurance-description">
			<div class="insurance-table">
				<img src="${cdnUrl}/img/wap/activity/pingan/zhxlb/table-content.png">
			</div>

			<#-- 条款 -->
			<@pinganProtocol/>
			<#-- 活动规则 -->
			<@activityRule/>
		</div>
		<#-- 尾部 -->
		<@footer activityPath="${activityPath}"/>
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
<@resultPopWindow title="" buttonText="立即领取10000元理财金" buttonFunction=""/>
<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@htmlFoot/>