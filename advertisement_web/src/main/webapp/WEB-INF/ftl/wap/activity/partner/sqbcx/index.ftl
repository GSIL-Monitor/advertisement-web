<#include "../../../common/core.ftl" />
<@htmlHead title="领100万平安出行保障，送888元现金红包" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/partner/sqbcx.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container sqbcx-container">
	<div class="banner">
		<#if customBannerConfig?? && (customBannerConfig?length > 0)>
		<img src="${customBannerConfig}">
		<#else>
		<img src="${cdnUrl}/img/wap/activity/partner/sqbcx/banner.png?${cdnFileVersion}">
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
		});
	</script>
	<div class="wrapper">
		<#-- 填写赠险表单信息区域 -->
		<div class="form-background">
			<div class="form-area">
				<#if surveyPositionConfig?? && surveyPositionConfig=="index">
				<div class="form-title">
					参与调查，免费领<span class="money">100万元</span>平安出行险
				</div>
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
						<img src="${cdnUrl}/img/wap/activity/partner/sqbcx/check.png"/>
						我同意<a href="javascript:;" onclick="showActivityRule();">《活动规则》</a>及<a href="javascript:;" onclick="showProtocol();">《安全条款》</a>并领取<a href="javascript:;" id="insuranceDetailButton">免费保障</a>
					</span>
					<@commonProtocolContent/>
					<div id="activityRulePopWindow" class="hide">
						<div class="activity-rule">
							<h3>活动规则</h3>
							<p>1、活动期间，每人限领一次红包，红包可以在指定收钱吧商户作为现金抵扣。</p>
							<p>2、参与用户身份信息、手机号等务必保证是本人真实有效信息，手机号可正常接听，我们会依照用户的身份信息、手机号等进行验证，如发现冒领、代领、信息不真实或利用非真实设备进行任何形式的作弊行为，将取消红包资格，远山保险将保留最终解释权。</p>
							<p>3、有效参与活动的用户身体健康，须要在25-50周岁。</p>
							<p>4、同期每位用户限领一份赠险，多次领取无效。</p>
							<p>5、领取的保险仅提供电子保单，保单生效后，投保用户会自动收到保险平台及保险公司短信通知。</p>
							<p>6、因单一保险公司（如平安保险）保障范围存在地域等限制因素，为提升用户体验，我们将及时为用户免费赠送其他保险公司同类型产品。（包括但不限制泰康人寿、中美大都会、百年人寿、阳光人寿、华夏保险等）。</p>
							<p>7、如有疑问，可致电服务热线：010-64167126或发送Email至服务邮箱：service@yuanshanbao.com</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<@randomSmsToken/>
		<div class="bottom-bg">
			<img src="${cdnUrl}/img/wap/activity/partner/sqbcx/bg_bottom.png?${cdnFileVersion}" alt="">
			<div class="footer">
				<input type="hidden" id="activityPath" value="${activityPath}"/>
				<span>
					<#if channel??>
						<#if channel.key?? && channel.key=="sqbzfbcx">
						<p>『此活动由远山科技提供，与支付宝无关』</p>
						<#else>
						<p>『此活动由远山科技提供，与微信无关』</p>
						</#if>
					</#if>
				</span>
			</div>
		</div>
		<#-- 尾部 -->
		<#-- <div class="footer-logo clearfix">
			<img src="${ossUrl}/img/wap/activity/partner/sqbcx/sqb-logo.png?${cdnFileVersion}"/>
			<img src="${ossUrl}/img/wap/activity/partner/sqbcx/pingan-logo.png"/>
		</div> -->
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
<@surveyPopWindow title="完成小调查，将有机会获得更大额度的红包" buttonText="提交" buttonFunction="" merchant="zhongying"/>
<@htmlFoot/>