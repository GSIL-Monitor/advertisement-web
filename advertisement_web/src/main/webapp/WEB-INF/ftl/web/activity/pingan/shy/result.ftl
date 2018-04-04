<#include "../../common/core.ftl" />
<@htmlHead title="" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<@jsFile file=["wap/activity/pingan3in1.js"] />
	<@cssFile file=["common/activity/pingan3in1.css", "web/activity/pingan3in1-web.css"] />
</@htmlHead>

<script type="text/javascript">
	$(function() {
		<#if alert=="true">
			<#if type=="zijia">
				<#assign original="自驾险"/>
			<#elseif type=="hangban">
				<#assign original="航班延误险"/>
			<#else>
				<#assign original="出行"/>
			</#if>
			TipWindow.showSingle(
				'亲，您领取的平安${original}因地域、年龄等限制因素，暂时无法领取，经过您的许可，我们将赠送其他保险公司（包括泰康人寿、大都会人寿等）的同类型产品，请知悉。');
		</#if>
	});
</script>

<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<div class="header">
		<div class="header-nav">
			<img src="${cdnUrl}/img/wap/activity/pingan/shy/pingan-logo.png">
		</div>
	</div>
	<#-- 成功通知 -->
	<div class="wrapper form-box">
		<div class="success-notify">
			<h1>恭喜您</h1>
			<h2>已成功领取${merchant.shortName}保险</h2>
		</div>
		<div class="form-title-split">
			<div class="form-title-left-icon"></div>
			<div class="form-title-line"></div>
			<div class="form-title-right-icon"></div>
		</div>
		<#-- 保单详情 -->
		<div class="policy-description">
			<div class="policy-table">
				<table border="1" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th colspan="2">${goods.fullName}</th>
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
					<tr>
						<td>飞机意外身故/残疾</td>
						<td>100万元</td>
					</tr>
					<tr>
						<td>火车、轮船、营运汽车意外身故/残疾</td>
						<td>10万元</td>
					</tr>
					<tr>
						<td>交通意外医疗</td>
						<td>2万元</td>
					</tr>
					<tr>
						<td>行李物品和旅行证件损失</td>
						<td>350元</td>
					</tr>
				</table>
			</div>
			<div class="policy-envelope">
				<img src="${cdnUrl}/img/wap/activity/pingan/shy/policy-envelope.png">
				<div class="policy-tip">
					<p>${merchant.shortName}客服后期会致电您确认保单信息等事宜；</p>
					<p>保单号稍后会以短信形式发送到您的手机，注意查收。</p>
				</div>
			</div>
		</div>
		<input type="hidden" id="gorderId" value="${gorderId?c}" />
		<#-- 尾部按钮 -->
		<div class="submit-button-shadow" style="margin-bottom: 20px;">
			<div class="submit-button" id="participantButton">
				参与<#if alert="true">问卷调查<#else>保费测算</#if>即送100元滴滴红包
			</div>
		</div>
	</div>
</div>
<@htmlFoot />