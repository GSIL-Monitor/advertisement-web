<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["wap/activity/pingan3in1-wap.css"] />
</@htmlHead>

<script type="text/javascript">
	$(function() {
		<#if alert=="true">
			<#if type=="zijia">
				<#assign original="自驾险"/>
			<#elseif type=="hangban">
				<#assign original="航班延误险"/>
			<#else>
				<#assign original="出行险"/>
			</#if>
			<#if insurant.channel?? && insurant.channel=="huaxia">
			<#else>
			TipWindow.showSingle(
				'亲，您领取的平安${original}因地域、年龄等限制因素，暂时无法领取，经过您的许可，我们将赠送其他保险公司（包括泰康人寿、华夏保险、百年人寿、大都会人寿等）的同类型产品，请知悉。');
			</#if>
		</#if>
	});
</script>

<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<div class="header">
		<img src="${cdnUrl}/img/common/logo/${merchant.englishName}-logo-white.png?${cdnFileVersion}">
	</div>
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
	<#-- 保单详情 -->
	<div class="policy-description">
		<img src="${cdnUrl}/img/wap/activity/pingan/common/policy-stamp.png" class="policy-stamp">
		<div class="policy-table">
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
		</div>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/pingan/shy/policy-envelope.png">
		</div>
	</div>
	<@policyResultTip/>
	<input type="hidden" id="gorderId" value="${gorderId?c}" />
	<@footer activityPath="/pingan/shy"/>
	<#-- 尾部按钮 -->
	<div class="footer-button-placeholder"></div>
	<div class="footer-button">
		<div class="submit-button-shadow">
			<div class="submit-button" id="participantButton">
				参与<#if alert="true">问卷调查<#else>保费测算</#if>即送100元滴滴红包
			</div>
		</div>
	</div>
</div>
<@htmlFoot />