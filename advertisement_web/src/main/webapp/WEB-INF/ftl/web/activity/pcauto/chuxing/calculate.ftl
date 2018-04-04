<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["wap/activity/pingan3in1.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["web/activity/pcauto/pcauto-chuxing-web.css"] />
</@htmlHead>
<div class="container">
	<div class="left">
		<div class="result-area">
			<img src="${cdnUrl}/img/web/activity/pcauto/chuxing/success.png"/>
			<p>已成功领取</p>
			<p>平安出行险</p>
		</div>
	</div>
	<div class="calculate-description left">
		<img src="${cdnUrl}/img/web/activity/pcauto/chuxing/calculate-info.png"/>
	</div>
	<div class="form-wrap calculate-form left">
		<form action="/activity/pingan/pcauto/chuxing/survey/calculateResult.html" method="post" id="calculateForm">
			<input type="hidden" name="gorderId" value="${gorderId?c}"/>
			<div class="form-item">
				<div class="left field-name">
					保险金额：
				</div>
				<div class="left input-area">
					<div class="field-input calculate-input">
						<input id="insuredSumSelectValue" name="insuredSumSelectValue" class="left field-input-control field-center-align calculate-input" value="请选择保额"/>
						<i class="input-triangle"></i>
						<input type="hidden" id="insuredSum" name="insuredSum" />
					</div>
					<div class="error-tip">
					
					</div>
				</div>
				<div class="left input-area">
					<div name="payWayValue" class="left radio-btn radio-btn-select" value="month">按月交费</div>
					<div name="payWayValue" class="right radio-btn" value="year">按年交费</div>
					<input type="hidden" id="payWay" name="payWay" value="month"/>
				</div>
			</div>
			<ul id="insuredSumPopTipList" class="select-list calculate-list">
				<#list calculatorList as calculator>
	            <li value="${calculator.insuredSum?c}" reference="${calculator.dailyAllowance}">${calculator.insuredSumValue}</li>
	            </#list>
			</ul>
			<div class="clearfix submit-button-area">
				<div class="submit-button cauculate-button" id="calculateButton">试算保费领取红包</div>
			</div>
		</form>
	</div>
		
	<#-- 尾部 -->
	<input type="hidden" id="activityPath" value="/pingan/pcauto/chuxing"/>
</div>
<@htmlFoot/>