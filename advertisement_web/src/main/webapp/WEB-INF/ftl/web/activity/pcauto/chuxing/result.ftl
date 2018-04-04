<#include "../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["wap/activity/pingan3in1.js"] />
	<@cssFile file=["web/activity/pinganddhb-web.css"] />
</@htmlHead>

<script type="text/javascript">
	$(function() {
		<#if alert=="true">
			TipWindow.showSingle(
				'亲，您领取的平安出行意外险因地域、年龄等限制因素，暂时无法领取，经过您的许可，我们将赠送其他保险公司（包括泰康人寿、华夏保险、百年人寿、大都会人寿等）的同类型产品，请知悉。');
		</#if>
	});
</script>

<div class="container result-container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<div class="header">
		<div class="header-wrap">
			<img src="${cdnUrl}/img/common/logo/${merchant.englishName}-logo.png?${cdnFileVersion}">
		</div>
	</div>
	<div class="result-wrap">
		<#-- 保单详情 -->
		<div class="policy-description">
			<div class="policy-stamp">
				<img src="${cdnUrl}/img/wap/activity/pingan/common/policy-stamp.png">
			</div>
			<div class="policy-table">
				<#-- 成功通知 -->
				<div class="success-notify">
					<#if insurant.policyNumber??>
					<h1>恭喜您</h1>
					<h2>已成功领取${merchant.shortName}保险</h2>
					<#else>
					<h1>信息已成功提交！</h1>
					<h4>${merchant.name}客服后期会致电您确认保单信息等具体事宜</h4>
					</#if>
				</div>
				<table border="1" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th colspan="2">${insurance.fullName}</th>
						</tr>
					</thead>
					<tr>
						<td>
							<p class="policy-title">投保人信息</p>
						</td>
						<td>
							<p class="policy-title">保险信息</p>
						</td>
					</tr>
					<tr>
						<td>
							
							<p>投保人：${insurant.name}</p>
							<p>联系方式：${insurant.mobile}</p>
							<p>生日：${insurant.birthdayValue}</p>
							<p>身份信息：${insurant.identityCard}</p>
						</td>
						<td>
							<p>保险公司：${merchant.name}</p>
							<p>保险产品：${insurance.name}</p>
							<p>保单号：<#if insurant.policyNumber??>${insurant.policyNumber}<#else>确认信息后1-2个工作日提供</#if></p>
							<p>保险有效期：即日起至${insurant.endTimeValue}</p>
						</td>
					</tr>
					<tr>
						<td class="policy-title">保险范围</td>
						<td class="policy-title">保险金额</td>
					</tr>
					<#if insurance.detailList??>
						<#list insurance.detailList as detail>
						<tr>
							<td>${detail.cover}</td>
							<td>${detail.amount}</td>
						</tr>
						</#list>
					</#if>
				</table>
				<#-- 尾部按钮 -->
				<div class="submit-button-area">
					<div class="submit-button" id="giftButton">
						领取退货无忧卡（等值25元现金）
					</div>
				</div>
			</div>
			<div class="policy-envelope">
				<img src="${cdnUrl}/img/wap/activity/pingan/shy/policy-envelope.png">
			</div>
			<div class="policy-tip">
				<p>${merchant.shortName}客服后期会致电您确认保单信息等事宜；</p>
				<p>保单号稍后会以短信形式发送到您的手机，注意查收。</p>
			</div>
		</div>
		<input type="hidden" id="gorderId" value="${gorderId?c}" />
	</div>
	<@footer activityPath="/pingan/xfdc"/>
</div>
<@htmlFoot />