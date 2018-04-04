<#include "../../../common/core.ftl" />
<@htmlHead title="免费领万元账户安全保障送25元现金红包" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["common/activity/pingan/zhxlb.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<script type="text/javascript">
	$(function(){
		$('#mobile').blur(function(){
			if (checkMobile('#mobile')) {
				$('.form-item').removeClass('hide');
			} else {
				clearError('#mobile');
			}
		});
		$('#mobile').bind('input', function(){
			if (checkMobile('#mobile')) {
				$('.form-item').removeClass('hide');
			} else {
				clearError('#mobile');
			}
		});
	});
</script>
<div class="container">
	<@commonHeader/>
	<@commonBanner path="pingan/zhxlb"/>

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
			<@commonZengXianForm hideIdentityCard="true"/>

			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button" id="submitButton">
					<#if channel?? && channel.key=="pawifizhxlb">
					立即领取
					<#else>
					立即领取<span style="font-size:2.2rem;">25元</span>现金红包
					</#if>
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
	<#if differentInsuranceConfig?? && differentInsuranceConfig=="true">
	<p>您领取的平安账户险数量有限，今日已被抢光，我们将赠送您<span class="money">平安100万交通出行意外险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
	<#else>
	<p>已成功领取<span class="money">平安账户险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
	</#if>
</#macro>
<@resultPopWindow title="" buttonText="立即领取25元现金红包" buttonFunction=""/>
<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@htmlFoot/>