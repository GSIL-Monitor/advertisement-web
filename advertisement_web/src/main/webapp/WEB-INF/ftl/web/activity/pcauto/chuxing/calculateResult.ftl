<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算结果" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["wap/activity/pingan3in1.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["web/activity/pcauto/pcauto-chuxing-web.css"] />
</@htmlHead>
<div class="container">
	<div class="logo left">
		<img src="${cdnUrl}/img/web/activity/pcauto/chuxing/pingan-logo.png"/>
	</div>
	<div class="calcalate-result left">
		<div class="result-title">
			<img src="${cdnUrl}/img/web/activity/pcauto/chuxing/calculate-result.png"/>
		</div>
		<div class="result-content">
			<#if payWay=="month">
				<p>您首期需支付三个月保费：<span class="money">￥${calculator.threeMonthPremium}元</span>，以后每月支付：<span class="money">￥${calculator.monthPremium}元</span>，折合每天仅：<span class="money">￥${calculator.dayForMonthPremium}元</span></p>
			<#else>
				<p>您每年需支付保费：<span class="money">￥${calculator.yearPremium}元</span>，折合每天仅：<span class="money">￥${calculator.dayForYearPremium}元</span></p>
			</#if>
		</div>
		<div class="result-tip">
			<img src="${cdnUrl}/img/web/activity/pcauto/chuxing/success.png"/>
			<p>滴滴红包已发送到您的手机，请注意查收。</p>
		</div>
	</div>
	<#-- 尾部 -->
	<input type="hidden" id="activityPath" value="/pingan/pcauto/chuxing"/>
</div>
<@htmlFoot/>