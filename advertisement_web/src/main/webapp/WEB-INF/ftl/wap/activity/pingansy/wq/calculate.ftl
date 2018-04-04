<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["wap/activity/pingan3in1.js"] />
	<@cssFile file=["common/activity/shanyin/pinganwq.css"] />
</@htmlHead>
<div class="container calculate-background">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<div class="header">
		<img src="${cdnUrl}/img/common/logo/pingan-logo.png?${cdnFileVersion}">
	</div>
	<#-- Banner信息 -->
	<div class="banner">
		<img src="${cdnUrl}/img/wap/activity/pingansy/calculate-banner.png?${cdnFileVersion}">
	</div>
	<div class="calculate-container">
		<#-- 已有多少人领取 -->
		<div class="form-title-area">
			<span class="form-title-content">
				<p>参与保费试算，即送最高<span class="money">100元</span>滴滴红包</p>
			</span>
		</div>
		<#-- <div class="calculate-detail">
			<h3>《平安鸿运随行意外保障计划》</h3>
			<p>意外保障全面，保<span class="money">20年</span>保<span class="money">200万</span></p>
			<p>交通意外<span class="money">双倍赔付</span>，保障彻底</p>
			<p>保费返还<span class="money">112%</span>，等于投资白赚</p>
		</div> -->
		<#-- 填写赠险表单信息区域 -->
		<div class="form-area">
			<form action="/m/activity/pingan/sywq/survey/calculateResult.html" method="post" id="calculateForm">
				<#-- 字段 -->
				<input type="hidden" name="gorderId" value="${gorderId?c}"/>
				<input type="hidden" id="type" value="chuxing"/>
				<div class="form-item">
					<div class="left field-name">
						保险金额：
					</div>
					<div class="right input-area">
						<div class="field-input">
							<i class="input-triangle"></i>
							<div id="insuredSumValue" name="insuredSumValue" class="left field-input-control field-select field-center-align">请选择保额</div>
							<input type="hidden" id="insuredSum" name="insuredSum" />
						</div>
						<div class="error-tip">
						
						</div>
					</div>
				</div>
				<div id="insuredSumPopTipList" class="poptip">
					<ul>
					<div class="poptip-select-head" >请选择保额<span class="tip-close right"><img src="${cdnUrl}/img/wap/common/tip-close.png"></span></div>
						<#list calculatorList as calculator>
							<li value="${calculator.insuredSum?c}" reference="${calculator.dailyAllowance}">${calculator.insuredSumValue}</li>
						</#list>
					</ul>
				</div>

				<div class="form-item">
					<div class="left field-name">
						住院津贴：
					</div>
					<div class="right input-area">
						<div id="dailyAllowanceValue" name="dailyAllowanceValue" class="field-allowance" >150元/天</div>
						<input type="hidden" id="dailyAllowance" name="dailyAllowance" value="150" readonly="readonly" />
					</div>
				</div>

				<div class="form-item">
					<div class="left field-name">
						交费方式：
					</div>
					<div class="right input-area">
						<div name="payWayValue" class="left radio-btn radio-btn-select" value="year">按年交费</div>
						<div name="payWayValue" class="right radio-btn" value="month">按月交费</div>
						<input type="hidden" id="payWay" name="payWay" value="year"/>
					</div>
				</div>

				<#-- 提交按钮 -->
				<div class="submit-button-area">
					<div class="submit-button" id="calculateButton">
						测算保费
					</div>
				</div>
			</form>
		</div>
	</div>
	<#-- 测保说明 -->
	<div class="calculator-description">
		<div class="calculator-description-title">
			<p>加入《鸿运随行返还型意外保障》</p>
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
</div>
<@htmlFoot />