<#include "../../../common/core.ftl" />
<#if insurant?? && insurant.policyNumber??>
<#assign resultTitle="领取成功"/>
<#else>
<#assign resultTitle="领取失败"/>
</#if>
<@htmlHead title="${resultTitle}" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["wap/activity/pingan3in1.js"] />
	<@cssFile file=["common/activity/shanyin/pinganzhx.css"] />
</@htmlHead>

<script type="text/javascript">
	$(function() {
		<#if alert=="true">
			TipWindow.showSingle(
				'亲，您领取的平安出行意外险因地域、年龄等限制因素，暂时无法领取，经过您的许可，我们将赠送其他保险公司（包括泰康人寿、华夏保险、百年人寿、大都会人寿等）的同类型产品，请知悉。');
		</#if>
		$('#linkButton').click(function(){
			location.href='${jumpUrl}';
		});
	});
</script>

<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<div class="header">
		<img src="${cdnUrl}/img/common/logo/<#if merchant?? && merchant.englishName??>${merchant.englishName}<#else>pingan</#if>-logo.png?${cdnFileVersion}">
	</div>
	<#-- 保单详情 -->
	<div class="policy-description">
		<div class="policy-table">
			<#-- 成功通知 -->
			<div class="success-notify">
				<#if insurant?? && insurant.policyNumber??>
				<h1>恭喜您</h1>
				<h2>100万平安出行险已生效</h2>
				<#else>
				<h2>抱歉，您领取的平安出行险，因地域、年龄、多次领取等限制，暂时无法领取。</h2>
				</#if>
				<#if result?? && result=="true">
				<h3>同时，恭喜您成功开通超级社保卡</h3>
				</#if>
			</div>
		</div>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/pingansy/policy-envelope.png">
		</div>
		<div class="submit-button" id="linkButton">
			查看超级社保卡
		</div>
	</div>
	<#if insurant?? && insurant.policyNumber??>
	<div class="policy-tip">
		<p>${merchant.shortName}客服后期会致电您确认保单信息等事宜；</p>
		<p>保单号稍后会以短信形式发送到您的手机，注意查收。</p>
	</div>
	</#if>
	<input type="hidden" id="gorderId" value="${gorderId?c}" />
	<div style="padding-bottom: 10rem;"></div>
</div>
<div class="policy-footer">
	<@footer activityPath="/pingan/syzhx"/>
</div>
<#if !(result ?? && result=="true")>
	<#-- 尾部按钮 -->
	<div class="footer-button-placeholder"></div>
	<div class="footer-button">
		<div class="submit-button-shadow">
			<div class="submit-button" id="participantButton">
				参与保费测算即送100元滴滴红包
			</div>
		</div>
	</div>
</#if>
<@htmlFoot />