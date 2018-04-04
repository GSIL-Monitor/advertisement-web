<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算结果" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["wap/activity/pingan3in1.js"] />
	<@cssFile file=["common/activity/shanyin/pinganwq.css"] />
</@htmlHead>
<div class="container calculate-background">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<div class="header">
		<img src="${cdnUrl}/img/common/logo/pingan-logo.png?${cdnFileVersion}">
	</div>
	<#-- 测保结果 -->
	<div class="policy-description">
		<div class="calculate-result">
			<div class="calculate-table">
				<div class="calculate-title">
					保费测算结果
				</div>
				<div class="calculate-head">
					<#if payWay=="month">
						<p>您首期需支付三个月保费：<span class="money">￥${calculator.threeMonthPremium}元</span></p>
						<p>以后每月支付：<span class="money">￥${calculator.monthPremium}元</span></p>
						<p>折合每天仅：<span class="money">￥${calculator.dayForMonthPremium}元</span></p>
					<#else>
						<p>您每年需支付保费：<span class="money">￥${calculator.yearPremium}元</span></p>
						<p>折合每天仅：<span class="money">￥${calculator.dayForYearPremium}元</span></p>
					</#if>
				</div>
				<ul class="calculate-content">
					<li>
						<span class="title">您可获得以下保障：</span>
					</li>
					<li>
						意外身故保障：<span class="money">${calculator.insuredSum}元</span>
					</li>
					<li>
						意外伤残保障：<span class="money">${calculator.insuredSum}元</span>＊评定伤残等级对应比例
					</li>
					<li>
						自驾车意外身故特别保障：<span class="money">${calculator.insuredSum}元</span>
					</li>
					<li>
						自驾车意外伤残特别保障：<span class="money">${calculator.insuredSum}元</span>＊评定伤残等级对应比例
					</li>
					<li>
						公共交通意外身故特别保障：<span class="money">${calculator.insuredSum}元</span>
					</li>
					<li>
						公共交通意外伤残特别保障：<span class="money">${calculator.insuredSum}元</span>＊评定伤残等级对应比例
					</li>
					<li>
						意外伤害住院日额津贴：<span class="money">${calculator.dailyAllowance}元/天</span>
					</li>
					<li>
						身故保障：所交保费的<span class="money">112%</span>
					</li>
					<li>
						满期生存保险金：所交保费的<span class="money">112%</span>
					</li>
				</ul>
			</div>
		</div>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/pingansy/policy-envelope.png">
		</div>
	</div>
	
	<#-- 测保说明 -->
	<div class="calculator-description">
		<div class="calculator-description-title">
			<p>加入《平安鸿运随行意外保障计划》</p>
			<p>为自己，更为家人</p>
		</div>
		<ul>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingansy/cal-icon-1.png">
				<span>
					<p>全方位意外保障</p>
					<p>意外住院、伤残、身故都能保</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingansy/cal-icon-2.png" class="li-right">
				<span class="span-right">
					<p>0-55周岁都可投保</p>
					<p>无门槛，为自己或家人均可投保</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingansy/cal-icon-3.png">
				<span>
					<p>最高200万赔付</p>
					<p>保障额度高，解决实际问题</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingansy/cal-icon-4.png" class="li-right">
				<span class="span-right">
					<p>住院津贴享300元/日</p>
					<p>意外伤害住院均可保障</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingansy/cal-icon-5.png">
				<span>
					<p>保费返还112%</p>
					<p>满期生存返还112%，还能赚钱</p>
				</span>
			</li>
			<li>
				<img src="${cdnUrl}/img/wap/activity/pingansy/cal-icon-6.png" class="li-right">
				<span class="span-right">
					<p>保障周期20年</p>
					<p>保障周期长，让您从容不迫</p>
				</span>
			</li>
		</ul>
	</div>

	<#-- 尾部 -->
	<@footer activityPath="/pingan/sywq"/>

	<div class="footer-button-placeholder"></div>
</div>

<#-- 尾部按钮 -->
<div class="footer-button">
	<div class="submit-button-shadow">
		<div class="submit-button" id="couponButton">
			立即领取滴滴红包
		</div>
	</div>
</div>
<@htmlFoot />