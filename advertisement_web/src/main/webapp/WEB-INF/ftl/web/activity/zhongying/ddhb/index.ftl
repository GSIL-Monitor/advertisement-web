<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取20万元中英交通意外保障" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/zhongying/ddhb-web.css"] />
</@htmlHead>
<#-- 结果弹窗 -->
<#macro resultTitleArea>
	<p>已成功领取<span class="money">中英交通意外险</span>，中英客服后续将致电以确认免费保险生效事宜。</p>
</#macro>
<@resultSurveyPopWindow />
<#if emailPositionConfig?? && emailPositionConfig=="popup">
<@emailPopWindow />
</#if>
<#-- 身份证弹窗 -->
<@identityCardPopWindow />

<#-- 保险详情弹窗 -->
<div id="insuranceDetailTip" class="tip-area insurance-detail-tip hide">
	<div class="tip-window center">
		<div class="favorite-tip">
			<img id="insuranceDetailImg" src="${cdnUrl}/img/wap/activity/zhongying/xjhb/insurance-detail.png">
		</div>
	</div>
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

<@commonBanner path="zhongying/ddhb" />
<div class="container">
	<div class="wrapper">
		<div class="form-wrap clearfix">
			<div class="survey-area left">
				<div class="description-title">
					<img src="${cdnUrl}/img/web/activity/zhongying/ddhb/survey-title.png">
				</div>
				<@surveyFormZhongYing/>
			</div>
			<div class="form-area right">
				<div class="description-title">
					<span>
						<img src="${cdnUrl}/img/web/activity/zhongying/ddhb/form-title.png">
					</span>
				</div>
				<@commonZengXianForm/>
				<div class="submit-button-area">
					<div class="submit-button" id="submitButton">立即领取</div>
				</div>		
				<#-- 条款 -->
				<div class="protocal">
					<span>
						<img src="${cdnUrl}/img/wap/activity/pingan/xxjhb/check.png"/>
						我同意<a href="javascript:;" onclick="showActivityRule();">《活动规则》</a>及<a href="javascript:;" onclick="showProtocol();">《用户协议》</a>并领取<a href="javascript:;" id="insuranceDetailButton">免费保障</a>
					</span>
					<@commonProtocolContent merchantName="中英" merchantDomain="www.aviva-cofco.com.cn"/>
					<div id="activityRulePopWindow" class="hide">
						<@activityRule defaultInsurance="中英人寿" otherInsurance="平安人寿、泰康人寿、中美大都会、百年人寿、华夏保险"/>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="wrapper">
	<#-- 尾部 -->
	<@footerZhongYing activityPath="${activityPath}"/>
</div>
<@htmlFoot/>