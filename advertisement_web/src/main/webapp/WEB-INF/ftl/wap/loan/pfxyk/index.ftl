<#include "../../common/core.ftl" />
<@htmlHead title="办信用卡送话费" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/loan/pfxyk.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container">
	<div class="banner">
		<#if customBannerConfig?? && (customBannerConfig?length > 0)>
		<img src="${customBannerConfig}">
		<#else>
		<img src="${cdnUrl}/img/wap/loan/pfxyk/banner.jpg?${cdnFileVersion}">
		</#if>
	</div>
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
			$('#name').val('测试');
		});
	</script>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<#-- 字段 -->
			<@embededTitleZengXianForm/>
			<input type="hidden" id="smsTokenId" value="${smsId}" />
			
			<#-- 提交按钮 -->
			<div class="submit-button-area">
				<div class="submit-button-form" id="submitInformationButton">
					
				</div>
			</div>
			<p class="tip">* 核卡成功后15个工作日内10元话费将充值到该手机</p>
			<div class="activity-rule">
				<div class="title"></div>
				<div class="rule">
					<p>1、活动有效期：2018年3月27日-2018年4月27日；</p>
					<p>2、每个用户每个手机号仅限参与1次，多次参与无效；</p>
					<p>3、通过此活动入口首次办理该行信用卡，并成功通过信用卡中心审核批复的客户，才能获得价值10元的话费；</p>
				    <p>4、核卡成功后15个工作日内我们会以短信的方式通知您的10元手机话费到账情况。</p>
				</div>
			</div>
			<div class="warm-tips">
				<span>【特别提示】“办卡送话费”为兴贷贷款平台回馈新老用户的促销活动，与各银行信用卡中心无关，最终解释权归兴贷贷款平台所有！</span>
			</div>
		</div>
		<@randomSmsToken/>
		<@footer activityPath="${activityPath}"/>
		<#-- 尾部 -->
		<input type="hidden" id="activityPath" value="${activityPath}"/>
	</div>
</div>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">平安出行险</span>，平安客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultCalculatePopWindow />
<#-- 验证码后置弹窗 -->
<@vertifyCodePopWindow />

<#-- 问卷弹窗 -->
<@surveyPopWindow />
<@htmlFoot/>