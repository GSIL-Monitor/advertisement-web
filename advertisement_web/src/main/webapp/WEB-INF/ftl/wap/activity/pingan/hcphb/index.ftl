<#include "../../../common/core.ftl" />
<@htmlHead title="中国平安送您100万春运出行保障" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/pingan/hcphb.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container">
	<@commonBanner path="pingan/hcphb"/>
	<script type="text/javascript">
		$(function(){
			$('#insuranceDetailButton').click(function(){
				TipWindow.showTip('#insuranceDetailTip');
			});
			$('.tip-overlay').click(function() {
				if (TipWindow.isShow('#insuranceDetailTip')) {
					TipWindow.hide('#insuranceDetailTip');
				}
			});
			$('#insuranceDetailTip').click(function(){
				TipWindow.hide('#insuranceDetailTip');
			});
		});
	</script>
	<div class="wrapper">
		<@commonAppliedCount/>
		<div class="wanshan"><img src="${cdnUrl}/img/wap/activity/pingan/hcphb/pencil.png" alt=""><span>完善信息，领取出行大礼包</span></div>
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<#if surveyPositionConfig?? && surveyPositionConfig=="index">
				<@surveyForm/>
				<div class="form-title">
					填写信息，保单生效
				</div>
				</#if>
				<#-- 字段 -->
				<@embededTitleZengXianForm/>
				<input type="hidden" id="smsTokenId" value="${smsId}" />
				
				<#-- 提交按钮 -->
				<div class="submit-button-area">
					<div class="submit-button-form" id="submitButton">
						
					</div>
				</div>
				<#-- 条款 -->
				<div class="protocal">
					<span>
						<img src="${cdnUrl}/img/wap/activity/pingan/hcphb/check.png"/>
						我同意<#-- <a href="javascript:;" onclick="showActivityRule();">《活动规则》</a>及 --><a href="javascript:;" onclick="showProtocol();">《安全条款》</a>并领取<a href="javascript:;" id="insuranceDetailButton">免费保障</a>
					</span>
					<@commonProtocolContent/>
					<#-- <div id="activityRulePopWindow" class="hide">
						<@activityRule/>
					</div> -->
				</div>
			</div>
			<div class="activity-wrapper">
				<@activityRule/>
			</div>
		</div>
		<@randomSmsToken/>
		<#-- 尾部 -->
		<@footer activityPath="${activityPath}"/>
	</div>
</div>
<#-- 保险详情弹窗 -->
<div id="insuranceDetailTip" class="tip-area insurance-detail-tip hide">
	<div class="tip-window center">
		<div class="favorite-tip">
			<img id="insuranceDetailImg" src="${cdnUrl}/img/wap/activity/pingan/hcphb/insurance-detail.png">
		</div>
	</div>
</div>
<#if emailPositionConfig?? && emailPositionConfig=="popup">
<#-- 邮箱弹窗 -->
<@emailPopWindow />
</#if>
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