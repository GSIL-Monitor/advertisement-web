<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取5万元平安自驾险" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/pingan/yazj.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container">
	<div class="header-embed">
		<@commonHeader description=""/>
	</div>
	<@commonBanner path="pingan/yazj"/>
	<i class="pay-success"></i>
	<i class="rolling-item"></i>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<#if surveyPositionConfig?? && surveyPositionConfig=="index">
				<div class="form-title">
					参与调查，免费领<span class="money">5万元</span>自驾险
				</div>
				<@surveyForm/>
				<div class="form-title" style="margin-top:2rem;">
					填写信息，保单生效
				</div>
				</#if>
				<#-- 字段 -->
				<@embededTitleZengXianForm/>
				<input type="hidden" id="smsTokenId" value="${smsId}" />
			</div>
		</div>
		<#-- 条款 -->
		<div class="protocal">
			<span>
				<img src="${cdnUrl}/img/wap/activity/pingan/yacx/check.png"/>
				同意<a href="javascript:;" onclick="showProtocol();">《安全条款》</a>及<a href="javascript:;" onclick="showActivityRule();">《活动规则》</a>并领取免费保险
			</span>
			<@pinganProtocolContent/>
			<div id="activityRulePopWindow" class="hide">
				<@activityRule/>
			</div>
		</div>
		<#-- 提交按钮 -->
		<div class="submit-button-area">
			<div class="submit-button-form" id="submitButton">
			</div>
		</div>

		<div class="insurance-description">
			<img src="${cdnUrl}/img/wap/activity/pingan/yazj/description.png"/>
		</div>
		<@randomSmsToken/>
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
	<p>已成功领取<span class="money">平安自驾险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultPopWindow title="" buttonText="参与保费测算领取${surveyPromotionTextConfig}" buttonFunction=""/>

<#-- 问卷弹窗 -->
<@surveyPopWindow title="完成下面问题，立即领取赠险。" buttonText="提交" buttonFunction=""/>
<@htmlFoot/>